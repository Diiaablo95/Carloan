package business;

/**
 * Classe che rappresenta l'oggetto di business "Sede".
 */
public class Sede extends BusinessObject {
    
    /**
     * Valore di default corrispondente ad un ID non ancora impostato per una sede.
     */
    public static final int DEFAULT_ID = -1;
    
    /**
     * Valore corrispondente a nessuna sede. Utilizzato dagli autoveicoli che non sono disponibili, che sono quindi
     * o in noleggio o in manutenzione ordinaria o straordinaria.
     */
    public static final int ID_NESSUNA_SEDE = -2;
    
    private static int ultimoNumero = 0;
    
    private int numero;
    private String nome;
    private String indirizzo;
    
    /**
     * L'ultimo ID per una sede generato automaticamente dal sistema.
     * @return l'ultimo ID per una sede generato automaticamente dal sistema.
     */
    public static int getUltimoNumero() {
	return ultimoNumero;
    }

    /**
     * Setta l'ultimo ID per una sede generato automaticamente dal sistema.
     * @param valore : l'ultimo ID per una sede generato automaticamente dal sistema.
     */
    public static void setUltimoNumero(int valore) {
	ultimoNumero = valore;
    }

    /**
     * L'ID di una sede.
     * @return l'ID di una sede.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Setta l'ID di una sede.
     * @param numero : l'ID di una sede.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Il nome di una sede.
     * @return il nome di una sede.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setta il nome di una sede.
     * @param nome : il nome di una sede.
     * L'ID di una sede e' una sequenza alfanumerica di lunghezza massima pari a 20 caratteri.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * L'indirizzo di una sede.
     * @return l'indirizzo di una sede.
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Setta l'indirizzo di una sede.
     * @param indirizzo : l'indirizzo di una sede.
     * Il nome di una sede e' una sequenza alfanumerica di lunghezza massima pari a 50 caratteri.
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return String.format("ID : %-5d, NOME : %-20s, INDIRIZZO : %-50s", this.numero, this.nome, this.indirizzo);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Sede s = (Sede) o;
	return this.numero == s.getNumero() && this.nome.equals(s.getNome()) && this.indirizzo.equals(s.getIndirizzo());
    }
}
