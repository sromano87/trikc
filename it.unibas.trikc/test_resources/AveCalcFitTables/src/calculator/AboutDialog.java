
package calculator;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * AboutDialog Class.
 * 
 * @author andima
 */

class AboutDialog extends JDialog implements Constants {
	
	private static int sleepTime = 100;
	private Canvas canvas;
	
	public AboutDialog(Frame parent){
		super(parent);
		setResizable(true);
		setTitle(TITLE);
		setSize(400, 200);
		initDialog();
	}
	
	private void initDialog(){
		getContentPane().setLayout(new GridLayout(1, 1, 0, 0));
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.setBackground(Color.WHITE);
		
		Icon icon = new ImageIcon("images"+File.separator+"logo.jpg");
		JLabel label = new JLabel(icon);
		canvas = new Canvas();
		canvas.setBackground(Color.WHITE);

		canvas.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON1)
					sleepTime -= 50;
				else sleepTime += 50;
				if(sleepTime <= 0)
					sleepTime = 10;
			}
		});
		
		JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
		
		JButton button = new JButton("Close");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(button);
		
		bottomPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		getContentPane().add(panel);
		
		int x = icon.getIconWidth();
		int y = icon.getIconHeight() * 2;
		
		// setSize(x, y);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		x = (dim.width / 4);
		y =  (dim.height / 4);
		
		setLocation(x, y);
	}
	
	public void setVisible(boolean b){
		super.setVisible(b);
		sleepTime = 100;
		if(b){
			new InfoSliderLancher(canvas).start();
		}
	}
	
	private class InfoSliderLancher extends Thread{
		
		private Canvas canvas;
		
		public InfoSliderLancher(Canvas c){
			canvas = c;
		}
		
		public void run(){
			String[] titles = new String[]{TITLE,
					"Copyright (C) 2005 AverageCalculator",
					"",
					"Realized by:",
					"   Matteo Antonio Emilio",
					"   antdim@gmail.com",
					"",
					TITLE + " has been realized in Java",
					"",
					"Java and all Java-related trademarks",
					"and logos are trademarks of Sun Microsystem, Inc.",
					"in the U.S., other countries, or both",
					"",
					"Special thanks to:",
					"   Eclipse - for development support",
					"   Firefox - for web search",
					"   Winamp  - for background music",
					"",
					"bug report to antdim@gmail.com",
					"",
					"",
					"In bocca al lupo per tutti gli esami",
					":-)"
			};
			
			for(int i = 0; i < titles.length; i++){
				if(!canvas.isVisible())
					return;
				new InfoSliderThread(canvas, titles[i]).start();
				try{
					Thread.sleep(13*sleepTime);
				}catch(InterruptedException ie){
					// nothing
				}
			}
		}
		
	}
	
	private class InfoSliderThread extends Thread{
		
		private Canvas canvas;
		private String message;
		
		public InfoSliderThread(Canvas c, String msg){
			canvas = c;
			message = msg;
		}
		
		public void run(){
			Graphics g = canvas.getGraphics();
			Dimension dim = canvas.getSize();
			
			int x = dim.width / 5;
			int y = dim.height;
			while(y >= 0){
				try{
					g.setColor(Color.WHITE);
					g.drawString(message, x, y + 1);
					g.setColor(Color.BLACK);
					g.drawString(message, x, y--);
				}catch(NullPointerException npe){
					return;
				}
				
				try{
					Thread.sleep(sleepTime);
				}catch(InterruptedException ie){
					// nothing
				}
			}
			try{
				g.setColor(Color.WHITE);
				g.drawString(message, x, y + 1);
			}catch(NullPointerException npe){}
			
		}
	}
}
