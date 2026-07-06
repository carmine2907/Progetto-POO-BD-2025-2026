package gui;

import controller.SistemaController;
import model.Utente;
import model.Atleta;
import model.Allenatore;
import model.Dirigente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Login gui.
 */
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

    /**
     * Instantiates a new Login gui.
     *
     * @param controller the controller
     * @param chiamante  the chiamante
     */
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
                frame.setVisible(false); // Nasconde temporaneamente il login

                // Apre la nuova finestrella del Token
               RegistrazioneHome tokenGUI = new RegistrazioneHome(controller, LoginGUI.this);
                tokenGUI.mostra();
            }
        });
    }

    /**
     * Mostra.
     */
    public void mostra() {
        frame.setVisible(true);
    }
}