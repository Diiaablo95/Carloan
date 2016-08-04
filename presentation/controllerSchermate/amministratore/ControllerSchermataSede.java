package presentation.controllerSchermate.amministratore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.Richiesta;
import business.Sede;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di sedi da parte dell'amministratore.
 */
public class ControllerSchermataSede implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata delle sedi.
     */
    public static final String PATH_SCHERMATA_SEDI = "/schermate/amministratore/SchermataSede.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di una sede.
     */
    public static final String ID_INSERIMENTO_SEDE = "C16";
    
    /**
     * Codice univoco per l'operazione di modifica di una sede.
     */
    public static final String ID_MODIFICA_SEDE = "C26";
    
    /**
     * Codice univoco per l'operazione di eliminazione di una sede.
     */
    public static final String ID_ELIMINAZIONE_SEDE = "C36";
    
    /**
     * Codice univoco per l'operazione di visualizzazione di sedi.
     */
    public static final String ID_VISUALIZZAZIONE_SEDI = "C46";
    
    private static final String ID_BOTTONE_RICERCA = "bottoneRicerca";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    
    @FXML
    private TextField campoId, campoNome, campoIndirizzo;
    
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di autoveicoli, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciSede(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
	
	if(bottonePremuto.getId().equals(ID_BOTTONE_RICERCA)) {
	    richiesta.setId(ID_VISUALIZZAZIONE_SEDI);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_SEDE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_SEDE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_SEDE);
	}
	
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    Sede sede = new Sede();
	    aggiungiParametri(sede);
	    richiesta.aggiungiOggetto(sede);
	    FrontController.processaRichiesta(richiesta);
	}
    }
    
    private void aggiungiParametri(Sede sede) {
	String id = campoId.getText();
	String nome = campoNome.getText();
	String indirizzo = campoIndirizzo.getText();
	
	if(!id.isEmpty()) {
	    sede.setNumero(Integer.parseInt(id));
	} else {
	    sede.setNumero(Sede.DEFAULT_ID);
	}
	
	if(!nome.isEmpty()) {
	    sede.setNome(nome);
	}
	
	if(!indirizzo.isEmpty()) {
	    sede.setIndirizzo(indirizzo);
	}
    }
}