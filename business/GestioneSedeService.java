package business;

import java.sql.SQLException;

import data.DAO;
import data.DAOSede;

/**
 * Gestore delle richieste riguardanti uno o piu' sedi.
 */
public class GestioneSedeService {
	
    /**
     * Aggiunge una nuova sede nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Per un corretto inserimento, devono essere completati i campi relativi al nome e all'indirizzo della sede da inserire.
     * L'ID e' generato automaticamente dal sistema.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovaSede(TO dati) {
    	Risposta risposta;
    	DAOSede daoSede;
    	Sede sede = (Sede) dati.prendiOggettoDaIndice(0);
    	aggiungiCampiDaSistema(sede);
    	
    	try {
    	    daoSede = new DAOSede();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoSede.crea(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Modifica una sede nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la modifica e' necessario inserire l'ID della sede da modificare piu' almeno un campo contenente il nuovo valore che
     * sostituira' il rispettivo precedente.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaSede(TO dati) {
    	Risposta risposta;
    	DAOSede daoSede;
    	
    	try {
    	    daoSede = new DAOSede();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoSede.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina una sede nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * Per l'eliminazione e' sufficiente inserire l'ID della sede da eliminare.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaSede(TO dati) {
    	Risposta risposta;
    	DAOSede daoSede;
    	
    	try {
    	    daoSede = new DAOSede();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoSede.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }

    /**
     * Cerca una o piu' sedi nel sistema di permanenza.
     * @param dati : l'oggetto contenente le chiavi di ricerca.
     * I campi sono tutti facoltativi e, se riempiti, fungono da parametri di ricerca per il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaSedi(TO dati) {
    	Risposta risposta;
    	DAOSede daoSede;
    	
    	try {
    	    daoSede = new DAOSede();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoSede.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    private static void aggiungiCampiDaSistema(Sede sede) {
	int ultimoId = Sede.getUltimoNumero();
    	sede.setNumero(ultimoId++);
    	Sede.setUltimoNumero(ultimoId);
    }
}