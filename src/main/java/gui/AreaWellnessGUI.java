package gui;
import controller.*;
import javax.swing.*;

public class AreaWellnessGUI extends JFrame {
    private JCheckBox JcheckBox1;
    private JCheckBox JcheckBox2;
    private JCheckBox JCheckBox3;
    private JButton JConferma;
    private JButton JIndietro;
    private Controller controller;

    public AreaWellnessGUI(Controller controller) {
        this.controller = controller;

        if (controller.getUtenteLoggato() != null) {
            setTitle("AreaWellness - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("AreaWellness");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }
}
