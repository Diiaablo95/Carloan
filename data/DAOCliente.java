package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.Cliente;
import business.GestioneClienteService;
import business.Richiesta;
import business.Risposta;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano i clienti.
 */
public class DAOCliente extends DAOService {

    private static final String QUERY_INSERIMENTO_CLIENTE = "INSERT INTO CLIENTE VALUES (?, ?, ?, ?, ?, ?, ?);";
    
    static final String QUERY_INSERIMENTO_CREDENZIALI = "INSERT INTO CREDENZIALI VALUES (?, ?, ?);"; 
    
    private static final String QUERY_MODIFICA_CLIENTE = "UPDATE CLIENTE ";
    
    static final String QUERY_MODIFICA_CREDENZIALI = "UPDATE CREDENZIALI SET PASSWORD = ? WHERE USERNAME = ?;";
    
    private static final String QUERY_ELIMINAZIONE_CLIENTE = "DELETE FROM CLIENTE WHERE USERNAME = ?;";
    
    static final String QUERY_ELIMINAZIONE_CREDENZIALI = "DELETE FROM CREDENZIALI WHERE USERNAME = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM CLIENTE JOIN CREDENZIALI ";

    /**
     * Restituisce un'istanza di questa classe.
     * @throws SQLException : se la connessione con il database non e' presente.
     */
    public DAOCliente() throws SQLException {
	super();
    }
    
    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneClienteService#aggiungiNuovoCliente(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Cliente cliente = (Cliente) dati.prendiOggettoDaIndice(0);
	String username = cliente.getUsername();
	String password = cliente.getPassword();
	String nome = cliente.getNome();
	String cognome = cliente.getCognome();
	String telefono = cliente.getTelefono();
	String email = cliente.getEmail();
	String tipo = cliente.getTipo().toString();	//Inserito dal sistema.
	String cf = cliente.getCf();
	String carta = cliente.getCarta();
	PreparedStatement queryInserimentoCliente;
	PreparedStatement queryInserimentoCredenziali;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimentoCredenziali = this.connessione.prepareStatement(QUERY_INSERIMENTO_CREDENZIALI);
	    queryInserimentoCredenziali.setString(1, username);
	    queryInserimentoCredenziali.setString(2, password);
	    queryInserimentoCredenziali.setString(3, tipo);
	    int esito = queryInserimentoCredenziali.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {	//Qui puo' esserci un inserimento di chiave duplicata.
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);		//Altrimenti l'errore e' dovuto ad altre cause esterne.
	    }
	    this.connessione.close();
	    return risposta;
	}
	
	try {
	    queryInserimentoCliente = this.connessione.prepareStatement(QUERY_INSERIMENTO_CLIENTE);
	    queryInserimentoCliente.setString(1, username);
	    queryInserimentoCliente.setString(2, nome);
	    queryInserimentoCliente.setString(3, cognome);
	    queryInserimentoCliente.setString(4, telefono);
	    queryInserimentoCliente.setString(5, email);
	    queryInserimentoCliente.setString(6, cf);
	    queryInserimentoCliente.setString(7, carta);
	    int esito = queryInserimentoCliente.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
		eliminaCliente(username);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {	//Qui puo' esserci un inserimento di chiave duplicata.
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);		//Altrimenti l'errore e' dovuto ad altre cause esterne.
	    }
	    eliminaCliente(username);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di modificare i dati nel database.
     * @param dati : dati da modificare.
     * @return l'esito della modifica.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneClienteService#modificaCliente(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Cliente cliente = (Cliente) dati.prendiOggettoDaIndice(0);
	String username = cliente.getUsername();
	String password = cliente.getPassword();
	String nome = cliente.getNome();
	String cognome = cliente.getCognome();
	String telefono = cliente.getTelefono();
	String email = cliente.getEmail();
	String cf = cliente.getCf();
	String carta = cliente.getCarta();
	PreparedStatement queryModificaCliente;
	PreparedStatement queryModificaCredenziali;
	Risposta risposta = new Risposta();
	boolean valoreInserito = false;
	
	try {
	    StringBuffer queryCompletaCliente = new StringBuffer(QUERY_MODIFICA_CLIENTE);
	    
	    if(nome != null) {
		queryCompletaCliente.append("SET NOME = '" +nome+ "' ");
		valoreInserito = true;
	    }
	    
	    if(cognome != null) {
		if(valoreInserito) {
		    queryCompletaCliente.append(", COGNOME = '" +cognome+ "' ");
		} else {
		    queryCompletaCliente.append("SET COGNOME = '" +cognome+ "' ");
		    valoreInserito = true;
		}
	    }
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompletaCliente.append(", TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompletaCliente.append("SET TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(email != null) {
		if(valoreInserito) {
		    queryCompletaCliente.append(", EMAIL = '" +email+ "' ");
		} else {
		    queryCompletaCliente.append("SET EMAIL = '" +email+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(cf != null) {
		if(valoreInserito) {
		    queryCompletaCliente.append(", CF = '" +cf+ "' ");
		} else {
		    queryCompletaCliente.append("SET CF = '" +cf+ "' ");
		    valoreInserito = true;
		}
	    }

	    if(carta != null) {
		if(valoreInserito) {
		    queryCompletaCliente.append(", CARTA = '" +carta+ "' ");
		} else {
		    queryCompletaCliente.append("SET CARTA = '" +carta+ "' ");
		    valoreInserito = true;
		}
	    }

	    if(valoreInserito) {
		queryCompletaCliente.append("WHERE USERNAME = '" +username+ "';");
		queryModificaCliente = this.connessione.prepareStatement(queryCompletaCliente.toString());
		int esito = queryModificaCliente.executeUpdate();

		if(esito == 0) {
		    risposta.setId(ERRORE_MODIFICA_DATI);
		} else {
		    risposta.setId(CODICE_MODIFICA_AVVENUTA);
		}
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    this.connessione.close();
	    return risposta;
	}

	if(password != null) {
	    queryModificaCredenziali = this.connessione.prepareStatement(QUERY_MODIFICA_CREDENZIALI);
	    try {
		queryModificaCredenziali.setString(1, password);
		queryModificaCredenziali.setString(2, username);
		int esito = queryModificaCredenziali.executeUpdate();
		    
		    if(esito == 0) {
			risposta.setId(ERRORE_MODIFICA_DATI);
		    } else {
			risposta.setId(CODICE_MODIFICA_AVVENUTA);
		    }
	    } catch (SQLException e) {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);
		System.err.println(MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE);
		System.exit(1);
	    } finally {
		this.connessione.close();
	    }
	} else if(valoreInserito) {
	    this.connessione.close();
	} else {
	    risposta.setId(ERRORE_MODIFICA_DATI);
	}
	return risposta;
    }

    /**
     * Permette di eliminare i dati dal database.
     * @param dati : dati da eliminare.
     * @return l'esito dell'eliminazione.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneClienteService#eliminaCliente(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Cliente cliente = (Cliente) dati.prendiOggettoDaIndice(0);
	String username = cliente.getUsername();
	PreparedStatement queryEliminazioneCliente;
	PreparedStatement queryEliminazioneCredenziali;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazioneCliente = this.connessione.prepareStatement(QUERY_ELIMINAZIONE_CLIENTE);
	    queryEliminazioneCliente.setString(1, username);
	    int esito = queryEliminazioneCliente.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_ELIMINAZIONE_DATI);
	    } else {
		risposta.setId(CODICE_ELIMINAZIONE_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    this.connessione.close();
	    return risposta;
	}
	
	try {
	    queryEliminazioneCredenziali = this.connessione.prepareStatement(QUERY_ELIMINAZIONE_CREDENZIALI);
	    queryEliminazioneCredenziali.setString(1, username);
	    int esito = queryEliminazioneCredenziali.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_ELIMINAZIONE_DATI);
		System.err.println(MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE);
		System.exit(1);
	    } else {
		risposta.setId(CODICE_ELIMINAZIONE_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    System.err.println(MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE);
	    System.exit(1);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di leggere i dati dal database.
     * @param dati : parametri per la ricerca.
     * @return il risultato della ricerca.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneClienteService#visualizzaClienti(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Cliente cliente = (Cliente) dati.prendiOggettoDaIndice(0);
	String username = cliente.getUsername();
	String password = cliente.getPassword();
	String nome = cliente.getNome();
	String cognome = cliente.getCognome();
	String telefono = cliente.getTelefono();
	String email = cliente.getEmail();
	String cf = cliente.getCf();
	String carta = cliente.getCarta();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    if(username != null) {
		queryCompleta.append("WHERE CLIENTE.USERNAME = '" +username+ "' ");
		valoreInserito = true;
	    }
	    if(password != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CREDENZIALI.PASSWORD = '" +password+ "' ");
		} else {
		    queryCompleta.append("WHERE CREDENZIALI.PASSWORD = '" +password+ "' ");
		    valoreInserito = true;
		}
	    }
	    if(nome != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.NOME = '" +nome+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.NOME = '" +nome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(cognome != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.COGNOME = '" +cognome+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.COGNOME = '" +cognome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    
	    }
	    if(email != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.EMAIL = '" +email+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.EMAIL = '" +email+ "' ");
		    valoreInserito = true;
		}
	    
	    }
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    }
		
	    if(cf != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.CF = '" +cf+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.CF = '" +cf+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(carta != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND CLIENTE.CARTA = '" +carta+ "' ");
		} else {
		    queryCompleta.append("WHERE CLIENTE.CARTA = '" +carta+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(valoreInserito) {
		queryCompleta.append("AND CLIENTE.USERNAME = CREDENZIALI.USERNAME;");
	    } else {
		queryCompleta.append("WHERE CLIENTE.USERNAME = CREDENZIALI.USERNAME");
	    }
	    queryLettura = this.connessione.prepareStatement(queryCompleta.toString());
	    ResultSet risultato = queryLettura.executeQuery();
	    
	    if(riempiBO(risposta, risultato)) {
		risposta.setId(CODICE_RESULTSET_PIENO);
	    } else {
		risposta.setId(CODICE_RESULTSET_VUOTO);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }
    
    private static boolean riempiBO(Risposta risposta, ResultSet risultato) throws SQLException {
	boolean valoriEsistenti = false;
	while(risultato.next()) {
	    valoriEsistenti = true;
	    String username = risultato.getString(1);
	    String nome = risultato.getString(2);
	    String cognome = risultato.getString(3);
	    String telefono = risultato.getString(4);
	    String email = risultato.getString(5);
	    String cf = risultato.getString(6);
	    String carta = risultato.getString(7);
	    String password = risultato.getString(8);
	    //Il tipo non ci interessa

	    Cliente cliente = new Cliente();
	    cliente.setUsername(username);
	    cliente.setPassword(password);
	    cliente.setNome(nome);
	    cliente.setCognome(cognome);
	    cliente.setTelefono(telefono);
	    cliente.setEmail(email);
	    cliente.setCf(cf);
	    cliente.setCarta(carta);

	    risposta.aggiungiOggetto(cliente);
	}
	return valoriEsistenti;
    }
    
    private void eliminaCliente(String username) {
	Richiesta richiestaEliminazione = new Richiesta();
	Cliente cliente = new Cliente();
	cliente.setUsername(username);
	richiestaEliminazione.aggiungiOggetto(cliente);
	
	try {
	    elimina(richiestaEliminazione);
	} catch (SQLException e) {
	    System.err.println(MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE);
	    e.printStackTrace();
	}
    }
}