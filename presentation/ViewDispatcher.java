package presentation;

import hashing.MD5Hasher;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import presentation.controllerSchermate.amministratore.ControllerSchermataAutoveicoloDipendente;
import presentation.controllerSchermate.amministratore.ControllerSchermataFasciaAuto;
import presentation.controllerSchermate.amministratore.ControllerSchermataModello;
import presentation.controllerSchermate.amministratore.ControllerSchermataOperatore;
import presentation.controllerSchermate.amministratore.ControllerSchermataPrincipaleAmministratore;
import presentation.controllerSchermate.amministratore.ControllerSchermataSede;
import presentation.controllerSchermate.cliente.ControllerSchermataEffettuazioneNoleggio;
import presentation.controllerSchermate.cliente.ControllerSchermataPrincipaleCliente;
import presentation.controllerSchermate.comuni.ControllerSchermataLogin;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import presentation.controllerSchermate.comuni.ControllerSchermataRisultatiRicerca;
import presentation.controllerSchermate.operatore.ControllerSchermataChiusuraNoleggio;
import presentation.controllerSchermate.operatore.ControllerSchermataCliente;
import presentation.controllerSchermate.operatore.ControllerSchermataNoleggioOperatore;
import presentation.controllerSchermate.operatore.ControllerSchermataPrincipaleOperatore;
import risorse.GestoreFileTXT;
import business.ApplicationController;
import business.Richiesta;
import business.Risposta;
import business.Utente;
import business.Utente.Tipo;
import data.DAO;
import fileManagement.TXTManager;

/**
 * Classe che si occupa di gestire la visualizzazione delle interfacce grafiche sia in base al tipo di richiesta che in base <nbsp>
 * all'esito della risposta.
 */
public class ViewDispatcher {
    
    private static final String TITOLO_ALERT_ERRORE = "Errore.";

    private static final String HEADER_ALERT_ERRORE = "Si e' verificato un errore.";
    
    private static final String MESSAGGIO_ERRORE_CONNESSIONE_DATABASE = "Si e' verificato un errore di connessione al database.\nSi prega di riprovare.";
    
    private static final String MESSAGGIO_ERRORE_INSERIMENTO = "Non e' stato possibile inserire di dati richiesti. Riprovare.";
    
    private static final String MESSAGGIO_ERRORE_CHIAVE_DUPLICATA = "E' gia' presente un elemento con l'identificatore inserito.";
    
    private static final String MESSAGGIO_ERRORE_MODIFICA = "Non e' stato possibile modificare i dati richiesti.";

    private static final String MESSAGGIO_ERRORE_ELIMINAZIONE = "Non e' stato possible eliminare i dati richiesti.";

    private static final String MESSAGGIO_ERRORE_NESSUN_RISULTATO_TROVATO = "Nessun elemento corrisponde ai parametri di ricerca.";

    private static final String TITOLO_ALERT_COMPLETAMENTO = "Operazione avvenuta con successo!";

    private static final String HEADER_ALERT_COMPLETAMENTO = "Effettuare altre operazioni o deautenticarsi dal sistema.";

    private static final String MESSAGGIO_INSERIMENTO_CORRETTO = "Inserimento dei dati riuscito.";

    private static final String MESSAGGIO_MODIFICA_CORRETTA = "I dati indicati sono stati modificati correttamente.";

    private static final String MESSAGGIO_ELIMINAZIONE_CORRETTA = "I dati indicati sono stati eliminato con successo.";
    
    /**
     * Metodo che gestisce in maniera opportuna il risultato ottenuto dalla computazione della richiesta mostrando <nbsp>
     * il giusto messaggio e la giusta schermata a livello grafico.
     * @param richiesta : la richiesta appena soddisfatta.
     * @param risultatoRichiesta : l'esito della computazione della richiesta.
     */
    
