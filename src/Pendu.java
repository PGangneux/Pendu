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

import javax.sql.rowset.serial.SerialRef;

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
    private Color couleurTop;
    private BorderPane banniere;

    public ImageView imgHome;
    

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    public Clavier getClavier() {
        return this.clavier;
    }
     
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        
        this.couleurTop = Color.LAVENDER;
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

        this.panelCentral = new BorderPane();
        this.banniere = new BorderPane();
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
        fenetre.setTop(this.banniere);
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private void titre(){
        
        //Partie haute de l'accueil
        
        Label  titre = new Label("Jeu du Pendu");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        
        titre.setAlignment(Pos.CENTER);
        //Les images des boutons 

        
        HBox lesBoutons = new HBox();

        lesBoutons.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfo);
        lesBoutons.setSpacing(5);

        this.banniere.setPadding(new Insets(15));
        this.banniere.setLeft(titre);
        this.banniere.setRight(lesBoutons);
        //Le background
        this.banniere.setBackground(new Background(new BackgroundFill(this.couleurTop, CornerRadii.EMPTY, Insets.EMPTY)));

  
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
    private void fenetreAccueil(){

        VBox lesDifficultes = new VBox();
        VBox positionnement = new VBox();
        VBox droite = new VBox();

        this.panelCentral.setTop(new VBox());
        this.panelCentral.setRight(droite);
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
        this.panelCentral.setCenter(positionnement);
        this.panelCentral.setPadding(new Insets(15));
    }

    private void fenetreParam(){
        ColorPicker pc=new ColorPicker(Color.web(this.couleurTop.toString()));
        pc.setOnAction(new ControleurCouleur(this, pc));
        panelCentral.setTop(pc);
        panelCentral.setCenter(new VBox());  
        panelCentral.setRight(new VBox());  
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
        this.fenetreAccueil();
    }
    
    public void modeJeu(){
        VBox center = new VBox();
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        this.dessin = new ImageView(new Image("file:img/pendu0.png"));
        this.pg = new ProgressBar();
        TilePane boutontTilePane = new TilePane();
        
        
        ControleurLettres controleurLettres = new ControleurLettres(this.modelePendu, this);
        this.clavier = new Clavier(Character.toString((char)65), controleurLettres);
        for (int i = 1; i<26; ++i){
            String lettre = Character.toString((char)65 + i);
            this.clavier.ajouterTouche(lettre);
            
        }
        this.clavier.ajouterTouche("-");
        for(Button button : this.clavier.getClavier()){
            boutontTilePane.getChildren().add(button);
        }
        center.getChildren().addAll(this.motCrypte,this.dessin,this.pg,boutontTilePane);
        this.panelCentral.setCenter(center);
        
        VBox right = new VBox();
        Label niveau = new Label("niveau " + this.leNiveau);
        TilePane tilePaneTimer = new TilePane();
        this.bJouer = new Button("Nouveau mot");
        ControleurLancerPartie controlLancer = new ControleurLancerPartie(this.modelePendu, this);
        this.bJouer.setOnAction(controlLancer);
        right.getChildren().addAll(niveau, tilePaneTimer,this.bJouer);
        this.panelCentral.setRight(right);

    }
    
    public void modeParametres(){
        this.fenetreParam();  
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        VBox center =(VBox) this.panelCentral.getCenter();
        center.getChildren().set(0, this.motCrypte);
        if(this.motCrypte.equals(this.motCrypte.getText())){
            
        }
        
        
        
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
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Le but du jeu est simple : deviner toute les lettres qui doivent composer un mot.\nÉventuellement avec un nombre limité de tentatives et des thèmes fixés à l'avance.\n A chaque fois que le joueur devine une lettre, celle-ci est affichée.\n Dans le cas contraire, le dessin d'un pendu se met à apparaître…");
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
        this.titre();
        stage.setScene(this.laScene());
        stage.show();
    }
    
    public void setCouleur(Color couleur){
        this.couleurTop = couleur;
    }

    public BorderPane getBanniere(){
        return this.banniere;
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }

}
