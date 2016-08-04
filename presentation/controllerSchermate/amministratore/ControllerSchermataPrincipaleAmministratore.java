package presentation.controllerSchermate.amministratore;

import presentation.controllerSchermate.operatore.ControllerSchermataPrincipaleOperatore;

/**
 * Controller che gestisce gli eventi riguardanti la schermata principale dell'amministratore.
 */
public class ControllerSchermataPrincipaleAmministratore extends ControllerSchermataPrincipaleOperatore {
    
    /**
     * Percorso al quale si trova il file relativo alla schermata principale dell'amministratore.
     */
    public static final String PATH_SCHERMATA_PRINCIPALE_AMMINISTRATORE = "/schermate/amministratore/SchermataPrincipaleAmministratore.fxml";
    
    /**
     * Mostra la schermata relativa alla gestione degli autoveicoli.
     */
    @Override
    public void visualizzaSchermataAutoveicoli() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataAutoveicoloDipendente.PATH_SCHERMATA_AUTOVEICOLI_AMMINISTRATORE);
    }
    
    /**
     * Mostra la schermata relativa alla gestione delle sedi.
     */
    public void visualizzaSchermataSedi() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataSede.PATH_SCHERMATA_SEDI);
    }
    
    /**
     * Mostra la schermata relativa alla gestione degli operatori.
     */
    public void visualizzaSchermataOperatori() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataOperatore.PATH_SCHERMATA_OPERATORI);
    }
    
    /**
     * Mostra la schermata relativa alla gestione delle fasce d'autoveicolo.
     */
    public void visualizzaSchermataFasce() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataFasciaAuto.PATH_SCHERMATA_FASCE);
    }
    
    /**
     * Mostra la schermata relativa alla gestione dei modelli.
     */
    public void visualizzaSchermataModelli() {
	visualizzaSchermataInPaneSecondario(ControllerSchermataModello.PATH_SCHERMATA_MODELLI);
    }
}