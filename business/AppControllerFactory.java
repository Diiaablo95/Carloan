package business;

import java.io.File;

import presentation.controllerSchermate.comuni.ControllerSchermataLogin;
import risorse.GestoreFileTXT;
import fileManagement.TXTManager;

/**
 * Classe che restituisce il corretto Application controller in base al tipo di utente che si e' autenticato nel sistema.
 */
public class AppControllerFactory {
    
    private static final String NOME_CLIENTE_LOGGATO = "CLIENTE";
    private static final String NOME_OPERATORE_LOGGATO = "OPERATORE";
    private static final String NOME_AMMINISTRATORE_LOGGATO = "AMMINISTRATORE";
    
    /**
     * Restituisce la corretta istanza di Application controller.
     * @return l'istanza di Application controller corrispondente al tipo di utente autenticato.
     */
    public static ApplicationController getController(String idOperazione) {
	ApplicationController controller = null;
	TXTManager txtM = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));
	//Se non c'e' il cookie o se l'operazione richiesta e' quella di logout istanzia il service per il log(generico).
	if(txtM.vuoto() || idOperazione.equals(ControllerSchermataLogin.ID_LOGOUT)) {
	    LogAppController lac = LogAppController.getInstance();
	    controller = lac;
	} else {
	    //Verifica la tipologia di utente loggato, prendendola dal cookie creato dal service di login.
	    String tipoUtenteAutenticato = null;
	    tipoUtenteAutenticato = txtM.leggi(GestoreFileTXT.NOME_TIPO_COOKIE);
	    
	    if(tipoUtenteAutenticato.equalsIgnoreCase(NOME_CLIENTE_LOGGATO)) {
		ClienteAppController cac = ClienteAppController.getInstance();
		controller = cac;
	    } else if(tipoUtenteAutenticato.equalsIgnoreCase(NOME_OPERATORE_LOGGATO)) {
		OperatoreAppController oac = OperatoreAppController.getInstance();
		controller = oac;
	    } else if(tipoUtenteAutenticato.equalsIgnoreCase(NOME_AMMINISTRATORE_LOGGATO)) {
		AmministratoreAppController aac = AmministratoreAppController.getInstance();
		controller = aac;
	    }
	}
	return controller;
    }
}