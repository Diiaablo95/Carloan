package main;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import presentation.controllerSchermate.comuni.ControllerSchermataLogin;
import risorse.GestoreFileTXT;
import business.FasciaAuto;
import business.Noleggio;
import business.Sede;
import fileManagement.TXTManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Ha il compito di gestire l'avvio dell'applicazione e l'istanziazione della relativa schermata.
 * @author Antonio Antonino & Michel Bellomo
 *
 */
public class Main extends Application {

    //Contenitore padre di tutte le schermate che vengono mostrate.
    private static Stage stagePrimario;

    //Il controller che ogni volta verra' istanziato in base alla schermata mostrata.
    private static Initializable controllerSchermataMostrata;

    //Il nodo radice di ogni documento FXML che contiene la GUI attualmente mostrata.
    private static AnchorPane nodoRadiceDocumentoXML;

    /**
     * Inizializza lo stage e la prima schermata di avvio.
     */
    public void start(Stage stage) {
	stagePrimario = stage;
	stagePrimario.setTitle("CarLoan");
	stagePrimario.setHeight(735);
	stagePrimario.setWidth(1280);
	stagePrimario.setResizable(false);
	stagePrimario.setOnCloseRequest(new SalvataggioDati());

	mostraSchermataAlPath(ControllerSchermataLogin.PERCORSO_SCHERMATA_LOGIN);
    }

    /**
     * Restituisce lo stage principale dell'intera applicazione.
     * @return lo stage principale.
     */
    public static Stage ottieniStage() {
	return stagePrimario;
    }

    /**
     * Mostra la schermata richiesta in base al percorso passato.
     * @param path : il path della finestra da mostrare.
     */
    public static void mostraSchermataAlPath(String path) {
	try {
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource(path));
	    AnchorPane schermataDaMostrare = (AnchorPane) loader.load();
	    controllerSchermataMostrata = loader.getController();	//Imposto il controller della classe mostrata
	    nodoRadiceDocumentoXML = schermataDaMostrare;

	    Scene scene = new Scene(schermataDaMostrare);
	    Main.ottieniStage().setScene(scene);
	    Main.ottieniStage().show();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Restituisce il controller della schermata mostrata.
     * @return il controller della schermata mostrata.
     */
    public static Initializable getControllerSchermataAttuale() {
	return controllerSchermataMostrata;
    }

    /**
     * Restituisce il nodo radice del documento XML associato alla schermata mostrata.
     * @return il nodo radice del documento XML associato alla schermata mostrata.
     */
    public AnchorPane getNodoRadiceXML() {
	return nodoRadiceDocumentoXML;
    }

    /**
     * Ha il compito di chiamare il metodo per l'istanziazione dello Stage e delle varie scene.
     * @param args : lo stage da cui partire per la creazione delle scene.
     */
    public static void main(String[] args) {
	settaVariabili();
	launch(args);
    }

    //Aggiorna l'ID attuale raggiunto dal sistema per i BO che prevedono la generazione automatica dell'ID.
    private static void settaVariabili() {
	File statoVariabili = new File(GestoreFileTXT.PERCORSO_TXT_VARIABILI);
	TXTManager managerVariabili = new TXTManager(statoVariabili);

	if(!managerVariabili.vuoto()) {
	    String nomeSede = GestoreFileTXT.NOME_SEDE_VARIABILI;
	    String nomeFascia = GestoreFileTXT.NOME_FASCIA_VARIABILI;
	    String nomeNoleggio = GestoreFileTXT.NOME_NOLEGGIO_VARIABILI;
	    
	    Sede.setUltimoNumero(Integer.parseInt(managerVariabili.leggi(nomeSede)));
	    FasciaAuto.setUltimoId(Integer.parseInt(managerVariabili.leggi(nomeFascia)));
	    Noleggio.setUltimoId(new BigInteger(managerVariabili.leggi(nomeNoleggio)));
	}
    }
}