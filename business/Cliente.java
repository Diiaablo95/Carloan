package business;

/**
 * Classe che rappresenta il cliente che vuole effettuare un operazione offerta dal sistema.
 */
public class Cliente extends Utente {

    private String cf;
    private String carta;
    
    /**
     * Il codice fiscale del cliente.
     * @return il codice fiscale del cliente.
     */
    public String getCf() {
        return cf;
    }
    
    /**
     * Setta il codice fiscale del cliente.
     * @param cf : il codice fiscale del cliente.
     * Il codice fiscale e' una sequenza alfanumerica lunga 16 caratteri identificativa di una persona.
     */
    public void setCf(String cf) {
        this.cf = cf;
    }
    
    /**
     * Il numero di carta associata al cliente.
     * @return il numero di carta associata al cliente.
     */
    public String getCarta() {
        return carta;
    }
    
    /**
     * Setta il numero di carta associata al cliente.
     * @param carta : il numero di carta associata al cliente.
     * Il numero di carta e' una sequenza alfanumerica lunga 16 caratteri.
     */
    public void setCarta(String carta) {
        this.carta = carta;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return super.toString() + String.format("CF : %-16s, CARTA : %-16s", this.cf, this.carta);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Cliente c = (Cliente) o;
	return super.equals(o) && this.carta.equals(c.getCarta()) && this.cf.equals(c.getCf());
    }
}