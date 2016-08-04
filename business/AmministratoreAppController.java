package business;

/**
 * Application controller che gestisce le richieste che possono essere generate dall'interazione dell'amministratore col sistema.
 */
public class AmministratoreAppController extends OperatoreAppController {
    
    private static AmministratoreAppController aap;
    
    /**
     * Ritorna l'unica istanza di questo Application controller.
     * @return l'istanza di Application controller per l'amministratore.
     */
    public static AmministratoreAppController getInstance() {
	if(aap == null) {
	    aap = new AmministratoreAppController();
	}
	return aap;
    }
    
    /**
     * Effettua il dispatching della richiesta al livello sottostante.
     * @param richiesta : la richiesta da soddisfare.
     * @return il risultato proveniente dall'elaborazione della richiesta.
     */
    @Override
    public TO gestisciRichiesta(TO richiesta) {
	Risposta risposta = (Risposta) super.gestisciRichiesta(richiesta);
	if(risposta == null) {
	    int tipoOperazione = ApplicationController.ottieniTipoOperazione(richiesta.getId());
	    BusinessObject oggetto = richiesta.prendiOggettoDaIndice(0);
	    
	    switch(tipoOperazione) {
	    	case ApplicationController.INSERIMENTO : {
	    	    if(oggetto.getClass() == Modello.class) {
	    		risposta = (Risposta) GestioneModelloService.aggiungiNuovoModello(richiesta);
	    	    } else if(oggetto.getClass() == Autoveicolo.class) {
	    		risposta = (Risposta) GestioneAutoveicoloService.aggiungiNuovoAutoveicolo(richiesta);
	    	    } else if(oggetto.getClass() == FasciaAuto.class) {
	    		risposta = (Risposta) GestioneFasciaAutoService.aggiungiNuovaFascia(richiesta);
	    	    } else if(oggetto.getClass() == Sede.class) {
	    		risposta = (Risposta) GestioneSedeService.aggiungiNuovaSede(richiesta);
	    	    } else if(oggetto.getClass() == Operatore.class) {
	    		risposta = (Risposta) GestioneOperatoreService.aggiungiNuovoOperatore(richiesta);
	    	    }
	    	} break;
	    	case ApplicationController.MODIFICA : {
	    	    if(oggetto.getClass() == Modello.class) {
	    		risposta = (Risposta) GestioneModelloService.modificaModello(richiesta);
	    	    } else if(oggetto.getClass() == FasciaAuto.class) {
	    		risposta = (Risposta) GestioneFasciaAutoService.modificaFascia(richiesta);
	    	    } else if(oggetto.getClass() == Sede.class) {
	    		risposta = (Risposta) GestioneSedeService.modificaSede(richiesta);
	    	    } else if(oggetto.getClass() == Operatore.class) {
	    		risposta = (Risposta) GestioneOperatoreService.modificaOperatore(richiesta);
	    	    }
	    	} break;
	    	case ApplicationController.ELIMINAZIONE : {
	    	    if(oggetto.getClass() == Modello.class) {
	    		risposta = (Risposta) GestioneModelloService.eliminaModello(richiesta);
	    	    } else if(oggetto.getClass() == Autoveicolo.class) {
	    		risposta = (Risposta) GestioneAutoveicoloService.eliminaAutoveicolo(richiesta);
	    	    } else if(oggetto.getClass() == FasciaAuto.class) {
	    		risposta = (Risposta) GestioneFasciaAutoService.eliminaFascia(richiesta);
	    	    } else if(oggetto.getClass() == Sede.class) {
	    		risposta = (Risposta) GestioneSedeService.eliminaSede(richiesta);
	    	    } else if(oggetto.getClass() == Operatore.class) {
	    		risposta = (Risposta) GestioneOperatoreService.eliminaOperatore(richiesta);
	    	    }
	    	} break;
	    	case ApplicationController.RICERCA : {
	    	    if(oggetto.getClass() == Modello.class) {
	    		risposta = (Risposta) GestioneModelloService.visualizzaModelli(richiesta);
	    	    } else if(oggetto.getClass() == FasciaAuto.class) {
	    		risposta = (Risposta) GestioneFasciaAutoService.visualizzaFasce(richiesta);
	    	    } else if(oggetto.getClass() == Sede.class) {
	    		risposta = (Risposta) GestioneSedeService.visualizzaSedi(richiesta);
	    	    } else if(oggetto.getClass() == Operatore.class) {
	    		risposta = (Risposta) GestioneOperatoreService.visualizzaOperatori(richiesta);
	    	    }
	    	} break;
	    }
	}
	return risposta;
    }
}