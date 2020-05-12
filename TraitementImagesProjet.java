package traitementimagesprojet;

import java.awt.image.BufferedImage;
import javafx.scene.control.MenuItem;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TraitementImagesProjet extends Application implements Initializable {

    private Scene scene;
    private Stage stagePrincipal;
    private BorderPane conteneurPrincipal;
    private PanelImage panelImage = null;
    private boolean areWelcomeClosed = false;

    @FXML
    private TabPane ContOnglet;
    @FXML
    private MenuItem menuOuvrir;
    @FXML
    public MenuItem menuEnregistre;
    @FXML
    private MenuItem menuAnnuler;
    @FXML
    private MenuItem menuRepeter;
    @FXML
    private MenuItem menuZoomIn;
    @FXML
    private MenuItem menuZoomOut;
    @FXML
    private MenuItem menuTailInit;
    @FXML
    private MenuItem menuHistogramme;
    @FXML
    private MenuItem menuHisCumule;
    @FXML
    private MenuItem menuNiveauGris;
    @FXML
    private MenuItem menuHistEtaler;
    @FXML
    private MenuItem menuHistEtalAmel;
    @FXML
    private MenuItem menuHisEgaliser;
    @FXML
    private MenuItem menuComparerHisto;
    @FXML
    private MenuItem menuComparerImage;

    @Override
    public void start(Stage primaryStage) {
        stagePrincipal = primaryStage;
        stagePrincipal.setTitle("HistoShop - Traitement d'images");
        initialisationConteneurPrincipal();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deactivermenu();
        //permet d'executer des operations lors qu'une combinaison de touche est utilisé  
        this.menuOuvrir.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuEnregistre.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuZoomIn.setAccelerator(new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuZoomOut.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuTailInit.setAccelerator(new KeyCodeCombination(KeyCode.MULTIPLY, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuRepeter.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        this.menuAnnuler.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
    }
    
    //Cette methode permet de fermer l'application 
    public void quitter() {
        Platform.exit();
    }

    private void initialisationConteneurPrincipal() {
        //créer un chargeur de fichier XML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TraitementImagesProjet.class.getResource("AccueilXML.fxml"));
        try {
            //charger le conteneur principale(BorderPane) du Fichier XML dans le conteneur de même type
            conteneurPrincipal = (BorderPane) loader.load();
            //On définit une scène principale avec notre conteneur
            scene = new Scene(conteneurPrincipal);
            //Que nous affectons à notre Stage
            stagePrincipal.setScene(scene);
            //Pour l'afficher
            stagePrincipal.show();

        } catch (IOException e) {
        }
    }

    public void ouvrirImage() {
        //permet d'ouvrire une boite de dialoge et selectionner l'image voulue grace au sélecteur de fichier JFileChooser
        FileChooser fileOuvrirImage = new FileChooser();
        fileOuvrirImage.setTitle("Choisir un Image");
        //filtrer les fichiers à choisir en affichant que les images ayant les extensions définit 
        fileOuvrirImage.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
        );

        File selecteFile = fileOuvrirImage.showOpenDialog(stagePrincipal);
        if (selecteFile != null) {
            panelImage = new PanelImage();
            ContOnglet.getSelectionModel().select(panelImage);
            ContOnglet.getTabs().add(panelImage);
            panelImage.ajouterImage(selecteFile);
            activermenu();
        }

    }

    public void enregistrerImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer Image");
        fileChooser.setInitialFileName("MonImage.png");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File savedFile = fileChooser.showSaveDialog(stagePrincipal);
        if (savedFile != null) {
            panelImage.enregistrerImage(savedFile);
        }
    }

    public void zoomIn() {
        panelImage.zoomIn();
    }

    public void zoomOut() {
        panelImage.zoomOut();
    }

    public void originalSize() {
        panelImage.originalSize();
    }

    private void deactivermenu() {
        menuEnregistre.setDisable(true);
        menuAnnuler.setDisable(true);
        menuRepeter.setDisable(true);
        menuZoomIn.setDisable(true);
        menuZoomOut.setDisable(true);
        menuTailInit.setDisable(true);
        menuHistogramme.setDisable(true);
        menuHisCumule.setDisable(true);
        menuNiveauGris.setDisable(true);
        menuHistEtaler.setDisable(true);
        menuHistEtalAmel.setDisable(true);
        menuHisEgaliser.setDisable(true);
        menuComparerHisto.setDisable(true);
        menuComparerImage.setDisable(true);

    }

    private void activermenu() {

        menuEnregistre.setDisable(false);
        menuAnnuler.setDisable(false);
        menuRepeter.setDisable(false);
        menuZoomIn.setDisable(false);
        menuZoomOut.setDisable(false);
        menuTailInit.setDisable(false);
        menuHistogramme.setDisable(false);
        menuHisCumule.setDisable(false);
        menuNiveauGris.setDisable(false);
        menuHistEtaler.setDisable(false);
        menuHistEtalAmel.setDisable(false);
        menuHisEgaliser.setDisable(false);
        menuComparerHisto.setDisable(false);
        menuComparerImage.setDisable(false);
    }

    public void annuler() {
        panelImage.retourArriere();
    }

    public void repeter() {
        panelImage.retourAvant();
    }

    public void imageEnNiveauGris() {
        panelImage.imageEnNiveauGris();
    }

    public void etaler() {
        panelImage.etalerHistogramme();
    }

    public void etalerameliorer() {
        panelImage.amelioreEtalHisto();
    }

    public void histogramme() {
        panelImage.afficheHistograme(0);
    }

    public void histogrammeCumule() {
        panelImage.afficheHistograme(1);
    }

    public void egaliser() {
        panelImage.egaliserHistogramme();
    }

    public void comparerImages() {
       		BufferedImage img=panelImage.getImageOrg();
		fenetreComparaison fenCmp=new fenetreComparaison(img);	
    }

    public void comparerHistogrammes() {
        /**
         * **************************************
         */
    }

    public void onTabChanged() {

        int id = ContOnglet.getSelectionModel().getSelectedIndex();

        if (areWelcomeClosed == false) {
            if (id != 0) {
                panelImage = (PanelImage) ContOnglet.getTabs().get(id);
            }
        } else {
            panelImage = (PanelImage) ContOnglet.getTabs().get(id);
        }

    }

    public void welcomeClosed() {
        areWelcomeClosed = true;
    }

    public static void main(String[] args) {
        launch(args);

    }
}
