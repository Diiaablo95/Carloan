package business;

import java.sql.SQLException;

import data.DAO;
import data.DAOModello;

/**
 * Gestore delle richieste riguardanti uno o piu' modelli.
 */
public class GestioneModelloService {

    /**
     * Aggiunge un nuovo modello nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Per il corretto inserimento del modello nella base di dati, tutti i campi devono essere completati.<br>
     * Inoltre l'ID della fascia di appartenenza deve corrispondere ad una fascia di autoveicoli gia' presente nel database.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovoModello(TO dati) {
    	Risposta risposta;
    	DAOModello daoModello;
    	
    	try {
    	    daoModello = new DAOModello();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoModello.crea(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Modifica un modello nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la modifica e' necessario specificare l'ID del modello da modificare e almeno un campo contenente il nuovo valore.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaModello(TO dati) {
    	Risposta risposta;
    	DAOModello daoModello;
    	
    	try {
    	    daoModello = new DAOModello();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoModello.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina un modello nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * E' sufficiente inserire solamente l'ID del modello per poterlo eliminare correttamente.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaModello(TO dati) {
    	Risposta risposta;
    	DAOModello daoModello;
    	
    	try {
    	    daoModello = new DAOModello();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoModello.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Cerca un modello nel sistema di permanenza.
     * @param dati : l'oggetto contenente le chiavi di ricerca.
     * Nessun campo e' obbligatorio, ma tutti fungono da parametri di ricerca, se completati.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaModelli(TO dati) {
    	Risposta risposta;
    	DAOModello daoModello;
    	
    	try {
    	    daoModello = new DAOModello();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoModello.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente di ottenere un modello specificandone l'ID.
     * @param idModello : l'ID del modello da cercare.
     * @return il modello avente come ID l'ID inserito come parametro di ricerca.
     * @throws SQLException : la connessione con il database non puo' essere stabilita.
     * @see Modello#setId(String) Condizione per l'inserimento di un ID corretto.
     */
    public static Modello ottieniModelloDaId(String idModello) throws SQLException {
	Modello modello = new Modello();
	modello.setId(idModello);
	modello.setIdFascia(FasciaAuto.DEFAULT_ID);
	Richiesta richiesta = new Richiesta();
	richiesta.aggiungiOggetto(modello);

	Risposta risposta = (Risposta) GestioneModelloService.visualizzaModelli(richiesta);
	if(risposta.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {
	    throw new SQLException();
	}
	modello = (Modello) risposta.prendiOggettoDaIndice(0);

	return modello;
    }
}