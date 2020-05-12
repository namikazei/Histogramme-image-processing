package traitementimagesprojet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class fenetreComparaison extends JFrame{

	private static final long serialVersionUID = 1L;

	private BufferedImage image=null;
	private JPanel contenairePrincipale=new JPanel();
	
	public   fenetreComparaison(BufferedImage img)
	{
		image=img;
		positionner();
		this.setSize(1000,800);
		this.setLocationRelativeTo(null);
		this.setContentPane(contenairePrincipale);
		this.setVisible(true);
	}
	
	
	private void positionner()
	{Histogramme histo0,histo1,histo2,histo3;
	comparaisontImage img0,img1,img2,img3;
	JPanel pane0,pane1,pane2,pane3,paneCentre0,paneCentre1,paneCentre2,paneCentre3;
	JLabel titre0,titre1,titre2,titre3;
	
	titre0=new JLabel("Originale");
	titre1=new JLabel("Etalée");
	titre2=new JLabel("Etalée(Améliorée)");
	titre3=new JLabel("Egalisée");
	
	Font police=new Font("Sans Serif", Font.PLAIN,25);
	
	titre0.setHorizontalAlignment(JLabel.CENTER);
	titre0.setFont(police);
        titre0.setForeground(Color.WHITE);
     
	titre1.setHorizontalAlignment(JLabel.CENTER);
	titre1.setFont(police);
        titre1.setForeground(Color.WHITE);
	
	titre2.setHorizontalAlignment(JLabel.CENTER);
	titre2.setFont(police);
        titre2.setForeground(Color.WHITE);
	
	titre3.setHorizontalAlignment(JLabel.CENTER);
	titre3.setFont(police);
        titre3.setForeground(Color.WHITE);
	
	histo0=new Histogramme(image);
	histo1=new Histogramme(image);
	histo2=new Histogramme(image);
	histo3=new Histogramme(image);
	
	histo1.etalerHistogramme();
	histo2.amelioreEtalHisto();
	histo3.egaliserHistogramme();
	
	histo0.afficherHistogramme(0);
	histo1.afficherHistogramme(0);
	histo2.afficherHistogramme(0);
	histo3.afficherHistogramme(0);
	
	img0=new comparaisontImage(image);
        img0.setBackground(new java.awt.Color(82, 82, 82));
	img1=new comparaisontImage(histo1.getImage());
        img1.setBackground(new java.awt.Color(82, 82, 82));
	img2=new comparaisontImage(histo2.getImage());
        img2.setBackground(new java.awt.Color(82, 82, 82));
	img3=new comparaisontImage(histo3.getImage());
	img3.setBackground(new java.awt.Color(82, 82, 82));
        
	pane0=new JPanel();
        pane0.setBackground(new java.awt.Color(82, 82, 82));
	pane1=new JPanel();
         pane1.setBackground(new java.awt.Color(82, 82, 82));
	pane2=new JPanel();
         pane2.setBackground(new java.awt.Color(82, 82, 82));
	pane3=new JPanel();
         pane3.setBackground(new java.awt.Color(82, 82, 82));
	
        
         
	pane0.setLayout(new BorderLayout());
	pane1.setLayout(new BorderLayout());
	pane2.setLayout(new BorderLayout());
	pane3.setLayout(new BorderLayout());
	
	paneCentre0=new JPanel();
	paneCentre1=new JPanel();
	paneCentre2=new JPanel();
	paneCentre3=new JPanel();
	
	
	paneCentre0.setLayout(new GridLayout(2, 1,0,5));
	paneCentre1.setLayout(new GridLayout(2, 1,0,5));
	paneCentre2.setLayout(new GridLayout(2, 1,0,5));
	paneCentre3.setLayout(new GridLayout(2, 1,0,5));
	
	
	pane0.add(titre0,BorderLayout.NORTH);
        paneCentre0.setBackground(new java.awt.Color(82, 82, 82));
	paneCentre0.add(img0);
	paneCentre0.add(histo0);
	pane0.add(paneCentre0,BorderLayout.CENTER);
	
	
	pane1.add(titre1,BorderLayout.NORTH);
        paneCentre1.setBackground(new java.awt.Color(82, 82, 82));
	paneCentre1.add(img1);
	paneCentre1.add(histo1);
	pane1.add(paneCentre1,BorderLayout.CENTER);
		

	pane2.add(titre2,BorderLayout.NORTH);
        paneCentre2.setBackground(new java.awt.Color(82, 82, 82));
	paneCentre2.add(img2);
	paneCentre2.add(histo2);
	pane2.add(paneCentre2,BorderLayout.CENTER);
	

	pane3.add(titre3,BorderLayout.NORTH);
        paneCentre3.setBackground(new java.awt.Color(82, 82, 82));
	paneCentre3.add(img3);
	paneCentre3.add(histo3);
	pane3.add(paneCentre3,BorderLayout.CENTER);
	
	contenairePrincipale.setLayout(new GridLayout(1,4,5,0));
        contenairePrincipale.setBackground(new java.awt.Color(82, 82, 82));
	contenairePrincipale.add(pane0);
	contenairePrincipale.add(pane1);
	contenairePrincipale.add(pane2);
	contenairePrincipale.add(pane3);
	}
}


	
	

