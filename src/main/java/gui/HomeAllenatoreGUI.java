package gui;

import controller.SistemaController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeAllenatoreGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JLabel lblBenvenuto;
    private JButton btnVisualizzaSquadra;
    private JButton btnLogout;
    private JButton btnAssegnaAtleta;

    private JFrame frame;
    private SistemaController controller;

    public HomeAllenatoreGUI(SistemaController controller) {
        this.controller = controller;

        // Inizializzazione del JFrame
        frame = new JFrame("Area Riservata - Allenatore");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        if (SistemaController.isUtenteAutenticato()) {
            lblBenvenuto.setText("Benvenuto Mister: " + SistemaController.getUtenteLoggato().getNome());
        }

        // --- AZIONE: VISUALIZZA SQUADRA (nella HomeAllenatoreGUI) ---
        btnVisualizzaSquadra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                VisualizzaSquadraGUI moduloSquadra = new VisualizzaSquadraGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloSquadra.mostra();
            }
        });

        btnAssegnaAtleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                AssegnaAtletaGUI moduloAssegnazione = new AssegnaAtletaGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloAssegnazione.mostra();
            }
        });

        // --- AZIONE: LOGOUT ---
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Logout effettuato con successo.");

                LoginGUI login = new LoginGUI(controller, new Home(controller));
                login.mostra();
                System.exit(0);
            }
        });
    }

    public void mostra() {
        frame.setVisible(true);
    }
}