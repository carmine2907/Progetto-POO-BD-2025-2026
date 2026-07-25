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


    private JButton btnLogin;
    private JButton btnRegistrati;


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


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = Jlogin.getText();
                String password = new String(Jpassword.getPassword());

                try {
                    boolean successo = controller.login(login, password);

                    if (successo) {
                        // applico una sorta di polimorfismo per il login
                        Utente loggato = SistemaController.getUtenteLoggato();


                        if (loggato instanceof Atleta) {
                            JOptionPane.showMessageDialog(frame, "Benvenuto Atleta " + loggato.getNome() + "!");


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


        btnRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false); // Nasconde temporaneamente il login

                // Apre la  finestrella del Token
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