package business;

import java.sql.SQLException;

import data.DAO;
import data.DAOFasciaAuto;

/**
 * Gestore delle richieste riguardanti uno o piu' fasce d'autoveicolo.
 */
public class GestioneFasciaAutoService {
    
    /**
     * Aggiunge una nuova fascia di autoveicoli nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Per un inserimento corretto e' sufficiente inserire solo il prezzo chilometrico della nuova fascia. L'ID viene generato automaticamente dal sistema.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovaFascia(TO dati) {
    	Risposta risposta;
    	DAOFasciaAuto daoFascia;
    	FasciaAuto fascia = (FasciaAuto) dati.prendiOggettoDaIndice(0);
    	aggiungiCampiDaSistema(fascia);
    	
    	try {
    	    daoFascia = new DAOFasciaAuto();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoFascia.crea(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Modifia i dati relativi ad una fascia di autoveicoli nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la modifica e' necessario inserire l'ID della fascia da modificare con il campo relativo al
     * prezzo chilometrico contenente il nuovo valore che andra' a sostituire il vecchio.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaFascia(TO dati) {
    	Risposta risposta;
    	DAOFasciaAuto daoFascia;
    	
    	try {
    	    daoFascia = new DAOFasciaAuto();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoFascia.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Ricerca nel sistema di permanenza una o piu' fasce secondo i paramentri.
     * @param dati : l'oggetto contenente le chiavi di ricerca.
     * Nessun campo e' obbligatorio ma tutti fungono da parametri di ricerca per il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaFasce(TO dati) {
    	Risposta risposta;
    	DAOFasciaAuto daoFascia;
    	
    	try {
    	    daoFascia = new DAOFasciaAuto();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoFascia.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina una fascia di autoveicoli nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * Per l'eliminazione e' sufficiente inserire l'ID della fascia da eliminare.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaFascia(TO dati) {
    	Risposta risposta;
    	DAOFasciaAuto daoFascia;
    	
    	try {
    	    daoFascia = new DAOFasciaAuto();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoFascia.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente di ottenere una fascia di autoveicoli a partire dal suo ID.
     * @param idFascia : l'ID della fascia d'autoveicolo da cercare.
     * @return la fascia corrispondente all'ID inserito.
     * @throws SQLException : la connessione con il database non puo' essere stabilita.
     */
    public static FasciaAuto ottieniFasciaDaId(int idFascia) throws SQLException {
	FasciaAuto fascia = new FasciaAuto();
	fascia.setId(idFascia);
	Richiesta richiesta = new Richiesta();
	richiesta.aggiungiOggetto(fascia);

	Risposta risposta = (Risposta) GestioneFasciaAutoService.visualizzaFasce(richiesta);
	if(risposta.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {
	    throw new SQLException();
	}
	fascia = (FasciaAuto) risposta.prendiOggettoDaIndice(0);

	return fascia;
    }
    
    private static void aggiungiCampiDaSistema(FasciaAuto fascia) {
	int ultimoId = FasciaAuto.getUltimoId();
    	fascia.setId(ultimoId++);
    	FasciaAuto.setUltimoId(ultimoId);
    }
}