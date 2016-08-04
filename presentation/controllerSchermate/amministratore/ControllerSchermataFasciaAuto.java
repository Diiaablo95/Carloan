package presentation.controllerSchermate.amministratore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.FasciaAuto;
import business.Richiesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di fasce d'auto da parte dell'amministratore.
 */
public class ControllerSchermataFasciaAuto implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata delle fasce.
     */
    public static final String PATH_SCHERMATA_FASCE = "/schermate/amministratore/SchermataFasciaAuto.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di una fascia.
     */
    public static final String ID_INSERIMENTO_FASCIA = "C13";
    
    /**
     * Codice univoco per l'operazione di modifica di una fascia.
     */
    public static final String ID_MODIFICA_FASCIA = "C23";
    
    /**
     * Codice univoco per l'operazione di eliminazione di una fascia.
     */
    public static final String ID_ELIMINAZIONE_FASCIA = "C33";
    
    /**
     * Codice univoco per l'operazione di visualizzazione di una o piu' fasce.
     */
    public static final String ID_VISUALIZZAZIONE_FASCE = "C43";
    
    private static final String ID_BOTTONE_RICERCA = "bottoneRicerca";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    
    @FXML
    private TextField campoId, campoPrezzoKm;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di fasce d'autoveicolo, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciFascia(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
	
	if(bottonePremuto.getId().equals(ID_BOTTONE_RICERCA)) {
	    richiesta.setId(ID_VISUALIZZAZIONE_FASCE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_FASCIA);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_FASCIA);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_FASCIA);
	}
	
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    FasciaAuto fascia = new FasciaAuto();
	    aggiungiParametri(fascia);
	    richiesta.aggiungiOggetto(fascia);
	    FrontController.processaRichiesta(richiesta);
	}
    }

    private void aggiungiParametri(FasciaAuto fascia) {
	String idFascia = this.campoId.getText();
	String prezzoKm = this.campoPrezzoKm.getText();
	
	if(idFascia.isEmpty()) {
	    fascia.setId(FasciaAuto.DEFAULT_ID);
	} else {
	    fascia.setId(Integer.parseInt(idFascia));
	}
	
	if(!prezzoKm.isEmpty()) {
	    double prezzoChilometrico = Double.parseDouble(prezzoKm);
	    fascia.setPrezzoKm(prezzoChilometrico);
	}
    }
}