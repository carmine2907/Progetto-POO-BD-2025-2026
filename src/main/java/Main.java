import model.Atleta;
import model.Squadra;
import database.ConnessioneDatabase;
import java.sql.Connection;
import java.util.Date;
import gui.LoginGUI;
import database.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // 1. CHIAMATA AL DATABASE: Carica il driver e stabilisce la connessione all'avvio
        try {
            System.out.println("Tentativo di connessione al database in corso...");
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connessione al database PostgreSQL stabilita con successo!");
            }
        } catch (SQLException e) {
            System.err.println("ERRORE: Impossibile connettersi al database pgAdmin!");
            e.printStackTrace();
        }


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                LoginGUI login = new LoginGUI(1,1);
                login.mostra();
            }
        });
    }
}

