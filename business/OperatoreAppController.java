package business;

/**
 * Application controller che gestisce le richieste che possono essere generate dall'interazione di un operatore col sistema.
 */
public class OperatoreAppController implements ApplicationController {

    private static OperatoreAppController oap;
    
    /**
     * Ritorna l'unica istanza di questo Application controller.
     * @return l'istanza di Application controller per l'operatore.
     */
    public static OperatoreAppController getInstance() {
	if(oap == null) {
	    oap = new OperatoreAppController();
	}
	return oap;
    }
    
    /**
     * Effettua il dispatching della richiesta al livello sottostante.
     * @param richiesta : la richiesta da soddisfare.
     * @return il risultato proveniente dall'elaborazione della richiesta.
     */
    @Override
    public TO gestisciRichiesta(TO richiesta) {
	Risposta risposta = null;
	int tipoOperazione = ApplicationController.ottieniTipoOperazione(richiesta.getId());
	BusinessObject oggetto = richiesta.prendiOggettoDaIndice(0);
	
	switch(tipoOperazione) {
    		case ApplicationController.INSERIMENTO : {
    		    if(oggetto.getClass() == Cliente.class) {
    			risposta = (Risposta) GestioneClienteService.aggiungiNuovoCliente(richiesta);
    		    }
    		} break;
    		case ApplicationController.MODIFICA : {
    		    if(oggetto.getClass() == Autoveicolo.class) {
        		risposta = (Risposta) GestioneAutoveicoloService.modificaAutoveicolo(richiesta);
        	    } else if(oggetto.getClass() == Cliente.class) {
        		risposta = (Risposta) GestioneClienteService.modificaCliente(richiesta);
        	    } else if(oggetto.getClass() == Noleggio.class) {
        		risposta = (Risposta) GestioneNoleggioService.modificaNoleggio(richiesta);
        	    }
        	} break;
    		case ApplicationController.ELIMINAZIONE : {
    		    if(oggetto.getClass() == Cliente.class) {
    			risposta = (Risposta) GestioneClienteService.eliminaCliente(richiesta);
    		    } else if(oggetto.getClass() == Noleggio.class) {
    			risposta = (Risposta) GestioneNoleggioService.eliminaNoleggio(richiesta);
    		    }
    		} break;
    		case ApplicationController.RICERCA : {
    		    if(oggetto.getClass() == Autoveicolo.class) {
    			risposta = (Risposta) GestioneAutoveicoloService.visualizzaAutoveicoli(richiesta);
    		    } else if(oggetto.getClass() == Noleggio.class) {
    			risposta = (Risposta) GestioneNoleggioService.visualizzaNoleggi(richiesta);
    		    } else if(oggetto.getClass() == Cliente.class) {
    			risposta = (Risposta) GestioneClienteService.visualizzaClienti(richiesta);
    		    }
    		} break;
    		case ApplicationController.CHIUSURA_NOLEGGIO_PROVVISORIA : {
    		    if(oggetto.getClass() == Noleggio.class) {
    			risposta = (Risposta) GestioneNoleggioService.chiudiNoleggioProvvisorio(richiesta);
    		    }
    		} break;
     		case ApplicationController.CHIUSURA_NOLEGGIO_DEFINITIVA : {
    		    if(oggetto.getClass() == Noleggio.class) {
    			risposta = (Risposta) GestioneNoleggioService.chiudiNoleggio(richiesta);
    		    }
    		} break;
	}
	return risposta;
    }
}