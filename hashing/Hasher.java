package hashing;

/**
 * Interfaccia che deve essere implementata da tutte le classi che forniscono metodi per creare digest a partire da una stringa.
 */
public interface Hasher {
    
    /**
     * Crea il digest della stringa.
     * @param input : la stringa dalla quale si deve ottenere il digest.
     * @return il digest della stringa in input.
     */
    public String hash(String input);
}
