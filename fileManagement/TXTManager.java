package fileManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe per gestire in lettura e scrittura piccoli documenti TXT contententi una sequenza di coppie del tipo "CHIAVE VALORE".
 * Il documento puo' contenere chiavi i cui nomi siano univoci all'interno del documento stesso.
 */
public class TXTManager extends FileManager {
    
    /**
     * Messaggio generato nel caso in cui si provi ad aggiungere un'entita' gia' esistente nel documento.
     */
    public static final String MESSAGGIO_ENTITA_ESISTENTE = "Entita' gia' presente nel documento.";
    
    private Scanner lettore;
    private FileWriter scrittore;
    
    /**
     * Crea un nuovo gestore di file TXT.
     * E' opportuno passare oltre al percorso e al nome del file, anche la sua estensione, ovvero ".txt" perche' <nbsp>
     * nel caso in cui il file non esista, viene creato ed e' opportuno che abbia la corretta estensione.
     * @param txtFile : il file da manipolare o, se non presente, da creare e poi manipolare.
     */
    public TXTManager(File txtFile) {
	super(txtFile);
    }
    
    /**
     * Inserisce una nuova entita' all'interno del documento TXT posizionandola in ultima posizione.
     * Non possono essere inseriti due entita' con lo stesso nome.
     * @param tag : nome dell'entita'.
     * @param valore : valore dell'entita'.
     * @throws EntitaEsistenteException : se l'entita' inserita e' gia' presente nel documento. 
     */
    public void scrivi(String tag, String valore) throws EntitaEsistenteException {
	
	if(esisteEntita(tag)) {
	    throw new EntitaEsistenteException(MESSAGGIO_ENTITA_ESISTENTE);
	} else {
	    try {
		this.scrittore = new FileWriter(this.fileDaGestire, true);
		this.scrittore.write(tag +" "+ valore +"\n");
		this.scrittore.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
    
    /**
     * Restituisce il valore di una entita'.
     * @param tag : nome dell'entita'.
     * @return il valore dell'entita'. Viene ritornato null se l'entita' non e' presente nel documento.
     */
    public String leggi(String tag) {
	try {
	    this.lettore = new Scanner(this.fileDaGestire);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	String valore = null;
	while(this.lettore.hasNextLine() && valore == null) {
	    String chiave = this.lettore.next();
	    if(chiave.equals(tag)) {
		valore = this.lettore.next();
	    } else if(this.lettore.hasNextLine()) {
		this.lettore.nextLine();
	    }
	}
	this.lettore.close();
	return valore;
    }
    
    /**
     * Svuota il contenuto del documento.
     */
    public void svuota() {
	try {
	    new FileWriter(this.fileDaGestire, false).close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private boolean esisteEntita(String nome) {
	return leggi(nome) != null;
    }
}