package presentation.controllerSchermate.cliente;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import presentation.FrontController;
import business.Noleggio;
import business.Noleggio.DurataNoleggio;
import business.Richiesta;
import business.Sede;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Controller che gestisce gli eventi riguardanti l'effettuazione di un noleggio da parte di un cliente.
 */
public class ControllerSchermataEffettuazioneNoleggio implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata di effettuazione di un noleggio.
     */
    public static final String PATH_SCHERMATA_EFFETTUAZIONE_NOLEGGIO = "/schermate/cliente/SchermataEffettuazioneNoleggio.fxml";
    
    /**
     * Codice univoco per l'operazione di effetuazione di un noleggio.
     */
    public static final String ID_EFFETTUAZIONE_NOLEGGIO = "C11";
    
    private static final String ID_RADIO_QUESTA_SEDE = "questaSede";
    private static final String ID_RADIO_ALTRA_SEDE = "altraSede";
    
    private static final String ID_RADIO_LIMITATO = "limitato";
    private static final String ID_RADIO_ILLIMITATO = "illimitato";
    
    private static final String ID_RADIO_GIORNALIERO = "giornaliero";
    private static final String ID_RADIO_SETTIMANALE = "settimanale";
    
    @FXML
    private TextField campoTarga, campoSedeFinale, campoChilometri;
    @FXML
    private DatePicker dataInizialePicker;
    @FXML
    private RadioButton altraSede, kmLimitato, durataGiornaliera;
    @FXML
    private ToggleGroup sedeDestinazione, tipoChilometraggio, durata;
    @FXML
    private VBox boxSede, boxChilometri;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Mostra la schermata principale del cliente.
     */
    public void tornaIndietro() {
	ControllerSchermataPrincipaleCliente.visualizzaSchermataPrincipaleCliente();
    }
    
    /**
     * Crea la richiesta che il sistema soddisfera' di effettuazione di un noleggio con i dati inseriti.
     */
    public void effettuaNoleggio() {
	Noleggio noleggio = new Noleggio();
	aggiungiParametri(noleggio);
	Richiesta richiesta = new Richiesta();
		
	richiesta.setId(ID_EFFETTUAZIONE_NOLEGGIO);
	richiesta.aggiungiOggetto(noleggio);
		
	FrontController.processaRichiesta(richiesta);
    }
    
    /**
     * Mostra o nasconde il box relativo all'ID della sede di consegna in base al luogo selezionato per la consegna.
     * @param click : l'evento generato dal click del mouse sul tasto per iniziare la ricerca.
     */
    public void gestisciBoxSede(MouseEvent click) {
	RadioButton tastoPremuto = (RadioButton) click.getSource();

	if(tastoPremuto == this.altraSede) {
	    this.boxSede.setVisible(true);
	} else {
	    this.boxSede.setVisible(false);
	}
    }
    
    /**
     * Mostra o nascone il box relativo al numero di chilometri compresi nel noleggio in base al tipo di chilometraggio <nbsp>
     * scelto.
     * @param click : l'evento generato dal click del mouse sul tasto per iniziare la ricerca.
     */
    public void gestisciBoxChilometri(MouseEvent click) {
	RadioButton tastoPremuto = (RadioButton) click.getSource();
	
	if(tastoPremuto == this.kmLimitato) {
	    this.boxChilometri.setVisible(true);
	} else {
	    this.boxChilometri.setVisible(false);
	}
    }
    
    private void aggiungiParametri(Noleggio noleggio) {
	String targa = this.campoTarga.getText();
	LocalDate dataIniziale = this.dataInizialePicker.getValue();
	int sedeFinale = getSedeDaToggleGroup();
	String idSedeFinale = this.campoSedeFinale.getText();
	int tipoChilometraggio = getChilometriDaToggleGroup();
	String chilometri = this.campoChilometri.getText();
	DurataNoleggio durata = getDurataDaToggleGroup();
	
	if(!targa.isEmpty()) {
	    noleggio.setTargaAuto(targa);
	}
	
	if(!dataIniziale.toString().isEmpty()) {
	    noleggio.setDataInizio(dataIniziale);
	}
	
	//Se la sede finale e' la stessa, si preleva l'id dal documento TXT, altriment la si prende dal campo relativo.
	if(sedeFinale == 1) {
	    noleggio.setSedeFinale(Integer.parseInt(idSedeFinale));
	} else {
	    noleggio.setSedeFinale(Sede.DEFAULT_ID); 	//Sara' poi gestita dal rispettivo service.
	}
	
	//Se il chilometraggio e' illimitato, si mette il valore di default, altrimenti si prende il valore dal campo relativo.
	if(tipoChilometraggio == 1) {
	    noleggio.setChilometraggio(Noleggio.VALORE_CHILOMETRAGGIO_ILLIMITATO);
	} else {
	    noleggio.setChilometraggio(Double.parseDouble(chilometri));
	}
	
	noleggio.setDurata(durata);
    }
    
    private int getSedeDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.sedeDestinazione.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	int sede = -1;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_ALTRA_SEDE)) {
		sede = 1;
	    } else if(idRadioSelezionato.equals(ID_RADIO_QUESTA_SEDE)) {
		sede = 0;
	    }
	}
	return sede;
    }
    
    private int getChilometriDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.tipoChilometraggio.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	int chilometraggio = -1;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_LIMITATO)) {
		chilometraggio = 0;
	    } else if(idRadioSelezionato.equals(ID_RADIO_ILLIMITATO)) {
		chilometraggio = 1;
	    }
	}
	return chilometraggio;
    }
    
    private DurataNoleggio getDurataDaToggleGroup() {
	RadioButton radioSelezionato = (RadioButton) this.durata.getSelectedToggle();
	String idRadioSelezionato = null;
	if(radioSelezionato != null) {
	    idRadioSelezionato = radioSelezionato.getId();
	}
	DurataNoleggio durata = null;
	
	if(idRadioSelezionato != null) {
	    if(idRadioSelezionato.equals(ID_RADIO_SETTIMANALE)) {
		durata = DurataNoleggio.SETTIMANALE;
	    } else if(idRadioSelezionato.equals(ID_RADIO_GIORNALIERO)) {
		durata = DurataNoleggio.GIORNALIERO;
	    }
	}
	return durata;
    }
}