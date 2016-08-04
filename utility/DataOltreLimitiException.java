package utility;

/**
 * Eccezione generata nel caso in cui una delle date inserite sia collocata temporalmente al di fuori dei limiti consentiti.
 */
@SuppressWarnings("serial")
public class DataOltreLimitiException extends RuntimeException {
    
    /**
     * Ritorna un'istanza di questa classe.
     * @param m : il messaggio da mostrare in caso di generazione di questa eccezione.
     */
    public DataOltreLimitiException(String m) {
	super(m);
    }
}