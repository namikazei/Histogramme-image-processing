package traitementimagesprojet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PanelImage extends Tab {

    BufferedImage monImage = null, imageCtrlY = null, imageOri = null, histogramme = null;
    String nomImage = null;
    private double zoom;
    private double tailleImage;
    private ImageView monImageView;
    BorderPane borderpan;

    //Constructeur qui défini la taille et la position du conteneur de l’image.
    public PanelImage() {
        super();
        imageCtrlY = monImage = imageOri;
        //le conteneur de positionnement 
        AnchorPane conteneur = new AnchorPane();
        //l'Image
        monImageView = new ImageView();
        //pour centrer l'images
        borderpan = new BorderPane();
        AnchorPane.setTopAnchor(borderpan, 0.0);
        AnchorPane.setRightAnchor(borderpan, 0.0);
        AnchorPane.setLeftAnchor(borderpan, 0.0);
        AnchorPane.setBottomAnchor(borderpan, 0.0);
        //mettre l'image au centre du borderpan
        borderpan.setCenter(monImageView);
        //mettre le borderpan en haut du AnchorPane
        conteneur.getChildren().add(borderpan);
        AnchorPane.setTopAnchor(borderpan, 10.0);
        //mettre mettre conteneur dans le tab
        setContent(conteneur);
    }

    /*transformer l'image en niveaux de gris 
    protected void imageEnNiveauGris() {
        int p,a,r,g,b,avg;
        
        for (int y = 0; y < monImage.getHeight(); y++) {
            for (int x = 0; x < monImage.getWidth(); x++) {
                 p = monImage.getRGB(x,y);

                a = (p >> 24) & 0xff;
                r = (p >> 16) & 0xff;
                 g = (p >> 8) & 0xff;
                b = p & 0xff;

                //calculate average
                avg = (r + g + b) / 3;

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                monImage.setRGB(x, y, p);
            }
            reloadImage();
        }
    }
*/
            //transformer l'image en niveaux de gris 
	protected void imageEnNiveauGris()
	{
		BufferedImage imageGris = new BufferedImage(monImage.getWidth(), monImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D surfaceImg = imageGris.createGraphics();
		surfaceImg.drawImage(monImage, null, null);	      
		monImage = imageGris;
		reloadImage();
	}
    
    protected void ajouterImage(File fichierImage) {
        try {
            nomImage = fichierImage.getName();
            monImage = ImageIO.read(fichierImage);
            imageOri = ImageIO.read(fichierImage);
            monImageView.setImage(SwingFXUtils.toFXImage(monImage, null));
            tailleImage = monImage.getWidth();
            zoom = tailleImage;
            setText(nomImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void enregistrerImage(File fichierImage) {
        BufferedImage image = monImage;
        try {
            ImageIO.write(image, "png", fichierImage);
        } catch (IOException e) {
        }
    }

    protected void reloadImage() {
        monImageView.setFitWidth(zoom);
        monImageView.setPreserveRatio(true);
        monImageView.setImage(SwingFXUtils.toFXImage(monImage, null));
    }

    // permet d'afficher soit l'histogramme simple ou  cumulé de l'image ( 0: simple 1:cumulé) )
    protected void afficheHistograme(int type) {
        JFrame f = new JFrame();
        f.getContentPane().setBackground(new java.awt.Color(82, 82, 82));
        Histogramme hist = null;

        switch (type) {
            case 0:
                f.setTitle("Histogramme");
                f.setBounds(300, 200, 280, 380);
                hist = new Histogramme(monImage);
                hist.afficherHistogramme(0);

                break;
            case 1:
                f.setTitle("Histogramme Cumulé");
                f.setBounds(300, 200, 280, 380);
                hist = new Histogramme(monImage);
                hist.afficherHistogramme(1);
                break;
        }
        f.add(hist);
        f.setVisible(true);
    }

    protected void etalerHistogramme() {
        Histogramme hist = new Histogramme(monImage);
        hist.etalerHistogramme();
        monImage = hist.getImage();
        reloadImage();
    }

    protected void amelioreEtalHisto() {
        Histogramme hist = new Histogramme(monImage);
        hist.amelioreEtalHisto();
        monImage = hist.getImage();
        reloadImage();
    }

    protected void egaliserHistogramme() {
        Histogramme hist = new Histogramme(monImage);
        hist.egaliserHistogramme();
        monImage = hist.getImage();
        reloadImage();
    }

    public void zoomIn() {
        zoom += tailleImage / 10;
        reloadImage();
    }

    public void zoomOut() {
        if (zoom > tailleImage / 10) {
            zoom -= tailleImage / 10;
            reloadImage();
        }
    }

    protected void retourAvant() {

        monImage = imageCtrlY;
        reloadImage();
    }

    protected void retourArriere() {
        imageCtrlY = monImage;
        monImage = imageOri;
        reloadImage();
    }

    public void originalSize() {
        zoom = tailleImage;
        reloadImage();
    }
    
            public BufferedImage getImageOrg()
        
        {
        	BufferedImage img,img2;
        	img=monImage;
        	monImage=imageOri;
        	imageEnNiveauGris();
        	img2=monImage;
        	monImage=img;
        	return img2;
        }

}
