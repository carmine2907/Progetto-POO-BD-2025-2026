package gui;

import javax.swing.*;

public class Home {
    private JPanel mainPanel;
    private static JFrame frameHome;
    private AutontificazioneController controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);


    }

    public Home() {
        controller = new AutontificazioneController();
        // Add action listeners or other initialization code here

    }


}
