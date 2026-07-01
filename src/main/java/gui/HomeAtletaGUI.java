package gui;

import controller.SistemaController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeAtletaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JLabel lblBenvenuto;
    private JButton btnVisualizzaPartite;
    private JButton btnStatoPagamenti;
    private JButton btnLogout;

    private JFrame frame;
    private SistemaController controller;

    public HomeAtletaGUI(SistemaController controller) {
        this.controller = controller;

        // Inizializzazione del JFrame
        frame = new JFrame("Area Riservata - Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        if (SistemaController.isUtenteAutenticato()) {
            lblBenvenuto.setText("Benvenuto Atleta: " + SistemaController.getUtenteLoggato().getNome());
        }

        // --- AZIONE: STATO PAGAMENTI ---
        btnStatoPagamenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Controllo stato pagamenti in corso...", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // --- AZIONE: VISUALIZZA PARTITE (nella HomeAtletaGUI) ---
        btnVisualizzaPartite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                VisualizzaPartiteGUI moduloPartite = new VisualizzaPartiteGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloPartite.mostra();
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
