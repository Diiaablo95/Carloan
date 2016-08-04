package business;

import java.io.File;
import java.math.BigInteger;
import java.sql.SQLException;

import business.Autoveicolo.Stato;
import business.businessUtility.CalcolatorePrezzo;
import risorse.GestoreFileTXT;
import utility.GestoreData;
import data.DAO;
import data.DAONoleggio;
import fileManagement.TXTManager;

/**
 * Gestore delle richieste riguardanti uno o piu' noleggi.
 */
public class GestioneNoleggioService {
    
    private static final String ID_NOLEGGIO_PROVVISORIO_COMPLETO = "R20";
    
    /**
     * Consente di aprire un nuovo noleggio ad un cliente.
     * @param dati : i dati riguardanti l'apertura del noleggio.
     * Tutti i campi tranne quello relativo all'ID devono essere completati per poter aprire correttamente un nuovo noleggio.
     * @return l'esito dell'apertura del noleggio.
     */
    public static TO apriNoleggio(TO dati) {
    	Risposta risposta = new Risposta();
    	DAONoleggio daoNoleggio;
    	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
    	Autoveicolo auto = new Autoveicolo();
    	auto.setTarga(noleggio.getTargaAuto());
    	auto.setIdSede(Sede.DEFAULT_ID);
    	dati.eliminaOggettoAllIndice(0);
    	dati.aggiungiOggetto(auto);
    	risposta = (Risposta) GestioneAutoveicoloService.visualizzaAutoveicoli(dati);	//Verifichiamo se l'auto e' disponibile.
    	auto = (Autoveicolo) risposta.prendiOggettoDaIndice(0);
    	if(auto.getStato() != Stato.DISPONIBILE) {
    	    risposta.setId(DAO.ERRORE_INSERIMENTO_DATI);
    	} else {
    	    auto = new Autoveicolo();
    	    auto.setTarga(noleggio.getTargaAuto());
    	    auto.setStato(Stato.NOLEGGIATO);
    	    dati.eliminaOggettoAllIndice(0);
    	    dati.aggiungiOggetto(auto);
    	    risposta = (Risposta) GestioneAutoveicoloService.modificaAutoveicolo(dati);
    	    
    	    if(risposta.getId().equals(DAO.CODICE_MODIFICA_AVVENUTA)) {
    		try {
    	    	    aggiungiCampiDaSistemaPerApertura(noleggio);	//La ricerca dell'autoveicolo potrebbe incontrare errori				
    	    	} catch(SQLException e) {				//di connessione.
    	    	    risposta = new Risposta();				
    		    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    		    return risposta;
    	    	}
    		
    		try {
    	    	    daoNoleggio = new DAONoleggio();
    	    	} catch(SQLException e) {
    	    	    risposta = new Risposta();
    	    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    	    return risposta;
    	    	}
    		
    		dati.eliminaOggettoAllIndice(0);
    		dati.aggiungiOggetto(noleggio);
    		try {
    	    	    risposta = (Risposta) daoNoleggio.crea(dati);
    	    	} catch(SQLException e) {
    	    	    risposta = new Risposta();
    	    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    	    return risposta;
    	    	}
    	    } else {
    		risposta.setId(DAO.ERRORE_INSERIMENTO_DATI);
    	    }
    	}
    	return risposta;
    }
    
    /**
     * Consente di chiudere un noleggio in maniera provvisoria, ovvero senza inserire l'ammontare di danni causati dal cliente.
     * @param dati : i nuovi dati da aggiungere al noleggio in chiusura.
     * Per la chiusura provvisoria di un noleggio e' necessario inserire l'ID del noleggio da chiudere e il chilometraggio finale
     * dell'autoveicolo noleggiato.
     * @return l'esito della chiusura provvisoria del noleggio.
     */
    public static TO chiudiNoleggioProvvisorio(TO dati) {
	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
	double costoTotale;
	try {
	    costoTotale = CalcolatorePrezzo.calcolaPrezzo(noleggio);
	} catch (SQLException e) {
	    dati.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return dati;
	}
	noleggio.setSommaDenaro(costoTotale);
	dati.setId(ID_NOLEGGIO_PROVVISORIO_COMPLETO);

	return dati;
    }
    
    /**
     * Consente di chiudere in maniera definitiva un noleggio.
     * Per chiusura definitiva si intende la chiusura di un noleggio calcolando come costo totale il costo del noleggio
     * piu' l'ammontare di eventuali danni causati dal cliente.
     * @param dati : i nuovi dati per la chiusura del noleggio.
     * E' necessario aggiungere solo l'eventuale spesa aggiuntiva per la manutenzione dell'autoveicolo noleggiato.
     * @return l'esito della chiusura definitiva del noleggio.
     */
    public static TO chiudiNoleggio(TO dati) {
	Risposta risposta;
    	DAONoleggio daoNoleggio;
    	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);		//Conterra' l'ID del noleggio e l'ammontare dei danni totali.
    	try {
    	    daoNoleggio = new DAONoleggio();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    aggiungiCampiDaSistemaPerChiusuraDefinitiva(noleggio);		//Una volta aggiunti i campi necessari, si inserisce il tutto nel DB.
    	} catch(SQLException e) {
    	    risposta = new Risposta();
	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoNoleggio.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	dati.eliminaOggettoAllIndice(0);
    	
    	Autoveicolo auto = new Autoveicolo();
    	auto.setTarga(noleggio.getTargaAuto());
    	auto.setStato(Stato.DISPONIBILE);
    	auto.setIdSede(noleggio.getSedeFinale());
    	dati.aggiungiOggetto(noleggio);
    	
    	risposta = (Risposta) GestioneAutoveicoloService.modificaAutoveicolo(dati);
    	
    	return risposta;
    }
    
