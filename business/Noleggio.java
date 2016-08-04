package business;

import java.math.BigInteger;
import java.time.LocalDate;

import utility.GestoreData;

/**
 * Classe che rappresenta l'oggetto di business "Noleggio".
 */
public class Noleggio extends BusinessObject {
    
    /**
     * Possibili tipologie di chilometraggio scelto per uno specifico noleggio.
     */
    public static enum DurataNoleggio {GIORNALIERO, SETTIMANALE};
    /**
    * Valore di default corrispondente al chilometraggio illimitato per un noleggio.
    */
   public static final double VALORE_CHILOMETRAGGIO_ILLIMITATO = -1;	//mentre 0 rappresenta il chilometraggio non inserito
   									//tra i parametri di ricerca.
   /**
    * Valore di default corrispondente ad un chilometraggio iniziale non impostato.
    */
   public static final double VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT = -1;
   
   /**
    * Valore di default corrispondente ad un tipo di chilometraggio non impostato.
    */
   public static final double VALORE_CHILOMETRAGGIO_NON_IMPOSTATO = -2;
   
   /**
    * Valore di default corrispondente ad un id non settato per un noleggio.
    */
   public static final BigInteger DEFAULT_ID = new BigInteger("-1");
    
    private static BigInteger ultimoId = BigInteger.ZERO;
    
    private BigInteger id;
    private String userCliente;
    private String targaAuto;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int sedeIniziale;
    private int sedeFinale;
    private double sommaDenaro;
    private double kmIniziale;
    private double kmFinale;
    private double chilometraggio;	//numero di chilometri deciso al momento della stipula del contratto dal cliente.
    private DurataNoleggio durata;
    
    /**L'ultimo ID per un noleggio generato automaticamente dal sistema.
     * @return ultimoId : l'ultimo ID generato dal sistema.
     */
    public static BigInteger getUltimoId() {
        return ultimoId;
    }
    
    /**Setta l'ultimo ID generato dal sistema.
     * @param ultimoId : l'ultimo ID generato dal sistema.
     */
    public static void setUltimoId(BigInteger ultimoId) {
        Noleggio.ultimoId = ultimoId;
    }
    
    /**
     * L' ID assegnato ad un noleggio.
     * @return l' ID assegnato ad un noleggio.
     */
    public BigInteger getId() {
        return id;
    }
    
    /**
     * Setta l'ID di un noleggio.
     * @param id : l'ID di un noleggio.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }
    
    /**
     * Lo username del cliente che effettua il noleggio.
     * @return lo username del cliente che effettua il noleggio.
     */
    public String getUserCliente() {
        return userCliente;
    }
    
    /**
     * Setta lo username del cliente che effettua il noleggio.
     * @see Utente#setUsername(String)
     * @param userCliente : lo username del cliente che effettua il noleggio.
     */
    public void setUserCliente(String userCliente) {
        this.userCliente = userCliente;
    }
    
    /**
     * La targa dell'auto noleggiata.
     * @return la targa dell'auto noleggiata.
     */
    public String getTargaAuto() {
        return targaAuto;
    }
    
    /**
     * Setta la targa dell'auto da noleggiare.
     * @see Autoveicolo#setTarga(String)
     * @param targaAuto : la targa dell'auto da noleggiare.
     */
    public void setTargaAuto(String targaAuto) {
        this.targaAuto = targaAuto;
    }
    
    /**
     * La data di inizio del noleggio.
     * @return la data di inizio del noleggio.
     */
    public LocalDate getDataInizio() {
        return dataInizio;
    }
    
