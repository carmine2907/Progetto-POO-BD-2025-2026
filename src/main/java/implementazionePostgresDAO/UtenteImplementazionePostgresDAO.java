package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

	private Connection connection;

	public UtenteImplementazionePostgresDAO() {
		try {
			connection = ConnessioneDatabase.getInstance().getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	/*@Override
	public void esempioQuery() {

	}*/

}
