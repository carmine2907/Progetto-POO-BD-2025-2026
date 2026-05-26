package gui;

import javax.swing.*;
import controller.AutenticazioneController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.LoginGUI;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private AutenticazioneController controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);


    }

    public Home() {
        controller = new AutenticazioneController();

    }
    public void mostra(){ frameHome.setVisible(true);}
}
