package traitementimagesprojet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import javax.swing.JComponent;

public class Histogramme extends JComponent {

    private static final long serialVersionUID = 1L;
    private BufferedImage image, ContHistogramme;
    private final int largeur = 256;
    private final int hauteur = 256;
    private Graphics2D dessinHistogramme;
    private int[] histogramme = new int[256];
    private int[] histogrammeCumul = new int[256];
    private int valMin, valMax;

    public Histogramme(BufferedImage monImage) {
        valMin = 255;
        valMax = 0;
        image = monImage;
        calculerHistogramme();
        calculerHistogrammeCumul();
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    protected void paintComponent(Graphics surface) {
        surface.drawImage(ContHistogramme, 5, 40, null);
    }

    private void calculerHistogramme() {
        initialiserHistogramme();
        Raster trame = image.getRaster();
        int[] NvGr = new int[trame.getNumBands()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                trame.getPixel(x, y, NvGr);
                histogramme[NvGr[0] % 256]++;
                if (NvGr[0] % 256 > valMax) {
                    valMax = NvGr[0] % 256;
                }
                if (NvGr[0] % 256 < valMin) {
                    valMin = NvGr[0] % 256;
                }
            }
        }
    }

    private void calculerHistogrammeCumul() {
        int cumulus = 0;
        for (int i = 0; i < 256; i++) {
            cumulus += histogramme[i];
            histogrammeCumul[i] = cumulus;
        }
    }

    public void afficherHistogramme(int type) {
        ContHistogramme = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_USHORT_GRAY);
        dessinHistogramme = ContHistogramme.createGraphics();
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, largeur - 1, hauteur - 1);
        dessinHistogramme.draw(rectangle);
        dessinHistogramme.setPaint(Color.WHITE);
        dessinHistogramme.fill(rectangle);
        dessinHistogramme.translate(0, hauteur);
        double surfaceImage = image.getWidth() * image.getHeight();
        double surfaceHistogramme = ContHistogramme.getWidth() * ContHistogramme.getHeight();
        dessinHistogramme.setPaint(Color.BLACK);
        if (type == 0) {
            dessinHistogramme.scale(1, -surfaceHistogramme / surfaceImage / 10);
            tracerHistogramme(histogramme);
        } else if (type == 1) {
            dessinHistogramme.scale(1, -surfaceHistogramme / surfaceImage / 300);
            tracerHistogramme(histogrammeCumul);
        }

    }

    private void tracerHistogramme(int hist[]) {
        for (int i = 0; i < 256; i++) {
            dessinHistogramme.drawLine(i, 0, i, hist[i]);
        }
    }

    private void initialiserHistogramme() {
        for (int i = 0; i < 256; i++) {
            histogramme[i] = 0;
        }
    }

    public void etalerHistogramme() {
        initialiserHistogramme();
        Raster trame = image.getRaster();
        BufferedImage imageEtaler = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster trameEtale = imageEtaler.getRaster();
        int[] valpix = new int[1];
        int[] NvGr = new int[trame.getNumBands()];
        //calcul de l'image étalée
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                trame.getPixel(x, y, NvGr);
                valpix[0] = (int) Math.round(255 * ((float) ((NvGr[0] % 256) - valMin) / (valMax - valMin)));
                trameEtale.setPixel(x, y, valpix);
                histogramme[valpix[0]]++;
            }
        }
        image = imageEtaler;
    }

    public void amelioreEtalHisto() {
        int alpha, beta;
        int taux = (int) Math.round((float) (image.getHeight() * image.getWidth()) / 100);
        int nbrepix = 0, i = 0;
        while (nbrepix < taux) {
            nbrepix += histogramme[i];
            i++;
        }
        alpha = i - 1;
        i = 255;
        nbrepix = 0;
        while (nbrepix < taux) {
            nbrepix += histogramme[i];
            i--;
        }
        beta = i + 1;
        Raster trameEA = image.getRaster();
        BufferedImage imageEtalerAmeliore = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster trameEtaleAmeliore = imageEtalerAmeliore.getRaster();

        initialiserHistogramme();
        int[] NvGr = new int[trameEA.getNumBands()];
        int[] valpix = new int[1];
        //calcul de l'image étalée
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                trameEA.getPixel(x, y, NvGr);
                valpix[0] = (int) Math.round(255 * ((float) (NvGr[0] % 256 - alpha) / (beta - alpha)));
                if (valpix[0] < 0) {
                    valpix[0] = 0;
                }
                if (valpix[0] > 255) {
                    valpix[0] = 255;
                }
                trameEtaleAmeliore.setPixel(x, y, valpix);
                histogramme[valpix[0]]++;
            }
        }
        image = imageEtalerAmeliore;
    }

    public void egaliserHistogramme() {
        Raster matrice = image.getRaster();
        BufferedImage trameIE = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster rasterTemporaire = trameIE.getRaster();

        double nbrePixel = image.getWidth() * image.getHeight();

        int numBands = matrice.getNumBands();
        int[] NvGr = new int[numBands];

        int[] valpix = new int[1];
        float taux;
        taux = 255 / (float) nbrePixel;
        initialiserHistogramme();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                matrice.getPixel(x, y, NvGr);
                valpix[0] = (int) Math.round((float) (taux * (histogrammeCumul[NvGr[0]])));
                if (valpix[0] < 0) {
                    valpix[0] = 0;
                }
                if (valpix[0] > 255) {
                    valpix[0] = 255;
                }
                rasterTemporaire.setPixel(x, y, valpix);
                histogramme[valpix[0]]++;
            }
        }

        image = trameIE;

    }

}
