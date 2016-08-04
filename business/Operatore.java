package business;

/**
 * Classe che rappresenta l'operatore che interagisce con i clienti per realizzare i servizi offerti dal sistema.
 */
public class Operatore extends Utente {
    
    private int idSede;

    /**
     * L'ID della sede in cui lavora l'operatore.
     * @return l'ID della sede in cui lavora l'operatore.
     */
    public int getIdSede() {
        return idSede;
    }

    /**
     * Setta l'ID della sede in cui lavora l'operatore.
     * L'ID della sede deve corrispondere a quello di una sede gia' presente nella base di dati.
     * @param idSede : l'ID della sede in cui lavora l'operatore.
     */
    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return super.toString() + String.format("SEDE : %-5d", this.idSede);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Operatore op = (Operatore) o;
	return super.equals(o) && this.idSede == op.getIdSede();
    }
}