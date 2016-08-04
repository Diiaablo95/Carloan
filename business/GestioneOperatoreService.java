package business;

import java.sql.SQLException;

import business.Utente.Tipo;
import data.DAO;
import data.DAOOperatore;

/**
 * Gestore delle richieste riguardanti uno o piu' operatori.
 */
public class GestioneOperatoreService {

    /**
     * Aggiunge un nuovo operatore nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Tutti i campi relativi all'operatore devono essere completati per poterlo inserire correttamente nella base di dati.
     * L'ID della sede relativa ad un operatore deve gia' essere presente nella base di dati.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovoOperatore(TO dati) {
    	Risposta risposta;
    	DAOOperatore daoOperatore;
    	
    	Operatore operatore = (Operatore) dati.prendiOggettoDaIndice(0);
    	operatore.setTipo(Tipo.OPERATORE);
    	
    	try {
    	    daoOperatore = new DAOOperatore();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoOperatore.crea(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Modifica un operatore nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la modifica e' necessario inserire lo username dell'operatore di cui si vogliono modificare i dati piu'
     * i vari campi gia' avvalorati con i nuovi valori che sostituiranno i precedenti.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaOperatore(TO dati) {
    	Risposta risposta;
    	DAOOperatore daoOperatore;
    	
    	try {
    	    daoOperatore = new DAOOperatore();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoOperatore.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina un operatore nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * E' sufficiente inserire lo username dell'operatore da eliminare per eliminarlo correttamente.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaOperatore(TO dati) {
    	Risposta risposta;
    	DAOOperatore daoOperatore;
    	
    	try {
    	    daoOperatore = new DAOOperatore();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoOperatore.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Cerca un operatore nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Tutti i campi sono facoltativi e fungono da parametri di ricerca per il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaOperatori(TO dati) {
    	Risposta risposta;
    	DAOOperatore daoOperatore;
    	
    	try {
    	    daoOperatore = new DAOOperatore();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoOperatore.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
}