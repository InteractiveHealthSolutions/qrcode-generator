/**
 * Copyright(C) ${year} Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: ${user}
 */
package com.ihsinformatics.qrcodegenerator;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author Muhammad AHMED
 */
public class GeneratorUI extends JFrame implements ActionListener, ItemListener {

    // declaration of variables
    private static final long serialVersionUID = 4652592648303169985L;
    private static final int size = 200;
    private static int columnLimit = 4;
    private static int pageLimit = 4;
    private static final int defaultRange = 19999;
    private static final String fileType = "png";
    private static String directory = "c:\\QRCodes";

    // declaration of components
    private UtilDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JFileChooser chooser;
    private JLabel label1;
    private JLabel label2;
    private JTextField locationIdTextField;
    private JCheckBox dateJCheckBox;
    private JLabel label3;
    private JLabel label4;
    private JComboBox dateFormatJComboBox;
    private JTextField dateTextField;
    private JLabel label5;
    private JSpinner serialFromSpinner;
    private JLabel label6;
    private JLabel label7;
    private JSpinner serialToSpinner;
    private JComboBox columnLimitJComboBox;
    private JComboBox rowLimitJComboBox;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JButton saveToButton;
    private JButton generatorButton;
    private JLabel label11;
    private JComboBox repeatLimitJComboBox;
    private JLabel label12;
    private JLabel label13;

    public GeneratorUI() {
	initComponents();
	File file = new File(directory);

	if (!file.exists() || !file.isDirectory()) {
	    file.mkdirs();
	}
	directory += "\\";
	dateTextField
		.setText(new SimpleDateFormat("yyMMdd").format(new Date()));

	serialFromSpinner.setValue(1);
	serialToSpinner.setValue(defaultRange);
    }

