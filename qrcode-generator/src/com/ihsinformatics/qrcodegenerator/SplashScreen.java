/**
 * Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Muhammad.ahmed@ihsinformatics.com
 */
package com.ihsinformatics.qrcodegenerator;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 *  Created on Feb 20, 2015
 */
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SplashScreen extends JFrame {
	// Variables declaration - do not modify
	private javax.swing.JLabel jLabel1;
	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JLabel versionJLabel;
	ImageIcon imageIcon;

	public SplashScreen(String version) {

		initComponents(version);

	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents(String version) {

		jProgressBar1 = new javax.swing.JProgressBar();
		versionJLabel = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		getContentPane().setLayout(null);
		getContentPane().add(jProgressBar1);
		jProgressBar1.setBounds(50, 220, 400, 30);

		versionJLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		versionJLabel.setText(version);
		getContentPane().add(versionJLabel);
		// versionJLabel.setBounds(374, 180, 70, 20);
		versionJLabel.setBounds(374, 180, 110, 20);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"splashScreen.png"))); // NOI18N
		getContentPane().add(jLabel1);
		jLabel1.setBounds(0, 0, 490, 300);
		setUndecorated(true);
		setSize(490, 310);
		pack();
	}// </editor-fold>

	public void setVersion(String version) {
		final String v = version;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				versionJLabel.setText(v);
			}
		});
	}

	public void setProgressMax(int maxProgress) {
		jProgressBar1.setMaximum(maxProgress);
	}

	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jProgressBar1.setValue(theProgress);
			}
		});
	}

	public void setProgress(String message, int progress) {
		final int theProgress = progress;
		final String theMessage = message;
		setProgress(progress);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jProgressBar1.setValue(theProgress);
				setMessage(theMessage);
			}
		});
	}

	public void setScreenVisible(boolean b) {
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(boo);
			}
		});
	}

	private void setMessage(String message) {
		if (message == null) {
			message = "";
			jProgressBar1.setStringPainted(false);
		} else {
			jProgressBar1.setStringPainted(true);
		}
		jProgressBar1.setString(message);
	}
}
