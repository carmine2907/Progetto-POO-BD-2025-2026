package gui;

import controller.SistemaController;
import model.Atleta;
import model.Squadra;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Visualizza squadra gui.
 */
public class VisualizzaSquadraGUI {
    private JPanel panelMain;
    private JComboBox<Squadra> cmbSquadre;
    private JButton btnMostraRosa;
    private JList<Atleta> listAtleti;
    private JButton btnChiudi;
    private JLabel lblSquadra;
    private JLabel lblListaAtleti;

    private JFrame frame;
    private SistemaController controller;
    private DefaultListModel<Atleta> listModel;

    /**
     * Instantiates a new Visualizza squadra gui.
     *
     * @param controller the controller
     */
    public VisualizzaSquadraGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Visualizza Rosa Squadra");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);


        listModel = new DefaultListModel<>();
        listAtleti.setModel(listModel);


        caricaSquadreNellaTendina();


        btnMostraRosa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Squadra squadraSelezionata = (Squadra) cmbSquadre.getSelectedItem();

                if (squadraSelezionata == null) {
                    JOptionPane.showMessageDialog(frame, "Selezionare una Squadra.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                listModel.clear();

                try {




                    List<Atleta> atleti = controller.getAtletiPerSquadra(squadraSelezionata.getIdSquadra());
                    if (atleti == null || atleti.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Nessun atleta presente in questa squadra.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        for (Atleta a : atleti) {
                            listModel.addElement(a);
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore durante il recupero dei dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiude la finestra
            }
        });
    }




    private void caricaSquadreNellaTendina() {
        List<Squadra> listaSquadre = controller.getTutteLeSquadre();
        for (Squadra s : listaSquadre) {
            cmbSquadre.addItem(s);
        }

    }

    /**
     * Mostra.
     */
    public void mostra() {
        frame.setVisible(true);
    }
}