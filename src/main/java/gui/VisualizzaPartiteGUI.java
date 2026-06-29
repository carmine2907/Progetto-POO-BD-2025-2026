package gui;

import controller.SistemaController;
import model.Partita;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizzaPartiteGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JList<Partita> listPartite;
    private JButton btnAggiorna;
    private JButton btnChiudi;

    private JFrame frame;
    private SistemaController controller;
    private DefaultListModel<Partita> listModel; // Modello per gestire i dati della JList

    public VisualizzaPartiteGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Calendario Partite");
        frame.setContentPane(panelMain);
        // Usiamo DISPOSE_ON_CLOSE per chiudere solo questa finestrella
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);

        // Inizializziamo il modello per la lista delle partite
        listModel = new DefaultListModel<>();
        listPartite.setModel(listModel);

        // Caricamento iniziale delle partite all'apertura della finestra
        caricaPartite();

        // --- AZIONE: BOTTONE AGGIORNA ---
        btnAggiorna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caricaPartite(); // Ricarica la lista dal database
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
     * Metodo per recuperare le partite dal database e inserirle nella JList.
     */
    private void caricaPartite() {
        // Svuotiamo la lista precedente prima di ricaricare
        listModel.clear();

        try {
            // ATTENZIONE: Qui dovrai chiamare il metodo reale del tuo controller/DAO
            // che restituisce tutte le partite future o quelle pertinenti all'atleta loggato.
            // Esempio: List<Partita> partite = controller.getTutteLePartite();

            /* --- STRUTTURA DA DECOMMENTARE QUANDO AVRAI IL METODO NEL CONTROLLER ---
            if (partite == null || partite.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nessuna partita in programma trovata.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Aggiungiamo le partite trovate al modello della JList
                for (Partita p : partite) {
                    listModel.addElement(p);
                }
            }
            */

            // Messaggio provvisorio finché non colleghi il DB
            JOptionPane.showMessageDialog(frame, "Simulazione: Qui comparirà la lista delle partite prelevate dal DB.", "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il recupero delle partite: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostra() {
        frame.setVisible(true);
    }
}