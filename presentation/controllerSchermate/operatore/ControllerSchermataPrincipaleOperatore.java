package presentation.controllerSchermate.operatore;

import java.io.IOException;

import main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import presentation.controllerSchermate.amministratore.ControllerSchermataAutoveicoloDipendente;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import presentation.controllerSchermate.comuni.ControllerSchermataRisultatiRicerca;

/**
 * Controller che gestisce gli eventi riguardanti la schermata principale di un operatore.
 */
public class ControllerSchermataPrincipaleOperatore extends ControllerSchermataPrincipale implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata principale di un operatore.
     */
    public static final String PATH_SCHERMATA_PRINCIPALE_OPERATORE = "/schermate/operatore/SchermataPrincipaleOperatore.fxml";
    
    @FXML
    protected AnchorPane paneSecondario;
    
    /**
     * Mostra la schermata relativa alla gestione degli autoveicoli.
     */
    public void visualizzaSchermataAutoveicoli() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataAutoveicoloDipendente.PATH_SCHERMATA_AUTOVEICOLI_OPERATORE);
    }
    
    /**
     * Mostra la schermata relativa alla gestione dei noleggi.
     */
    public void visualizzaSchermataNoleggi() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataNoleggioOperatore.PATH_SCHERMATA_NOLEGGI);
    }
    
    /**
     * Mostra la schermata relativa alla gestione dei clienti.
     */
    public void visualizzaSchermataClienti() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataCliente.PATH_SCHERMATA_CLIENTI);
    }
    
    public void visualizzaSchermataRisultati() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataRisultatiRicerca.PATH_SCHERMATA_RISULTATI_RICERCA);
    }
    
    protected void visualizzaSchermataInPaneSecondario(String path) {
	try {
	    FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(path));
            AnchorPane schermataDaMostrare = (AnchorPane) loader.load();
            
            this.paneSecondario.getChildren().clear();	//Si elimina la imageView del logo
            this.paneSecondario.getChildren().add(schermataDaMostrare);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}