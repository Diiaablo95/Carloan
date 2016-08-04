package presentation.controllerSchermate.cliente;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import business.Noleggio;
import business.Richiesta;
import business.Sede;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigInteger;

import presentation.FrontController;

/**
 * Controller che gestisce gli eventi riguardanti la visualizzazione dei noleggi compiuti da parte di un cliente.
 */
public class ControllerSchermataVisualizzazioneNoleggiCliente implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata di visualizzazione dei noleggi.
     */
    public static final String PATH_SCHERMATA_VISUALIZZAZIONE_NOLEGGI = "/schermate/cliente/SchermataVisualizzazioneNoleggiCliente.fxml";
    
    /**
     * Codice univoco per l'operazione di visualizzazione dei noleggi effettuati da parte di un cliente.
     */
    public static final String ID_VISUALIZZAZIONE_NOLEGGI = "C41";
    
    @FXML
    private TextField campoId, campoTarga, campoSedeIniziale, campoSedeFinale;
    @FXML
    private DatePicker dataInizialePicker, dataFinalePicker;

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
     * Crea la richiesta che il sistema soddisfera' di ricerca dei noleggi in base ai parametri inseriti.
     */
    public void ricercaNoleggi() {
	Noleggio noleggio = new Noleggio();
	aggiungiParametri(noleggio);
	
	Richiesta richiesta = new Richiesta();
	richiesta.setId(ID_VISUALIZZAZIONE_NOLEGGI);
	richiesta.aggiungiOggetto(noleggio);
	
	FrontController.processaRichiesta(richiesta);
    }
    
    //Metodo ausiliario per riempire il business object in base ai campi avvalorati.
    private void aggiungiParametri(Noleggio noleggio) {
	String id = this.campoId.getText();
	String targa = this.campoTarga.getText();
	String sedeIniziale = this.campoSedeIniziale.getText();
	String sedeFinale = this.campoSedeFinale.getText();
	LocalDate dataInizio = this.dataInizialePicker.getValue();
	LocalDate dataFine = this.dataFinalePicker.getValue();
	
	if(!id.isEmpty()) {
	    noleggio.setId(new BigInteger(id));
	} else {
	    noleggio.setId(Noleggio.DEFAULT_ID);
	}
	
	if(!targa.isEmpty()) {
	    noleggio.setTargaAuto(targa);
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
	
	if(dataInizio != null) {
	    noleggio.setDataInizio(dataInizio);
	}
	
	if(dataFine != null) {
	    noleggio.setDataFine(dataFine);
	}
    }
}