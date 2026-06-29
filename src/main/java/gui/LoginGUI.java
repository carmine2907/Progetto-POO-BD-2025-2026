package gui;

import controller.SistemaController;
import model.Utente;
import model.Atleta;
import model.Allenatore;
import model.Dirigente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private JPanel mainPanel;
    private JTextField Jlogin;
    private JPasswordField Jpassword;
    private JLabel JtestoUser;
    private JLabel JtestoPass;

    // Nomi aggiornati per maggiore chiarezza. Assicurati che nel .form abbiano questo "field name"
    private JButton btnLogin;
    private JButton btnRegistrati;

    // Rimosso 'static' dal JFrame per evitare conflitti se si aprono più finestre
    private JFrame frame;
    private SistemaController controller;
    private Home frameChiamante;

    public LoginGUI(SistemaController controller, Home chiamante) {
        this.controller = controller;
        this.frameChiamante = chiamante;

        frame = new JFrame("Accesso al Sistema");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // --- AZIONE PER IL BOTTONE DI LOGIN ---
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = Jlogin.getText();
                String password = new String(Jpassword.getPassword());

                try {
                    boolean successo = controller.login(login, password);

                    if (successo) {
                        // Recuperiamo l'oggetto polimorfico dell'utente appena loggato
                        Utente loggato = SistemaController.getUtenteLoggato();

                        // --- INIZIO SMISTAMENTO PER RUOLO ---
                        if (loggato instanceof Atleta) {
                            JOptionPane.showMessageDialog(frame, "Benvenuto Atleta " + loggato.getNome() + "!");

                            // Apre la dashboard specifica per l'Atleta
                            HomeAtletaGUI homeAtleta = new HomeAtletaGUI(controller);
                            homeAtleta.mostra();
                        }
                        else if (loggato instanceof Allenatore) {
                            JOptionPane.showMessageDialog(frame, "Benvenuto Mister " + loggato.getNome() + "!");

                            // Apre la dashboard specifica per l'Allenatore
                            HomeAllenatoreGUI homeAllenatore = new HomeAllenatoreGUI(controller);
                            homeAllenatore.mostra();
                        }
                        else if (loggato instanceof Dirigente) {
                            JOptionPane.showMessageDialog(frame, "Accesso Area Dirigenza autorizzato.");

                            // Apre la dashboard specifica per il Dirigente
                            HomeDirigenteGUI homeDirigente = new HomeDirigenteGUI(controller);
                            homeDirigente.mostra();
                        }
                        else {
                            // Nessun ruolo specifico (es. Utente base o Admin di sistema)
                            JOptionPane.showMessageDialog(frame, "Benvenuto " + loggato.getNome() + "!");

                            // Torna alla Home generica
                            chiamante.aggiornaDopoLogin();
                            chiamante.mostra();
                        }
                        // --- FINE SMISTAMENTO ---

                        // In ogni caso, il login ha avuto successo, quindi chiudiamo questa finestra
                        frame.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Username o Password errate.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore di sistema: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- AZIONE PER IL BOTTONE REGISTRATI ---
        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Nascondiamo temporaneamente la finestra di login
                frame.setVisible(false);

                // Creiamo e mostriamo la schermata di registrazione, passandogli 'noi stessi' (LoginGUI.this)
                // in modo che dopo la registrazione possa riaprire il Login
                RegistrazioneGUI registrazione = new RegistrazioneGUI(controller, LoginGUI.this);
                registrazione.mostra();
            }
        });
    }

    public void mostra() {
        frame.setVisible(true);
    }
}