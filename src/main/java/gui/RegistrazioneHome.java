package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Registrazione home.
 */
public class RegistrazioneHome {
    private JPanel panelMain;
    private JTextField txtToken;
    private JButton btnAvanti;
    private JButton btnAnnulla;
    private JLabel lblInsToken;

    private JFrame frame;
    private SistemaController controller;
    private LoginGUI chiamante;

    /**
     * Instantiates a new Registrazione home.
     *
     * @param controller the controller
     * @param chiamante  the chiamante
     */
    public RegistrazioneHome(SistemaController controller, LoginGUI chiamante) {
        this.controller = controller;
        this.chiamante = chiamante;

        frame = new JFrame("Verifica Codice Tesseramento");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // --- AZIONE: BOTTONE AVANTI ---
        btnAvanti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String token = txtToken.getText().trim();

                // Chiudiamo questa piccola finestra di inserimento token
                frame.dispose();

                // Smistamento verso la GUI corretta
                switch (token) {
                    case "0001":
                        // Apre la schermata specifica per gli atleti
                        new RegistrazioneAtletaGUI(controller, chiamante).mostra();
                        break;

                    case "2224":
                        // Apre la schermata per i dirigenti
                        new RegistrazioneDirigenteGUI(controller, chiamante).mostra();
                        break;

                    case "5557":
                        // Apre la schermata per gli allenatori
                        new RegistrazioneAllenatoreGUI(controller, chiamante).mostra();
                        break;

                    default:
                        // Se il codice è sbagliato, mostriamo errore e riapriamo il Login
                        JOptionPane.showMessageDialog(null,
                                "Codice non valido. Riprova o contatta la segreteria.",
                                "Errore Token", JOptionPane.ERROR_MESSAGE);
                        chiamante.mostra();
                        break;
                }
            }
        });

        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                chiamante.mostra(); // Torna al Login
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