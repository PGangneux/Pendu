import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        System.out.println(nomDuRadiobouton);
        if(nomDuRadiobouton.equals("Médium")){
            this.modelePendu.setNiveau(MotMystere.MOYEN);
        }
        else if (nomDuRadiobouton.equals("Difficile")){
            this.modelePendu.setNiveau(MotMystere.DIFFICILE);
        }
        else if (nomDuRadiobouton.equals("Expert")){
            this.modelePendu.setNiveau(MotMystere.EXPERT);
        }
        else{
            this.modelePendu.setNiveau(MotMystere.FACILE);
        }
    }
}