    public static void gestisciRisultato(Richiesta richiesta, Risposta risultatoRichiesta) {
	if(risultatoRichiesta.getId().equals(DAO.ERRORE_CONNESSIONE_DATABASE)) {						//ERRORE CONNESSIONE DB
	    generaAlertErrore(MESSAGGIO_ERRORE_CONNESSIONE_DATABASE);
	} else {
	    Utente utenteLoggato = null;
	    if(richiesta.getId().equals(ControllerSchermataLogin.ID_LOGIN)) {							//OPZIONE 1 : LOGIN
		if(risultatoRichiesta.getId().equals(DAO.CODICE_RESULTSET_PIENO)) {
		    utenteLoggato = (Utente) risultatoRichiesta.prendiOggettoDaIndice(0);
		    utenteLoggato.setPassword(MD5Hasher.getInstance().hash(utenteLoggato.getPassword()));
		    if(utenteLoggato.getTipo() == Tipo.CLIENTE) {
			Main.mostraSchermataAlPath(ControllerSchermataPrincipaleCliente.PATH_SCHERMATA_PRINCIPALE_CLIENTE);
		    } else if(utenteLoggato.getTipo() == Tipo.OPERATORE) {
			Main.mostraSchermataAlPath(ControllerSchermataPrincipaleOperatore.PATH_SCHERMATA_PRINCIPALE_OPERATORE);
		    } else if(utenteLoggato.getTipo() == Tipo.AMMINISTRATORE) {
			Main.mostraSchermataAlPath(ControllerSchermataPrincipaleAmministratore.PATH_SCHERMATA_PRINCIPALE_AMMINISTRATORE);
		    }
		    //Dato che tutte le schermate principali di cliente, operatore e amministratore ereditano dalla classe
		    //SchermataPrincipale, e' possibile impostare a prescindere lo username dell'utente loggato, senza conoscere
		    //il suo tipo effettivo.
		    ControllerSchermataPrincipale csp = (ControllerSchermataPrincipale) Main.getControllerSchermataAttuale();
		    csp.setUserName(utenteLoggato.getUsername());
		} else if(risultatoRichiesta.getId().equals(DAO.CODICE_RESULTSET_VUOTO)) {
		    ControllerSchermataLogin csl = (ControllerSchermataLogin) Main.getControllerSchermataAttuale();
		    csl.mostraMessaggioErroreCredenziali();
		}
	    } else if(richiesta.getId().equals(ControllerSchermataLogin.ID_LOGOUT)) {						//OPZIONE 2 : LOGOUT
		Main.mostraSchermataAlPath(ControllerSchermataLogin.PERCORSO_SCHERMATA_LOGIN);
		//Non si scende piu' giu' con i livelli, ma si mostra la relativa schermata passando il noleggio in fase di chiusura
	    } else if(richiesta.getId().equals(ControllerSchermataNoleggioOperatore.ID_CHIUSURA_NOLEGGIO_PROVVISORIA)) {	//OPZIONI 3 : CHIUSURA PROVVISORIA
		Main.mostraSchermataAlPath(ControllerSchermataChiusuraNoleggio.PATH_SCHERMATA_CHIUSURA_NOLEGGIO);
		ControllerSchermataChiusuraNoleggio cscn = (ControllerSchermataChiusuraNoleggio) Main.getControllerSchermataAttuale();
		cscn.setRichiestaNoleggio(richiesta);		//Avvalora il campo passando il noleggio ancora in fase di chiusura
	    } else if(richiesta.getId().equals(ControllerSchermataChiusuraNoleggio.ID_CHIUSURA_NOLEGGIO_DEFINITIVA)) {		//OPZIONI 4 : CHIUSURA DEFINITIVA
		if(risultatoRichiesta.getId().equals(DAO.CODICE_MODIFICA_AVVENUTA)) {
		    generaAlertSuccesso(MESSAGGIO_MODIFICA_CORRETTA);
		} else if(risultatoRichiesta.getId().equals(DAO.ERRORE_MODIFICA_DATI)) {
		    generaAlertErrore(MESSAGGIO_ERRORE_MODIFICA);
		}
	    } else {
		utenteLoggato = getUtenteLoggato();
		if(ApplicationController.ottieniTipoOperazione(richiesta.getId()) == ApplicationController.INSERIMENTO) {	//OPZIONI 5 : INSERIMENTO
		    if(richiesta.getId().equals(ControllerSchermataAutoveicoloDipendente.ID_INSERIMENTO_AUTOVEICOLO) ||
			    richiesta.getId().equals(ControllerSchermataFasciaAuto.ID_INSERIMENTO_FASCIA) ||
			    richiesta.getId().equals(ControllerSchermataModello.ID_INSERIMENTO_MODELLO) ||
			    richiesta.getId().equals(ControllerSchermataOperatore.ID_INSERIMENTO_OPERATORE) ||
			    richiesta.getId().equals(ControllerSchermataSede.ID_INSERIMENTO_SEDE) ||
			    richiesta.getId().equals(ControllerSchermataCliente.ID_INSERIMENTO_CLIENTE) ||
			    richiesta.getId().equals(ControllerSchermataEffettuazioneNoleggio.ID_EFFETTUAZIONE_NOLEGGIO)) {
			if(risultatoRichiesta.getId().equals(DAO.CODICE_INSERIMENTO_AVVENUTO)) {
			    generaAlertSuccesso(MESSAGGIO_INSERIMENTO_CORRETTO);
			} else if(risultatoRichiesta.getId().equals(DAO.ERRORE_INSERIMENTO_DATI)) {
			    generaAlertErrore(MESSAGGIO_ERRORE_INSERIMENTO);
			} else if(risultatoRichiesta.getId().equals(DAO.ERRORE_CHIAVE_PRESENTE)) {
			    generaAlertErrore(MESSAGGIO_ERRORE_CHIAVE_DUPLICATA);
			}
		    } 
		} else if(ApplicationController.ottieniTipoOperazione(richiesta.getId()) == ApplicationController.MODIFICA) {	//OPZIONI 6 : MODIFICA
		    if(richiesta.getId().equals(ControllerSchermataAutoveicoloDipendente.ID_MODIFICA_AUTOVEICOLO) ||
			    richiesta.getId().equals(ControllerSchermataFasciaAuto.ID_MODIFICA_FASCIA) ||
			    richiesta.getId().equals(ControllerSchermataModello.ID_MODIFICA_MODELLO) ||
			    richiesta.getId().equals(ControllerSchermataSede.ID_MODIFICA_SEDE) ||
			    richiesta.getId().equals(ControllerSchermataCliente.ID_MODIFICA_CLIENTE) ||
			    richiesta.getId().equals(ControllerSchermataOperatore.ID_MODIFICA_OPERATORE)) {	
			if(risultatoRichiesta.getId().equals(DAO.CODICE_MODIFICA_AVVENUTA)) {
			    generaAlertSuccesso(MESSAGGIO_MODIFICA_CORRETTA);
			} else if(risultatoRichiesta.getId().equals(DAO.ERRORE_MODIFICA_DATI)) {
			    generaAlertErrore(MESSAGGIO_ERRORE_MODIFICA);
			}
		    }
		} else if(ApplicationController.ottieniTipoOperazione(richiesta.getId()) == ApplicationController.ELIMINAZIONE) {	//OPZIONI 7 : ELIMINAZIONE
		    if(richiesta.getId().equals(ControllerSchermataAutoveicoloDipendente.ID_ELIMINAZIONE_AUTOVEICOLO) ||
			    richiesta.getId().equals(ControllerSchermataFasciaAuto.ID_ELIMINAZIONE_FASCIA) ||
			    richiesta.getId().equals(ControllerSchermataModello.ID_ELIMINAZIONE_MODELLO) ||
			    richiesta.getId().equals(ControllerSchermataOperatore.ID_ELIMINAZIONE_OPERATORE) ||
			    richiesta.getId().equals(ControllerSchermataSede.ID_ELIMINAZIONE_SEDE) ||
			    richiesta.getId().equals(ControllerSchermataCliente.ID_ELIMINAZIONE_CLIENTE) ||
			    richiesta.getId().equals(ControllerSchermataNoleggioOperatore.ID_ELIMINAZIONE_NOLEGGIO)) {
			if(risultatoRichiesta.getId().equals(DAO.CODICE_ELIMINAZIONE_AVVENUTA)) {
			    generaAlertSuccesso(MESSAGGIO_ELIMINAZIONE_CORRETTA);
			} else if(risultatoRichiesta.getId().equals(DAO.ERRORE_ELIMINAZIONE_DATI)) {
			    generaAlertErrore(MESSAGGIO_ERRORE_ELIMINAZIONE);
			}
		    }
		} else if(ApplicationController.ottieniTipoOperazione(richiesta.getId()) == ApplicationController.RICERCA) {		//OPZIONI 8 : RICERCA
		    if(risultatoRichiesta.getId().equals(DAO.CODICE_RESULTSET_VUOTO)) {
			generaAlertErrore(MESSAGGIO_ERRORE_NESSUN_RISULTATO_TROVATO);
		    } else {
			Main.mostraSchermataAlPath(ControllerSchermataRisultatiRicerca.PATH_SCHERMATA_RISULTATI_RICERCA);
			ControllerSchermataRisultatiRicerca csrr = (ControllerSchermataRisultatiRicerca) Main.getControllerSchermataAttuale();
			csrr.impostaElementiTrovati(risultatoRichiesta);
		    }
		} 
	    }
	}
    }
    
    private static void generaAlertErrore(String messaggioErrore) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle(TITOLO_ALERT_ERRORE);
	alert.setHeaderText(HEADER_ALERT_ERRORE);
	alert.setContentText(messaggioErrore);
	alert.show();
    }
    
    private static void generaAlertSuccesso(String messaggioSuccesso) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(TITOLO_ALERT_COMPLETAMENTO);
	alert.setHeaderText(HEADER_ALERT_COMPLETAMENTO);
	alert.setContentText(messaggioSuccesso);
	alert.show();
    }
    
    private static Utente getUtenteLoggato() {
	Utente utenteDaRestituire = new Utente();
	TXTManager txtManager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));
	utenteDaRestituire.setUsername(txtManager.leggi(GestoreFileTXT.NOME_USERNAME_COOKIE));
	utenteDaRestituire.setTipo(Tipo.valueOf(txtManager.leggi(GestoreFileTXT.NOME_TIPO_COOKIE)));
	
	return utenteDaRestituire;
    }
}
