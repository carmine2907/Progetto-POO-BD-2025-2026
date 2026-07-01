package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrazioneDirigenteGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRuoloOrganizzativo; // Menu a tendina per il ruolo
    private JButton btnRegistrati;
    private JButton btnAnnulla;
    private JLabel lblRuoloOrganizzativoDirigente;
    private JLabel lblPassword;
    private JLabel lblUsername;
    private JLabel lblNome;
    private JLabel lblCognome;

    private JFrame frame;
    private SistemaController controller;
    private LoginGUI chiamante;

    public RegistrazioneDirigenteGUI(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        frame = new JFrame("Registrazione Dirigente");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        // Popoliamo la tendina con i classici ruoli societari
        cmbRuoloOrganizzativo.addItem("Presidente");
        cmbRuoloOrganizzativo.addItem("Vice Presidente");
        cmbRuoloOrganizzativo.addItem("Direttore Sportivo");

        // --- AZIONE: BOTTONE REGISTRATI ---
        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String ruoloOrganizzativo = (String) cmbRuoloOrganizzativo.getSelectedItem();

                try {
                    // Chiamiamo il metodo specifico per il dirigente nel controller
                    controller.registraDirigente(username, password, nome, cognome, ruoloOrganizzativo);

                    JOptionPane.showMessageDialog(frame,
                            "Registrazione Dirigente completata con successo!\nOra puoi effettuare l'accesso.",
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
                chiudiETornaAlLogin();
            }
        });
    }

    private void chiudiETornaAlLogin() {
        frame.dispose();
        chiamante.mostra();
    }

    public void mostra() {
        frame.setVisible(true);
    }
}