package data;

import java.sql.SQLException;

import business.TO;

/**
 * Classe che implementa i meccanismi per la ricerca di informazioni utili per realizzare i servizi di business.
 */
public abstract class DAOService extends DAO {
    
    public DAOService() throws SQLException {
	super();
    }

    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public abstract TO crea(TO dati) throws SQLException;
    
    /**
     * Permette di modificare i dati nel database.
     * @param dati : dati da modificare.
     * @return l'esito della modifica.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public abstract TO modifica(TO dati) throws SQLException;
    
    /**
     * Permette di eliminare i dati dal database.
     * @param dati : dati da eliminare.
     * @return l'esito dell'eliminazione.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public abstract TO elimina(TO dati) throws SQLException;
}