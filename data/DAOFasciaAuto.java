package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.FasciaAuto;
import business.GestioneFasciaAutoService;
import business.Risposta;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano le fasce di autoveicolo.
 */
public class DAOFasciaAuto extends DAOService {

    private static final String QUERY_INSERIMENTO = "INSERT INTO FASCIAAUTO VALUES (?, ?);";
    
    private static final String QUERY_MODIFICA = "UPDATE FASCIAAUTO SET PREZZO = ? WHERE ID = ?;";
    
    private static final String QUERY_ELIMINAZIONE = "DELETE FROM FASCIAAUTO WHERE ID = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM FASCIAAUTO ";
    
    /**
     * Crea un'istanza per questo DAO.
     * @throws SQLException : errore di connessione al database.
     */
    public DAOFasciaAuto() throws SQLException {
	super();
    }

    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneFasciaAutoService#aggiungiNuovaFascia(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {		//Impossibile una duplicazione perche' ID gestito dal sistema.
	FasciaAuto fascia = (FasciaAuto) dati.prendiOggettoDaIndice(0);
	int id = fascia.getId();
	double prezzoChilometrico = fascia.getPrezzoKm();
	PreparedStatement queryInserimento;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimento = this.connessione.prepareStatement(QUERY_INSERIMENTO);
	    queryInserimento.setInt(1, id);
	    queryInserimento.setDouble(2, prezzoChilometrico);
	    int esito = queryInserimento.executeUpdate();	//executeUpdate usata per inserimenti, modifiche ed eliminazioni
	    
	    if(esito == 0) {	//Per qualche altra ragione apparte la chiave duplicata, che non e' possibile
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();		//Unica riga che rilancia all'esterno una SQLException
	}
	return risposta;
    }

    /**
     * Permette di modificare i dati nel database.
     * @param dati : dati da modificare.
     * @return l'esito della modifica.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneFasciaAutoService#modificaFascia(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	FasciaAuto fascia = (FasciaAuto) dati.prendiOggettoDaIndice(0);
	int id = fascia.getId();
	double nuovoPrezzoChilometrico = fascia.getPrezzoKm();
	PreparedStatement queryModifica;
	Risposta risposta = new Risposta();
	
	try {
	    queryModifica = this.connessione.prepareStatement(QUERY_MODIFICA);
	    queryModifica.setDouble(1, nuovoPrezzoChilometrico);
	    queryModifica.setInt(2, id);
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
     * @see GestioneFasciaAutoService#eliminaFascia(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	FasciaAuto fascia = (FasciaAuto) dati.prendiOggettoDaIndice(0);
	int idFasciaDaEliminare = fascia.getId();
	PreparedStatement queryEliminazione;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazione = this.connessione.prepareStatement(QUERY_ELIMINAZIONE);
	    queryEliminazione.setInt(1, idFasciaDaEliminare);
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
     * @see GestioneFasciaAutoService#visualizzaFasce(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	FasciaAuto fascia = (FasciaAuto) dati.prendiOggettoDaIndice(0);
	int idFasciaDaLeggere = fascia.getId();
	double prezzoKmDaLeggere = fascia.getPrezzoKm();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    if(idFasciaDaLeggere != FasciaAuto.DEFAULT_ID) {	//Vuol dire che l'ID appartiene ai parametri di ricerca
		queryCompleta.append(" WHERE ID = '" +idFasciaDaLeggere+ "' ");
		valoreInserito = true;
	    }
	    
	    if(prezzoKmDaLeggere != 0) {
		if(valoreInserito) {
		    queryCompleta.append("AND PREZZO = '" +prezzoKmDaLeggere+ "' ");
		} else {
		    queryCompleta.append(" WHERE PREZZO = '" +prezzoKmDaLeggere+ "' ");
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
	    int id = risultato.getInt(1);		//Sappiamo che sono sempre quelli i campi perche' facciamo la select *.
	    double prezzo = risultato.getDouble(2);

	    FasciaAuto fascia = new FasciaAuto();
	    fascia.setId(id);
	    fascia.setPrezzoKm(prezzo);
	    risposta.aggiungiOggetto(fascia);
	}
	return valoriEsistenti;
    }
}