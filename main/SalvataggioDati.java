package main;

import java.io.File;

import business.FasciaAuto;
import business.Noleggio;
import business.Sede;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import risorse.GestoreFileTXT;
import fileManagement.EntitaEsistenteException;
import fileManagement.TXTManager;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Classe che origina un'istanza nel momento in cui l'utente desidera chiudere l'applicazione.
 *
 */
public class SalvataggioDati implements EventHandler<WindowEvent> {

    /**
     * Intercetta la chiusura della finestra per poter mostrare un alert di conferma all'utente, 
     * salvare i valori dei contatori raggiunti dal sistema e cancellare l'eventuale cookie di sessione presente.
     */
    @Override
    public void handle(WindowEvent event) {
	if(ControllerSchermataPrincipale.alertConfermato()) {
	    File cookieSessione = new File(GestoreFileTXT.PERCORSO_TXT_COOKIE);
	    TXTManager txtManager = new TXTManager(cookieSessione);
	    txtManager.cancella();

	    salvaVariabili();   
	}
    }
    
    private static void salvaVariabili() {
	File statoVariabili = new File(GestoreFileTXT.PERCORSO_TXT_VARIABILI);
	TXTManager managerVariabili = new TXTManager(statoVariabili);
	if(!managerVariabili.vuoto()) {
	    managerVariabili.svuota();
	}
	String nomeSede = GestoreFileTXT.NOME_SEDE_VARIABILI;
	String nomeFascia = GestoreFileTXT.NOME_FASCIA_VARIABILI;
	String nomeNoleggio = GestoreFileTXT.NOME_NOLEGGIO_VARIABILI;

	try {
	    managerVariabili.scrivi(nomeSede, Integer.toString(Sede.getUltimoNumero()));
	    managerVariabili.scrivi(nomeFascia, Integer.toString(FasciaAuto.getUltimoId()));
	    managerVariabili.scrivi(nomeNoleggio, Noleggio.getUltimoId().toString());
	} catch (EntitaEsistenteException e) {
	    e.printStackTrace();
	}
    }
}