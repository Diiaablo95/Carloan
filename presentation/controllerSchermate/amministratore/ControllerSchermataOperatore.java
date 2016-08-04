package presentation.controllerSchermate.amministratore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.Operatore;
import business.Richiesta;
import business.Sede;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di un operatore da parte dell'amministratore.
 */
public class ControllerSchermataOperatore implements Initializable {
    
    /**
     *Percorso al quale si trova il file relativo alla schermata degli oepratori. 
     */
    public static final String PATH_SCHERMATA_OPERATORI = "/schermate/amministratore/SchermataOperatore.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di un operatore.
     */
    public static final String ID_INSERIMENTO_OPERATORE = "C15";
    
    /**
     * Codice univoco per l'operazione di modifica di un operatore.
     */
    public static final String ID_MODIFICA_OPERATORE = "C25";
    
    /**
     * Codice univoco per l'operazione di eliminazione di un operatore.
     */
    public static final String ID_ELIMINAZIONE_OPERATORE = "C35";
    
    /**
     * Codice univoco per l'operazione di visualizzazione di operatori.
     */
    public static final String ID_VISUALIZZAZIONE_OPERATORI = "C45";
    
    private static final String ID_BOTTONE_RICERCA = "bottoneRicerca";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    
    @FXML
    private TextField campoUsername, campoPassword, campoNome, campoCognome, campoEmail, campoTelefono, campoSede;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di operatore, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciOperatore(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
	
	if(bottonePremuto.getId().equals(ID_BOTTONE_RICERCA)) {
	    richiesta.setId(ID_VISUALIZZAZIONE_OPERATORI);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_OPERATORE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_OPERATORE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_OPERATORE);
	}
	
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    Operatore operatore = new Operatore();
	    aggiungiParametri(operatore);
	    richiesta.aggiungiOggetto(operatore);
	    FrontController.processaRichiesta(richiesta);
	}
    }
    
    private void aggiungiParametri(Operatore operatore) {
	String username = this.campoUsername.getText();
	String password = this.campoPassword.getText();
	String nome = this.campoNome.getText();
	String cognome = this.campoCognome.getText();
	String email = this.campoEmail.getText();
	String telefono = this.campoTelefono.getText();
	String sede = this.campoSede.getText();
	
	if(!username.isEmpty()) {
	    operatore.setUsername(username);
	}
	
	if(!password.isEmpty()) {
	    operatore.setPassword(password);
	}
	
	if(!nome.isEmpty()) {
	    operatore.setNome(nome);
	}
	
	if(!cognome.isEmpty()) {
	    operatore.setCognome(cognome);
	}
	
	if(!email.isEmpty()) {
	    operatore.setEmail(email);
	}
	
	if(!telefono.isEmpty()) {
	    operatore.setTelefono(telefono);
	}
	
	if(!sede.isEmpty()) {
	    operatore.setIdSede(Integer.parseInt(sede));
	} else {
	    operatore.setIdSede(Sede.DEFAULT_ID);
	}
    }
}