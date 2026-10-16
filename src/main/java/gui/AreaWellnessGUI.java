package gui;
import controller.Controller;
import model.MembroVip;
import model.ServizioWellness;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Area wellness gui.
 */
public class AreaWellnessGUI extends JFrame {
    private JCheckBox saunaCheckBox;
    private JCheckBox fisioterapiaCheckBox;
    private JCheckBox nutrizionistaCheckBox;
    private JButton JConferma;
    private JButton JIndietro;
    private JPanel mainPanel;
    private Controller controller;
    private JFrame framePrecendente;

    /**
     * Instantiates a new Area wellness gui.
     *
     * @param controller       the controller
     * @param framePrecendente the frame precendente
     */
    public AreaWellnessGUI(Controller controller,JFrame framePrecendente) {
        this.controller = controller;
        this.framePrecendente= framePrecendente;
         setContentPane(mainPanel);
        if (controller.getUtenteLoggato() != null) {
            setTitle("AreaWellness - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("AreaWellness");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        GestioneEventi();
        controllaServiziGiaPrenotati();

    }
    private void controllaServiziGiaPrenotati() {
        try {
            List<ServizioWellness> listaServiziDb = controller.ottieniStatoServiziWellness();
            MembroVip utenteLoggato = (MembroVip) controller.getUtenteLoggato();

            for (ServizioWellness servizio : listaServiziDb) {


                if (!servizio.getDisponibile()) {



                    boolean prenotatoDaMe = false;
                    if (servizio.getMembroPrenotante() != null &&
                            servizio.getMembroPrenotante().equals(utenteLoggato.getId_utente())) {
                        prenotatoDaMe = true;
                    }


                    if (servizio.getNomeServizio().equalsIgnoreCase("Sauna")) {
                        saunaCheckBox.setEnabled(false);
                        if (prenotatoDaMe) saunaCheckBox.setSelected(true);
                    }
                    else if (servizio.getNomeServizio().equalsIgnoreCase("Fisioterapia") || servizio.getNomeServizio().equalsIgnoreCase("Massaggio Sportivo")) {
                        fisioterapiaCheckBox.setEnabled(false);
                        if (prenotatoDaMe) fisioterapiaCheckBox.setSelected(true);
                    }
                    else if (servizio.getNomeServizio().equalsIgnoreCase("Nutrizionista")) {
                        nutrizionistaCheckBox.setEnabled(false);
                        if (prenotatoDaMe) nutrizionistaCheckBox.setSelected(true);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Errore durante il caricamento dello storico servizi: " + ex.getMessage());
        }
    }
    private void GestioneEventi() {


        JConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ServizioWellness> serviziSelezionati = new ArrayList<>();


                if (saunaCheckBox.isSelected() && saunaCheckBox.isEnabled()) {
                    serviziSelezionati.add(new ServizioWellness("W1", "Sauna", true, null));
                }
                if (fisioterapiaCheckBox.isSelected()&& fisioterapiaCheckBox.isEnabled()) {
                    serviziSelezionati.add(new ServizioWellness("W3", "Fisioterapia", true, null));
                }
                if (nutrizionistaCheckBox.isSelected() && nutrizionistaCheckBox.isEnabled()) {
                    serviziSelezionati.add(new ServizioWellness("W2", "Nutrizionista", true, null));
                }


                if (serviziSelezionati.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Seleziona almeno un servizio prima di confermare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }



                    MembroVip membroVip = (MembroVip) controller.getUtenteLoggato();


                    boolean successo = controller.prenotaServizi(membroVip, serviziSelezionati);

                    if (successo) {
                        JOptionPane.showMessageDialog(mainPanel, "Servizi prenotati con successo!", "Conferma", JOptionPane.INFORMATION_MESSAGE);

                        if (saunaCheckBox.isSelected()) {
                            saunaCheckBox.setEnabled(false);
                            saunaCheckBox.setSelected(false);
                        }
                        if (fisioterapiaCheckBox.isSelected()) {
                            fisioterapiaCheckBox.setEnabled(false);
                            fisioterapiaCheckBox.setSelected(false);
                        }
                        if (nutrizionistaCheckBox.isSelected()) {
                            nutrizionistaCheckBox.setEnabled(false);
                            nutrizionistaCheckBox.setSelected(false);
                        }
                    }

            }
        });


        JIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (framePrecendente != null) {
                    framePrecendente.setVisible(true);
                }
                dispose();
            }
        });
    }

}
