package utility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe che implementa un gestore di date.
 * La classe contiene una serie di metodi utili a gestire una o piu' date.
 */
public class GestoreData {
    
    private static final String MESSAGGIO_DATA_OLTRE_LIMITI = "Data inserita oltre i limiti consentiti.";
    
    private static final String MESSAGGIO_ORDINE_DATE = "La data di fine deve essere posta cronologicamente dopo la data di inizio.";
    
    /**
     * Formatta la data secondo il formato AAAA-MM-GG.
     * @param data : la data da formattare.
     * @return la data formattata.
     * La stringa restituita sara' nel formato "AAAA-MM-GG".
     */
    public static String formattaData(LocalDate data) {
	return data.toString();
    }
    
    /**
     * Restituisce un'istanza di LocalDate corrispondente alla stringa passata in input.
     * @param data : la stringa rappresentate la data nel formata "AAAA-MM-GG".
     * @return l'oggetto LocalDate rappresentante la data in input.
     */
    public static LocalDate deFormattaData(String data) {
	return LocalDate.parse(data);
    }
    
    /**
     * Restituisce la distanza in giorni tra due date.
     * La data di inizio deve essere localizzata temporalmente prima della data di fine.
     * La prima data possibile inseribile e' il 01-01-0000 e l'ultima e' 31-12-9999.
     * @param dataInizio : la data di inizio.
     * @param dataFine : la data di fine.
     * @return il numero di giorni che intercorrono tra le due date.
     */
    public static int distanzaDate(LocalDate dataInizio, LocalDate dataFine) throws DataOltreLimitiException {
	if(dataFine.isBefore(dataInizio)) {
	    throw new OrdineDateException(MESSAGGIO_ORDINE_DATE);
	} else if(dataInizio.getYear() < 0 || dataFine.getYear() > 9999) {
	    throw new DataOltreLimitiException(MESSAGGIO_DATA_OLTRE_LIMITI);
	} else {
	    return (int) dataInizio.until(dataFine, ChronoUnit.DAYS);
	}
    }
}