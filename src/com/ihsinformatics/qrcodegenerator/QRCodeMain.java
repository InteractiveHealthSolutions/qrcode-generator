/**
 * This class generates QR codes for  IDs  
 */

package com.ihsinformatics.qrcodegenerator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 * 
 */
public final class QRCodeMain
{
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		java.awt.EventQueue.invokeLater (new Runnable ()
		{
			public void run ()
			{
				GeneratorUI frame = new GeneratorUI ();
				frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
			//	frame.getContentPane ().add (new GeneratorUI ());
				frame.pack ();
				frame.setVisible (true);
		//		frame.setIconImage(new ImageIcon(getClass().getResource("/ihs.png")).getImage());
				
			}
		});
	}
}
