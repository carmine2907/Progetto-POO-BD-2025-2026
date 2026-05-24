package gui;

import javax.swing.*;
import controller.AutenticazioneController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private JPanel mainPanel;
    private JLabel Jtitolo1;
    private JTextField JUsername;
    private JPasswordField Jpassword;
    private JButton JButton;
    private JLabel JTitolo2;
    private static JFrame frameHome;
    private AutenticazioneController controller;

    public LoginGUI() {
        controller = new AutenticazioneController();

        JButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String username = JUsername.getText();
                String password = new String(Jpassword.getPassword());

                try
                {
                    boolean successo = controller.login(username, password);

                    if (successo)
                    {
                        JOptionPane.showMessageDialog(frameHome, "Benvenuto " + username + "!");

                        // Qua ho iniziato a mettere una transizione alla home
                        Home home = new Home();
                        home.mostra();

                        frameHome.setVisible(false);
                        frameHome.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frameHome,"Username o Password errate.","Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }

                catch (IllegalArgumentException ex)
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
