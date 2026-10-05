package gui;
import controller.*;
import javax.swing.*;

public class IscrittoGUI extends JFrame {
    private JPanel mainPanel;
    private JButton JVisualizzaScheda;
    private JTextArea JtextVisualizzaS;
    private JComboBox JSelezionaCorso;
    private JButton JPrenota;
    private JButton JPaga;
    private JButton JAreaWellness;
    private JLabel JLabelSeleziona;
    private Controller controller;

    public IscrittoGUI(Controller controller) {
         this.controller =controller;
        setContentPane(mainPanel);
        setTitle("Area Iscritto - " + controller.getUtenteLoggato().getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);


    }
}
