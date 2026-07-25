package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Registrazione allenatore gui.
 */
public class RegistrazioneAllenatoreGUI {

    private JPanel panelMain;
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbQualifica;
    private JButton btnRegistrati;
    private JButton btnAnnulla;

    private JFrame frame;
    private SistemaController controller;
    private LoginGUI chiamante;

    /**
     * Instantiates a new Registrazione allenatore gui.
     *
     * @param controller the controller
     * @param chiamante  the chiamante
     */
    public RegistrazioneAllenatoreGUI(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        frame = new JFrame("Registrazione Allenatore");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        cmbQualifica.addItem("UEFA Pro");
        cmbQualifica.addItem("UEFA A");
        cmbQualifica.addItem("UEFA B");
        cmbQualifica.addItem("UEFA C");
        cmbQualifica.addItem("Allenatore Dilettante");


        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String cognome = txtCognome.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String qualifica = (String) cmbQualifica.getSelectedItem();

                try {

                    controller.registraAllenatore(username, password, nome, cognome, qualifica);

                    JOptionPane.showMessageDialog(frame,
                            "Registrazione Allenatore completata con successo!\nOra puoi effettuare l'accesso.",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);

                    chiudiETornaAlLogin();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
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