import gui.Home;
import model.Atleta;
import model.Squadra;
import gui.LoginGUI;
import database.ConnessioneDatabase;
import controller.SistemaController;
import implementazionePostgresDAO.AtletaImplementazionePostgresDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {


    private static SistemaController sistemaController;

    public static void main(String[] args) {

        // 1. CHIAMATA AL DATABASE:
        try {
            System.out.println("SISTEMA: Connessione al database in corso...");
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("SISTEMA: Connessione a PostgreSQL stabilita!");
            }
        } catch (SQLException e) {
            System.err.println("SISTEMA - ERRORE CRITICO: Database offline!");
            e.printStackTrace();
        }

        // 2. INIZIALIZZAZIONE CONTROLLER E INIEZIONE DEL DAO

        AtletaImplementazionePostgresDAO atletaDAO = new AtletaImplementazionePostgresDAO();
        sistemaController = new SistemaController();

        // 3. AVVIO DELLA COMPONENTE GRAFICA ORIGINALE
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               Home h =new Home(sistemaController);
                LoginGUI login = new LoginGUI(sistemaController, h);
                //login.mostra();
            }
        });
    }

    // Metodo statico che permette a LoginGUI e Home di recuperare lo stesso identico controller attivo
    public static SistemaController getSistemaController() {
        return sistemaController;
    }
}