    private void initComponents() {
	Properties p = new Properties();
	p.put("text.today", "Today");
	p.put("text.month", "Month");
	p.put("text.year", "Year");

	model = new UtilDateModel();
	// datePanel = new JDatePanelImpl(model);
	// datePicker = new JDatePickerUtil(datePanel);
	datePanel = new JDatePanelImpl(model, p);
	datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	// datePicker = new JDatePickerUtil();

	label1 = new JLabel();
	label2 = new JLabel();
	locationIdTextField = new JTextField();
	dateJCheckBox = new JCheckBox();
	label3 = new JLabel();
	label4 = new JLabel();
	dateFormatJComboBox = new JComboBox();
	dateTextField = new JTextField();
	label5 = new JLabel();
	serialFromSpinner = new JSpinner();
	label6 = new JLabel();
	label7 = new JLabel();
	serialToSpinner = new JSpinner();
	columnLimitJComboBox = new JComboBox();
	rowLimitJComboBox = new JComboBox();
	label8 = new JLabel();
	label9 = new JLabel();
	label10 = new JLabel();
	saveToButton = new JButton();
	generatorButton = new JButton();
	label11 = new JLabel();
	repeatLimitJComboBox = new JComboBox();
	label12 = new JLabel();
	label13 = new JLabel();

	// ======== this ========
	setIconImage(new ImageIcon(getClass().getResource("onlyihs.png"))
		.getImage());
	setTitle("QR Code Generator");
	setResizable(false);
	Container contentPane = getContentPane();

	// ---- label1 ----
	label1.setText("QR CODE GENERATOR");
	label1.setFont(label1.getFont().deriveFont(
		label1.getFont().getSize() + 16f));

	// ---- label2 ----
	label2.setText("Prefix  :");
	label2.setFont(label2.getFont().deriveFont(
		label2.getFont().getStyle() | Font.BOLD));

	// ---- locationIdTextField ----
	locationIdTextField.setText("01");
	locationIdTextField.setToolTipText("Prefix like 01");
	locationIdTextField.setFont(locationIdTextField.getFont().deriveFont(
		locationIdTextField.getFont().getStyle() & ~Font.BOLD));

	// ---- dateJCheckBox ----
	dateJCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
	dateJCheckBox.setFont(dateJCheckBox.getFont().deriveFont(
		dateJCheckBox.getFont().getStyle() & ~Font.BOLD));
	dateJCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
	dateJCheckBox.setIconTextGap(1);
	dateJCheckBox.setContentAreaFilled(false);

	// ---- label3 ----
	label3.setText("Date Format     :");
	label3.setFont(label3.getFont().deriveFont(
		label3.getFont().getStyle() | Font.BOLD));

	// ---- label4 ----
	label4.setText("Date    :");
	label4.setFont(label4.getFont().deriveFont(
		label4.getFont().getStyle() | Font.BOLD));

	// ---- dateFormatJComboBox ----
	dateFormatJComboBox.setFont(dateFormatJComboBox.getFont().deriveFont(
		dateFormatJComboBox.getFont().getStyle() & ~Font.BOLD));
	dateFormatJComboBox.setModel(new DefaultComboBoxModel(new String[] {
		"yyMMdd", "yyMM", "yy" }));

	// ---- dateTextField ----
	dateTextField.setFont(dateTextField.getFont().deriveFont(
		dateTextField.getFont().getStyle() & ~Font.BOLD));

	// ---- label5 ----
	label5.setText("Serial Number Range      :");
	label5.setFont(label5.getFont().deriveFont(
		label5.getFont().getStyle() | Font.BOLD));

	// ---- serialFromSpinner ----
	serialFromSpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
	serialFromSpinner.setFont(serialFromSpinner.getFont().deriveFont(
		serialFromSpinner.getFont().getStyle() & ~Font.BOLD));

	// ---- label6 ----
	label6.setText("From");
	label6.setFont(label6.getFont().deriveFont(
		label6.getFont().getStyle() | Font.BOLD));

	// ---- label7 ----
	label7.setText("To");
	label7.setFont(label7.getFont().deriveFont(
		label7.getFont().getStyle() | Font.BOLD));

	// ---- serialToSpinner ----
	serialToSpinner.setModel(new SpinnerNumberModel(19999, null, 19999, 1));
	serialToSpinner.setFont(serialToSpinner.getFont().deriveFont(
		serialToSpinner.getFont().getStyle() & ~Font.BOLD));

	// ---- columnLimitJComboBox ----
	columnLimitJComboBox.setModel(new DefaultComboBoxModel(
		new String[] { "1", "2", "3", "4" }));
	columnLimitJComboBox.setFont(columnLimitJComboBox.getFont().deriveFont(
		columnLimitJComboBox.getFont().getStyle() & ~Font.BOLD));
	columnLimitJComboBox.setSelectedIndex(3);

	// ---- rowLimitJComboBox ----
	rowLimitJComboBox.setModel(new DefaultComboBoxModel(
		new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
	rowLimitJComboBox.setFont(rowLimitJComboBox.getFont().deriveFont(
		rowLimitJComboBox.getFont().getStyle() & ~Font.BOLD));
	rowLimitJComboBox.setSelectedIndex(7);

	// ---- label8 ----
	label8.setText("row(s)");
	label8.setFont(label8.getFont().deriveFont(
		label8.getFont().getStyle() | Font.BOLD));

	// ---- label9 ----
	label9.setText("column(s)");
	label9.setFont(label9.getFont().deriveFont(
		label9.getFont().getStyle() | Font.BOLD));

	// ---- label10 ----
	label10.setText("Save To  :" + directory);
	label10.setFont(label10.getFont().deriveFont(
		label10.getFont().getStyle() | Font.BOLD));

	// ---- saveToButton ----
	saveToButton.setText("Save To");
	saveToButton.setFont(saveToButton.getFont().deriveFont(
		saveToButton.getFont().getStyle() | Font.BOLD));

	// ---- generatorButton ----
	generatorButton.setText("Generate");
	generatorButton.setFont(generatorButton.getFont().deriveFont(
		generatorButton.getFont().getStyle() | Font.BOLD));

	// ---- label11 ----
	label11.setText("Copies per Code   :");
	label11.setFont(label11.getFont().deriveFont(
		label11.getFont().getStyle() | Font.BOLD));

	// ---- repeatLimitJComboBox ----
	repeatLimitJComboBox.setModel(new DefaultComboBoxModel(
		new String[] { "1", "2", "4" }));

	// ---- label12 ----
	label12.setText("Columns/Rows per Page   :");
	label12.setFont(label12.getFont().deriveFont(
		label12.getFont().getStyle() | Font.BOLD));

	// ---- label13 ----
	label13.setText("Date Appender  :");
	label13.setFont(label13.getFont().deriveFont(
		label13.getFont().getStyle() | Font.BOLD));

	datePicker.setEnabled(false);
	dateTextField.setEnabled(false);
	dateFormatJComboBox.setEnabled(false);
	dateFormatJComboBox.addItemListener(this);
	dateJCheckBox.addItemListener(this);
	generatorButton.addActionListener(this);

	GroupLayout contentPaneLayout = new GroupLayout(contentPane);
	contentPane.setLayout(contentPaneLayout);
	contentPaneLayout
		.setHorizontalGroup(contentPaneLayout
			.createParallelGroup()
			.addGroup(
				contentPaneLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						contentPaneLayout
							.createParallelGroup()
							.addGroup(
								GroupLayout.Alignment.TRAILING,
								contentPaneLayout
									.createSequentialGroup()
									.addGroup(
										contentPaneLayout
											.createParallelGroup()
											.addComponent(
												label5,
												GroupLayout.PREFERRED_SIZE,
												180,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												label11,
												GroupLayout.PREFERRED_SIZE,
												180,
												GroupLayout.PREFERRED_SIZE))
									.addGap(44,
										44,
										44)
									.addComponent(
										repeatLimitJComboBox))
							.addGroup(
								contentPaneLayout
									.createSequentialGroup()
									.addGroup(
										contentPaneLayout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING,
												false)
											.addComponent(
												label12,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												label2,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												label4,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												label3,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												label13,
												GroupLayout.Alignment.LEADING,
												GroupLayout.PREFERRED_SIZE,
												106,
												GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										contentPaneLayout
											.createParallelGroup()
											.addGroup(
												contentPaneLayout
													.createSequentialGroup()
													.addComponent(
														label9)
													.addGap(15,
														15,
														15)
													.addComponent(
														columnLimitJComboBox,
														GroupLayout.PREFERRED_SIZE,
														42,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(
														label8)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
													.addComponent(
														rowLimitJComboBox,
														GroupLayout.PREFERRED_SIZE,
														46,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												contentPaneLayout
													.createSequentialGroup()
													.addComponent(
														label6)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
													.addComponent(
														serialFromSpinner,
														GroupLayout.PREFERRED_SIZE,
														42,
														GroupLayout.PREFERRED_SIZE)
													.addGap(18,
														18,
														18)
													.addComponent(
														label7)
													.addGap(18,
														18,
														18)
													.addComponent(
														serialToSpinner,
														GroupLayout.PREFERRED_SIZE,
														54,
														GroupLayout.PREFERRED_SIZE))
											.addComponent(
												locationIdTextField)
											.addComponent(
												datePicker,
												GroupLayout.Alignment.TRAILING)
											.addComponent(
												dateFormatJComboBox,
												GroupLayout.Alignment.TRAILING)
											.addGroup(
												contentPaneLayout
													.createSequentialGroup()
													.addComponent(
														dateJCheckBox)
													.addGap(0,
														0,
														Short.MAX_VALUE))))
							.addGroup(
								GroupLayout.Alignment.TRAILING,
								contentPaneLayout
									.createSequentialGroup()
									.addComponent(
										label10,
										GroupLayout.PREFERRED_SIZE,
										213,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
									.addGroup(
										contentPaneLayout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
											.addComponent(
												generatorButton,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												saveToButton,
												GroupLayout.PREFERRED_SIZE,
												105,
												GroupLayout.PREFERRED_SIZE))
									.addGap(51,
										51,
										51)))
					.addContainerGap())
			.addGroup(
				contentPaneLayout
					.createSequentialGroup()
					.addGap(57, 57, 57)
					.addComponent(label1,
						GroupLayout.PREFERRED_SIZE,
						350, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));
	contentPaneLayout
		.setVerticalGroup(contentPaneLayout
			.createParallelGroup()
			.addGroup(
				contentPaneLayout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1,
						GroupLayout.PREFERRED_SIZE, 34,
						GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								label2,
								GroupLayout.PREFERRED_SIZE,
								26,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								locationIdTextField,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup()
							.addComponent(
								dateJCheckBox)
							.addComponent(
								label13,
								GroupLayout.PREFERRED_SIZE,
								21,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup()
							.addComponent(
								label3,
								GroupLayout.PREFERRED_SIZE,
								24,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								dateFormatJComboBox,
								GroupLayout.PREFERRED_SIZE,
								26,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								label4,
								GroupLayout.PREFERRED_SIZE,
								19,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								datePicker,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								label5,
								GroupLayout.PREFERRED_SIZE,
								26,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(label6)
							.addComponent(
								serialToSpinner,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(label7)
							.addComponent(
								serialFromSpinner,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING,
								false)
							.addGroup(
								contentPaneLayout
									.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
									.addComponent(
										label8)
									.addComponent(
										rowLimitJComboBox,
										GroupLayout.PREFERRED_SIZE,
										24,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										label12,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										label9))
							.addComponent(
								columnLimitJComboBox,
								GroupLayout.PREFERRED_SIZE,
								24,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING,
								false)
							.addComponent(
								label11,
								GroupLayout.PREFERRED_SIZE,
								21,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								repeatLimitJComboBox,
								GroupLayout.DEFAULT_SIZE,
								26,
								Short.MAX_VALUE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						contentPaneLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING,
								false)
							.addComponent(
								label10,
								GroupLayout.PREFERRED_SIZE,
								33,
								GroupLayout.PREFERRED_SIZE)
							.addGroup(
								contentPaneLayout
									.createSequentialGroup()
									.addGap(5,
										5,
										5)
									.addComponent(
										saveToButton,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(generatorButton,
						GroupLayout.PREFERRED_SIZE, 31,
						GroupLayout.PREFERRED_SIZE)
					.addGap(17, 17, 17)));
	pack();
	setLocationRelativeTo(getOwner());

    }// end of init method

    public void generateCodes() {
	String locationText = locationIdTextField.getText();
	// String dateFrom = dateTextField.getText ();
	// SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
	// Date date = new Date ();
	// rowLimit=Integer.parseInt(rowLimitJComboBox.getSelectedItem().toString());
	int columnDiffer = Integer.parseInt(repeatLimitJComboBox
		.getSelectedItem().toString());
	columnLimit = Integer.parseInt(columnLimitJComboBox.getSelectedItem()
		.toString());
	pageLimit = Integer.parseInt(rowLimitJComboBox.getSelectedItem()
		.toString());
	StringBuilder error = new StringBuilder();
	// Validate data
	if (locationText.equals("")
		|| !locationText.matches("[0-9a-zA-Z]{1,4}")) {
	    error.append("Location IDs are empty or invalid." + "\n");
	}
	if (dateJCheckBox.isSelected()) {
	    if (datePicker.getModel().getValue() == null
		    || datePicker.getModel().getValue().toString().equals("")) {
		error.append("please Select the Date" + "\n");
	    }
	}
	/*
	 * if (dateFrom.equals ("")) { error.append ("Date is empty." + "\n"); }
	 * try { date = format.parse (dateFrom); } catch (ParseException e) {
	 * error.append
	 * ("Date is invalid. Make sure the date is in the format: YYYY-MM-DD" +
	 * "\n"); }
	 */
	int from = Integer.parseInt(serialFromSpinner.getValue().toString());
	int to = Integer.parseInt(serialToSpinner.getValue().toString());
	if (from > to) {
	    error.append("Serial number range is not in order. Start value is greater than End value.");
	}
	if (to > defaultRange) {
	    error.append("Serial number range cannot be greater than "
		    + defaultRange);
	}
	if (error.length() > 0) {
	    JOptionPane.showMessageDialog(this, error.toString());
	    return;
	}
	// String[] locations = locationText.split (",");
	// String dateString = new SimpleDateFormat ("yyMM").format (date);
	// for (String s : locations)
	// {
	ArrayList<String> files = codeGenerator(locationText, from, to,
		dateJCheckBox.isSelected());
	// Merge images one file
	BufferedImage page = new BufferedImage(size * columnLimit, size
		* pageLimit, BufferedImage.TYPE_INT_ARGB);
	Graphics2D graphics = page.createGraphics();
	int cnt = 1, i = 0, j = 0, x = 0, y = 0;
	for (String file : files) {

	    try {
		if (i == columnLimit) {
		    i = 0;
		    j++;
		}
		// Write the file if the page limit is reached
		if (j == pageLimit) {
		    i = j = 0;
		    graphics.dispose();
		    File qrFile = new File(directory + locationText + '-'
			    + cnt++ + ".png");
		    try {
			ImageIO.write(page, fileType, qrFile);
			page = new BufferedImage(size * columnLimit, size
				* pageLimit, BufferedImage.TYPE_INT_ARGB);
			graphics = page.createGraphics();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}

		for (int k = 0; k < columnDiffer; k++) {
		    x = (size * i++);
		    y = (size * j);
		    BufferedImage image = ImageIO.read(new File(file));
		    graphics.drawImage(image, x, y, null);
		}

	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	System.out.println();
	// }
	JOptionPane.showMessageDialog(this, "Done!");
    }

    /*
     * this function, lets user select directory where QRcodes are stored
     * 
     * @CreatedBy Muhammad.ahmed@ihsinformatics.com
     */
    private void directorySelection() {
	// folder chooser
	chooser = new JFileChooser();
	chooser.setCurrentDirectory(new java.io.File("."));
	chooser.setDialogTitle("Select Folder to Save");
	chooser.setAcceptAllFileFilterUsed(false);
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    // System.out.println("getCurrentDirectory(): "+
	    // chooser.getCurrentDirectory());
	    // System.out.println("getSelectedFile() : " +
	    // chooser.getSelectedFile());
	    directory = chooser.getSelectedFile().getAbsolutePath();
	    System.out.println("location : " + directory);
	    label10.setText("Save To : " + directory);
	} else {
	    System.out.println("No Selection ");
	}
	chooser = null;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == generatorButton) {

	    generateCodes();

	} else if (e.getSource() == saveToButton) {
	    directorySelection();

	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
	if (e.getSource() == dateJCheckBox) {
	    if (dateJCheckBox.isSelected()) {

		datePicker.setEnabled(true);
		dateFormatJComboBox.setEnabled(true);
		;
	    } else {
		datePicker.setEnabled(false);
		dateFormatJComboBox.setEnabled(false);

	    }
	}

    }

    /*
     * this function to format date according to format
     * 
     * @CreatedBy Muhammad.ahmed@ihsinformatics.com
     */
    private String formatDate(String format, Date date) {

	return new SimpleDateFormat(format).format(date);
    }

    /*
     * this function compares date according to its format
     * 
     * @CreatedBy Muhammad.ahmed@ihsinformatics.com
     */
    public boolean compareDatesByFormat(String format, String newDate) {

	// jugar
	if (format.equals("yy")) {
	    return newDate.matches("[0-9][0-9]");

	}

	// this doesnt work on yy.
	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	dateFormat.setLenient(false);
	try {

	    dateFormat.parse(newDate);
	} catch (ParseException pe) {
	    return false;
	}

	catch (Exception pe) {
	    return false;
	}

	return true;
    }

    private ArrayList<String> codeGenerator(String prefix, int from, int to,
	    boolean dateCheck) {
	ArrayList<String> files = new ArrayList<String>();
	if (dateCheck) {
	    Date pickerDate = (Date) datePicker.getModel().getValue();
	    String dateFormat = (String) dateFormatJComboBox.getSelectedItem();
	    String date = formatDate(dateFormat, pickerDate);
	    for (int i = from; i <= to; i++) {
		try {

		    String qrCodeText = prefix + date
			    + String.format("%06d", i);
		    qrCodeText += "-"
			    + ChecksumHandler.calculateLuhnDigit(qrCodeText);
		    String filePath = directory + qrCodeText + ".png";
		    files.add(filePath);
		    QrCodeHandler.createQRImage(filePath, qrCodeText, size,
			    fileType);
		    System.out.println(qrCodeText + "\t");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	} else {
	    // ArrayList<String> files = new ArrayList<String> ();
	    for (int i = from; i <= to; i++) {
		try {
		    String qrCodeText = prefix /* + dateString */
			    + String.format("%06d", i);
		    qrCodeText += "-"
			    + ChecksumHandler.calculateLuhnDigit(qrCodeText);
		    String filePath = directory + qrCodeText + ".png";
		    files.add(filePath);
		    QrCodeHandler.createQRImage(filePath, qrCodeText, size,
			    fileType);
		    System.out.println(qrCodeText + "\t");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }

	}
	return files;
    }

}
