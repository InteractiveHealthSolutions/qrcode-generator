/**
 * Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors:muhammad.ahmed@ihsinformatics.com
 */

package com.ihsinformatics.qrcodegenerator;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.github.zafarkhaja.semver.Version;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 * 
 */
public final class QRCodeMain {
	/**
	 * @param args
	 */
	private static Version v;
	private static SplashScreen screen;

	public static void main(String[] args) {

		v = Version.valueOf("1.3.0-preAlpha");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// SplashScreen splash=new SplashScreen(imageIcon);

				try {
					new QRCodeMain();

					// Thread.sleep(1000);
					// SwingUtilities.updateComponentTreeUI(screen);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// screen=new SplashScreen();
				// splashScreenInit();
				/*
				 * GeneratorUI frame = new GeneratorUI();
				 * frame.setDefaultCloseOperation
				 * (WindowConstants.EXIT_ON_CLOSE); // frame.getContentPane
				 * ().add (new GeneratorUI ()); frame.pack();
				 * frame.setVisible(true); // frame.setIconImage(new //
				 * ImageIcon(getClass().getResource("/ihs.png")).getImage());
				 */
			}
		});

		runProgress();
	}

	public QRCodeMain() throws InterruptedException {
		// initialize the splash screen
		splashScreenInit();
		// do something here to simulate the program doing something that
		// is time consuming

		// splashScreenDestruct();
		// System.exit(0);
	}

	public static void runProgress() {
		// update progressbar
		for (int i = 0; i <= 100; i++) {
			final int currentValue = i;
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						screen.setProgress(currentValue + "%", currentValue);
						// pb.setValue(currentValue);
						SwingUtilities.updateComponentTreeUI(screen);
					}
				});
				java.lang.Thread.sleep(0);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(screen, e.getMessage());
			}
		}
		viewQRCodeGenerator();
	}

	private static void viewQRCodeGenerator() {
		// screen.setScreenVisible(false);
		GeneratorUI frame = new GeneratorUI();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// frame.getContentPane ().add (new GeneratorUI ());
		frame.pack();
		frame.setVisible(true);
		// frame.setIconImage(new
		// ImageIcon(getClass().getResource("/ihs.png")).getImage());
		screen.dispose();

	}

	private void splashScreenInit() {
		// ImageIcon myImage = new
		// ImageIcon(com.ihsinformatics.qrcodegenerator.QRCodeMain.class.getResource("splashScreen.png"));
		screen = new SplashScreen(v.getNormalVersion());
		screen.setSize(490, 340);
		screen.setVisible(true);
		screen.setLocationRelativeTo(null);
		// screen.set
		screen.setProgressMax(100);

	}
}
