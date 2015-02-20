/**
 * Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: owais.hussain@irdresearch.org
 */

package com.ihsinformatics.qrcodegenerator;

import com.sun.media.sound.InvalidFormatException;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class ChecksumHandler {
	/**
	 * Calculates Luhn check digit
	 * 
	 * @param idWithoutCheckdigit
	 * @return
	 * @throws Exception
	 */
	public static int calculateLuhnDigit(String idWithoutCheckdigit)
			throws InvalidFormatException {
		String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_-/";
		idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();
		int sum = 0;
		for (int i = 0; i < idWithoutCheckdigit.length(); i++) {
			char ch = idWithoutCheckdigit.charAt(idWithoutCheckdigit.length()
					- i - 1);
			if (validChars.indexOf(ch) == -1)
				throw new InvalidFormatException("\"" + ch
						+ "\" is an invalid character");
			int digit = (int) ch - 48;
			int weight;
			if (i % 2 == 0) {
				weight = (2 * digit) - (int) (digit / 5) * 9;
			} else {
				weight = digit;
			}
			sum += weight;
		}
		sum = Math.abs(sum) + 10;
		return (10 - (sum % 10)) % 10;

	}
}
