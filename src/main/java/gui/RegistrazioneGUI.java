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
    private JLabel lblTokenID;
    private JTextField txtCodiceRuolo;
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

        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lettura degli input esistenti
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                // NUOVO: Lettura del codice identificativo
                String codiceRuolo = txtCodiceRuolo.getText().trim();

                try {
                    // Passiamo anche il codice al metodo del controller
                    controller.registraUtente(username, password, nome, cognome, codiceRuolo);

                    JOptionPane.showMessageDialog(frame,
                            "Registrazione completata con successo!\nOra puoi effettuare l'accesso.",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);

                    chiudiETornaAlLogin();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
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
        frame.dispose();
        chiamante.mostra(); // Rende di nuovo visibile la finestra di Login
    }

    public void mostra() {
        frame.setVisible(true);
    }



}