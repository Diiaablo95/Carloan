package business;

/**
 * Interfaccia che deve essere implementata da tutti i controller che hanno lo scopo di soddisfare le richieste delle varie<nbsp>
 * tipologie di utente.
 */
public interface ApplicationController {
    
    /**
     * Rappresenta il valore dell'operazione di autenticazione. 
     */
    static final int LOG = 0;
    
    /**
     * Rappresenta il valore dell'operazione di inserimento.
     */
    static final int INSERIMENTO = 1;
    
    /**
     * Rappresenta il valore dell'operazione di modifica.
     */
    static final int MODIFICA = 2;
    
    /**
     * Rappresenta il valore dell'operazione di eliminazione.
     */
    static final int ELIMINAZIONE = 3;
    
    /**
     * Rappresenta il valore dell'operazione di ricerca.
     */
    static final int RICERCA = 4;
    
    /**
     * Rappresenta il valore dell'operazione di chiusura provvisoria di un noleggio.
     */
    static final int CHIUSURA_NOLEGGIO_PROVVISORIA = 5;
    
    /**
     * Rappresenta il valore dell'operazione di chiusura definitiva di un noleggio.
     */
    static final int CHIUSURA_NOLEGGIO_DEFINITIVA = 6;
    
    
    /**
     * Effettua il dispatching della richiesta al livello sottostante.
     * @param richiesta : la richiesta da soddisfare.
     * @return il risultato proveniente dall'elaborazione della richiesta.
     */
    public TO gestisciRichiesta(TO richiesta);
    
    /**
     * Indica quale richiesta, tra quelle indicate sopra, e' da soddisfare.
     * @param codice : il codice della richiesta da soddisfare.
     * @return l'operazione da intraprendere per soddisfare la richiesta.
     */
    public static int ottieniTipoOperazione(String codice) {
	return Integer.parseInt(codice.substring(1, 2));
    }
}