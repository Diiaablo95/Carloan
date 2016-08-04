package hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe che crea digest di stringhe utilizzando l'algoritmo MD5.
 */
public class MD5Hasher implements Hasher {

    /**
     * Tipo di algoritmo utilizzato per l'hashing.
     */
    public static final String ALGORITMO = "MD5";
    
    private static MD5Hasher hasher;
    
    //Costruttore privato poiche' classe Singleton.
    private MD5Hasher(){}
    
    /**
     * Restituisce l'unica istanza di questa classe.
     * @return l'unica istanza di questa classe.
     */
    public static MD5Hasher getInstance() {
	if(hasher == null) {
	    hasher = new MD5Hasher();
	}
	return hasher;
    }
    
    /**
     * Crea il digest della stringa.
     * @param input : la stringa dalla quale si deve ottenere il digest.
     * @return il digest della stringa in input.
     */
    @Override
    public String hash(String input) {
	MessageDigest md = null;
	try {
	    md = MessageDigest.getInstance(ALGORITMO);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	md.update(input.getBytes());
	
	byte[] digestArray = md.digest();
	 
	StringBuffer sb = new StringBuffer();
	//Trasformazione della codifica del digest ottenuto.
	for (int i = 0; i < digestArray.length; i++) {
	    sb.append(Integer.toString((digestArray[i] & 0xff) + 0x100, 16).substring(1));
	}
	return sb.toString();
    }
}