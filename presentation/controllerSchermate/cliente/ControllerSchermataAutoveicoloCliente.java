package presentation.controllerSchermate.cliente;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import business.Autoveicolo;
import business.Richiesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la ricerca di un autoveicolo da parte di un cliente.
 */
public class ControllerSchermataAutoveicoloCliente implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata di visualizzazione degli autoveicoli.
     */
    public static final String PATH_SCHERMATA_VISUALIZZAZIONE_AUTOVEICOLI = "/schermate/cliente/SchermataAutoveicoloCliente.fxml";
    
    /**
     * Codice univoco per l'operazione di visualizzazione degli autoveicoli disponibili presso la sede dalla quale si richiede l'operazione.
     */
    public static final String ID_VISUALIZZAZIONE_AUTOVEICOLI = "C40";
    
    @FXML
    private TextField campoTarga, campoModello;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Mostra la schermata principale del cliente.
     * @param click : l'evento generato dal click del mouse sulla sezione per tornare indietro.
     */
    public void tornaIndietro(MouseEvent click) {
	ControllerSchermataPrincipaleCliente.visualizzaSchermataPrincipaleCliente();
    }
    
    /**
     * Crea la richiesta che il sistema soddisfera' di ricerca degli autoveicoli in base ai parametri inseriti.
     */
    public void ricercaAutoveicoli() {
	Autoveicolo auto = new Autoveicolo();
	aggiungiParametri(auto);
	Richiesta richiesta = new Richiesta();
	richiesta.setId(ID_VISUALIZZAZIONE_AUTOVEICOLI);
	richiesta.aggiungiOggetto(auto);
        	
	FrontController.processaRichiesta(richiesta);
    }
    
    //Metodo ausiliario per riempire il business object in base ai campi avvalorati.
    private void aggiungiParametri(Autoveicolo auto) {
	String targa = this.campoTarga.getText();
	String modello = this.campoModello.getText();
	
	if(!targa.isEmpty()) {
	    auto.setTarga(targa);
	}
	
	if(!modello.isEmpty()) {
	    auto.setIdModello(modello);
	}
    }
}