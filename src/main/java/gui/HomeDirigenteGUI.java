package gui;

import controller.SistemaController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeDirigenteGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JLabel lblBenvenuto;
    private JButton btnPianificaPartita;
    private JButton btnLogout;
    private JButton btnVisualizzaSquadra;

    private JFrame frame;
    private SistemaController controller;

    public HomeDirigenteGUI(SistemaController controller) {
        this.controller = controller;

        // Inizializzazione del JFrame
        frame = new JFrame("Area Riservata - Dirigente");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400); // Dimensione di default
        frame.setLocationRelativeTo(null); // Centra la finestra

        // Imposta il messaggio di benvenuto dinamico
        if (SistemaController.isUtenteAutenticato()) {
            lblBenvenuto.setText("Benvenuto Dirigente: " + SistemaController.getUtenteLoggato().getNome());
        }

        // --- AZIONE: ASSEGNA ATLETA (nella HomeDirigenteGUI) ---

        // --- AZIONE: PIANIFICA PARTITA (nella HomeDirigenteGUI) ---
        btnPianificaPartita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                PianificaPartitaGUI moduloPartita = new PianificaPartitaGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloPartita.mostra();
            }
        });

        btnVisualizzaSquadra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                VisualizzaSquadraGUI moduloSquadra = new VisualizzaSquadraGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloSquadra.mostra();
            }
        });

        // --- AZIONE: LOGOUT ---
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Svuota la sessione nel controller
                controller.logout();

                // 2. Chiude questa finestra
                frame.dispose();

                // 3. Riapre la finestra di Login (assumendo che Home abbia un costruttore vuoto o simile)
                // Se la tua classe Home richiede parametri, adattali qui di conseguenza.
                JOptionPane.showMessageDialog(null, "Logout effettuato con successo.");
                LoginGUI login = new LoginGUI(controller, new Home(controller));
                login.mostra();
                System.exit(0); // Rimuovi System.exit e de-commenta le righe sopra per abilitare il loop delle finestre
            }
        });
    }

    public void mostra() {
        frame.setVisible(true);
    }
}
