package presentation.controllerSchermate.comuni;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import main.Main;
import fileManagement.TXTManager;
import business.BusinessObject;
import business.Risposta;
import business.Utente.Tipo;
import presentation.controllerSchermate.amministratore.ControllerSchermataPrincipaleAmministratore;
import presentation.controllerSchermate.cliente.ControllerSchermataPrincipaleCliente;
import presentation.controllerSchermate.operatore.ControllerSchermataPrincipaleOperatore;
import risorse.GestoreFileTXT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;

/**
 * Controller che si occupa della gestione e visualizzazione dei risultati generati dal sistema in base alla richiesta effettuata.
 */
public class ControllerSchermataRisultatiRicerca implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata dei risultati per un dipendente.
     */
    public static final String PATH_SCHERMATA_RISULTATI_RICERCA = "/schermate/comuni/SchermataRisultatiRicerca.fxml";
    
    @FXML
    private ListView<String> listaElementiTrovati = new ListView<String>();
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Mostra la schermata principale dell'utente loggato nel sistema.
     */
    public void tornaIndietro() {
	TXTManager manager = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_COOKIE));
	Tipo tipoUtenteLoggato = Tipo.valueOf(manager.leggi(GestoreFileTXT.NOME_TIPO_COOKIE));
	String usernameUtenteLoggato = manager.leggi(GestoreFileTXT.NOME_USERNAME_COOKIE);
	
	if(tipoUtenteLoggato == Tipo.CLIENTE) {
	    Main.mostraSchermataAlPath(ControllerSchermataPrincipaleCliente.PATH_SCHERMATA_PRINCIPALE_CLIENTE);
	} else if(tipoUtenteLoggato == Tipo.OPERATORE) {
	    Main.mostraSchermataAlPath(ControllerSchermataPrincipaleOperatore.PATH_SCHERMATA_PRINCIPALE_OPERATORE);
	} else if(tipoUtenteLoggato == Tipo.AMMINISTRATORE) {
	    Main.mostraSchermataAlPath(ControllerSchermataPrincipaleAmministratore.PATH_SCHERMATA_PRINCIPALE_AMMINISTRATORE);
	}
	//In ogni caso avvalora il label di benvenuto con lo username dell'utente loggato.
	ControllerSchermataPrincipale csp = (ControllerSchermataPrincipale) Main.getControllerSchermataAttuale();
	csp.setUserName(usernameUtenteLoggato);
    }
    
    /**
     * Consente di avvalorare la lista per la visualizzazione dei risultati della ricerca.
     * @param risposta : la lista degli elementi trovati durante la ricerca.
     */
    public void impostaElementiTrovati(Risposta risposta) {
	ObservableList<String> lista = FXCollections.observableArrayList();
	
	for(BusinessObject oggetto : risposta) {		//Utilizzo dell'iteratore!!
	    lista.add(oggetto.toString());
	}
	
	popolaLista(lista);
    }
    
    private void popolaLista(ObservableList<String> elementiDaInserire) {
	this.listaElementiTrovati.setItems(elementiDaInserire);
	this.listaElementiTrovati.setCellFactory(ComboBoxListCell.forListView(elementiDaInserire));
    }
}