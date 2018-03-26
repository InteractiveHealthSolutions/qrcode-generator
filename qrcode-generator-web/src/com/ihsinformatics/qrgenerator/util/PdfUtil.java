/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
package com.ihsinformatics.qrgenerator.util;

import java.awt.PageAttributes.OrientationRequestedType;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import com.ihsinformatics.qrgenerator.Constant;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author haris.asif@ihsinformatics.com, owais.hussain@ihsinforamtics.com
 *
 */
public class PdfUtil {

	/**
	 * This inner class handles Header and Footer
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 *
	 */
	public class HeaderFooterEvent extends PdfPageEventHelper {

		int pageNumber;

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			pageNumber++;
		}

		/**
		 * Adds the header and the footer.
		 */
		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			Phrase header = new Phrase("Date:" + new Date().toString());
			Phrase footer = new Phrase(String.format("Page %d", pageNumber));
			Rectangle rect = writer.getBoxSize("art");
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, header, 
					rect.getRight(), rect.getTop(), 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer,
					(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 12, 0);
		}
	}

	/**
	 * Generate PDF and return as Stream
	 * 
	 * @param lines
	 * @param pageWidth
	 * @param pageHeight
	 * @param orientation
	 * @return
	 * @throws DocumentException
	 */
	public ByteArrayOutputStream generatePdf(String[] lines, OrientationRequestedType orientation, int columnLimit,
			int imageCopies, int boxWidth) throws DocumentException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Document document = new Document();
		document.addAuthor(Constant.FILE_AUTHOR);
		PdfPTable table;
		int maxColumns = 0;
		PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
		// Set table parameters according to orientation
		if (orientation == OrientationRequestedType.PORTRAIT) {
			maxColumns = 6;
			table = new PdfPTable(maxColumns);
			document.setPageSize(PageSize.A4);
			table.setTotalWidth(PageSize.A4.getWidth());
			float[] margins = getMargins(document.getPageSize(), 20);
			document.setMargins(margins[0], margins[1], margins[2], margins[3]);
			// Attach header and footer
			pdfWriter.setBoxSize("art", new Rectangle(25, 33, 580, 812));
			pdfWriter.setPageEvent(new HeaderFooterEvent());
		} else {
			maxColumns = 8;
			table = new PdfPTable(maxColumns);
			document.setPageSize(PageSize.A4.rotate());
			table.setTotalWidth(PageSize.A4.rotate().getWidth());
			float[] margins = getMargins(document.getPageSize(), 20);
			document.setMargins(margins[0], margins[1], margins[2], margins[3]);
			// Attach header and footer
			pdfWriter.setBoxSize("art", new Rectangle(25, 33, 580, 812).rotate());
			pdfWriter.setPageEvent(new HeaderFooterEvent());
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		int count = 0; // Copy counter
		// Create QR code images for each text (for number of copies defined)
		for (String text : lines) {
			Image itextImage = QrCodeImageUtil.convertToImage(text, boxWidth, boxWidth, getTextImageLength(text));
			int currentColumnCount = 0;
			for (int i = 0; i < imageCopies; i++) {
				currentColumnCount++;
				PdfPCell cell = new PdfPCell(itextImage);
				cell.setBorder(Rectangle.NO_BORDER);
				count++;
				table.addCell(cell);
			}
			if (currentColumnCount == columnLimit) {
				for (int i = columnLimit; i < maxColumns; i++) {
					PdfPCell cell = new PdfPCell(new Phrase());
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
				}
				currentColumnCount = 0;
			}
		}
		for (int i = 0; i < maxColumns /* TODO: this was fixed value of 6 */; i++) {
			if (count % maxColumns != 0) {
				PdfPCell cell = new PdfPCell(new Phrase());
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				count++;
			}
		}
		document.open();
		document.newPage();
		document.add(table);
		document.close();
		return outputStream;
	}

	/**
	 * Get Margins (Left, Right, Top, Bottom) from the given page size of
	 * respective percentage
	 * 
	 * @param pageSize
	 * @param percentage
	 * @return
	 */
	private float[] getMargins(Rectangle pageSize, int percentage) {
		float[] margins = { 0, 0, 0, 0 };
		if (pageSize == PageSize.A4) {
			margins[0] = 5;
			margins[1] = -145;
			margins[2] = 20;
			margins[3] = 2;
		} else if (pageSize == PageSize.A4.rotate()) {
			margins[0] = 5;
			margins[1] = -200;
			margins[2] = 29;
			margins[3] = 20;
		}
		return margins;
	}

	/**
	 * Returns appropriate length of image to set according to length of string
	 * 
	 * @param string
	 * @return
	 */
	private int getTextImageLength(String string) {
		// if (string.length() > 0 && string.length() <= 5) {
		// length = 58L;
		// } else if (string.length() >= 6 && string.length() <= 9) {
		// length = 38L;
		// } else if (string.length() >= 10 && string.length() <= 11) {
		// length = 36L;
		// } else if (string.length() >= 12 && string.length() <= 14) {
		// length = 27L;
		// } else if (string.length() >= 15 && string.length() <= 17) {
		// length = 22L;
		// } else {
		// length = 15L;
		// }
		// The closest formula I could devise to the hit-trial method
		double exp = Math.exp(5.65D);
		Double length = (exp / string.length());
		return length.intValue();
	}

}