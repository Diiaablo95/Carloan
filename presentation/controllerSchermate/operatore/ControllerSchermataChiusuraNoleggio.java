package presentation.controllerSchermate.operatore;

import java.net.URL;
import java.util.ResourceBundle;

import presentation.FrontController;
import presentation.controllerSchermate.comuni.ControllerSchermataPrincipale;
import business.Noleggio;
import business.Richiesta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller che gestisce gli eventi riguardanti la chiusura definitiva di un noleggio da parte di un operatore.
 */
public class ControllerSchermataChiusuraNoleggio implements Initializable {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata di chiusura definitiva di un noleggio.
     */
    public static final String PATH_SCHERMATA_CHIUSURA_NOLEGGIO = "/schermate/operatore/SchermataChiusuraNoleggio.fxml";
    
    /**
     * Codice univoco per l'operazione di chiusura definitiva di un noleggio.
     */
    public static final String ID_CHIUSURA_NOLEGGIO_DEFINITIVA = "C61";
    
    private static String messaggioNoleggio = "Noleggio n. : ";
    
    @FXML
    private Label labelIdNoleggio, prezzoNoleggio;
    @FXML
    private TextField totaleDanni;
    
    private Richiesta richiestaNoleggioDaChiudere;
    
    /**
     * Viene chiamato per istanziare il controller quando la schermata ad esso associata viene mostrata.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    /**
     * Consente di passare l'informazione sul noleggio da chiudere alla schermata di chiusura del noleggio.
     */
    public void setRichiestaNoleggio(Richiesta richiestaNoleggio) {
	Noleggio noleggio = (Noleggio) richiestaNoleggio.prendiOggettoDaIndice(0);
	this.labelIdNoleggio.setText(messaggioNoleggio + noleggio.getId().toString());
	this.prezzoNoleggio.setText(noleggio.getSommaDenaro() + " euro");
	this.richiestaNoleggioDaChiudere = richiestaNoleggio;
    }
    
    /**
     * Crea la richiesta che il sistema soddisfera' di chiusura di un noleggio con i dati inseriti.
     */
    public void chiudiNoleggio() {
	if(ControllerSchermataPrincipale.alertConfermato()) {
	    aggiungiParametri();
	    FrontController.processaRichiesta(this.richiestaNoleggioDaChiudere);		//E poi si processa completamente la richiesta   
	}
    }
    
    private void aggiungiParametri() {
	String danni = this.totaleDanni.getText();
	if(!danni.isEmpty()) {
	    Noleggio noleggioDaChiudere = (Noleggio) this.richiestaNoleggioDaChiudere.prendiOggettoDaIndice(0);
	    double costoBase = noleggioDaChiudere.getSommaDenaro();
	    double aggiuntaDanni = Double.parseDouble(danni);		//AGGIUNGO IL VALORE INSERITO DELL'OPERATORE
	    noleggioDaChiudere.setSommaDenaro(costoBase + aggiuntaDanni);
	}
	this.richiestaNoleggioDaChiudere.setId(ID_CHIUSURA_NOLEGGIO_DEFINITIVA);	//CAMBIO L'ID DELLA RICHIESTA
    }
}