package database; // c'era D grande


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

	// istanza singleton
	private static ConnessioneDatabase instance;

	// connessione al database
	private Connection connection;

	// parametri PostgreSQL
	private static final String URL =
			"jdbc:postgresql://localhost:5432/ScuolaCalcio";

	private static final String USER = "postgres";

	private static final String PASSWORD = "Mascottino#7";

	// costruttore priv
	private ConnessioneDatabase()
			throws SQLException {

		connection = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	// metodo getinstance
	public static ConnessioneDatabase getInstance()
			throws SQLException {

		if (instance == null) {

			instance = new ConnessioneDatabase();
		}

		return instance;
	}

	// getter connessione
	public Connection getConnection() {

		return connection;
	}
}