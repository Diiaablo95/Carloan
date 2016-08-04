package presentation.controllerSchermate.comuni;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Classe che mette a disposizione i metodi comuni a tutti i controller delle schermate principali per ogni tipo di utente.
 */
public abstract class ControllerSchermataPrincipale implements Initializable {
    
    private static final String TITOLO_ALERT = "Conferma dell'operazione.";
    private static final String HEADER_ALERT = "CONFERMA";
    private static final String TESTO_ALERT = "Si desidera confermare l'operazione?";
    
    private String messaggioBenvenuto = "Benvenuto, *!";
    
    @FXML
    protected Label labelUsername;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Mostra nuovamente la schermata di login. Il tasto per il logout e' presente in tutte e sole le schermate principali.
     */
    public void logout() {
	ControllerSchermataLogin.logout();
    }
    
    /**
     * Consente di impostare lo username dell'utente autenticato in modo tale da poterlo utilizzare.
     * @param username : username dell'utente autenticato.
     */
    public void setUserName(String username) {
	messaggioBenvenuto = messaggioBenvenuto.replace("*", username);	//Sostituiamo all'asterisco il nome dell'utente loggato.
	this.labelUsername.setText(messaggioBenvenuto);
    }
    
    /**
     * Genera l'alert per la conferma di un'azione distruttiva.
     * @return l'esito della scelta dell'utente. True o false a seconda se l'utente confermi o rifiuti l'operazione.
     */
    public static boolean alertConfermato() {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(TITOLO_ALERT);
	alert.setHeaderText(HEADER_ALERT);
	alert.setContentText(TESTO_ALERT);
	Optional<ButtonType> esito = alert.showAndWait();
	return esito.get() == ButtonType.OK;
    }
}