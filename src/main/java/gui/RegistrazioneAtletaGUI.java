package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrazioneAtletaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtDataNascita; // Nuovo campo per la data
    private JComboBox<String> cmbRuolo; // Menu a tendina per il ruolo (più sicuro del testo libero)
    private JButton btnRegistrati;
    private JButton btnAnnulla;
    private JLabel lblNome;
    private JLabel lblCognome;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblDataNascita;
    private JLabel lblRuolo;

    private JFrame frame;
    private SistemaController controller;
    private LoginGUI chiamante;

    public RegistrazioneAtletaGUI(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        frame = new JFrame("Registrazione Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        // Un po' più grande per contenere i nuovi campi
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);

        // Popoliamo la tendina dei ruoli (puoi personalizzarli nel file .form o qui)
        cmbRuolo.addItem("Portiere");
        cmbRuolo.addItem("Difensore");
        cmbRuolo.addItem("Centrocampista");
        cmbRuolo.addItem("Attaccante");

        // --- AZIONE: BOTTONE REGISTRATI ---
        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String dataNascita = txtDataNascita.getText().trim(); // Formato consigliato: YYYY-MM-DD
                String ruolo = (String) cmbRuolo.getSelectedItem();

                try {
                    // Validazione locale base per la data
                    if (dataNascita.isEmpty()) {
                        throw new IllegalArgumentException("La data di nascita è obbligatoria.");
                    }

                    // Chiamiamo il metodo specifico per l'atleta nel controller
                    controller.registraAtleta(username, password, nome, cognome, dataNascita, ruolo);

                    JOptionPane.showMessageDialog(frame,
                            "Registrazione Atleta completata con successo!\nOra puoi effettuare l'accesso.",
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