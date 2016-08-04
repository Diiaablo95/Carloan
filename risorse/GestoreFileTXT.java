package risorse;

import fileManagement.FileManager;

/**
 * Classe contenente tutte le informazioni necessarie alla corretta gestione dei file TXT utili al sistema.
 */
public class GestoreFileTXT {

    /**
     * Percorso in cui si trova o in cui viene creato il documento contenente l'ID della sede in cui il terminale su cui gira l'applicazione si trova.
     */
    public static final String PERCORSO_TXT_SEDE = FileManager.DIRECTORY_BASE +  "/risorse/idSede.txt";
    
    /**
     * Nome dell'entita' relatva all'id della sede in cui si trova il terminale.
     */
    public static final String NOME_ID_SEDE = "id";
    
    /**
     * Percorso in cui si trova o in cui viene creato il cookie di sessione.
     */
    public static final String PERCORSO_TXT_COOKIE = FileManager.DIRECTORY_BASE + "/risorse/sessionCookie.txt";
    
    /**
     * Nome dell'entita' relativa allo username dell'utente autenticato.
     */
    public static final String NOME_USERNAME_COOKIE = "username";
    
    /**
     * Nome dell'entita' relativa al tipo di utente autenticato.
     */
    public static final String NOME_TIPO_COOKIE = "tipo";
    
    /**
     * Nome dell'entita' relativa alla data di autenticazione.
     */
    public static final String NOME_DATA_COOKIE = "data";
    
    /**
     * Percorso in cui si trova o in cui viene creato il documento contenente le credenziali di accesso al database.
     */
    public static final String PERCORSO_TXT_CREDENZIALI = FileManager.DIRECTORY_BASE + "/risorse/credenzialiDB.txt";
    
    /**
     * Nome dell'entita' relativa all'host su cui si trova il database.
     */
    public static final String NOME_HOST_CREDENZIALI = "host";
    
    /**
     * Nome dell'entita' relativa al nome del database contenente le informazioni del sistema.
     */
    public static final String NOME_DB_CREDENZIALI = "db";
    
    /**
     * Nome dell'entita' relativa al nome utente per accede al database.
     */
    public static final String NOME_USERNAME_CREDENZIALI = "user";
    
    /**
     * Nome dell'entita' relativa alla password per accedere al database.
     */
    public static final String NOME_PASSWORD_CREDENZIALI = "password";
    
    /**
     * Percorso in cui si trova o in cui viene creato il documento contenente l'ultimo valore per le variabili <nbsp>
     * generate automaticamente dal sistema.
     */
    public static final String PERCORSO_TXT_VARIABILI = FileManager.DIRECTORY_BASE+ "/risorse/valoriVariabili.txt";
    
    /**
     * Nome dell'entita relativa all'ultimo ID per una sede.
     */
    public static final String NOME_SEDE_VARIABILI = "sede";
    
    /**
     * Nome dell'entita relativa all'ultimo ID per una fascia.
     */
    public static final String NOME_FASCIA_VARIABILI = "fascia";
    
    /**
     * Nome dell'entita relativa all'ultimo ID per un noleggio.
     */
    public static final String NOME_NOLEGGIO_VARIABILI = "noleggio";
}
