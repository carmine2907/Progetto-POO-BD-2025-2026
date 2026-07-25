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


        jButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frameHome.setVisible(false);


                LoginGUI login = new LoginGUI(controller, Home.this);
                login.mostra();
            }
        });
    }

    /**
     * Mostra.
     */

    public void mostra() {
        frameHome.setVisible(true);
    }

    /**
     * Aggiorna dopo login.
     */

    public void aggiornaDopoLogin() {
        jButtonLogin.setVisible(false);


        JOptionPane.showMessageDialog(
                frameHome, "Ricordati di recuperare i token per essere parte della scuola calcio!", "Avviso Scuola Calcio",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}