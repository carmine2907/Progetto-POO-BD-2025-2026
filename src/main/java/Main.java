import gui.Home;
import database.ConnessioneDatabase;
import controller.SistemaController;

import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Main.
 */
public class Main {

    private static SistemaController sistemaController;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        //CHIAMATA AL DATABASE
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


        sistemaController = new SistemaController();

        // 3. AVVIO DELLA COMPONENTE GRAFICA
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                Home homeGUI = new Home(sistemaController);
                homeGUI.mostra();
            }
        });
    }


    /**
     * Gets sistema controller.
     *
     * @return the sistema controller
     */
    public static SistemaController getSistemaController() {
        return sistemaController;
    }
}