package gui;

import controller.SistemaController;
import model.Atleta;
import model.Squadra;
import controller.Exceptions.AtletaGiaPresenteException;
import controller.Exceptions.PagamentoNonValidoException;
import controller.Exceptions.SquadraCompletaException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Assegna atleta gui.
 */
public class AssegnaAtletaGUI {

    private JPanel panelMain;
    private JComboBox<Atleta> cmbAtleti;
    private JComboBox<Squadra> cmbSquadre;
    private JButton btnConferma;
    private JButton btnAnnulla;
    private JLabel lblAtleti;
    private JLabel lblSelSquadra;

    private JFrame frame;
    private SistemaController controller;

    /**
     * Instantiates a new Assegna atleta gui.
     *
     * @param controller the controller
     */
    public AssegnaAtletaGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Modulo Assegnazione Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);


        caricaDatiNelleTendine();


        btnConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Atleta atletaSelezionato = (Atleta) cmbAtleti.getSelectedItem();
                    Squadra squadraSelezionata = (Squadra) cmbSquadre.getSelectedItem();

                    if (atletaSelezionato == null || squadraSelezionata == null) {
                        JOptionPane.showMessageDialog(frame, "Selezionare Atleta e Squadra.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }


                    controller.assegnaAtletaASquadra(atletaSelezionato, squadraSelezionata);

                    JOptionPane.showMessageDialog(frame, "Atleta assegnato con successo alla squadra!", "Operazione completata", JOptionPane.INFORMATION_MESSAGE);

                } catch (AtletaGiaPresenteException ex) {

                    JOptionPane.showMessageDialog(frame, "Questo atleta fa già parte di questa specifica squadra.", "Attenzione", JOptionPane.WARNING_MESSAGE);

                } catch (PagamentoNonValidoException ex) {

                    JOptionPane.showMessageDialog(frame, "Impossibile assegnare: " + ex.getMessage(), "Pagamento mancante", JOptionPane.ERROR_MESSAGE);

                } catch (SquadraCompletaException ex) {
                    // La squadra ha raggiunto il limite max_giocatori
                    JOptionPane.showMessageDialog(frame, "Impossibile assegnare: " + ex.getMessage(), "Squadra Piena", JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {

                    if (ex.getMessage().contains("uq_iscrizione_atleta_stagione")) {
                        JOptionPane.showMessageDialog(frame,
                                "Impossibile procedere: l'atleta selezionato è GIÀ ISCRITTO a una squadra per la stagione in corso!",
                                "Atleta già tesserato",
                                JOptionPane.ERROR_MESSAGE);
                    } else {

                        JOptionPane.showMessageDialog(frame, "Errore di sistema: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


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

    /**
     * Mostra.
     */
    public void mostra() {
        frame.setVisible(true);
    }
}