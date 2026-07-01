package gui;

import controller.SistemaController;
import model.Atleta;
import model.Squadra;
import controller.exceptions.AtletaGiaPresenteException;
import controller.exceptions.PagamentoNonValidoException;
import controller.exceptions.SquadraCompletaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssegnaAtletaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JComboBox<Atleta> cmbAtleti;
    private JComboBox<Squadra> cmbSquadre;
    private JButton btnConferma;
    private JButton btnAnnulla;
    private JLabel lblAtleti;
    private JLabel lblSelSquadra;

    private JFrame frame;
    private SistemaController controller;

    public AssegnaAtletaGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Modulo Assegnazione Atleta");
        frame.setContentPane(panelMain);
        // Usiamo DISPOSE_ON_CLOSE altrimenti chiudendo questa finestrella si chiuderebbe tutto il programma!
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        // 1. Popoliamo i menu a tendina (Combobox)
        caricaDatiNelleTendine();

        // --- AZIONE: BOTTONE CONFERMA ---
        btnConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recuperiamo gli oggetti selezionati nei menu a tendina
                Atleta atletaSelezionato = (Atleta) cmbAtleti.getSelectedItem();
                Squadra squadraSelezionata = (Squadra) cmbSquadre.getSelectedItem();

                // Controllo di sicurezza se le tendine sono vuote
                if (atletaSelezionato == null || squadraSelezionata == null) {
                    JOptionPane.showMessageDialog(frame, "Selezionare un Atleta e una Squadra validi.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Chiamiamo il metodo del Controller
                    controller.assegnaAtletaASquadra(atletaSelezionato, squadraSelezionata);

                    JOptionPane.showMessageDialog(frame, "Atleta assegnato con successo alla squadra " + squadraSelezionata.getNome() + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Chiudiamo la finestrella dopo il successo
                    frame.dispose();

                } catch (PagamentoNonValidoException | SquadraCompletaException | AtletaGiaPresenteException customEx) {
                    // Gestione delle tue eccezioni personalizzate
                    JOptionPane.showMessageDialog(frame, customEx.getMessage(), "Impossibile Assegnare", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    // Gestione di errori generici (es. Database o Utente non loggato)
                    JOptionPane.showMessageDialog(frame, "Errore di sistema: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- AZIONE: BOTTONE ANNULLA ---
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiude semplicemente la finestra tornando alla Home del Dirigente
            }
        });
    }

    /**
     * Metodo di supporto per riempire le ComboBox.
     * NOTA: Affinché i nomi si vedano bene nel menu a tendina, assicurati che
     * le classi Atleta e Squadra abbiano un buon metodo toString() implementato!
     */
    private void caricaDatiNelleTendine() {


        List<Atleta> listaAtleti = controller.getTuttiGliAtleti();
        for (Atleta a : listaAtleti) {
            cmbAtleti.addItem(a);
        }

        List<Squadra> listaSquadre = controller.getTutteLeSquadre();
        for (Squadra s : listaSquadre) {
            cmbSquadre.addItem(s);
        }

    }

    public void mostra() {
        frame.setVisible(true);
    }
}