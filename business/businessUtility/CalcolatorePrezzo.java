package business.businessUtility;

import java.sql.SQLException;
import java.time.LocalDate;

import utility.GestoreData;
import business.GestioneNoleggioService;
import business.Noleggio;
import business.Noleggio.DurataNoleggio;

/**
 * Calcolatore del prezzo totale di un noleggio in base alle regole di business seguite dall'azienda.
 */
public class CalcolatorePrezzo {

    /**
     * Prezzo base per un noleggio giornaliero con chilometraggio limitato.
     */
    public static final double PREZZO_BASE_GIORNALIERO_LIMITATO = 40.0;
    
    /**
     * Prezzo base per un noleggio giornaliero con chilometraggio illimitato.
     */
    public static final double PREZZO_BASE_GIORNALIERO_ILLIMITATO = 50.0;
    
    /**
     * Prezzo base per un noleggio settimanale con chilometraggio limitato.
     */
    public static final double PREZZO_BASE_SETTIMANALE_LIMITATO = 250.0;
    
    /**
     * Prezzo base per un noleggio giornaliero con chilometraggio illimitato.
     */
    public static final double PREZZO_BASE_SETTIMANALE_ILLIMITATO = 300.0;


    /**
     * Multa base per un noleggio giornaliero con chilometraggio limitato.
     * La multa scatta se si supera la data di consegna o i chilometri prefissati.
     */
    public static final double MULTA_BASE_GIORNALIERO_LIMITATO = 15.0;
    
    /**
     * Prezzo base per un noleggio giornaliero con chilometraggio ilimitato.
     * La multa scatta se si supera la data di consegna.
     */
    public static final double MULTA_BASE_GIORNALIERO_ILLIMITATO = 20.0;
    
    /**
     * Prezzo base per un noleggio settimanale con chilometraggio limitato.
     * La multa scatta se si supera la data di consegna o i chilometri prefissati.
     */
    public static final double MULTA_BASE_SETTIMANALE_LIMITATO = 60.0;
    
    /**
     * Prezzo base per un noleggio settimanale con chilometraggio illimitato.
     * La multa scatta se si supera la data di consegna.
     */
    public static final double MULTA_BASE_SETTIMANALE_ILLIMITATO = 50.0;

    
    /**
     * Costo per chilometro in piu' per un noleggio giornaliero.
     */
    public static final double MULTA_KM_GIORNALIERO = 0.50;
    
    /**
     * Costo per chilometro in piu' per un noleggio settimanale.
     */
    public static final double MULTA_KM_SETTIMANALE = 0.30;

    
    /**
     * Costo per giorni in piu' per un noleggio giornaliero.
     */
    public static final double MULTA_GIORNI_GIORNALIERO = 12.0;
    
    /**
     * Costo per giorn in piu' per un noleggio settimanale.
     */
    public static final double MULTA_GIORNI_SETTIMANALE = 10.0;

    /**
     * Calcola il prezzo totale del noleggio, esclusi i danni addizionali causati dal cliente.
     * @param noleggio : il noleggio contenente tutti i campi avvalorati tranne il campo del prezzo.
     * @return il prezzo base totale del noleggio, in base alle regole di business dettate dall'azienda.
     * @throws SQLException : se si verifica un errore di connessione al database nella prelevazione dei dati necessari al calcolo finale del costo del noleggio.
     */
    public static double calcolaPrezzo(Noleggio noleggio) throws SQLException {
	double totale = 0;
	double kmFinale = noleggio.getKmFinale();
	double kmIniziale = GestioneNoleggioService.ottieniKmInizialeDaIdNoleggio(noleggio.getId().toString());
	double prezzoKm = GestioneNoleggioService.ottieniPrezzoChilometricoDaNoleggio(noleggio);
	double totaleChilometri = kmFinale - kmIniziale;
	DurataNoleggio durata = noleggio.getDurata();
	double chilometraggio = noleggio.getChilometraggio();
	LocalDate dataInizio = noleggio.getDataInizio();
	LocalDate dataFine = noleggio.getDataFine();

	//Giornaliero e limitato.
	if(durata.equals(DurataNoleggio.GIORNALIERO) && chilometraggio != Noleggio.VALORE_CHILOMETRAGGIO_ILLIMITATO) {
	    totale = PREZZO_BASE_GIORNALIERO_LIMITATO + (totaleChilometri * prezzoKm);
	    int distanzaDate = GestoreData.distanzaDate(dataInizio, dataFine);
	    totale += MULTA_GIORNI_GIORNALIERO * distanzaDate;
	    double kmInEccesso = GestioneNoleggioService.kmInEccesso(noleggio);
	    totale += MULTA_KM_GIORNALIERO * kmInEccesso;

	    if(distanzaDate > 0 || kmInEccesso > 0) {
		totale += MULTA_BASE_GIORNALIERO_LIMITATO;
	    }
	    //Giornaliero e illimitato.
	} else if(durata.equals(DurataNoleggio.GIORNALIERO) && chilometraggio == Noleggio.VALORE_CHILOMETRAGGIO_ILLIMITATO) {
	    totale = PREZZO_BASE_GIORNALIERO_ILLIMITATO + (totaleChilometri * prezzoKm);
	    int distanzaDate = GestoreData.distanzaDate(dataInizio, dataFine);
	    totale += MULTA_GIORNI_GIORNALIERO * distanzaDate;

	    if(distanzaDate > 0) {
		totale += MULTA_BASE_GIORNALIERO_ILLIMITATO;
	    }	
	    //Settimanale e limitato.
	} else if(durata.equals(DurataNoleggio.SETTIMANALE) && chilometraggio != Noleggio.VALORE_CHILOMETRAGGIO_ILLIMITATO) {
	    totale = PREZZO_BASE_SETTIMANALE_LIMITATO + (totaleChilometri * prezzoKm);
	    int distanzaDate = GestoreData.distanzaDate(dataInizio, dataFine);
	    totale += MULTA_GIORNI_SETTIMANALE * distanzaDate;
	    double kmInEccesso = GestioneNoleggioService.kmInEccesso(noleggio); 
	    totale += MULTA_KM_SETTIMANALE * kmInEccesso;

	    if(distanzaDate > 0 || kmInEccesso > 0) {
		totale += MULTA_BASE_SETTIMANALE_LIMITATO * distanzaDate;
	    }
	    //Settimanale e illimitato.
	} else if(durata.equals(DurataNoleggio.SETTIMANALE) && chilometraggio == Noleggio.VALORE_CHILOMETRAGGIO_ILLIMITATO) {
	    totale = PREZZO_BASE_SETTIMANALE_ILLIMITATO + (totaleChilometri * prezzoKm);
	    int distanzaDate = GestoreData.distanzaDate(dataInizio, dataFine);
	    totale += MULTA_GIORNI_SETTIMANALE * distanzaDate;

	    if(distanzaDate > 0) {
		totale += MULTA_BASE_SETTIMANALE_ILLIMITATO;
	    }
	}
	return totale;
    }
}