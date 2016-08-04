package fileManagement;

/**
 * Eccezione generata se si cerca di inserire una nuova entita' con lo stesso nome di un'altra gia' presente all'interno del documento.
 */
@SuppressWarnings("serial")
public class EntitaEsistenteException extends Exception {
    
    /**
     * Restituisce una nuova istanza di questa classe.
     * @param m : il messaggio da mostrare a video.
     */
    public EntitaEsistenteException(String m) {
	super(m);
    }
}
