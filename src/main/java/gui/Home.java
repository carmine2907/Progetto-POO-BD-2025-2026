package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanel;
    private JTextField Jtext;
    private JPasswordField JpasswordField;
    private JButton JbuttonLogin;
    private JLabel Jusername;
    private JLabel Jpassword;
    private static JFrame frameHome;
    private Controller controller;

    public Home() {
        controller = new Controller();
        frameHome = new JFrame("Home");
        frameHome.setContentPane(this.mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);

        JbuttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effettuaLogin();
            }
            private void effettuaLogin() {
                String username = Jtext.getText().trim();
                String password = new String(JpasswordField.getText().trim());

                // Controllo campi vuoti lato GUI
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserisci sia username che password.", "Campi incompleti", JOptionPane.WARNING_MESSAGE);
                }
                try {
                    // Richiamo il TUO metodo del controller
                    boolean successo = controller.accedi(username, password);

                    if (successo) {
                        Utente utenteLoggato = controller.getUtenteLoggato();

                        JOptionPane.showMessageDialog(null,
                                "Accesso effettuato con successo!",
                                "Login Completato",
                                JOptionPane.INFORMATION_MESSAGE);

                        frameHome.dispose();

                        if (utenteLoggato instanceof Istruttore) {

                            // (Opzionale) Se ti servono metodi specifici dell'istruttore, fai il cast:
                            Istruttore istruttore = (Istruttore) utenteLoggato;

                            IstruttoreGUI istruttoreGUI = new IstruttoreGUI(controller);
                            istruttoreGUI.setVisible(true);

                        } else if (utenteLoggato instanceof MembroVip || utenteLoggato instanceof Iscritto) {
                          IscrittoGUI iscrittoGUI = new IscrittoGUI(controller);
                          iscrittoGUI.setVisible(true);

                        }
                    }
                } catch (Exception ex) {
                    // Cattura l'eccezione lanciata dal tuo metodo (es. "Credenziali errate!")
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
}}
