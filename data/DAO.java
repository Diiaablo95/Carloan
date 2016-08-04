package data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import risorse.GestoreFileTXT;
import fileManagement.TXTManager;
import business.TO;

/**
 * Classe che deve essere concretizzata da tutte quelle classi che si occupano di prelevare dati dai data source.
 * Contiene i parametri per la connessione al database.
 */
public abstract class DAO {
    
    /**
     * Codice di risposta nel caso di errore di connessione al database.
     */
    public static final String ERRORE_CONNESSIONE_DATABASE = "R00";
    
    /**
     * Codice di risposta nel caso di credenziali corrette.
     */
    public static final String CODICE_RESULTSET_PIENO = "R10";
    
    /**
     * Codice di risposta nel caso di credenziali errate o username non presente.
     */
    public static final String CODICE_RESULTSET_VUOTO = "R01";
    
    /**
     * Codice di risposta nel caso di errore nell'inserimento dei dati richiesti.
     */
    public static final String ERRORE_INSERIMENTO_DATI = "R02";
    
    /**
     * Codice di risposta nel caso di inserimento di un valore duplicato per la chiave primaria di una tabella.
     */
    public static final String ERRORE_CHIAVE_PRESENTE = "R03";
    
    /**
     * Codice di risposta nel caso di inserimento andato a buon fine.
     */
    public static final String CODICE_INSERIMENTO_AVVENUTO = "R11";
    
    /**
     * Codice di risposta nel caso di errore nella modifica dei dati richiesti.
     */
    public static final String ERRORE_MODIFICA_DATI = "R04";
    
    /**
     * Codice di risposta nel caso di modifica andata a buon fine.
     */
    public static final String CODICE_MODIFICA_AVVENUTA = "R12";
    
    /**
     * Codice di risposta nel caso di errore nell'eliminazione dei dati richiesti.
     */
    public static final String ERRORE_ELIMINAZIONE_DATI = "R05";
    
    /**
     * Codice di risposta nel caso di eliminazione andata a buon fine.
     */
    public static final String CODICE_ELIMINAZIONE_AVVENUTA = "R13";
    
    /**
     * Codice della SQLExcpetion generato in caso di eccezione dovuta a chiave duplicata.
     */
    protected static final int CODICE_ERRORE_CHIAVE_DUPLICATA = 1062;
    
    /**
     * Messaggio visualizzato in casi estremi di probabile inconsistenza dei dati nel database.
     */
    protected static final String MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE = "Si prega di controllare il database per probabile inconsistenza dei dati.";
    
    private StringBuffer percorsoDB = new StringBuffer("jdbc:mysql://");
    protected Connection connessione;
    
    /**
     * Costruttore che restituisce un'istanza della classe.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public DAO() throws SQLException {
	connetti();
    }
    
    /**
     * Permette di leggere i dati dal database.
     * @param dati : parametri per la ricerca.
     * @return il risultato della ricerca.
     * @throws SQLException : la connessione con il database non e' disponibile.
     */
    public abstract TO leggi(TO dati) throws SQLException;
    
    private void connetti() throws SQLException {
	TXTManager man = new TXTManager(new File(GestoreFileTXT.PERCORSO_TXT_CREDENZIALI));
	//Preleva i dati di accesso da credenzialiDB.txt
	String nomeHost = GestoreFileTXT.NOME_HOST_CREDENZIALI;
	String nomeDB = GestoreFileTXT.NOME_DB_CREDENZIALI;
	String nomeUsername = GestoreFileTXT.NOME_USERNAME_CREDENZIALI;
	String nomePassword = GestoreFileTXT.NOME_PASSWORD_CREDENZIALI;
	String[] credenziali = new String[4];
	credenziali[0] = man.leggi(nomeHost);
	credenziali[1] = man.leggi(nomeDB);
	credenziali[2] = man.leggi(nomeUsername);
	credenziali[3] = man.leggi(nomePassword);
	this.percorsoDB.append(credenziali[0] + "/" + credenziali[1]);
	this.connessione = (Connection) DriverManager.getConnection(this.percorsoDB.toString(), credenziali[2], credenziali[3]);
    }
}