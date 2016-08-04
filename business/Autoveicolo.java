package business;

/**
 * Classe che rappresenta l'oggetto di business "Autoveicolo".
 */
public class Autoveicolo extends BusinessObject {
    
    /**
     * Possibile stato in cui puo' trovarsi un autoveicolo.
     */
    public static enum Stato {DISPONIBILE, NOLEGGIATO, MANUTENZIONE_ORDINARIA, MANUTENZIONE_STRAORDINARIA};

    private String targa;
    private Stato stato;
    private int idSede;
    private String idModello;
    private double chilometraggio;

    /**
     * Targa dell'autoveicolo.
     * @return la targa dell'autoveicolo.
     */
    public String getTarga() {
        return targa;
    }
    
    /**
     * Setta la targa dell'autoveicolo.
     * @param targa : la targa dell'autoveicolo.
     * La targa deve seguire il formato AA000AA, dove A indica un qualsiasi carattere appartenente all'alfabeto inglese e
     * 0 indica una qualsiasi cifra compresa tra 0 e  9.
     */
    public void setTarga(String targa) {
        this.targa = targa;
    }
    
    /**
     * Lo stato attuale dell'autoveicolo.
     * @return lo stato attuale dell'autoveicolo.
     */
    public Stato getStato() {
        return stato;
    }
    
    /**
     * Setta lo stato attuale dell'autoveicolo.
     * @param stato : lo stato attuale dell'autoveicolo.<br>
     * @see business.Autoveicolo.Stato Stato
     */
    public void setStato(Stato stato) {
        this.stato = stato;
    }
    
    /**
     * La sede in cui si trova l'autoveicolo, se disponibile.
     * @return l'ID della sede in cui si trova l'autoveicolo.
     */
    public int getIdSede() {
        return idSede;
    }
    
    /**
     * Setta la sede in cui si trova attualmente l'autoveicolo.
     * @param idSede : l'ID della sede in cui si trova l'autoveicolo.
     * @see business.Sede Sede
     */
    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }
    
    /**
     * Il modello dell'autoveicolo.
     * @return l'ID del modello dell'autoveicolo.
     */
    public String getIdModello() {
        return idModello;
    }
    
    /**
     * Setta il modello di appartenenza dell'autoveicolo.
     * @param idModello : l'ID del modello dell'autoveicolo.
     * @see business.Modello Modello
     */
    public void setIdModello(String idModello) {
        this.idModello = idModello;
    }
    
    /**
     *Chilometraggio dell'autoveicolo.
     * @return il chilometraggio dell'autoveicolo.
     */
    public double getChilometraggio() {
	return chilometraggio;
    }

    /**
     * Setta il chilometraggio dell'autoveicolo.
     * @param chilometraggio : il chilometraggio dell'autoveicolo.
     */
    public void setChilometraggio(double chilometraggio) {
	this.chilometraggio = chilometraggio;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return String.format("TARGA: %-7s, STATO: %-30s, SEDE: %-5d, MODELLO: %-40s, CHILOMETRAGGIO: %-8.2f", this.targa, this.stato.toString(),
		this.idSede, this.idModello, this.chilometraggio);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Autoveicolo a = (Autoveicolo) o;
	return this.targa.equals(a.getTarga()) && this.stato == a.getStato() && this.idSede == a.getIdSede() && this.idModello.equals(a.getIdModello()) && 
		this.chilometraggio == a.getChilometraggio();
    }
}