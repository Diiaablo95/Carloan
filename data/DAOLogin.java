package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.Risposta;
import business.TO;
import business.Utente;
import business.Utente.Tipo;

/**
 * Classe che implementa i meccanismi per la ricerca di informazioni sulle credenziali di accesso di tutti gli utenti.
 */
public class DAOLogin extends DAO {
    
    /**
     * Query eseguita dal database per la restituzione delle credenziali di accesso di uno specifico utente.
     */
    public static final String QUERY_CREDENZIALI = "SELECT * FROM CREDENZIALI WHERE USERNAME = ? AND PASSWORD = ?;";

    /**
     * Costruttore che restituisce un'istanza di questa classe.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public DAOLogin() throws SQLException{
	super();
    }

    @Override
    /**
     * Permette di leggere i dati dal database.
     * @param dati : parametri per la ricerca.
     * @return il risultato della ricerca.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public TO leggi(TO dati) throws SQLException {
	Utente utente = (Utente) dati.prendiOggettoDaIndice(0);
	String user = utente.getUsername();
	String password = utente.getPassword();
	PreparedStatement queryCredenziali;
	Risposta risposta = new Risposta();
	try {
	    queryCredenziali = this.connessione.prepareStatement(QUERY_CREDENZIALI);
	    queryCredenziali.setString(1, user);
	    queryCredenziali.setString(2, password);
	    ResultSet risultato = queryCredenziali.executeQuery();
	    
	    /*Se le credenziali sono corrette, allora username e password inseriti rimangono invariati
	      e verra' solo aggiunto il campo relativo alla tipologia di utente loggato.*/
	    if(riempiBO(risposta, risultato)) {
		risposta.setId(CODICE_RESULTSET_PIENO);
	    } else {	//Nessuna fascia trovata
		risposta.setId(CODICE_RESULTSET_VUOTO);
	    }
	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }
    
    private static boolean riempiBO(Risposta risposta, ResultSet risultato) throws SQLException {
	boolean valoriEsistenti = false;
	while(risultato.next()) {
	    valoriEsistenti = true;
	    String username = risultato.getString(1);
	    String password = risultato.getString(2);
	    String tipo = risultato.getString(3);
	    
	    Utente utente = new Utente();
	    utente.setUsername(username);
	    utente.setPassword(password);
	    utente.setTipo(Tipo.valueOf(tipo));
	    
	    risposta.aggiungiOggetto(utente);
	}
	return valoriEsistenti;
    }
}