    /**
     * Setta la data di inizio del noleggio.
     * @param dataInizio : la data di inizio del noleggio.
     */
    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }
    
    /**
     * La data di fine del noleggio.
     * @return la data di fine del noleggio.
     */
    public LocalDate getDataFine() {
        return dataFine;
    }
    
    /**
     * Setta la data di fine del noleggio.
     * @param dataFine : la data di fine del noleggio.
     * La data di fine del noleggio deve essere collocata temporalmente dopo la data di inizio dello stesso.
     */
    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
    
    /**
     * L'ID della sede iniziale del noleggio.
     * @return l'ID della sede iniziale del noleggio.
     */
    public int getSedeIniziale() {
        return sedeIniziale;
    }
    
    /**
     * Setta l'ID della sede iniziale del noleggio.
     * @param sedeIniziale : la sede iniziale del noleggio.
     */
    public void setSedeIniziale(int sedeIniziale) {
        this.sedeIniziale = sedeIniziale;
    }
    
    /**
     * L'ID della sede finale del noleggio.
     * @return l'ID della sede finale del noleggio.
     */
    public int getSedeFinale() {
        return sedeFinale;
    }
    
    /**
     * Setta l'ID della sede Finale del noleggio.
     * @param sedeFinale : l'ID della sede finale del noleggio.
     */
    public void setSedeFinale(int sedeFinale) {
        this.sedeFinale = sedeFinale;
    }
    
    /**
     * Il costo totale del noleggio.
     * @return il costo totale del noleggio.
     */
    public double getSommaDenaro() {
        return sommaDenaro;
    }
    
    /**
     * Setta il costo totale del noleggio.
     * @param sommaDenaro : il costo totale del noleggio.
     */
    public void setSommaDenaro(double sommaDenaro) {
        this.sommaDenaro = sommaDenaro;
    }
    
    /**
     * Il chilometraggio iniziale dell'autoveicolo noleggiato.
     * @return il chilometraggio iniziale dell'autoveicolo noleggiato.
     */
    public double getKmIniziale() {
        return kmIniziale;
    }
    
    /**
     * Setta il chilometraggio iniziale dell'autoveicolo noleggiato.
     * @param kmIniziale : il chilometraggio iniziale dell'autoveicolo noleggiato.
     */
    public void setKmIniziale(double kmIniziale) {
        this.kmIniziale = kmIniziale;
    }
    
    /**
     * Il chilometraggio finale dell'autoveicolo noleggiato.
     * @return il chilometraggio finale dell'autoveicolo noleggiato.
     */
    public double getKmFinale() {
        return kmFinale;
    }
    
    /**
     * Setta il chilometraggio finale dell'autoveicolo noleggiato.
     * @param kmFinale : il chilometraggio finale dell'autoveicolo noleggiato.
     * Il chilometraggio finale dell'autoveicolo deve essere maggiore o uguale a quello iniziale dello stesso.
     */
    public void setKmFinale(double kmFinale) {
        this.kmFinale = kmFinale;
    }
    
    /**
     * Il chilometraggio totale dell'autoveicolo.
     * @return il chilometraggio totale dell'autoveicolo.
     */
    public double getChilometraggio() {
        return chilometraggio;
    }
    
    /**
     * Setta il chilometraggio totale dell'autoveicolo.
     * @param chilometraggio : il chilometraggio totale dell'autoveicolo.
     * @see Noleggio#VALORE_CHILOMETRAGGIO_ILLIMITATO
     */
    public void setChilometraggio(double chilometraggio) {
        this.chilometraggio = chilometraggio;
    }
    
    /**
     * Il tipo di durata del noleggio.
     * @return il tipo di durata del noleggio.
     */
    public DurataNoleggio getDurata() {
        return durata;
    }
    
    /**
     * Setta il tipo di durata del noleggio.
     * @param durata : il tipo di durata del noleggio.
     * @see Noleggio.DurataNoleggio
     */
    public void setDurata(DurataNoleggio durata) {
        this.durata = durata;
    }
    
    @Override
    /**
     * Restituisce la rappresentazione sotto forma di stringa dei valori appartenenti all'autoveicolo.
     */
    public String toString() {
	String primaParte = String.format("ID: %-30d, USER : %-15s, TARGA : %-7s, INIZIO : %-10s", this.id, this.userCliente, this.targaAuto, GestoreData.formattaData(this.dataInizio));
	String parteDataFine = null;
	if(this.dataFine != null) {
	    parteDataFine = String.format(", FINE : %-10s, ", GestoreData.formattaData(this.dataFine));
	}
	String secondaParte = String.format("SEDE INIZIALE : %-5d, SEDE FINALE : %-5d, SOMMA DENARO : %-8.2f, KM INIZIALE : %-8.2f, KM FINALE : %-8.2f, DURATA : %-15s", this.sedeIniziale, this.sedeFinale, this.sommaDenaro,
		this.kmIniziale, this.kmFinale, this.durata.toString());
	
	return primaParte + (parteDataFine == null ? secondaParte : parteDataFine + secondaParte); 
    }
    
    @Override
    /**
     * Ritorna vero se tutti i campi sono uguali.
     */
    public boolean equals(Object o) {
	Noleggio n = (Noleggio) o;
	return this.chilometraggio == n.getChilometraggio() && this.dataFine.equals(n.getDataFine()) && this.dataInizio.equals(n.getDataInizio()) &&
		this.durata == n.getDurata() && this.id.equals(n.getId()) && this.kmFinale == n.getKmFinale() && this.kmIniziale == n.getKmIniziale() &&
		this.sedeFinale == n.getSedeIniziale() && this.sedeFinale == n.getSedeFinale() && this.sommaDenaro == n.getSommaDenaro() &&
		this.targaAuto.equals(n.getTargaAuto()) && this.userCliente.equals(n.getUserCliente());
    }
}