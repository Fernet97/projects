package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

public class GiocaDAO implements Fut5alDAO{

	private final String TABLE_NAME = "gioca";
	
	
	@Override
	public synchronized Collection<Bean> getAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Bean> giochi = new LinkedList<Bean>();

		String selectSQL = "SELECT * FROM " + this.TABLE_NAME;

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Gioca bean = new Gioca();
				bean.setID(rs.getInt("ID"));
				bean.setAtleta_Email(rs.getString("Atleta_Email"));
				bean.setPartita_ID(rs.getInt("Partita_ID"));
				bean.setStatoInvito(rs.getString("StatoInvito"));
				giochi.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		
		return giochi;
	}
	

	@Override
	public Bean getByID(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Gioca bean = new Gioca();

		String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE ID = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setAtleta_Email(rs.getString("Atleta_Email"));
				bean.setPartita_ID(rs.getInt("Partita_ID"));
				bean.setStatoInvito(rs.getString("StatoInvito"));


			}

		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
					bean = null; //AGGIUNTO DOPO IL CASO DI TEST
					}
				
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return bean;
	}

	@Override
	public synchronized void update(Bean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void add(Bean b) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (Atleta_Email, Partita_ID, StatoInvito) VALUES (?, ?, ?)";

		
		Gioca a = (Gioca) b;
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, a.getAtleta_Email());
			preparedStatement.setInt(2, a.getPartita_ID());
			preparedStatement.setString(3, a.getStatoInvito());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}

	
		
		
	}

	@Override
	public synchronized void remove(int id) throws SQLException {
	
		Connection connection = null;
		Statement  statement = null;

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE ID ="+id;

		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			int deleted = statement.executeUpdate(query);
			connection.commit();
			System.out.println("(si doveva eliminare id="+id+")eliminati: "+deleted );



		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	
	
	}
	
	
	public synchronized int  NumGiocDellapartita(Bean b){
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int numRecord = 1;

		Gioca g = (Gioca) b;
		
		String selectSQL = "SELECT * FROM " + this.TABLE_NAME+" WHERE Partita_ID="+g.getPartita_ID();

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			System.out.println(selectSQL);

			
			while (rs.next()) {
				numRecord++;
			}
			
			System.out.println("Num persone partecipanti alla partita:"+numRecord+"/10");

			
		}catch (Exception e) {
		}
		
		return numRecord;
				
	}
	
	
	
	
	public synchronized int findRidondance(Bean b){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int numRecord = 1;

		Gioca g = (Gioca) b;
		
		//record partecipazioni di quel giocatore per quella partita

		String query = "SELECT *  FROM " + this.TABLE_NAME+ " WHERE Atleta_Email='"+g.getAtleta_Email() +"' AND Partita_ID="+g.getPartita_ID();
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(query);

			ResultSet rs = preparedStatement.executeQuery(query);
			
			System.out.println(query);
			while (rs.next()) {
				numRecord++;
			}
			
			System.out.println("Numero di istanze gioca per quel giocatore:"+numRecord);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return numRecord;
	}
}
