package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.FasciaAuto;
import business.GestioneModelloService;
import business.Modello;
import business.Modello.Cambio;
import business.Modello.Carrozzeria;
import business.Modello.Motore;
import business.Modello.Porte;
import business.Risposta;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano i modelli di autoveicolo.
 */
public class DAOModello extends DAOService {

    private static final String QUERY_INSERIMENTO = "INSERT INTO MODELLO VALUES (?, ?, ?, ?, ?, ?);";
    
    private static final String QUERY_MODIFICA = "UPDATE MODELLO ";
    
    private static final String QUERY_ELIMINAZIONE = "DELETE FROM MODELLO WHERE ID = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM MODELLO ";

    /**
     * Crea un'istanza per questo DAO.
     * @throws SQLException : errore di connessione al database.
     */
    public DAOModello() throws SQLException {
	super();
    }
    
    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneModelloService#aggiungiNuovoModello(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Modello modello = (Modello) dati.prendiOggettoDaIndice(0);
	String id = modello.getId();
	String porte = modello.getPorte().toString();
	String cambio = modello.getCambio().toString();
	String carrozzeria = modello.getCarrozzeria().toString();
	String motore = modello.getMotore().toString();
	int idFascia = modello.getIdFascia();
	
	PreparedStatement queryInserimento;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimento = this.connessione.prepareStatement(QUERY_INSERIMENTO);
	    queryInserimento.setString(1, id);
	    queryInserimento.setString(2, porte);
	    queryInserimento.setString(3, cambio);
	    queryInserimento.setString(4, carrozzeria);
	    queryInserimento.setString(5, motore);
	    queryInserimento.setInt(6, idFascia);
	    int esito = queryInserimento.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {	//Qui puo' esserci un inserimento di chiave duplicata.
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);		//Altrimenti l'errore e' dovuto ad altre cause esterne.
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
     * @see GestioneModelloService#modificaModello(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Modello modello = (Modello) dati.prendiOggettoDaIndice(0);
	String id = modello.getId();
	Porte porte = modello.getPorte();
	Cambio cambio = modello.getCambio();
	Carrozzeria carrozzeria = modello.getCarrozzeria();
	Motore motore = modello.getMotore();
	int idFascia = modello.getIdFascia();
	
	PreparedStatement queryModifica;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_MODIFICA);
	    boolean valoreInserito = false;
	    
	    if(porte != null) {
		queryCompleta.append("SET PORTE = '" +porte.toString()+ "' ");
		valoreInserito = true;
	    }
	    
	    if(cambio != null) {
		if(valoreInserito) {
		    queryCompleta.append(", CAMBIO = '" +cambio.toString()+ "' ");
		} else {
		    queryCompleta.append("SET CAMBIO = '" +cambio.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(carrozzeria != null) {
		if(valoreInserito) {
		    queryCompleta.append(", CARROZZERIA = '" +carrozzeria.toString()+ "' ");
		} else {
		    queryCompleta.append("SET CARROZZERIA = '" +carrozzeria.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(motore != null) {
		if(valoreInserito) {
		    queryCompleta.append(", MOTORE = '" +motore.toString()+ "' ");
		} else {
		    queryCompleta.append("SET MOTORE = '" +motore.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(idFascia != FasciaAuto.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append(", IDFASCIA = '" +idFascia+ "' ");
		} else {
		    queryCompleta.append("SET IDFASCIA = '" +idFascia+ "' ");
		    valoreInserito = true;
		}
	    }
	    queryCompleta.append("WHERE ID = '" +id+ "';");	//L'id e' obbligatorio nella ricerca, e viene sempre aggiunto alla fine.
	    
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
     * @see GestioneModelloService#eliminaModello(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Modello modello = (Modello) dati.prendiOggettoDaIndice(0);
	String idModelloDaEliminare = modello.getId();
	PreparedStatement queryEliminazione;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazione = this.connessione.prepareStatement(QUERY_ELIMINAZIONE);
	    queryEliminazione.setString(1, idModelloDaEliminare);
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
     * @see GestioneModelloService#visualizzaModelli(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Modello modello = (Modello) dati.prendiOggettoDaIndice(0);
	String idModelloDaLeggere = modello.getId();
	Porte porte = modello.getPorte();
	Cambio cambio = modello.getCambio();
	Carrozzeria carrozzeria = modello.getCarrozzeria();
	Motore motore = modello.getMotore();
	int idFascia = modello.getIdFascia();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    if(idModelloDaLeggere != null) {	//Vuol dire che l'ID appartiene ai parametri di ricerca
		queryCompleta.append("WHERE ID = '" +idModelloDaLeggere+ "' ");
		valoreInserito = true;
	    }
	    
	    if(porte != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND PORTE = '" +porte.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE PORTE = '" +porte.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(cambio != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CAMBIO = '" +cambio.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE CAMBIO = '" +cambio.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(carrozzeria != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CARROZZERIA = '" +carrozzeria.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE CARROZZERIA = '" +carrozzeria.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(motore != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND MOTORE = '" +motore.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE MOTORE = '" +motore.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(idFascia != FasciaAuto.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append("AND IDFASCIA = '" +idFascia+ "' ");
		} else {
		    queryCompleta.append("WHERE IDFASCIA = '" +idFascia+ "' ");
		    valoreInserito = true;
		}
	    }
	    queryCompleta.append(";");		//Si aggiunge il ; per terminare correttamente la query.
	    
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
	    String id = risultato.getString(1);
	    String porte = risultato.getString(2);
	    String cambio = risultato.getString(3);
	    String carrozzeria = risultato.getString(4);
	    String motore = risultato.getString(5);
	    int idFascia = risultato.getInt(6);

	    Modello modello = new Modello();
	    modello.setId(id);
	    modello.setPorte(Porte.valueOf(porte));
	    modello.setCambio(Cambio.valueOf(cambio));
	    modello.setCarrozzeria(Carrozzeria.valueOf(carrozzeria));
	    modello.setMotore(Motore.valueOf(motore));
	    modello.setIdFascia(idFascia);

	    risposta.aggiungiOggetto(modello);
	}
	return valoriEsistenti;
    }
}