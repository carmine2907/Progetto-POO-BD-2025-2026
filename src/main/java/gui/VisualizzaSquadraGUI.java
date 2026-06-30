package gui;

import controller.SistemaController;
import model.Atleta;
import model.Squadra;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizzaSquadraGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JComboBox<Squadra> cmbSquadre;
    private JButton btnMostraRosa;
    private JList<Atleta> listAtleti;
    private JButton btnChiudi;
    private JLabel lblSquadra;
    private JLabel lblListaAtleti;

    private JFrame frame;
    private SistemaController controller;
    private DefaultListModel<Atleta> listModel; // Modello per gestire i dati della JList

    public VisualizzaSquadraGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Visualizza Rosa Squadra");
        frame.setContentPane(panelMain);
        // Usiamo DISPOSE_ON_CLOSE per chiudere solo questa finestrella
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);

        // Inizializziamo il modello per la lista degli atleti
        listModel = new DefaultListModel<>();
        listAtleti.setModel(listModel);

        // 1. Popoliamo il menu a tendina delle squadre
        caricaSquadreNellaTendina();

        // --- AZIONE: BOTTONE MOSTRA ROSA ---
        btnMostraRosa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Squadra squadraSelezionata = (Squadra) cmbSquadre.getSelectedItem();

                if (squadraSelezionata == null) {
                    JOptionPane.showMessageDialog(frame, "Selezionare una Squadra.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Svuotiamo la lista precedente
                listModel.clear();

                try {
                    // ATTENZIONE: Qui dovrai recuperare la lista reale degli atleti dal Controller
                    // Esempio: List<Atleta> atleti = controller.getAtletiPerSquadra(squadraSelezionata);

                    // --- SIMULAZIONE (da sostituire con la chiamata reale al DB) ---
                    List<Atleta> atleti = squadraSelezionata.getAtleti();

                    if (atleti == null || atleti.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Nessun atleta presente in questa squadra.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Aggiungiamo gli atleti trovati al modello della JList per visualizzarli
                        for (Atleta a : atleti) {
                            listModel.addElement(a);
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore durante il recupero dei dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- AZIONE: BOTTONE CHIUDI ---
        btnChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiude la finestra
            }
        });
    }

    /**
     * Metodo di supporto per riempire la ComboBox delle Squadre.
     */
    private void caricaSquadreNellaTendina() {
        List<Squadra> listaSquadre = controller.getTutteLeSquadre();
        for (Squadra s : listaSquadre) {
            cmbSquadre.addItem(s);
        }

    }

    public void mostra() {
        frame.setVisible(true);
    }
}