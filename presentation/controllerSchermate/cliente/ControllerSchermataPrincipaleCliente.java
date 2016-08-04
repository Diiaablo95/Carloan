package presentation.controllerSchermate.cliente;

import java.io.File;

import fileManagement.TXTManager;
import main.Main;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import risorse.GestoreFileTXT;
import javafx.fxml.Initializable;

/**
 * Controller che gestisce gli eventi riguardanti la schermata principale di un cliente.
 */
public class ControllerSchermataPrincipaleCliente extends ControllerSchermataPrincipale implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata principale di un cliente.
     */
    public static final String PATH_SCHERMATA_PRINCIPALE_CLIENTE = "/schermate/cliente/SchermataPrincipaleCliente.fxml";
    
    /**
     * Visualizza la schermata principale del cliente.
     */
    public static void visualizzaSchermataPrincipaleCliente() {
	Main.mostraSchermataAlPath(PATH_SCHERMATA_PRINCIPALE_CLIENTE);
	ControllerSchermataPrincipaleCliente cspc = (ControllerSchermataPrincipaleCliente) Main.getControllerSchermataAttuale();
	cspc.setUserName(new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE)).leggi(GestoreFileTXT.NOME_USERNAME_COOKIE));
    }
    
    /**
     * Mostra la schermata relativa all'effettuazione di un nuovo noleggio da parte del cliente.
     */
    public void effettuaNoleggio() {
	Main.mostraSchermataAlPath(ControllerSchermataEffettuazioneNoleggio.PATH_SCHERMATA_EFFETTUAZIONE_NOLEGGIO);
    }
    
    /**
     * Mostra la schermata relativa alla visualizzazione dei veicoli presenti nella sede di riferimento.
     */
    public void visualizzaAutoveicoli() {
	Main.mostraSchermataAlPath(ControllerSchermataAutoveicoloCliente.PATH_SCHERMATA_VISUALIZZAZIONE_AUTOVEICOLI);
    }
    
    /**
     * Mostra la schermata relativa alla visualizzazione dei noleggi effettuati dal cliente.
     */
    public void visualizzaNoleggi() {
	Main.mostraSchermataAlPath(ControllerSchermataVisualizzazioneNoleggiCliente.PATH_SCHERMATA_VISUALIZZAZIONE_NOLEGGI);
    }
}