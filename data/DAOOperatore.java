package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.GestioneOperatoreService;
import business.Operatore;
import business.Richiesta;
import business.Risposta;
import business.Sede;
import business.TO;
import business.Utente.Tipo;

/**
 * Classe che permette di manipolare i dati che riguardano gli operatori.
 */
public class DAOOperatore extends DAOService {

    private static final String QUERY_INSERIMENTO_OPERATORE = "INSERT INTO DIPENDENTE VALUES (?, ?, ?, ?, ?, ?);";
    
    private static final String QUERY_MODIFICA_OPERATORE = "UPDATE DIPENDENTE ";
    
    private static final String QUERY_ELIMINAZIONE_OPERATORE = "DELETE FROM  DIPENDENTE WHERE USERNAME = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM DIPENDENTE JOIN CREDENZIALI ";
    
    /**
     * Restituisce un'istanza di questa classe.
     * @throws SQLException : se la connessione con il database non e' presente.
     */
    public DAOOperatore() throws SQLException {
	super();
    }

    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneOperatoreService#aggiungiNuovoOperatore(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Operatore operatore = (Operatore) dati.prendiOggettoDaIndice(0);
	String username = operatore.getUsername();
	String password = operatore.getPassword();
	String nome = operatore.getNome();
	String cognome = operatore.getCognome();
	String telefono = operatore.getTelefono();
	String email = operatore.getEmail();
	Tipo tipo = operatore.getTipo();		//Aggiunto automaticamente dal service corrispondente, in caso di inserimento. 
	int sede = operatore.getIdSede();
	PreparedStatement queryInserimentoOperatore;
	PreparedStatement queryInserimentoCredenziali;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimentoCredenziali = this.connessione.prepareStatement(DAOCliente.QUERY_INSERIMENTO_CREDENZIALI);
	    queryInserimentoCredenziali.setString(1, username);
	    queryInserimentoCredenziali.setString(2, password);
	    queryInserimentoCredenziali.setString(3, tipo.toString());
	    int esito = queryInserimentoCredenziali.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch(SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    }
	    this.connessione.close();
	    return risposta;
	}
	
	try {
	    queryInserimentoOperatore = this.connessione.prepareStatement(QUERY_INSERIMENTO_OPERATORE);
	    queryInserimentoOperatore.setString(1, username);
	    queryInserimentoOperatore.setString(2, nome);
	    queryInserimentoOperatore.setString(3, cognome);
	    queryInserimentoOperatore.setString(4, telefono);
	    queryInserimentoOperatore.setString(5, email);
	    queryInserimentoOperatore.setInt(6, sede);
	    int esito = queryInserimentoOperatore.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
		eliminaOperatore(username);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {	//Qui puo' esserci un inserimento di chiave duplicata.
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);		//Altrimenti l'errore e' dovuto ad altre cause esterne.
	    }
	    eliminaOperatore(username);
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
     * @see GestioneOperatoreService#modificaOperatore(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Operatore operatore = (Operatore) dati.prendiOggettoDaIndice(0);
	String username = operatore.getUsername();
	String password = operatore.getPassword();
	String nome = operatore.getNome();
	String cognome = operatore.getCognome();
	String telefono = operatore.getTelefono();
	String email = operatore.getEmail();
	int sede = operatore.getIdSede();
	PreparedStatement queryModificaOperatore;
	PreparedStatement queryModificaCredenziali;
	Risposta risposta = new Risposta();
	boolean valoreInserito = false;
	
	try {
	    StringBuffer queryCompletaOperatore = new StringBuffer(QUERY_MODIFICA_OPERATORE);
	    
	    if(nome != null) {
		queryCompletaOperatore.append("SET NOME = '" +nome+ "' ");
		valoreInserito = true;
	    }
	    
	    if(cognome != null) {
		if(valoreInserito) {
		    queryCompletaOperatore.append(", COGNOME = '" +cognome+ "' ");
		} else {
		    queryCompletaOperatore.append("SET COGNOME = '" +cognome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompletaOperatore.append(", TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompletaOperatore.append("SET TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(email != null) {
		if(valoreInserito) {
		    queryCompletaOperatore.append(", EMAIL = '" +email+ "' ");
		} else {
		    queryCompletaOperatore.append("SET EMAIL = '" +email+ "' ");
		    valoreInserito = true;
		}
	    }

	    if(sede != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompletaOperatore.append(", SEDE = '" +sede+ "' ");
		} else {
		    queryCompletaOperatore.append("SET SEDE = '" +sede+ "' ");
		    valoreInserito = true;
		}
	    }

	    if(valoreInserito) {
		queryCompletaOperatore.append("WHERE USERNAME = '" +username+ "';");
		queryModificaOperatore = this.connessione.prepareStatement(queryCompletaOperatore.toString());
		int esito = queryModificaOperatore.executeUpdate();

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
	    queryModificaCredenziali = this.connessione.prepareStatement(DAOCliente.QUERY_MODIFICA_CREDENZIALI);
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
     * @see GestioneOperatoreService#eliminaOperatore(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Operatore operatore = (Operatore) dati.prendiOggettoDaIndice(0);
	String username = operatore.getUsername();
	PreparedStatement queryEliminazioneOperatore;
	PreparedStatement queryEliminazioneCredenziali;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazioneOperatore = this.connessione.prepareStatement(QUERY_ELIMINAZIONE_OPERATORE);
	    queryEliminazioneOperatore.setString(1, username);
	    int esito = queryEliminazioneOperatore.executeUpdate();
	    
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
	    queryEliminazioneCredenziali = this.connessione.prepareStatement(DAOCliente.QUERY_ELIMINAZIONE_CREDENZIALI);
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
     * @see GestioneOperatoreService#visualizzaOperatori(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Operatore operatore = (Operatore) dati.prendiOggettoDaIndice(0);
	String username = operatore.getUsername();
	String password = operatore.getPassword();
	String nome = operatore.getNome();
	String cognome = operatore.getCognome();
	String telefono = operatore.getTelefono();
	String email = operatore.getEmail();
	int sede = operatore.getIdSede();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    if(username != null) {
		queryCompleta.append("WHERE DIPENDENTE.USERNAME = '" +username+ "' ");
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
		    queryCompleta.append("AND DIPENDENTE.NOME = '" +nome+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.NOME = '" +nome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(cognome != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DIPENDENTE.COGNOME = '" +cognome+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.COGNOME = '" +cognome+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DIPENDENTE.TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    
	    }
	    
	    if(email != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DIPENDENTE.EMAIL = '" +email+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.EMAIL = '" +email+ "' ");
		    valoreInserito = true;
		}
	    
	    }
	    
	    if(telefono != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DIPENDENTE.TELEFONO = '" +telefono+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.TELEFONO = '" +telefono+ "' ");
		    valoreInserito = true;
		}
	    }
		
	    if(sede != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append("AND DIPENDENTE.SEDE = '" +sede+ "' ");
		} else {
		    queryCompleta.append("WHERE DIPENDENTE.SEDE = '" +sede+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(valoreInserito) {
		queryCompleta.append("AND DIPENDENTE.USERNAME = CREDENZIALI.USERNAME WHERE CREDENZIALI.TIPOUTENTE = '" +Tipo.OPERATORE+"';");
	    } else {
		queryCompleta.append("WHERE DIPENDENTE.USERNAME = CREDENZIALI.USERNAME AND CREDENZIALI.TIPOUTENTE = '" +Tipo.OPERATORE+ "';");
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
	    int sede = risultato.getInt(6);
	    String password = risultato.getString(7);
	    //Il tipo non ci interessa

	    Operatore operatore = new Operatore();
	    operatore.setUsername(username);
	    operatore.setPassword(password);
	    operatore.setNome(nome);
	    operatore.setCognome(cognome);
	    operatore.setTelefono(telefono);
	    operatore.setEmail(email);
	    operatore.setIdSede(sede);

	    risposta.aggiungiOggetto(operatore);
	}
	return valoriEsistenti;
    }
    
    private void eliminaOperatore(String username) {
	Richiesta richiestaEliminazione = new Richiesta();
	Operatore operatore = new Operatore();
	operatore.setUsername(username);
	richiestaEliminazione.aggiungiOggetto(operatore);
	
	try {
	    elimina(richiestaEliminazione);
	} catch (SQLException e) {
	    System.err.println(MESSAGGIO_ERRORE_INCONSISTENZA_DATABASE);
	    e.printStackTrace();
	}
    }
}