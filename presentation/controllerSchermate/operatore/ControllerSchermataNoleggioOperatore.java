package presentation.controllerSchermate.operatore;

import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.cliente.ControllerSchermataVisualizzazioneNoleggiCliente;
import business.Noleggio;
import business.Richiesta;
import business.Noleggio.DurataNoleggio;
import business.Sede;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la ricerca di un autoveicolo da parte di un operatore.
 */
public class ControllerSchermataNoleggioOperatore implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata di gestione dei noleggi.
     */
    public static final String PATH_SCHERMATA_NOLEGGI = "/schermate/operatore/SchermataNoleggioOperatore.fxml";
    
    /**
     * Codice univoco per l'operazione di modifica di un noleggio.
     */
    public static final String ID_MODIFICA_NOLEGGIO = "C21";
    
    /**
     * Codice univoco per l'operazione di eliminazione di un noleggio.
     */
    public static final String ID_ELIMINAZIONE_NOLEGGIO = "C31";
    
    /**
     * Codice univoco per l'operazione di chiusura provvisoria di un noleggio.
     */
    public static final String ID_CHIUSURA_NOLEGGIO_PROVVISORIA = "C51";
    
    private static final String ID_BOTTONE_CHIUSURA = "bottoneChiudi";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    private static final String ID_BOTTONE_VISUALIZZAZIONE = "bottoneVisualizza";
    
    private static final String ID_RADIO_GIORNALIERO = "giornaliero";
    private static final String ID_RADIO_SETTIMANALE = "settimanale";
    
    @FXML
    private TextField campoId, campoUsername, campoTarga, campoCosto, campoSedeIniziale, campoSedeFinale, campoKmFinale;
    @FXML
    private DatePicker dataInizialePicker, dataFinalePicker;
    @FXML
    private ToggleGroup tipoKm, durata;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di noleggi, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciNoleggio(MouseEvent click) {
	Noleggio noleggio = new Noleggio();
	aggiungiParametri(noleggio);
	Button bottonePremuto = (Button) click.getSource();
	
	Richiesta richiesta = new Richiesta();
	richiesta.aggiungiOggetto(noleggio);
	
	if(bottonePremuto.getId().equals(ID_BOTTONE_CHIUSURA)) {
	    richiesta.setId(ID_CHIUSURA_NOLEGGIO_PROVVISORIA);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_NOLEGGIO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_NOLEGGIO);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_VISUALIZZAZIONE)) {
	    richiesta.setId(ControllerSchermataVisualizzazioneNoleggiCliente.ID_VISUALIZZAZIONE_NOLEGGI);
	}
	
	FrontController.processaRichiesta(richiesta);
    }
    
    //id noleggio obbligatorio
    private void aggiungiParametri(Noleggio noleggio) {
	String id = this.campoId.getText();
	String username = this.campoUsername.getText();
	String targa = this.campoTarga.getText();
	LocalDate dataInizio = this.dataInizialePicker.getValue();
	LocalDate dataFine = this.dataFinalePicker.getValue();
	String costo = this.campoCosto.getText();
	String sedeIniziale = this.campoSedeIniziale.getText();
	String sedeFinale = this.campoSedeFinale.getText();
	String kmFinale = this.campoKmFinale.getText();
	DurataNoleggio durata = getDurataDaToggleGroup();
	
	if(!id.isEmpty()) {
	    noleggio.setId(new BigInteger(id));
	} else {
	    noleggio.setId(Noleggio.DEFAULT_ID);
	}
	
	if(!username.isEmpty()) {
	    noleggio.setUserCliente(username);
	}
	
	if(!targa.isEmpty()) {
	    noleggio.setTargaAuto(targa);
	}
	
	if(dataInizio != null) {
	    noleggio.setDataInizio(dataInizio);
	}
	
	if(dataFine != null) {
	    noleggio.setDataFine(dataFine);
	}
	
	if(!costo.isEmpty()) {
	    noleggio.setSommaDenaro(Double.parseDouble(costo));
	}
	
	if(!sedeIniziale.isEmpty()) {
	    noleggio.setSedeIniziale(Integer.parseInt(sedeIniziale));
	} else {
	    noleggio.setSedeIniziale(Sede.DEFAULT_ID);
	}
	
	if(!sedeFinale.isEmpty()) {
	    noleggio.setSedeFinale(Integer.parseInt(sedeFinale));
	} else {
	    noleggio.setSedeFinale(Sede.DEFAULT_ID);
	}

	if(!kmFinale.isEmpty()) {
	    noleggio.setChilometraggio(Double.parseDouble(kmFinale));
	} else {
	    noleggio.setChilometraggio(Noleggio.VALORE_CHILOMETRAGGIO_NON_IMPOSTATO);
	}
	
	noleggio.setKmIniziale(Noleggio.VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT);
	
	if(durata != null) {
	    noleggio.setDurata(durata);
	}
    }
    
    private DurataNoleggio getDurataDaToggleGroup() {
	RadioButton bottoneSelezionato = (RadioButton) this.durata.getSelectedToggle();
	DurataNoleggio durata = null;
	
	if(bottoneSelezionato != null) {
	    if(bottoneSelezionato.getId().equals(ID_RADIO_GIORNALIERO)) {
		durata = DurataNoleggio.GIORNALIERO;
	    } else if(bottoneSelezionato.getId().equals(ID_RADIO_SETTIMANALE)) {
		durata = DurataNoleggio.SETTIMANALE;
	    }
	}
	return durata;
    }
}