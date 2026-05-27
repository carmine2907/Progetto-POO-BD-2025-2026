package implementazionePostgresDAO;
import model.Atleta;
import database.ConnessioneDatabase;
import dao.AtletaDAO;
import model.Atleta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AtletaImplementazionePostgresDAO
		implements AtletaDAO {

	private Connection connection;

	public AtletaImplementazionePostgresDAO() {

		try {

			connection = ConnessioneDatabase.getInstance().getConnection();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void salva(Atleta atleta) {

		String sql =
				"""
                INSERT INTO atleta(
                    nome,
                    cognome,
                    email
                )
                VALUES (?, ?, ?)
                """;

		try {

			PreparedStatement ps =
					connection.prepareStatement(sql);

			ps.setString(
					1,
					atleta.getNome()
			);

			ps.setString(
					2,
					atleta.getCognome()
			);

			ps.setString(
					3,
					atleta.getEmail()
			);

			ps.executeUpdate();

			System.out.println(
					"Atleta salvato."
			);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public Atleta cercaPerId(int id) {

		String sql =
				"""
                SELECT *
                FROM atleta
                WHERE id_atleta = ?
                """;

		try {

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Atleta atleta = new Atleta("1","mariosdi","mario","rossi","15 luglio 2015","Attaccante" );



				atleta.setNome(
						rs.getString("nome")
				);

				atleta.setCognome(
						rs.getString("cognome")
				);

				atleta.setEmail(
						rs.getString("email")
				);

				return atleta;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Atleta> trovaTutti() {

		List<Atleta> lista =
				new ArrayList<>();

		String sql =
				"""
                SELECT *
                FROM atleta
                """;

		try {

			PreparedStatement ps =
					connection.prepareStatement(sql);

			ResultSet rs =
					ps.executeQuery();

			while (rs.next()) {

				Atleta atleta = new Atleta("2","luig","luigi","verdi","7 luglio 2010","portiere");



				atleta.setNome(
						rs.getString("nome")
				);

				atleta.setCognome(
						rs.getString("cognome")
				);

				atleta.setEmail(
						rs.getString("email")
				);

				lista.add(atleta);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public void elimina(int id) {

		String sql =
				"""
                DELETE FROM atleta
                WHERE id_atleta = ?
                """;

		try {

			PreparedStatement ps =
					connection.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();

			System.out.println(
					"Atleta eliminato."
			);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}