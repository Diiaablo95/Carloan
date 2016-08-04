package presentation.controllerSchermate.amministratore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.cliente.ControllerSchermataAutoveicoloCliente;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.Autoveicolo;
import business.Richiesta;
import business.Sede;
import business.Autoveicolo.Stato;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di un autoveicolo da parte di un dipendente.
 */
public class ControllerSchermataAutoveicoloDipendente implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata degli autoveicoli per l'amministratore.
     */
    public static final String PATH_SCHERMATA_AUTOVEICOLI_AMMINISTRATORE = "/schermate/amministratore/SchermataAutoveicoloAmministratore.fxml";
    
    /**
     * Percorso al quale si trova il file relativo alla schermata degli autoveicoli per un operatore.
     */
    public static final String PATH_SCHERMATA_AUTOVEICOLI_OPERATORE = "/schermate/operatore/SchermataAutoveicoloOperatore.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di un autoveicolo.
     */
    public static final String ID_INSERIMENTO_AUTOVEICOLO = "C10";
    
    /**
     * Codice univoco per l'operazione di modifica di un autoveicolo.
     */
    public static final String ID_MODIFICA_AUTOVEICOLO = "C20";
    
    /**
     * Codice univoco per l'operazione di eliminazione di un autoveicolo.
     */
    public static final String ID_ELIMINAZIONE_AUTOVEICOLO = "C30";
    
    private static final String ID_BOTTONE_RICERCA = "bottoneRicerca";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    
    private static final String ID_RADIO_DISPONIBILE = "disponibile";
    private static final String ID_RADIO_NOLEGGIATO = "noleggiato";
    private static final String ID_RADIO_MANUTENZIONE_ORDINARIA = "manutenzioneOrdinaria";
    private static final String ID_RADIO_MANUTENZIONE_STRAORDINARIA = "manutenzioneStraordinaria";
    
    @FXML
    private TextField campoTarga, campoSede, campoModello;
    @FXML
    private ToggleGroup stato;
    @FXML
    private HBox boxSede;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di autoveicoli, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciAutoveicolo(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
	
	if(bottonePremuto.getId().equals(ID_BOTTONE_RICERCA)) {
	    richiesta.setId(ControllerSchermataAutoveicoloCliente.ID_VISUALIZZAZIONE_AUTOVEICOLI);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_AUTOVEICOLO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_AUTOVEICOLO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_AUTOVEICOLO);
	}
	
	//Se il tasto premuto e' per l'eliminazione e si conferma l'alert, o se il tasto premuto non e' quello per l'eliminazione.
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    Autoveicolo auto = new Autoveicolo();
	    aggiungiParametri(auto);
	    richiesta.aggiungiOggetto(auto);
	    
	    FrontController.processaRichiesta(richiesta);
	}
    }
    
    /**
     * Consente di visualizzare o nascondere il box relativo alla sede di consegna in base all'opzione scelta.
     * @param click : l'evento generato dal click su una opzione di sede.
     */
    public void gestisciBoxSede(MouseEvent click) {
	RadioButton bottonePremuto = (RadioButton) click.getSource();
	if(bottonePremuto.getId().equals(ID_RADIO_DISPONIBILE)) {
	    this.boxSede.setVisible(true);
	} else {
	    this.boxSede.setVisible(false);
	}
    }

    //Per modifica, eliminazione e' obbligatoria la targa. Per ricerca nessun campo e' obbligatorio. Per inserimento tutti.
    private void aggiungiParametri(Autoveicolo auto) {
	String targa = this.campoTarga.getText();
	Stato stato = getStatoDaToggleGroup();
	String sede = this.campoSede.getText();
	String modello = this.campoModello.getText();
	
	if(!targa.isEmpty()) {
	    auto.setTarga(targa);
	}
	
	auto.setStato(stato);
	
	if(!this.boxSede.isVisible()) {
	    auto.setIdSede(Sede.DEFAULT_ID);
	} else {
	    auto.setIdSede(Integer.parseInt(sede));
	}
	
	if(!modello.isEmpty()) {
	    auto.setIdModello(modello);
	}
    }

    private Stato getStatoDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.stato.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	Stato statoAutoveicolo = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_NOLEGGIATO)) {
		statoAutoveicolo = Stato.NOLEGGIATO;
	    } else if(idRadioSelezionato.equals(ID_RADIO_DISPONIBILE)) {
		statoAutoveicolo = Stato.DISPONIBILE;
	    } else if(idRadioSelezionato.equals(ID_RADIO_MANUTENZIONE_ORDINARIA)) {
		statoAutoveicolo = Stato.MANUTENZIONE_ORDINARIA;
	    } else if(idRadioSelezionato.equals(ID_RADIO_MANUTENZIONE_STRAORDINARIA)) {
		statoAutoveicolo = Stato.MANUTENZIONE_STRAORDINARIA;
	    }
	}
	return statoAutoveicolo;
    }
}