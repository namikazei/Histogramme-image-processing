package traitementimagesprojet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class comparaisontImage extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage imgCmp=null;
	private double zoom=1.0;
	private Image image;
        private int width=0;
	public comparaisontImage(BufferedImage img)
	{super();
        imgCmp=img;
        width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4;
	setBounds(0,0,width,width);
		
		zoom=width/(float)imgCmp.getWidth();
	}
	
	 @Override
		protected void paintComponent(Graphics g)
		{
	            
			super.paintComponent(g);
			if(imgCmp != null){
	                    image = imgCmp;
	                    Graphics2D g2D = (Graphics2D) g;
	                    g2D.scale(zoom, zoom);
	                    g2D.drawImage(image, 0, 0, this);
	                    repaint();
	                }          
		}

}
