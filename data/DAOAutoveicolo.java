package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.Autoveicolo;
import business.GestioneAutoveicoloService;
import business.Risposta;
import business.Autoveicolo.Stato;
import business.Sede;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano gli autoveicoli.
 */
public class DAOAutoveicolo extends DAOService {

    private static final String QUERY_INSERIMENTO = "INSERT INTO AUTOVEICOLO VALUES (?, ?, ?, ?, ?);";
    
    private static final String QUERY_MODIFICA = "UPDATE AUTOVEICOLO ";
    
    private static final String QUERY_ELIMINAZIONE = "DELETE FROM AUTOVEICOLO WHERE TARGA = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM AUTOVEICOLO ";
    
    /**
     * Restituisce un'istanza di questa classe.
     * @throws SQLException : se la connessione con il database non e' presente.
     */
    public DAOAutoveicolo() throws SQLException {
	super();
    }
    
    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneAutoveicoloService#aggiungiNuovoAutoveicolo(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
	String targa = auto.getTarga();
	String modello = auto.getIdModello();
	String stato = auto.getStato().toString();
	int sede = auto.getIdSede();
	double chilometraggio = auto.getChilometraggio();
	
	PreparedStatement queryInserimento;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimento = this.connessione.prepareStatement(QUERY_INSERIMENTO);
	    queryInserimento.setString(1, targa);
	    queryInserimento.setString(2, modello);
	    queryInserimento.setString(3, stato);
	    queryInserimento.setInt(4, sede);
	    queryInserimento.setDouble(5, chilometraggio);
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
     * @see GestioneAutoveicoloService#modificaAutoveicolo(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
	String targa = auto.getTarga();
	String modello = auto.getIdModello();
	Stato stato = auto.getStato();
	int sede = auto.getIdSede();
	double chilometraggio = auto.getChilometraggio();
	
	PreparedStatement queryModifica;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_MODIFICA);
	    boolean valoreInserito = false;
	    
	    
	    if(modello != null) {
		queryCompleta.append(", MODELLO = '" +modello+ "' ");
		valoreInserito = true;
	    }
	    
	    if(stato != null) {
		if(valoreInserito) {
		    queryCompleta.append(", STATO = '" +stato.toString()+ "' ");
		} else {
		    queryCompleta.append("SET STATO = '" +stato.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sede != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append(", SEDE = ");
		    if(sede == Sede.ID_NESSUNA_SEDE) {
			queryCompleta.append("NULL "); 
		    }else {
			queryCompleta.append("'" +sede+ "' ");
		    }
		} else {
		    queryCompleta.append("SET SEDE = '" +sede+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(chilometraggio != 0) {
		if(valoreInserito) {
		    queryCompleta.append(", CHILOMETRAGGIO = '" +chilometraggio+ "' ");
		} else {
		    queryCompleta.append("SET CHILOMETRAGGIO = '" +chilometraggio+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    queryCompleta.append("WHERE TARGA = '" +targa+ "';");
	    
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
     * @see GestioneAutoveicoloService#eliminaAutoveicolo(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
	String targa = auto.getTarga();
	PreparedStatement queryEliminazione;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazione = this.connessione.prepareStatement(QUERY_ELIMINAZIONE);
	    queryEliminazione.setString(1, targa);
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
     * @see GestioneAutoveicoloService#visualizzaAutoveicoli(TO)
     * @see GestioneAutoveicoloService#visualizzaAutoveicoliCliente(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
	String targa = auto.getTarga();
	String modello = auto.getIdModello();
	Stato stato = auto.getStato();
	int sede = auto.getIdSede();
	double chilometraggio = auto.getChilometraggio();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    if(targa != null) {
		queryCompleta.append("WHERE TARGA = '" +targa+ "' ");
		valoreInserito = true;
	    }
	    
	    if(modello != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND MODELLO = '" +modello+ "' ");
		} else {
		    queryCompleta.append("WHERE MODELLO = '" +modello+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(stato != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND STATO = '" +stato.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE STATO = '" +stato.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sede != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append("AND SEDE = '" +sede + "' ");
		} else {
		    queryCompleta.append("WHERE SEDE = '" +sede + "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(chilometraggio != 0) {
		if(valoreInserito) {
		    queryCompleta.append("AND CHILOMETRAGGIO = '" +chilometraggio+ "' ");
		} else {
		    queryCompleta.append("WHERE CHILOMETRAGGIO = '" +chilometraggio+ "' ");
		    valoreInserito = true;
		}
	    }
	    queryCompleta.append(";");
	    
	    queryLettura = this.connessione.prepareStatement(queryCompleta.toString());
	    
	    ResultSet risultato = queryLettura.executeQuery();
	    
	    if(riempiBO(risposta, risultato)) {
		risposta.setId(CODICE_RESULTSET_PIENO);
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
	    String targa = risultato.getString(1);
	    String modello = risultato.getString(2);
	    String stato = risultato.getString(3);
	    int sede = risultato.getInt(4);
	    double chilometraggio = risultato.getDouble(5);

	    Autoveicolo auto = new Autoveicolo();
	    auto.setTarga(targa);
	    auto.setIdModello(modello);
	    auto.setStato(Stato.valueOf(stato));
	    auto.setIdSede(sede);
	    auto.setChilometraggio(chilometraggio);

	    risposta.aggiungiOggetto(auto);
	}
	return valoriEsistenti;
    } 
}