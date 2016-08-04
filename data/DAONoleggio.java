package data;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import utility.GestoreData;
import business.GestioneNoleggioService;
import business.Noleggio;
import business.Risposta;
import business.Sede;
import business.Noleggio.DurataNoleggio;
import business.TO;

/**
 * Classe che permette di manipolare i dati che riguardano i noleggi di autoveicolo.
 */
public class DAONoleggio extends DAOService {

    private static final String QUERY_INSERIMENTO = "INSERT INTO NOLEGGIO VALUES (?, ?, ?, ?, ?, ?, NULL, NULL, ?, NULL, ?, ?);";
    
    private static final String QUERY_MODIFICA = "UPDATE NOLEGGIO ";
    
    private static final String QUERY_ELIMINAZIONE = "DELETE FROM NOLEGGIO WHERE ID = ?;";
    
    private static final String QUERY_LETTURA = "SELECT * FROM NOLEGGIO ";
    
    /**
     * Restituisce un'istanza di questa classe.
     * @throws SQLException : se la connessione con il database non e' presente.
     */
    public DAONoleggio() throws SQLException {
	super();
    }
    
    /**
     * Permette di inserire i dati nel database.
     * @param dati : dati da inserire.
     * @return l'esito dell'inserimento.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneNoleggioService#apriNoleggio(TO)
     */
    @Override
    public TO crea(TO dati) throws SQLException {
	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
	BigInteger id = noleggio.getId();
	String username = noleggio.getUserCliente();
	String targa = noleggio.getTargaAuto();
	int sedeIniziale = noleggio.getSedeIniziale();
	int sedeFinale = noleggio.getSedeFinale();
	String dataIniziale = GestoreData.formattaData(noleggio.getDataInizio());
	double kmIniziale = noleggio.getKmIniziale();
	double chilometraggioAccordato = noleggio.getChilometraggio();
	DurataNoleggio durata = noleggio.getDurata();
	
	PreparedStatement queryInserimento;
	Risposta risposta = new Risposta();
	
	try {
	    queryInserimento = this.connessione.prepareStatement(QUERY_INSERIMENTO);
	    queryInserimento.setString(1, id.toString());
	    queryInserimento.setString(2, username);
	    queryInserimento.setString(3, targa);
	    queryInserimento.setInt(4, sedeIniziale);
	    queryInserimento.setInt(5, sedeFinale);
	    queryInserimento.setString(6, dataIniziale);
	    queryInserimento.setDouble(7, kmIniziale);
	    queryInserimento.setDouble(8, chilometraggioAccordato);
	    queryInserimento.setString(9, durata.toString());
	    int esito = queryInserimento.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_INSERIMENTO_DATI);
	    } else {
		risposta.setId(CODICE_INSERIMENTO_AVVENUTO);
	    }
	} catch (SQLException e) {
	    if(e.getErrorCode() == CODICE_ERRORE_CHIAVE_DUPLICATA) {
		risposta.setId(ERRORE_CHIAVE_PRESENTE);
	    } else {
		risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	    }
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
     * @see GestioneNoleggioService#modificaNoleggio(TO)
     */
    @Override
    public TO modifica(TO dati) throws SQLException {
	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
	BigInteger id = noleggio.getId();
	String username = noleggio.getUserCliente();
	String targa = noleggio.getTargaAuto();
	int sedeIniziale = noleggio.getSedeIniziale();
	int sedeFinale = noleggio.getSedeFinale();
	LocalDate dataInizio = noleggio.getDataInizio();
	LocalDate dataFine = noleggio.getDataFine();
	double kmIniziale = noleggio.getKmIniziale();
	double chilometraggioAccordato = noleggio.getChilometraggio();
	DurataNoleggio durata = noleggio.getDurata();
	PreparedStatement queryModifica;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_MODIFICA);
	    boolean valoreInserito = false;
	    
	    if(!username.isEmpty()) {
		queryCompleta.append("SET USERCLIENTE = '" +username+ "' ");
		valoreInserito = true;
	    }
	    
	    if(!targa.isEmpty()) {
		if(valoreInserito) {
		    queryCompleta.append(", TARGAAUTO = '" +targa+ "' ");
		} else {
		    queryCompleta.append("SET TARGAAUTO = '" +targa+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sedeIniziale != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append(", SEDEINIZIALE = '" +sedeIniziale+ "' ");
		} else {
		    queryCompleta.append("SET SEDEINIZIALE = '" +sedeIniziale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sedeFinale != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append(", SEDEFINALE = '" +sedeFinale+ "' ");
		} else {
		    queryCompleta.append("SET SEDEFINALE = '" +sedeFinale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(dataInizio != null) {
		if(valoreInserito) {
		    queryCompleta.append(", DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		} else {
		    queryCompleta.append("SET DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		}
	    }
	    
	    if(dataFine != null) {
		if(valoreInserito) {
		    queryCompleta.append(", DATAIFINALE = '" +GestoreData.formattaData(dataFine)+ "' ");
		} else {
		    queryCompleta.append("SET DATAFINALE = '" +GestoreData.formattaData(dataFine)+ "' ");
		}
	    }
	    
	    if(kmIniziale != Noleggio.VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT) {
		if(valoreInserito) {
		    queryCompleta.append(", KMINIZIALE = '" +kmIniziale+ "' ");
		} else {
		    queryCompleta.append("SET KMINIZIALE = '" +kmIniziale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(chilometraggioAccordato != Noleggio.VALORE_CHILOMETRAGGIO_NON_IMPOSTATO) {
		if(valoreInserito) {
		    queryCompleta.append(", CHILOMETRAGGIO = '" +chilometraggioAccordato+ "' ");
		} else {
		    queryCompleta.append("SET CHILOMETRAGGIO = '" +chilometraggioAccordato+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(durata != null) {
		if(valoreInserito) {
		    queryCompleta.append(", DURATA = '" +durata.toString()+ "' ");
		} else {
		    queryCompleta.append("SET DURATA = '" +durata.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    queryCompleta.append("WHERE ID = '" +id.toString()+ "';");
	    
	    queryModifica = this.connessione.prepareStatement(queryCompleta.toString());
	    int esito = queryModifica.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_MODIFICA_DATI);
	    } else {
		risposta.setId(CODICE_MODIFICA_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
	} finally {
	    this.connessione.close();
	}
	return risposta;
    }

    /**
     * Permette di eliminare i dati dal database.
     * @param dati : dati da eliminare.
     * @return l'esito dell'eliminazione.
     * @throws SQLException : la connessione con il database non e' disponibile.
     * @see GestioneNoleggioService#eliminaNoleggio(TO)
     */
    @Override
    public TO elimina(TO dati) throws SQLException {
	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
	BigInteger id = noleggio.getId();
	PreparedStatement queryEliminazione;
	Risposta risposta = new Risposta();
	
	try {
	    queryEliminazione = this.connessione.prepareStatement(QUERY_ELIMINAZIONE);
	    queryEliminazione.setString(1, id.toString());
	    int esito = queryEliminazione.executeUpdate();
	    
	    if(esito == 0) {
		risposta.setId(ERRORE_ELIMINAZIONE_DATI);
	    } else {
		risposta.setId(CODICE_ELIMINAZIONE_AVVENUTA);
	    }
	} catch (SQLException e) {
	    risposta.setId(ERRORE_CONNESSIONE_DATABASE);
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
     * @see GestioneNoleggioService#visualizzaNoleggi(TO)
     * @see GestioneNoleggioService#visualizzaNoleggiCliente(TO)
     */
    @Override
    public TO leggi(TO dati) throws SQLException {
	Noleggio noleggio = (Noleggio) dati.prendiOggettoDaIndice(0);
	BigInteger id = noleggio.getId();
	String username = noleggio.getUserCliente();
	String targa = noleggio.getTargaAuto();
	int sedeIniziale = noleggio.getSedeIniziale();
	int sedeFinale = noleggio.getSedeFinale();
	LocalDate dataInizio = noleggio.getDataInizio();
	LocalDate dataFine = noleggio.getDataFine();
	double kmIniziale = noleggio.getKmIniziale();
	double chilometraggioAccordato = noleggio.getChilometraggio();
	DurataNoleggio durata = noleggio.getDurata();
	PreparedStatement queryLettura;
	Risposta risposta = new Risposta();
	
	try {
	    StringBuffer queryCompleta = new StringBuffer(QUERY_LETTURA);
	    boolean valoreInserito = false;
	    
	    if(id != Noleggio.DEFAULT_ID) {
		queryCompleta.append("WHERE ID = '" +id.toString()+ "' ");
		valoreInserito = true;
	    }
	    
	    if(username != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND USERCLIENTE = '" +username+ "' ");
		} else {
		    queryCompleta.append("WHERE USERCLIENTE = '" +username+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(targa != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND TARGAAUTO = '" +targa+ "' ");
		} else {
		    queryCompleta.append("WHERE TARGAAUTO = '" +targa+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sedeIniziale != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append("AND SEDEINIZIALE = '" +sedeIniziale+ "' ");
		} else {
		    queryCompleta.append("WHERE SEDEINIZIALE = '" +sedeIniziale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(sedeFinale != Sede.DEFAULT_ID) {
		if(valoreInserito) {
		    queryCompleta.append("AND SEDEFINALE = '" +sedeFinale+ "' ");
		} else {
		    queryCompleta.append("WHERE SEDEFINALE = '" +sedeFinale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(dataInizio != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		} else {
		    queryCompleta.append("WHERE DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		}
	    }
	    
	    if(dataFine != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		} else {
		    queryCompleta.append("WHERE DATAINIZIALE = '" +GestoreData.formattaData(dataInizio)+ "' ");
		}
	    }
	    
	    if(kmIniziale != Noleggio.VALORE_CHILOMETRAGGIO_INIZIALE_DEFAULT) {
		if(valoreInserito) {
		    queryCompleta.append("AND KMINIZIALE = '" +kmIniziale+ "' ");
		} else {
		    queryCompleta.append("WHERE KMINIZIALE = '" +kmIniziale+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(chilometraggioAccordato != Noleggio.VALORE_CHILOMETRAGGIO_NON_IMPOSTATO) {
		if(valoreInserito) {
		    queryCompleta.append("AND CHILOMETRAGGIO = '" +chilometraggioAccordato+ "' ");
		} else {
		    queryCompleta.append("WHERE CHILOMETRAGGIO = '" +chilometraggioAccordato+ "' ");
		    valoreInserito = true;
		}
	    }
	    
	    if(durata != null) {
		if(valoreInserito) {
		    queryCompleta.append("AND DURATA = '" +durata.toString()+ "' ");
		} else {
		    queryCompleta.append("WHERE DURATA = '" +durata.toString()+ "' ");
		    valoreInserito = true;
		}
	    }
	    queryCompleta.append(";");
	    
	    queryLettura = this.connessione.prepareStatement(queryCompleta.toString());
	    
	    ResultSet risultato = queryLettura.executeQuery();
	    
	    if(riempiBO(risposta, risultato)) {
		risposta.setId(CODICE_RESULTSET_PIENO);
	    } else {	//Nessuna fascia trovata
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
	    BigInteger id = new BigInteger(risultato.getString(1));
	    String username = risultato.getString(2);
	    String targa = risultato.getString(3);
	    int sedeIniziale = risultato.getInt(4);
	    int sedeFinale = risultato.getInt(5);
	    String dataIniziale = risultato.getString(6);
	    String dataFinale = risultato.getString(7);
	    double sommaDenaro = risultato.getDouble(8);
	    double kmIniziale = risultato.getDouble(9);
	    double kmFinale = risultato.getDouble(10);
	    double chilometraggio = risultato.getDouble(11);
	    DurataNoleggio durata = DurataNoleggio.valueOf(risultato.getString(12));

	    Noleggio noleggio = new Noleggio();
	    noleggio.setId(id);
	    noleggio.setUserCliente(username);
	    noleggio.setTargaAuto(targa);
	    noleggio.setSedeIniziale(sedeIniziale);
	    noleggio.setSedeFinale(sedeFinale);
	    noleggio.setDataInizio(GestoreData.deFormattaData(dataIniziale));
	    if(dataFinale != null) {
		noleggio.setDataFine(GestoreData.deFormattaData(dataFinale));
	    }
	    noleggio.setSommaDenaro(sommaDenaro);
	    noleggio.setKmIniziale(kmIniziale);
	    noleggio.setKmFinale(kmFinale);
	    noleggio.setChilometraggio(chilometraggio);
	    noleggio.setDurata(durata);

	    risposta.aggiungiOggetto(noleggio);
	}
	return valoriEsistenti;
    } 
}