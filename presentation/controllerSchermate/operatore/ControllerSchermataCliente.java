package presentation.controllerSchermate.operatore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.Cliente;
import business.Richiesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller che gestisce gli eventi riguardanti la gestione di un cliente da parte di un operatore.
 */
public class ControllerSchermataCliente implements Initializable {

    /**
     * Percorso al quale si trova il file relativo alla schermata di gestione dei clienti.
     */
    public static final String PATH_SCHERMATA_CLIENTI = "/schermate/operatore/SchermataCliente.fxml";
    
    /**
     * Codice univoco per l'operazione di inserimento di un nuovo cliente.
     */
    public static final String ID_INSERIMENTO_CLIENTE = "C12";
    
    /**
     * Codice univoco per l'operazione di modifica di un cliente.
     */
    public static final String ID_MODIFICA_CLIENTE = "C22";
    
    /**
     * Codice univoco per l'operazione di eliminazione di un cliente.
     */
    public static final String ID_ELIMINAZIONE_CLIENTE = "C32";
    
    /**
     * Codice univoco per l'operazione di visualizzazione di clienti.
     */
    public static final String ID_VISUALIZZAZIONE_CLIENTI = "C42";
    
    private static final String ID_BOTTONE_INSERIMENTO = "bottoneInserisci";
    private static final String ID_BOTTONE_MODIFICA = "bottoneModifica";
    private static final String ID_BOTTONE_ELIMINAZIONE = "bottoneElimina";
    private static final String ID_BOTTONE_VISUALIZZAZIONE = "bottoneVisualizza";
    
    @FXML
    private TextField campoUsername, campoPassword, campoNome, campoCognome, campoEmail, campoTelefono, campoCF, campoCarta;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Crea la richiesta che il sistema soddisfera' di gestione di clienti, in base al tasto premuto.
     * @param click : l'evento generato dal click del mouse su uno dei tasti presenti nella schermata.
     */
    public void gestisciCliente(MouseEvent click) {
	Button bottonePremuto = (Button) click.getSource();
	Richiesta richiesta = new Richiesta();
    	
	if(bottonePremuto.getId().equals(ID_BOTTONE_INSERIMENTO)) {
	    richiesta.setId(ID_INSERIMENTO_CLIENTE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_MODIFICA)) {
	    richiesta.setId(ID_MODIFICA_CLIENTE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    richiesta.setId(ID_ELIMINAZIONE_CLIENTE);
	} else if(bottonePremuto.getId().equals(ID_BOTTONE_VISUALIZZAZIONE)) {
	    richiesta.setId(ID_VISUALIZZAZIONE_CLIENTI);
	}
	
	if((bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE) && ControllerSchermataPrincipale.alertConfermato()) || !bottonePremuto.getId().equals(ID_BOTTONE_ELIMINAZIONE)) {
	    Cliente cliente = new Cliente();
	    aggiungiParametri(cliente);
	    richiesta.aggiungiOggetto(cliente);
	    FrontController.processaRichiesta(richiesta);   
	}
    }
    
    //username cliente obbligatorio.
    private void aggiungiParametri(Cliente cliente) {
	String username = this.campoUsername.getText();
	String password = this.campoPassword.getText();
	String nome = this.campoNome.getText();
	String cognome = this.campoCognome.getText();
	String email = this.campoEmail.getText();
	String telefono = this.campoTelefono.getText();
	String cf = this.campoCF.getText();
	String idCarta = this.campoCarta.getText();
	
	if(!username.isEmpty()) {
	    cliente.setUsername(username);
	} 

	if(!password.isEmpty()) {
	    cliente.setPassword(password);
	}
	
	if(!nome.isEmpty()) {
	    cliente.setNome(nome);   
	}
	
	if(!cognome.isEmpty()) {
	    cliente.setCognome(cognome);
	}
	
	if(!email.isEmpty()) {
	    cliente.setEmail(email);   
	}
	
	if(!telefono.isEmpty()) {
	    cliente.setTelefono(telefono);	
	}
	
	if(!cf.isEmpty()) {
	    cliente.setCf(cf);
	}
	
	if(!idCarta.isEmpty()) {
	    cliente.setCarta(idCarta);
	}
    }
}