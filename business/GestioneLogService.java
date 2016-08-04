package business;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

import risorse.GestoreFileTXT;
import utility.GestoreData;
import data.DAO;
import data.DAOLogin;
import fileManagement.EntitaEsistenteException;
import fileManagement.TXTManager;


/**
 * Gestore delle richieste riguardanti l'autenticazione e la deautenticazione di un utente dal sistema.
 */
public class GestioneLogService {
    
    /**
     * ID relativo all'esito positivo della deautenticazione dell'utente dal sistema.
     */
    public static final String DEAUTENTICAZIONE_CON_SUCCESSO = "R14";
    
    /**
     * Verifica il corretto abbinamento tra username e password inseriti.
     * @param dati : l'oggetto contenente le credenziali inserite.
     * @return l'esito della verifica con il relativo codice di stato.
     */
    public static TO autentica(TO dati) {
	Risposta risposta;
	DAOLogin daol;
	
	try {
	    daol = new DAOLogin();
	} catch(SQLException e) {
	    System.err.println(e.getMessage());
	    System.err.println(e.getErrorCode());
	    risposta = new Risposta();
	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return risposta;
	}
	
	try {
	    risposta = (Risposta) daol.leggi(dati);
	} catch(SQLException e) {
	    risposta = new Risposta();
	    risposta.setId(DAO.ERRORE_CONNESSIONE_DATABASE);
	    return risposta;
	}
	
	if(risposta.getId().equals(DAO.CODICE_RESULTSET_PIENO)) {
	    Utente utente = (Utente) risposta.prendiOggettoDaIndice(0);
	    createCookie(utente);
	}
	
	return risposta;
    }
    
    /**
     * Cancella il cookie di sessione e deautentica l'utente dal sistema.
     * @return l'esito della deautenticazione.
     */
    public static TO deAutentica() {
	File cookieSessione = new File(GestoreFileTXT.PERCORSO_TXT_COOKIE);
	TXTManager txtManager = new TXTManager(cookieSessione);
	txtManager.cancella();
	
	Risposta risposta = new Risposta();
	risposta.setId(DEAUTENTICAZIONE_CON_SUCCESSO);
	return risposta;
    }
    
    //Crea il cookie di sessione utilizzando i parametri dell'utente restituiti dal DAO del login.
    private static void createCookie(Utente utente) {
	File cookieSessione = new File(GestoreFileTXT.PERCORSO_TXT_COOKIE);
	TXTManager txtManager = new TXTManager(cookieSessione);
	LocalDate dataAttuale = LocalDate.now();
	try {	
	    txtManager.scrivi(GestoreFileTXT.NOME_USERNAME_COOKIE, utente.getUsername());
	    txtManager.scrivi(GestoreFileTXT.NOME_TIPO_COOKIE, utente.getTipo().toString());
	    txtManager.scrivi(GestoreFileTXT.NOME_DATA_COOKIE, GestoreData.formattaData(dataAttuale));
	} catch (EntitaEsistenteException e) {
	    e.printStackTrace();
	}
    }
}