package gui;

import controller.SistemaController;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home
{
    private JPanel mainPanel;
    private JButton jButtonLogin;
    private static JFrame frameHome;
    private SistemaController controller;

    public static void main(String[] args)
    {
    SistemaController controller = new SistemaController();
    Home home = new Home(controller);
    }

    public Home(SistemaController controller)
    {
     this.controller = controller;

        frameHome = new JFrame("Home");
        frameHome.setContentPane(mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);
        jButtonLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frameHome.setVisible(false);

                LoginGUI login = new LoginGUI(controller, Home.this);
                login.mostra();
            }
        });
    }
    public void mostra(){ frameHome.setVisible(true);}
    public void aggiornaDopoLogin()
    {
        jButtonLogin.setVisible(false);
    }
}