    /**
     * Consente di modificare i dati di un noleggio, sia esso ancora aperto o gia' chiuso.
     * @param dati : i dati da modificare relativi al noleggio.
     * Per una corretta modifica e' necessario inserire l'ID del noleggio da modificare e almeno un campo contenente il nuovo
     * valore che andra' a sostituire il corrispettivo precedente.
     * @return l'esito della modifica.
     */
    public static TO modificaNoleggio(TO dati) {
    	Risposta risposta;
    	DAONoleggio daoNoleggio;
    	
    	try {
    	    daoNoleggio = new DAONoleggio();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoNoleggio.modifica(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente di eliminare un noleggio dal sistema di permanenza.
     * @param dati : l'ID del noleggio da eliminare.
     * E' sufficiente inserire solamente l'ID del noleggio da eliminare.
     * @return l'esito dell'eliminazione.
     */
    public static TO eliminaNoleggio(TO dati) {
    	Risposta risposta;
    	DAONoleggio daoNoleggio;
    	
    	try {
    	    daoNoleggio = new DAONoleggio();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoNoleggio.elimina(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente ad un cliente di visualizzare i noleggi da egli effettuati.
     * @param dati : i parametri per effettuare la ricerca.
     * Nessun campo e' obbligatorio, tutti quanti fungono da parametri di ricerca per il risultato finale.
     * @return il risultato della ricerca.
     */
    public static TO visualizzaNoleggiCliente(TO dati) {
    	Risposta risposta;
    	DAONoleggio daoNoleggio;
    	
    	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
    	aggiungiCampiDaSistemaPerVisualizzazioneCliente(noleggio);
    	
    	try {
    	    daoNoleggio = new DAONoleggio();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoNoleggio.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Consente di visualizzare tutti i noleggi presenti nel sistema di permanenza, siano essi aperti o chiusi.
     * @param dati : i parametri per la ricerca.
     * Nessun campo e' obbligatorio, tutti quanti fungono da parametri di ricerca per il risultato finale.
     * @return l'esito della ricerca.
     */
    public static TO visualizzaNoleggi(TO dati) {
    	Risposta risposta;
    	DAONoleggio daoNoleggio;
    	
    	try {
    	    daoNoleggio = new DAONoleggio();
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	try {
    	    risposta = (Risposta) daoNoleggio.leggi(dati);
    	} catch(SQLException e) {
    	    risposta = new Risposta();
    	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
    	    return risposta;
    	}
    	
    	return risposta;
    }
    
    /**
     * Ritorna il numero totale di chilometri percorsi in piu' rispetto a quelli accordati.
     * @param noleggio : il noleggio del quale calcolare i chilometri in eccesso.
     * Il noleggio deve essere concluso e deve essere formato da un chilometraggio limitato.
     */
    public static double kmInEccesso(Noleggio noleggio) {
	return (noleggio.getKmFinale() - noleggio.getKmIniziale()) - noleggio.getChilometraggio();
    }
    
    /**
     * Consente di ottenere il costo chilometrico della fascia di un autoveicolo noleggiato.
     * @param noleggio : il noleggio del quale si vuole ottenere il costo chilometrico associato alla fascia di autoveicolo noleggiato.
     * @return il costo chilometrico della fascia associata all' autoveicolo noleggiato.
     * @throws SQLException : la connessione con il database non puo' essere stabilita.
     */
    public static double ottieniPrezzoChilometricoDaNoleggio(Noleggio noleggio) throws SQLException {
	Richiesta richiestaNoleggio = new Richiesta();
	richiestaNoleggio.aggiungiOggetto(noleggio);
	noleggio = (Noleggio) GestioneNoleggioService.visualizzaNoleggi(richiestaNoleggio).prendiOggettoDaIndice(0);
	String targaAuto = noleggio.getTargaAuto();
	Autoveicolo autoNoleggiata = GestioneAutoveicoloService.ottieniAutoveicoloDaTarga(targaAuto);

	String idModello = autoNoleggiata.getIdModello();
	Modello modello = GestioneModelloService.ottieniModelloDaId(idModello);

	int idFascia = modello.getIdFascia();
	FasciaAuto fascia = GestioneFasciaAutoService.ottieniFasciaDaId(idFascia);

	double prezzoKm = fascia.getPrezzoKm();
	return prezzoKm;
    }
    
    public static double ottieniKmInizialeDaIdNoleggio(String idNoleggio) throws SQLException {
	Noleggio noleggio = new Noleggio();
	noleggio.setId(new BigInteger(idNoleggio));
	noleggio.setSedeIniziale(Sede.DEFAULT_ID);
	noleggio.setSedeFinale(Sede.DEFAULT_ID);
	noleggio.setChilometraggio(Noleggio.VALORE_CHILOMETRAGGIO_NON_IMPOSTATO);
	noleggio.setKmIniziale(Noleggio.VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT);
	Richiesta richiestaNoleggio = new Richiesta();
	richiestaNoleggio.aggiungiOggetto(noleggio);
	noleggio = (Noleggio)GestioneNoleggioService.visualizzaNoleggi(richiestaNoleggio).prendiOggettoDaIndice(0);
	
	return noleggio.getKmIniziale();
    }
    
    private static void aggiungiCampiDaSistemaPerApertura(Noleggio noleggio) throws SQLException {
	//generazione automatica dell'ID del noleggio
    	BigInteger id = Noleggio.getUltimoId();
    	noleggio.setId(id);
    	Noleggio.setUltimoId(id.add(BigInteger.ONE));
    	
    	//estrapolazione della sede iniziale e finale(se non impostata) da assegnare al noleggio
    	TXTManager manager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_SEDE));
    	int idSedeIniziale = Integer.parseInt(manager.leggi(GestoreFileTXT.NOME_ID_SEDE));
    	noleggio.setSedeIniziale(idSedeIniziale);
    	if(noleggio.getSedeFinale() == Sede.DEFAULT_ID) {
    	    noleggio.setSedeFinale(idSedeIniziale);
    	}
    	
    	//estrapolazione del cliente da assegnare al noleggio
    	manager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));
    	noleggio.setUserCliente(manager.leggi(GestoreFileTXT.NOME_USERNAME_COOKIE));
    	noleggio.setDataInizio(GestoreData.deFormattaData(manager.leggi(GestoreFileTXT.NOME_DATA_COOKIE)));
    
    	//estrapolazione del chilometraggio iniziale
    	Autoveicolo auto = new Autoveicolo();
    	auto.setTarga(noleggio.getTargaAuto());		//impostiamo il TO contenente la targa per la ricerca dell'auto da noleggiare.
    	auto.setIdSede(Sede.DEFAULT_ID);
    	TO TOAutodaCercare = new Richiesta();
    	TOAutodaCercare.aggiungiOggetto(auto);
    	
    	TO TOAutoTrovata = GestioneAutoveicoloService.visualizzaAutoveicoli(TOAutodaCercare);
    	if(TOAutoTrovata.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {
    	    throw new SQLException();		//la ricerca potrebbe lanciare un messaggio di errore di connessione.
    	}
    	auto = (Autoveicolo) TOAutoTrovata.prendiOggettoDaIndice(0);
    	double chilometraggioAuto = auto.getChilometraggio();
    	noleggio.setKmIniziale(chilometraggioAuto);
    }				//e potra' essere re-inserito nel database dopo aver calcolato il prezzo totale(senza gli ulteriori danni).
    
    private static void aggiungiCampiDaSistemaPerChiusuraDefinitiva(Noleggio noleggioDaChiudere) throws SQLException {
	double prezzoFinale = noleggioDaChiudere.getSommaDenaro();
	TO TONoleggio = new Richiesta();
	TONoleggio.aggiungiOggetto(noleggioDaChiudere);		//Prendo il noleggio in sospeso.
	
	TONoleggio = visualizzaNoleggi(TONoleggio);
	if(TONoleggio.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {
	    throw new SQLException();
	}
	
	noleggioDaChiudere = (Noleggio) TONoleggio.prendiOggettoDaIndice(0);		//Ora il noleggio in input punta al noleggio presente nel DB, in cui mancano
	noleggioDaChiudere.setSommaDenaro(prezzoFinale);				//solo la data finale, che viene impostata subito, e la somma totale, salvata
	TXTManager txtManager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));	//all'inizio nella variabile prezzoFinale.
	String dataFinale = null;
	dataFinale = txtManager.leggi(GestoreFileTXT.NOME_DATA_COOKIE);
	noleggioDaChiudere.setDataFine(GestoreData.deFormattaData(dataFinale));
    }
    
    private static void aggiungiCampiDaSistemaPerVisualizzazioneCliente(Noleggio noleggio) {
	//Aggiunta dell'id dell'utente per la visualizzazione dei noleggi.
	TXTManager manager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));
	noleggio.setUserCliente(manager.leggi(GestoreFileTXT.NOME_USERNAME_COOKIE));
	noleggio.setKmIniziale(Noleggio.VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT);
	noleggio.setChilometraggio(Noleggio.VALORE_CHILOMETRAGGIO_NON_IMPOSTATO);
    }
}