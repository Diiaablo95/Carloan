package business;

import java.sql.SQLException;

import business.Utente.Tipo;
import data.DAO;
import data.DAOCliente;

/**
 * Gestore delle richieste riguardanti uno o piu' clienti.
 */
public class GestioneClienteService {
    
    /**
     * Aggiunge un nuovo cliente nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Tutti i campi devono essere riempiti.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovoCliente(TO dati) {
    	Risposta risposta;
    	DAOCliente daoCliente;
    	
    	Cliente cliente = (Cliente) dati.prendiOggettoDaIndice(0);
    	cliente.setTipo(Tipo.CLIENTE); 	//Da inserire nella tabella delle credenziali.
    	
    	try {
    	    daoCliente = new DAOCliente();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoCliente.crea(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Modifica un cliente nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la ricerca e' necessario inserire lo username (identificativo) del cliente e almeno un campo da modificare,
     * contenente gia' il nuovo valore da salvare.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaCliente(TO dati) {
    	Risposta risposta;
    	DAOCliente daoCliente;

    	
    	try {
    	    daoCliente = new DAOCliente();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoCliente.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Cerca un cliente nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la visualizzazione nessun campo e' obbligatorio, ma tutti i campi riempiti fungono da parametri di ricerca per
     * il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaClienti(TO dati) {
    	Risposta risposta;
    	DAOCliente daoCliente;
    	
    	try {
    	    daoCliente = new DAOCliente();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoCliente.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina un cliente nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * Per l'eliminazione e' sufficiente inserire solamente lo username del cliente da eliminare dalla base di dati.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaCliente(TO dati) {
    	Risposta risposta;
    	DAOCliente daoCliente;
    	
    	try {
    	    daoCliente = new DAOCliente();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoCliente.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
}