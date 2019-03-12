package storage;

import java.sql.SQLException;
import java.util.Collection;

public interface Fut5alDAO{
	

	/**
	 * @return una collezione di bean ricavati dalla tabella
	 * @throws SQLException 
	 */
	public Collection<Bean> getAll() throws SQLException; 
	
	/**
	 * @param id del record nella tabella
	 * @return Bean riempito  con i campi di quel record
	 * @throws SQLException 
	 */
	public Bean getByID(int id) throws SQLException; 
	

	/**
	 * 
	 * @param id l'identificativo dell'oggetto da modificare
	 * @param b il bean riempito con i parametri modificati che sostituir� il record indicato da id
	 */
	public void update(int id, Bean b); 
	
	/**
	 * @param b � il bean che riempir� un nuovo record
	 * @throws SQLException 
	 */
	public void add(Bean b) throws SQLException; 
	
	/**
	 * @param identificativo del record che verr� eliminato
	 */
	public void remove(int id);

}
