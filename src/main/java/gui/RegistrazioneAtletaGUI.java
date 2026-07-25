package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Registrazione atleta gui.
 */
public class RegistrazioneAtletaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtDataNascita;
    private JComboBox<String> cmbRuolo;
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

    /**
     * Instantiates a new Registrazione atleta gui.
     *
     * @param controller the controller
     * @param chiamante  the chiamante
     */
    public RegistrazioneAtletaGUI(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        frame = new JFrame("Registrazione Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);


        cmbRuolo.addItem("Portiere");
        cmbRuolo.addItem("Difensore");
        cmbRuolo.addItem("Centrocampista");
        cmbRuolo.addItem("Attaccante");


        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String dataNascita = txtDataNascita.getText().trim(); // Formato consigliato: AAAA-MM-GG
                String ruolo = (String) cmbRuolo.getSelectedItem();

                try {

                    if (dataNascita.isEmpty()) {
                        throw new IllegalArgumentException("La data di nascita è obbligatoria.");
                    }


                    controller.registraAtleta(username, password, nome, cognome, dataNascita, ruolo);

                    JOptionPane.showMessageDialog(frame,
                            "Registrazione Atleta completata con successo!\nOra puoi effettuare l'accesso.",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);

                    chiudiETornaAlLogin();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Attenzione data non valida!!", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore durante la registrazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


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

    /**
     * Mostra.
     */
    public void mostra() {
        frame.setVisible(true);
    }
}