package business;

/**
 * Classe che rappresenta l'oggetto di business "Modello di autoveicolo".
 */
public class Modello extends BusinessObject {

    /**
     * Possibile numero di porte per un modello di autoveicolo.
     */
    public static enum Porte {TRE, CINQUE};
    
    /**
     * Possible tipo di carrozzeria per un modello di autoveicolo.
     */
    public static enum Carrozzeria {BERLINA, MONOVOLUME, DUE_VOLUMI, STATION_WAGON, COUPE, DECAPPOTTABILE, SPIDER};
    
    /**
     * Possibile tipo di cambio per un modello di autoveicolo.
     */
    public static enum Cambio {AUTOMATICO, MANUALE};
    
    /**
     * Possibile tipo di motore per un modello di autoveicolo.
     */
    public static enum Motore {DIESEL, BENZINA};
    
    private String id;
    private int idFascia;
    private Porte porte;
    private Carrozzeria carrozzeria;
    private Cambio cambio;
    private Motore motore;
    
    /**
     * L'ID del modello.
     * @return l'ID del modello.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Setta l'ID del modello.
     * L'ID di un modello e' una sequenza alfanumerica di lunghezza massima pari a 50 caratteri.
     * @param id : l'ID del modello.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * La fascia a cui appartiene il modello.
     * @return l'ID della fascia.
     */
    public int getIdFascia() {
        return idFascia;
    }
    
    /**
     * Setta la fascia di appartenenza del modello.
     * @see FasciaAuto#setId(int)
     * @param idFascia : l'ID della fascia di appartenenza del modello.
     */
    public void setIdFascia(int idFascia) {
        this.idFascia = idFascia;
    }
    
    /**
     * Il numero di porte del modello.
     * @return il numero di porte del modello.
     */
    public Porte getPorte() {
        return porte;
    }
    
    /**
     * Setta il numero di porte del modello.
     * @param porte : il numero di porte del modello.
     * @see Modello.Porte Porte
     */
    public void setPorte(Porte porte) {
        this.porte = porte;
    }
    
    /**
     * Il tipo di carrozzeria del modello.
     * @return il tipo di carrozzeria del modello.
     */
    public Carrozzeria getCarrozzeria() {
        return carrozzeria;
    }
    
    /**
     * Setta il tipo di carrozzeria del modello.
     * @param carrozzeria : il tipo di carrozzeria del modello.
     * @see Modello.Carrozzeria Carrozzeria
     */
    public void setCarrozzeria(Carrozzeria carrozzeria) {
        this.carrozzeria = carrozzeria;
    }
    
    /**
     * Il tipo di cambio del modello.
     * @return il tipo di cambio del modello.
     */
    public Cambio getCambio() {
        return cambio;
    }
    
    /**
     * Setta il tipo di cambio del modello.
     * @param cambio : il tipo di cambio del modello.
     * @see Modello.Cambio Cambio
     */
    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }
    
    /**
     * Il tipo di motore del modello.
     * @return il tipo di motore del modello.
     */
    public Motore getMotore() {
        return motore;
    }
    
    /**
     * Setta il tipo di motore del modello.
     * @param motore : il tipo di motore del modello.
     * @see Modello.Motore Motore
     */
    public void setMotore(Motore motore) {
        this.motore = motore;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return String.format("ID : %-40s, FASCIA : %-20d, PORTE : %-10s, CARROZZERIA : %-40s, MOTORE : %-15s, CAMBIO : %-15s", 
		this.id, this.idFascia, this.porte.toString(), this.carrozzeria.toString(), this.motore.toString(), this.cambio.toString());
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Modello m = (Modello) o;
	return this.cambio == m.getCambio() && this.carrozzeria == m.getCarrozzeria() && this.id.equals(m.getId()) &&
		this.idFascia == m.getIdFascia() && this.motore == m.getMotore() && this.porte == m.getPorte();
    }
}