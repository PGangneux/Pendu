import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton Info
     */    
    private Button boutonInfo;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;
    public ImageView imgHome;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        // A terminer d'implementer

        // Les boutons 
        Image imgHome = new Image("file:img/home.png", 50, 50, false, true);
        ImageView viewHome = new ImageView(imgHome);
        Image imgParam = new Image("file:img/parametres.png", 50, 50, false, true);
        ImageView viewParam = new ImageView(imgParam);
        Image imgInfo = new Image("file:img/info.png", 50, 50, false, true);
        ImageView viewInfo = new ImageView(imgInfo);

        ControleurInfos controlInfos = new ControleurInfos(this);
        RetourAccueil controlAccueil = new RetourAccueil(this.modelePendu, this);
        ControleurParametre controlParam = new ControleurParametre(this, this.modelePendu);
        ControleurLancerPartie controlLancer = new ControleurLancerPartie(this.modelePendu, this);

        this.boutonMaison = new Button();
        this.boutonMaison.setGraphic(viewHome);
        this.boutonMaison.setOnAction(controlAccueil);

        this.boutonParametres = new Button();
        this.boutonParametres.setGraphic(viewParam);
        this.boutonParametres.setOnAction(controlParam);

        this.boutonInfo = new Button();
        boutonInfo.setGraphic(viewInfo);
        this.boutonInfo.setOnAction(controlInfos);

        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setOnAction(controlLancer);
        
        this.niveaux = Arrays.asList("Facile", "Médium", "Difficile", "Expert");
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        
        //Partie haute de l'accueil
        BorderPane banniere = new BorderPane();
        Label  titre = new Label("Jeu du Pendu");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        titre.setAlignment(Pos.CENTER);
        //Les images des boutons 

        
        HBox lesBoutons = new HBox();

        lesBoutons.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfo);
        lesBoutons.setSpacing(5);

        banniere.setPadding(new Insets(15));
        banniere.setLeft(titre);
        banniere.setRight(lesBoutons);
        //Le background
        banniere.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));

        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private BorderPane fenetreAccueil(){
        BorderPane accueil = new BorderPane();
        
   

        VBox lesDifficultes = new VBox();
        VBox positionnement = new VBox();

        ToggleGroup groupe = new ToggleGroup();
        boolean difficulteParDefaut = false;
        for (String unNiveau : this.niveaux){
            RadioButton r = new RadioButton(unNiveau);
            r.setToggleGroup(groupe);
            lesDifficultes.getChildren().add(r);
            if (!difficulteParDefaut){
                r.setSelected(true);
                difficulteParDefaut = true;
            }

        
        }

        lesDifficultes.setSpacing(5);
        TitledPane difficulte = new TitledPane("Niveau de difficulté", lesDifficultes);
        
        positionnement.getChildren().addAll(this.bJouer, difficulte);
        positionnement.setSpacing(10);
        accueil.setCenter(positionnement);
        accueil.setPadding(new Insets(15));

        return accueil;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral = fenetreAccueil();
        
    }
    
    public void modeJeu(){
        // A implementer
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        // A implémenter
        return null; // A enlever
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?");
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.modeAccueil();
        stage.setScene(this.laScene());
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
