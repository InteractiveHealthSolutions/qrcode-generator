/**
 * Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Ahmed
 */
package com.ihsinformatics.qrcodegenerator;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 *  Created on Apr 14, 2015
 */
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

public class Main {
	public static void main(String[] args) {
		JFrame parentFrame = new JFrame();
		parentFrame.setSize(500, 150);
		JLabel jl = new JLabel();
		jl.setText("Count : 0");

		parentFrame.add(BorderLayout.CENTER, jl);
		parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		parentFrame.setVisible(true);

		final JDialog dlg = new JDialog(parentFrame, "Progress Dialog", true);
		JProgressBar dpb = new JProgressBar(0, 100);
		dlg.add(BorderLayout.CENTER, dpb);
		dlg.add(BorderLayout.NORTH, new JLabel("Progress..."));
		dlg.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dlg.setSize(300, 75);
		dlg.setLocationRelativeTo(parentFrame);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				dlg.setVisible(true);
			}
		});
		t.start();
		for (int i = 0; i <= 500; i++) {
			jl.setText("Count : " + i);
			dpb.setValue(i);
			if (dpb.getValue() == 500) {
				dlg.setVisible(false);
				System.exit(0);

			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dlg.setVisible(true);
	}
}