package business;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe che deve essere concretizzata da tutte le classi che forniscono un mezzo per veicolare dati attraverso i<nbsp>
 * vari livelli di cui il sistema e' composto.
 */
public abstract class TO implements Iterable<BusinessObject> {
    
    protected String id;
    protected ArrayList<BusinessObject> dati = new ArrayList<BusinessObject>();
    
    /**Setta l'ID della richiesta o dell'esito della computazione di una richiesta.
     * @param id : l'ID della richiesta o dell'esito.
     */
    public void setId(String id) {
	this.id = id;
    }
    
    /**
     * L'ID della richiesta o del risultato.
     * @return l'ID della richiesta o del risultato. 
     */
    public String getId() {
	return this.id;
    }
    
    /**
     * Permette di aggiungere un oggetto di business al transfer object.
     * @param oggetto : l'oggetto di business da aggiungere al transfer object.
     */
    public void aggiungiOggetto(BusinessObject oggetto) {
	this.dati.add(oggetto);
    }
    
    /**
     * Permette di ottenere l'oggetto di business nella posizione espressa dall'indice passato.
     * Il primo business object si trova in posizione 0.
     * @param indice : l'indice dal quale si vuole ottenere il business object desiderato.
     * @return il business object all'indice passato come parametro.
     * @throws IndexOutOfBoundsException : se l'indice inserito e'uguale, minore o superiore al numero di business object<nbsp>
     * contenuti nel transfer object.
     */
    public BusinessObject prendiOggettoDaIndice(int indice) throws IndexOutOfBoundsException {
	return this.dati.get(indice);
    }
    
    /**
     * Permette di eliminare un oggetto di business dal TO specificandone l'indice.
     * @param indice : l'indice dell'oggetto di business all'interno del TO.
     * @throws IndexOutOfBoundsException : se l'indice inserito e' uguale, minore o superiore al numero di business object<nbsp>
     * contenuto nel transfer object.
     */
    public void eliminaOggettoAllIndice(int indice) throws IndexOutOfBoundsException {
	this.dati.remove(indice);
    }
    
    /**
     * Stabilisce se l'oggetto di questa classe non contiene BO al suo interno.
     * @return true se il TO non contiene alcun BO.
     */
    public boolean vuoto() {
	return this.dati.size() == 0;
    }
    
    @Override
    /**
     * Ritorna vero se i due TO hanno lo stesso ID e contengono gli stessi BO nello stesso ordine.
     */
    public boolean equals(Object o) {
	TO to = (TO) o;
	return this.id.equals(to.getId()) && this.dati.equals(to.dati);
    }
    
    /**
     * Restituisce un iteratore che permette di scorrere tutti i business object presenti nel transfer object.
     */
    public Iterator<BusinessObject> iterator() {
	return new TOIterator<BusinessObject>(this);
    }
    
    //Iteratore del TO che sfrutta l'iteratore presente nell'ArrayList di BusinessObject interno.
    @SuppressWarnings("hiding")
    private class TOIterator<BusinessObject> implements Iterator<BusinessObject> {
	
	private Iterator<BusinessObject> iterator;
	
	/**
	 * Restituisce un nuovo iteratore per il transfer object.
	 * @param transferObject : il transfer object per il quale si vuole ottenere l'iteratore.
	 */
	@SuppressWarnings("unchecked")
	public TOIterator(TO transferObject) {
	    this.iterator = (Iterator<BusinessObject>) transferObject.dati.iterator();
	}
	
	@Override
	public boolean hasNext() {
	    return this.iterator.hasNext();
	}

	@Override
	public BusinessObject next() {
	    return this.iterator.next();
	}
    }
}
