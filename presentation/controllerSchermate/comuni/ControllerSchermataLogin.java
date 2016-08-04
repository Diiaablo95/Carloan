package presentation.controllerSchermate.comuni;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import business.Richiesta;
import business.Utente;

/**
 * Controller che gestisce gli eventi riguardanti l'autenticazione di un utente nel sistema.
 */
public class ControllerSchermataLogin implements Initializable {

    /**
     * Il percorso al quale si trova il file relativo alla schermata di login.
     */
    public static final String PERCORSO_SCHERMATA_LOGIN = "/schermate/comuni/SchermataLogin.fxml";
    
    /**
     * Codice univoco per l'operazione di autenticazione nel sistema.
     */
    public static final String ID_LOGIN = "C00";
    
    /**
     * Codice univoco per l'operazione di logout.
     */
    public static final String ID_LOGOUT = "C01";
    
    private static final String ERRORE_CREDENZIALI_ERRATE = "Credenziali inserite non valide. Riprovare.";
    private static final String ERRORE_CAMPO_VUOTO = "Completare entrambi i campi richiesti.";

    @FXML
    private TextField campoUtente, campoPassword;
    @FXML
    private Label labelErrore;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Mostra la schermata principale dell'utente in base alla tipologia di utente autenticato.
     */
    public void log() {
	String username = this.campoUtente.getText();
	String password = this.campoPassword.getText();
	
	if(username.isEmpty() || password.isEmpty()) {
	    this.mostraMessaggioErrore(ERRORE_CAMPO_VUOTO);
	} else {
	    Utente user = new Utente();
	    user.setUsername(username);
	    user.setPassword(password);
	    
	    Richiesta richiesta = new Richiesta();
	    richiesta.setId(ID_LOGIN);
	    richiesta.aggiungiOggetto(user);
	    
	    FrontController.processaRichiesta(richiesta);
	}
    }
    
    /**
     * Mostra nuovamente la schermata di login, deautenticando l'utente dal sistema.
     */
    public static void logout() {
	Richiesta richiesta = new Richiesta();
	richiesta.setId(ID_LOGOUT);
	
	FrontController.processaRichiesta(richiesta);
    }
    
    /**
     * Consente di visualizzare, nel relativo label, un messaggio di errore relativo all'errore nell'inserimento delle credenziali.
     */
    public void mostraMessaggioErroreCredenziali() {
	this.mostraMessaggioErrore(ERRORE_CREDENZIALI_ERRATE);
    }
    
    private void mostraMessaggioErrore(String messaggio) {
	this.labelErrore.setText(messaggio);
    }
}