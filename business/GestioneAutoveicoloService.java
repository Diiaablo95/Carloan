package business;

import java.io.File;
import java.sql.SQLException;

import business.Autoveicolo.Stato;
import risorse.GestoreFileTXT;
import data.DAO;
import data.DAOAutoveicolo;
import fileManagement.TXTManager;

/**
 * Gestore delle richieste riguardanti uno o piu' autoveicoli.
 */
public class GestioneAutoveicoloService {

    /**
     * Aggiunge un nuovo autoveicolo nel sistema di permanenza.
     * @param dati : l'oggetto contenente i dati inseriti.
     * Tutti i campi dell'autoveicolo devono essere pieni per poterlo inserire correttamente nella base di dati.<br>
     * La targa inserita non deve essere gia' presente nel database, mentre la sede e il modello di appartenenza devono gia'
     * essere stati inseriti precedentemente.
     * @return l'esito della creazione con il relativo codice di stato.
     */
    public static TO aggiungiNuovoAutoveicolo(TO dati) {
	Risposta risposta;
	DAOAutoveicolo daoAutoveicolo;

	try {
	    daoAutoveicolo = new DAOAutoveicolo();
	} catch(SQLException e) {
	    risposta = new Risposta();
	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return risposta;
	}

	try {
	    risposta = (Risposta) daoAutoveicolo.crea(dati);
	} catch(SQLException e) {
	    risposta = new Risposta();
	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return risposta;
	}

	return risposta;
    }
    
    /**
     * Modifica un autoveicolo nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto e i campi da modificare.
     * Per la modifica e' necessario inserire la targa (identificativa) dell'autoveicolo e almeno un campo che si intende
     * modificare, inserendo direttamente il nuovo valore.
     * @return l'esito della modifica con il relativo codice di stato.
     */
    public static TO modificaAutoveicolo(TO dati) {
    	Risposta risposta;
    	DAOAutoveicolo daoAutoveicolo;
    	//Se si cambia lo stato dell'auto in uno di questi tre, la sede viene impostata a "nessun valore"
    	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
    	if(auto.getStato() == Stato.MANUTENZIONE_ORDINARIA || auto.getStato() == Stato.MANUTENZIONE_STRAORDINARIA ||
    		auto.getStato() == Stato.NOLEGGIATO) {
    	    auto.setIdSede(Sede.ID_NESSUNA_SEDE);
    	}
    	
    	try {
    	    daoAutoveicolo = new DAOAutoveicolo();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoAutoveicolo.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Cerca un autoveicolo della sede corrente nel sistema di permanenza.
     * @param dati : l'oggetto contenente i parametri di ricerca impostati.
     * Per la visualizzazione nessun campo e' obbligatorio, ma tutti i campi riempiti fungono da parametri di ricerca per
     * il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaAutoveicoliCliente(TO dati) {
    	Risposta risposta;
    	DAOAutoveicolo daoAutoveicolo;
    	
    	Autoveicolo auto = (Autoveicolo) dati.prendiOggettoDaIndice(0);
    	aggiungiCampiDaSistema(auto);	//Aggiunge ID della sede e disponibilita'
    	
    	try {
    	    daoAutoveicolo = new DAOAutoveicolo();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoAutoveicolo.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Cerca un qualunque autoveicolo nel sistema di permanenza.
     * @param dati : l'oggetto contenente i parametri di ricerca impostati.
     * Per la visualizzazione nessun campo e' obbligatorio, ma tutti i campi riempiti fungono da parametri di ricerca per
     * il risultato finale.
     * @return l'esito della ricerca con il relativo codice di stato.
     */
    public static TO visualizzaAutoveicoli(TO dati) {
    	Risposta risposta;
    	DAOAutoveicolo daoAutoveicolo;
    	
    	try {
    	    daoAutoveicolo = new DAOAutoveicolo();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoAutoveicolo.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Elimina un autoveicolo nel sistema di permanenza.
     * @param dati : l'oggetto contenente l'ID dell'oggetto da eliminare.
     * Per l'eliminazione e' sufficiente inserire solamente la targa dell'autoveicolo da eliminare dalla base di dati.
     * @return l'esito della eliminazione con il relativo codice di stato.
     */
    public static TO eliminaAutoveicolo(TO dati) {
    	Risposta risposta;
    	DAOAutoveicolo daoAutoveicolo;
    	
    	try {
    	    daoAutoveicolo = new DAOAutoveicolo();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoAutoveicolo.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente di ottenere l'autoveicolo associato ad una targa cercata.
     * @param targa : la targa corrispondente all'autoveicolo da cercare.
     * @see Autoveicolo#setTarga(String) Condizione per l'inserimento di una targa corretta.
     * @return l'autoveicolo avente come targa la targa cercata.
     * @throws SQLException : la connessione con il database non puo' essere stabilita.
     */
    public static Autoveicolo ottieniAutoveicoloDaTarga(String targa) throws SQLException {
	Autoveicolo auto = new Autoveicolo();
	auto.setTarga(targa);
	auto.setIdSede(Sede.DEFAULT_ID);
	Richiesta richiesta = new Richiesta();
	richiesta.aggiungiOggetto(auto);

	Risposta risposta = (Risposta) GestioneAutoveicoloService.visualizzaAutoveicoli(richiesta);
	if(risposta.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {
	    throw new SQLException();
	}
	auto = (Autoveicolo) risposta.prendiOggettoDaIndice(0);

	return auto;
    }
    
    //Cerca gli autoveicoli presenti nella sede del terminale e disponibili.
    private static void aggiungiCampiDaSistema(Autoveicolo auto) {
    	TXTManager manager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_SEDE));
    	int idSede = Integer.parseInt(manager.leggi(GestoreFileTXT.NOME_ID_SEDE));
    	auto.setIdSede(idSede);
    	auto.setStato(Stato.DISPONIBILE);
    }
}