package business;

/**
 * Classe che rappresenta l'utente che si autentica nel sistema. 
 */
public class Utente extends BusinessObject {
    
    /**
     * Possibili tipologie di utente autenticato.
     */
    public static enum Tipo {CLIENTE, OPERATORE, AMMINISTRATORE};
    
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private Tipo tipo;
    
    /**
     * Username dell'utente.
     * @return lo username dell'utente.
     */
    public String getUsername() {
        return this.username;
    }
    
    /**
     * Setta lo username dell'utente.
     * @param username : lo username dell'utente.
     * Lo username e' una sequenza alfanumerica di massimo 15 caratteri.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Password dell'utente.
     * @return la password dell'utente.
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Setta la password dell'utente.
     * @param password : la password dell'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Nome dell'utente.
     * @return il nome dell'utente.
     */
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Setta il nome dell'utente..
     * @param nome : il nome dell'utente.
     * Il nome e' una sequenza di massimo 20 caratteri
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Cognome dell'utente.
     * @return il cognome dell'utente.
     */
    public String getCognome() {
        return this.cognome;
    }
    
    /**
     * Setta il cognome dell'utente.
     * @param cognome : il cognome dell'utente.
     * Il nome e' una sequenza di massimo 20 caratteri
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    /**
     * Telefono dell'utente nel formato internazionale con il prefisso +39.
     * @return il telefono dell'utente.
     */
    public String getTelefono() {
        return this.telefono;
    }
    
    /**
     * Setta il numero di telefono dell'utente.
     * @param telefono : il numero di telefono dell'utente.
     * Il numero di telefono e' formato da una sequenza di 13 caratteri, di cui i primi 3 sono +39.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * L'indirizzo mail dell'utente.
     * @return l'indirizzo mail dell'utente.
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Setta l'indirizzo mail dell'utente.
     * @param email : l'indirizzo mail dell'utente.
     * L'email e' una sequenza alfanumerica di caratteri che segue il formato internazionale.
     * La lunghezza massima e' pari a 50 caratteri.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Il tipo di utente autenticato.
     * @param tipo : il tipo di utente autenticato.
     * @see Utente.Tipo Tipo
     */
    public void setTipo(Tipo tipo) {
	this.tipo = tipo;
    }
    
    /**
     * Il tipo di utente autenticato.
     * @return il tipo di utente autenticato.
     */
    public Tipo getTipo() {
	return this.tipo;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	return String.format("USER: %-15s, PASSWORD: %-32s, NOME: %-20s, COGNOME: %-20s, TELEFONO: %-13s, EMAIL: %-50s ", 
		this.username, this.password, this.nome, this.cognome, this.telefono, this.email);
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Utente u = (Utente) o;
	return this.cognome!= null? this.equals(u.getCognome()) : u.getCognome() == null && this.email != null? this.email.equals(u.getEmail()) : u.getEmail() == null && this.nome != null? this.nome.equals(u.getNome()) : u.getNome() == null &&
		this.password != null? this.password.equals(u.getPassword()) : u.getPassword() == null && this.telefono != null? this.telefono.equals(u.getTelefono()) : u.getTelefono() == null && this.username != null? this.username.equals(u.getUsername()) : u.getUsername() == null && this.tipo == u.getTipo();
    }
}