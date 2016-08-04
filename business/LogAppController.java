package business;

import presentation.controllerSchermate.comuni.ControllerSchermataLogin;

/**
 * Application controller che gestisce le richieste che vengono generate all'atto di autenticazione e <nbsp>
 * deautenticazione di un utente dal sistema.
 */
public class LogAppController implements ApplicationController {

    private static LogAppController lap;
    
    /**
     * Ritorna l'unica istanza di questo Application controller.
     * @return l'istanza di Application controller per l'amministratore.
     */
    public static LogAppController getInstance() {
	if(lap == null) {
	    lap = new LogAppController();
	}
	return lap;
    }
    
    /**
     * Effettua il dispatching della richiesta al livello sottostante.
     * @param richiesta : la richiesta da soddisfare.
     * @return il risultato proveniente dall'elaborazione della richiesta.
     */
    @Override
    public TO gestisciRichiesta(TO richiesta) {
	Risposta risposta = new Risposta();
	
	if(richiesta.getId().equals(ControllerSchermataLogin.ID_LOGIN)) {
	    risposta = (Risposta) GestioneLogService.autentica(richiesta);
	} else if(richiesta.getId().equals(ControllerSchermataLogin.ID_LOGOUT)) {
	    risposta = (Risposta) GestioneLogService.deAutentica();
	}
	
	return risposta;
    }
}