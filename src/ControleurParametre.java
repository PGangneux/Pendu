import javafx.event.EventHandler;
import javafx.event.ActionEvent ;
import javafx.scene.control.Button;

/**
 * Controleur des boutons en haut à droite et le bouton lancer partie
 */
public class ControleurParametre implements EventHandler<ActionEvent>{
    
    /**
    * modèle du jeu
    */
    private MotMystere modelePendu;

    /**
     * vue du jeu
     */
    private Pendu appli;
    
    /**
     * @param appli vue du jeu
     * @param modelePendu modele du jeu
     */
    public ControleurParametre(Pendu appli, MotMystere modelePendu){
        this.appli = appli;
        this.modelePendu = modelePendu;
        
    }
    
    /**
     * Aller à la page paramètre en vérifiant que la partie n'est pas en cours
     * @param event l'événement
     */
    @Override
    public void handle(ActionEvent event){
        this.appli.modeParametres();
        

    }
}
