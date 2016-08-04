package presentation.controllerSchermate.amministratore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.FasciaAuto;
import business.Modello;
import business.Modello.Cambio;
import business.Modello.Carrozzeria;
import business.Modello.Motore;
import business.Modello.Porte;
import business.Richiesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di modelli d'auto da parte dell'amministratore.
 */
public class ControllerSchermataModello implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata dei modelli.
     */
    public static final String PATH_SCHERMATA_MODELLI = "/schermate/amministratore/SchermataModello.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di un modello di autoveicolo.
     */
    public static final String ID_INSERIMENTO_MODELLO = "C14";
    
    /**
     * Codice univoco per l'operazione di modifica di un modello di autoveicolo.
     */
    public static final String ID_MODIFICA_MODELLO = "C24";
    
    /**
     * Codice univoco per l'operazione di eliminazione. di un modello di autoveicolo.
     */
    public static final String ID_ELIMINAZIONE_MODELLO = "C34";
    
    /**
     * Codice univoco per l'operazione di visualizzazione dei modelli di autoveicolo.
     */
    public static final String ID_VISUALIZZAZIONE_MODELLI = "C44";
    
    private static final String ID_BOTTONE_RICERCA = "bottoneRicerca";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    
    private static final String ID_RADIO_AUTOMATICO = "automatico";
    private static final String ID_RADIO_MANUALE = "manuale";
    
    private static final String ID_RADIO_TRE_PORTE = "trePorte";
    private static final String ID_RADIO_CINQUE_PORTE = "cinquePorte";
   
    private static final String ID_RADIO_MONOVOLUME = "monovolume";
    private static final String ID_RADIO_STATION_WAGON = "stationWagon";
    private static final String ID_RADIO_DUE_VOLUMI = "dueVolumi";
    private static final String ID_RADIO_SPIDER = "spider";
    private static final String ID_RADIO_BERLINA = "berlina";
    private static final String ID_RADIO_COUPE = "coupe";
    private static final String ID_RADIO_DECAPPOTTABILE = "decappottabile";
    
    private static final String ID_RADIO_BENZINA = "benzina";
    private static final String ID_RADIO_DIESEL = "diesel";
    
    @FXML
    private TextField campoId, campoFascia;
    @FXML
    private ToggleGroup cambio, porte, carrozzeria, motore;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di modelli, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciModello(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
	if(bottonePremuto.getId().equals(ID_BOTTONE_RICERCA)) {
	    richiesta.setId(ID_VISUALIZZAZIONE_MODELLI);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_MODELLO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_MODELLO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_MODELLO);
	}
	
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    Modello modello = new Modello();
	    aggiungiParametri(modello);
	    richiesta.aggiungiOggetto(modello);
	    FrontController.processaRichiesta(richiesta);   
	}
    }
    
    private void aggiungiParametri(Modello modello) {
	String idModello = this.campoId.getText();
	String idFascia = this.campoFascia.getText();
	Cambio cambio = getCambioDaToggleGroup();
	Carrozzeria carrozzeria = getCarrozzeriaDaToggleGroup();
	Motore motore = getMotoreDaToggleGroup();
	Porte porte = getPorteDaToggleGroup();
	
	if(!idModello.isEmpty()) {
	    modello.setId(idModello);
	}
	
	if(!idFascia.isEmpty()) {
	    modello.setIdFascia(Integer.parseInt(idFascia));
	} else {
	    modello.setIdFascia(FasciaAuto.DEFAULT_ID);
	}
	
	if(cambio != null) {
	    modello.setCambio(cambio);
	}
	
	if(carrozzeria != null) {
	    modello.setCarrozzeria(carrozzeria);
	}
	
	if(motore != null) {
	    modello.setMotore(motore);
	}
	
	if(porte != null) {
	    modello.setPorte(porte);
	}
    }

    private Cambio getCambioDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.cambio.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	Cambio cambio = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_AUTOMATICO)) {
		cambio = Cambio.AUTOMATICO;
	    } else if(idRadioSelezionato.equals(ID_RADIO_MANUALE)) {
		cambio = Cambio.MANUALE;
	    }
	}
	return cambio;
    }
    
    private Carrozzeria getCarrozzeriaDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.carrozzeria.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	Carrozzeria carrozzeria = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_BERLINA)) {
		carrozzeria = Carrozzeria.BERLINA;
	    } else if(idRadioSelezionato.equals(ID_RADIO_COUPE)) {
		carrozzeria = Carrozzeria.COUPE;
	    } else if(idRadioSelezionato.equals(ID_RADIO_DECAPPOTTABILE)) {
		carrozzeria = Carrozzeria.DECAPPOTTABILE;
	    } else if(idRadioSelezionato.equals(ID_RADIO_MONOVOLUME)) {
		carrozzeria = Carrozzeria.MONOVOLUME;
	    } else if(idRadioSelezionato.equals(ID_RADIO_DUE_VOLUMI)) {
		carrozzeria = Carrozzeria.DUE_VOLUMI;
	    } else if(idRadioSelezionato.equals(ID_RADIO_SPIDER)) {
		carrozzeria = Carrozzeria.SPIDER;
	    } else if(idRadioSelezionato.equals(ID_RADIO_STATION_WAGON)) {
		carrozzeria = Carrozzeria.STATION_WAGON;
	    }
	}
	return carrozzeria;
    }
    
    private Motore getMotoreDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.motore.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	Motore motore = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_BENZINA)) {
		motore = Motore.BENZINA;
	    } else if(idRadioSelezionato.equals(ID_RADIO_DIESEL)) {
		motore = Motore.DIESEL;
	    }
	}
	return motore;
    }
    
    private Porte getPorteDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.porte.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	Porte porte = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_TRE_PORTE)) {
		porte = Porte.TRE;
	    } else if(idRadioSelezionato.equals(ID_RADIO_CINQUE_PORTE)) {
		porte = Porte.CINQUE;
	    }
	}
	return porte;
    }
}