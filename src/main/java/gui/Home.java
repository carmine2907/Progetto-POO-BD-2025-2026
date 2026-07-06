package gui;

import controller.SistemaController;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The type Home.
 */
public class Home {
    private JPanel mainPanel;
    private JButton jButtonLogin;

    // Rimosso lo 'static'. Ogni istanza di Home avrà il suo JFrame personale.
    private JFrame frameHome;
    private SistemaController controller;

    /**
     * Instantiates a new Home.
     *
     * @param controller the controller
     */
    public Home(SistemaController controller) {
        this.controller = controller;

        // Inizializzazione della finestra direttamente nel costruttore
        frameHome = new JFrame("Home");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setLocationRelativeTo(null); // Centra la finestra sullo schermo

        // Azione del bottone di Login
        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Nasconde la Home
                frameHome.setVisible(false);

                // Apre il Login passando il controller e l'istanza corrente della Home (this)
                LoginGUI login = new LoginGUI(controller, Home.this);
                login.mostra();
            }
        });
    }

    /**
     * Mostra.
     */
// Metodo chiamato dall'esterno per rendere visibile la GUI
    public void mostra() {
        frameHome.setVisible(true);
    }

    /**
     * Aggiorna dopo login.
     */
// Metodo chiamato dalla LoginGUI per aggiornare l'interfaccia dopo l'accesso
    public void aggiornaDopoLogin() {
        jButtonLogin.setVisible(false);
    }
}