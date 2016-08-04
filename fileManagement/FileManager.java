package fileManagement;

import java.io.File;
import java.io.IOException;

/**
 * Classe che deve essere concretizzata da tutte quelle classi che permettono la gestione di un qualsiasi tipo di file.
 */
public abstract class FileManager {
    //File da manipolare.
    protected File fileDaGestire;
    
    /**
     * Rappresenta la root directory di tutti i file presenti nel progetto.
     * Consente di rendere la localizzazione dei files platform-independent.
     */
    public static final String DIRECTORY_BASE = System.getProperty("user.dir") + "/src";
    
    /**
     * Salva il file su cui i vari gestori specializzati devono lavorare.
     * @param fileDaGestire : il file da manipolare.
     * @throws IOException : se si verifica un errore nella creazione del file.
     */
    public FileManager(File fileDaGestire) {
	this.fileDaGestire = fileDaGestire;
	try {
	    this.fileDaGestire.createNewFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Svuota il contenuto del documento.
     */
    public abstract void svuota();
    
    /**
     * Cancella il file.
     */
    public void cancella() {
	this.fileDaGestire.delete();
    }
    
    /**
     * Indica se il file da manipolare e' vuoto o no.
     * @return l'esito della verifica sul file.
     */
    public boolean vuoto() {
	return this.fileDaGestire.length() == 0;
    }  
}