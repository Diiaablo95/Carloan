package business;

/**
 * Classe che rappresenta l'oggetto di business "Fascia d'autoveicolo".
 */
public class FasciaAuto extends BusinessObject {

    /**
     * ID assegnato di default dal sistema nel caso di ricerca in cui non viene inserito l'ID da cercare.
     */
    public static final int DEFAULT_ID = -1;
    
    private static int ultimoId = 0;
    
    private int id;
    private double prezzoKm;
    
    /**
     * L'ultimo ID per una fascia generato automaticamente dal sistema.
     * @return l'ultimo ID per una fascia generato automaticamente dal sistema.
     */
    public static int getUltimoId() {
	return ultimoId;
    }
    
    /**
     * Setta l'ultimo ID per una fascia generato automaticamente dal sistema.
     * @param valore : l'ultimo ID per una fascia generato automaticamente dal sistema.
     */
    public static void setUltimoId(int valore) {
	ultimoId = valore;
    }

    /**
     * L'ID di una fascia di autoveicolo.
     * @return l'ID di una fascia di autoveicolo.
     */
    public int getId() {
        return id;
    }

    /**
     * Setta l'ID di una fascia di autoveicolo.
     * @param id : l'ID di una fascia di autoveicolo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Il prezzo chilometrico per una fascia di autoveicolo.
     * @return il prezzo chilometrico per una fascia di autoveicolo.
     */
    public double getPrezzoKm() {
        return prezzoKm;
    }

    /**
     * Setta il prezzo chilometrico per una fascia di autoveicolo.
     * @param prezzoKm : il prezzo chilometrico per una fascia di autoveicolo.
     */
    public void setPrezzoKm(double prezzoKm) {
        this.prezzoKm = prezzoKm;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return String.format("ID : %-20d, KM/euro : %-8.2f", this.id, this.prezzoKm);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	FasciaAuto f = (FasciaAuto) o;
	return this.id == f.getId() && this.prezzoKm == f.getPrezzoKm();
    }
}