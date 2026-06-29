package gui;

import controller.SistemaController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrazioneGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegistrati;
    private JButton btnAnnulla;

    private JFrame frame;
    private SistemaController controller;
    private LoginGUI chiamante; // Riferimento alla finestra di Login

    public RegistrazioneGUI(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        // Inizializzazione del JFrame
        frame = new JFrame("Registrazione Nuovo Utente");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centra la finestra

        // --- AZIONE: BOTTONE REGISTRATI ---
        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Lettura degli input
                String nome = txtNome.getText();
                String cognome = txtCognome.getText();
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                try {
                    // 2. Chiamata al Controller per la logica di business
                    controller.registraUtente(nome, cognome, username, password);

                    // 3. Se non vengono lanciate eccezioni, la registrazione ha avuto successo
                    JOptionPane.showMessageDialog(frame, "Registrazione completata con successo!\nOra puoi effettuare l'accesso.", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // 4. Torniamo alla schermata di Login
                    chiudiETornaAlLogin();

                } catch (IllegalArgumentException ex) {
                    // Gestione errori di validazione (es. campi vuoti, password corta)
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    // Gestione errori di sistema o eccezioni custom (es. UtenteGiaEsistenteException)
                    JOptionPane.showMessageDialog(frame, "Errore durante la registrazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- AZIONE: BOTTONE ANNULLA ---
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // L'utente ha cambiato idea, torniamo semplicemente al login
                chiudiETornaAlLogin();
            }
        });
    }

    // Metodo helper per evitare di ripetere codice
    private void chiudiETornaAlLogin() {
        frame.dispose(); // Distrugge la finestra di registrazione
        chiamante.mostra(); // Rende di nuovo visibile la finestra di Login
    }

    public void mostra() {
        frame.setVisible(true);
    }
}