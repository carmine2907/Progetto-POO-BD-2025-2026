import gui.Home;
import database.ConnessioneDatabase;
import controller.SistemaController;

import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static SistemaController sistemaController;

    public static void main(String[] args) {

        // 1. CHIAMATA AL DATABASE (Ottimo per il debug iniziale!)
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

        // 2. INIZIALIZZAZIONE CONTROLLER
        // (I DAO di PostgreSQL vengono generati in automatico dentro questo costruttore)
        sistemaController = new SistemaController();

        // 3. AVVIO DELLA COMPONENTE GRAFICA
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Creiamo e mostriamo ESCLUSIVAMENTE la Home.
                // Sarà il bottone dentro la Home ad istanziare e aprire la LoginGUI.
                Home homeGUI = new Home(sistemaController);
                homeGUI.mostra();
            }
        });
    }

    // Metodo statico che permette di recuperare lo stesso identico controller attivo
    public static SistemaController getSistemaController() {
        return sistemaController;
    }
}