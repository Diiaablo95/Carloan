package utility;

/**
 * Eccezione generata nel caso in cui la data finale inserita si trovi temporalmente prima della data iniziale.
 */
@SuppressWarnings("serial")
public class OrdineDateException extends RuntimeException {

    /**
     * Ritorna un'istanza di questa classe.
     * @param m : il messaggio da mostrare in caso di generazione di questa eccezione.
     */
    public OrdineDateException(String m) {
	super(m);
    }
}