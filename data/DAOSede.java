package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.GestioneSedeService;
import business.Risposta;
import business.Sede;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano le sedi.
 */
public class DAOSede extends DAOService {

    private static final String QUERY_INSERIMENTO = "INSERT INTO SEDE VALUES (?, ?, ?);";
    
    private static final String QUERY_MODIFICA = "UPDATE SEDE ";
    
    private static final String QUERY_ELIMINAZIONE = "DELETE FROM SEDE WHERE NUMERO = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM SEDE ";

    /**
     * Restituisce un'istanza di questa classe.
     * @throws SQLException : se la connessione con il database non e' presente.
     */
    public DAOSede() throws SQLException {
	super();
    }

    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneSedeService#aggiungiNuovaSede(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Sede sede = (Sede) dati.prendiOggettoDaIndice(0);
	int id = sede.getNumero();
	String nome = sede.getNome();
	String indirizzo = sede.getIndirizzo();
	
	PreparedStatement queryInserimento;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimento = this.connessione.prepareStatement(QUERY_INSERIMENTO);
	    queryInserimento.setInt(1, id);
	    queryInserimento.setString(2, nome);
	    queryInserimento.setString(3, indirizzo);
	    int esito = queryInserimento.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    }
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di modificare i dati nel database.
     * @param dati : dati da modificare.
     * @return l'esito della modifica.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneSedeService#modificaSede(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Sede sede = (Sede) dati.prendiOggettoDaIndice(0);
	int id = sede.getNumero();
	String nome = sede.getNome();
	String indirizzo = sede.getIndirizzo();
	
	PreparedStatement queryModifica;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_MODIFICA);
	    boolean valoreInserito = false;
	    
	    if(nome != null) {
		queryCompleta.append("SET NOME = '" +nome+ "' ");
		valoreInserito = true;
	    }
	    
	    if(indirizzo != null) {
		if(valoreInserito) {
		    queryCompleta.append(", INDIRIZZO = '" +indirizzo+ "' ");
		} else {
		    queryCompleta.append("SET INDIRIZZO = '" +indirizzo+ "' ");
		    valoreInserito = true;
		}
	    }
	    queryCompleta.append("WHERE NUMERO = '" +id+ "';");
	    
	    queryModifica = this.connessione.prepareStatement(queryCompleta.toString());
	    int esito = queryModifica.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_MODIFICA_DATI);
	    } else {
		risposta.setId(CODICE_MODIFICA_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di eliminare i dati dal database.
     * @param dati : dati da eliminare.
     * @return l'esito dell'eliminazione.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneSedeService#eliminaSede(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Sede sede = (Sede) dati.prendiOggettoDaIndice(0);
	int id = sede.getNumero();
	PreparedStatement queryEliminazione;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazione = this.connessione.prepareStatement(QUERY_ELIMINAZIONE);
	    queryEliminazione.setInt(1, id);
	    int esito = queryEliminazione.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_ELIMINAZIONE_DATI);
	    } else {
		risposta.setId(CODICE_ELIMINAZIONE_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di leggere i dati dal database.
     * @param dati : parametri per la ricerca.
     * @return il risultato della ricerca.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneSedeService#visualizzaSedi(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Sede sede = (Sede) dati.prendiOggettoDaIndice(0);
	int id = sede.getNumero();
	String nome = sede.getNome();
	String indirizzo = sede.getIndirizzo();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    
	    if(id != Sede.DEFAULT_ID) {
		queryCompleta.append("WHERE NUMERO = '" +id+ "' ");
		valoreInserito = true;
	    }
	    
	    if(nome != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND NOME = '" +nome+ "' ");
		} else {
		    queryCompleta.append("WHERE NOME = '" +nome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(indirizzo != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND INDIRIZZO = '" +indirizzo+ "' ");
		} else {
		    queryCompleta.append("WHERE INDIRIZZO = '" +indirizzo+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    queryCompleta.append(";");
	    
	    queryLettura = this.connessione.prepareStatement(queryCompleta.toString());
	    
	    ResultSet risultato = queryLettura.executeQuery();
	    
	    if(riempiBO(risposta, risultato)) {
		risposta.setId(CODICE_RESULTSET_PIENO);;
	    } else {	//Nessuna fascia trovata
		risposta.setId(CODICE_RESULTSET_VUOTO);
	    }
	} catch (SQLException e) {
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
	    int id = risultato.getInt(1);
	    String nome = risultato.getString(2);
	    String indirizzo = risultato.getString(3);

	    Sede sede = new Sede();
	    sede.setNumero(id);
	    sede.setNome(nome);
	    sede.setIndirizzo(indirizzo);

	    risposta.aggiungiOggetto(sede);
	}
	return valoriEsistenti;
    }
}