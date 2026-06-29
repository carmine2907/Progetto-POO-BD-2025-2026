package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private JPanel mainPanel;;
    private JTextField Jlogin;
    private JPasswordField Jpassword;
    private JLabel JtestoUser;
    private JLabel JtestoPass;
    private JButton JButton;
    private JButton registratiBottone;
    private static JFrame frameHome;
    private SistemaController controller;
    private Home frameChiamante;

    public LoginGUI(SistemaController controller, Home chiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        frameHome = new JFrame("Accesso al Sistema");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setLocationRelativeTo(null);

        JButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String login = Jlogin.getText();
                String password = new String(Jpassword.getPassword());

                try
                {
                    boolean successo = controller.login(login, password);

                    if (successo)
                    {
                        JOptionPane.showMessageDialog(frameHome, "Benvenuto " + login + "!");

                        chiamante.aggiornaDopoLogin();
                        chiamante.mostra();

                        frameHome.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frameHome,"Username o Password errate.","Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }

                catch   (IllegalArgumentException ex)
                {
                    JOptionPane.showMessageDialog(frameHome, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                }

                catch (Exception ex)
                {
                     JOptionPane.showMessageDialog(frameHome, "Errore di sistema: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void mostra(){ frameHome.setVisible(true);}
}
