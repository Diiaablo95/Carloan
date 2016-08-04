package business;

/**
 * Application controller che gestisce le richieste che possono essere generate dall'interazione di un cliente col sistema.
 */
public class ClienteAppController implements ApplicationController {

    private static ClienteAppController cap;
    
    /**
     * Ritorna l'unica istanza di questo Application controller.
     * @return l'istanza di Application controller per il cliente.
     */
    public static ClienteAppController getInstance() {
	if(cap == null) {
	    cap = new ClienteAppController();
	}
	return cap;
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
		    if(oggetto.getClass() == Noleggio.class) {
			risposta = (Risposta) GestioneNoleggioService.apriNoleggio(richiesta);
		    }
		} break;
		case ApplicationController.RICERCA : {
		    if(oggetto.getClass() == Autoveicolo.class) {
			risposta = (Risposta) GestioneAutoveicoloService.visualizzaAutoveicoliCliente(richiesta);
		    } else if(oggetto.getClass() == Noleggio.class) {
			risposta = (Risposta) GestioneNoleggioService.visualizzaNoleggiCliente(richiesta);
		    }
		} break;
	}
	return risposta;
    }
}