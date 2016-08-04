package presentation;

import hashing.MD5Hasher;

import presentation.controllerSchermate.amministratore.ControllerSchermataOperatore;
import presentation.controllerSchermate.comuni.ControllerSchermataLogin;
import presentation.controllerSchermate.operatore.ControllerSchermataCliente;
import business.AppControllerFactory;
import business.ApplicationController;
import business.Richiesta;
import business.Risposta;
import business.TO;
import business.Utente;

/**
 * Classe che funge da unico punto di accesso per la gestione delle richieste da parte dei controller di tutte le schermate.
 */
public class FrontController {
    
    /**
     * Metodo che consente di veicolare la richiesta da soddisfare ai livelli sottostanti.
     * Essa funge da punto di accesso unico per tutte le richieste da soddisfare.
     * @param richiesta : la richiesta da soddisfare.
     */
    public static void processaRichiesta(TO richiesta) {
	cifraPassword(richiesta);
	ApplicationController ac = AppControllerFactory.getController(richiesta.getId());
	Risposta risultatoRichiesta = (Risposta) ac.gestisciRichiesta(richiesta);
	ViewDispatcher.gestisciRisultato((Richiesta) richiesta, risultatoRichiesta);
    }
    
    //CIFRATURA DELLA PASSWORD. CONVIENE FARLA A LIVELLO DI PRESENTAZIONE NEL CASO IN CUI L'APPLICAZIONE FOSSE MULTI-TIER.
    private static void cifraPassword(TO richiesta) {
	if(richiesta.getId().equals(ControllerSchermataLogin.ID_LOGIN) ||
		richiesta.getId().equals(ControllerSchermataCliente.ID_INSERIMENTO_CLIENTE) ||
		richiesta.getId().equals(ControllerSchermataCliente.ID_MODIFICA_CLIENTE) || 
		richiesta.getId().equals(ControllerSchermataCliente.ID_VISUALIZZAZIONE_CLIENTI) ||
		richiesta.getId().equals(ControllerSchermataOperatore.ID_INSERIMENTO_OPERATORE) ||
		richiesta.getId().equals(ControllerSchermataOperatore.ID_MODIFICA_OPERATORE) ||
		richiesta.getId().equals(ControllerSchermataOperatore.ID_VISUALIZZAZIONE_OPERATORI)) {
	    Utente utente = (Utente) richiesta.prendiOggettoDaIndice(0);
	    String passwordDaCifrare = utente.getPassword();
	    
	    if(passwordDaCifrare != null) {
		utente.setPassword(MD5Hasher.getInstance().hash(passwordDaCifrare));
	    }
	}
    }
}