package gui;
import controller.Controller;
import model.MembroVip;
import model.ServizioWellness;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AreaWellnessGUI extends JFrame {
    private JCheckBox saunaCheckBox;
    private JCheckBox fisioterapiaCheckBox;
    private JCheckBox nutrizionistaCheckBox;
    private JButton JConferma;
    private JButton JIndietro;
    private JPanel mainPanel;
    private Controller controller;
    private JFrame framePrecendente;

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
        setSize(400, 400);
        setVisible(true);

        GestioneEventi();
        controllaServiziGiaPrenotati();

    }
    private void controllaServiziGiaPrenotati() {
        try {
            // Recuperiamo l'utente loggato e facciamo il cast a MembroVip
            MembroVip membroVip = (MembroVip) controller.getUtenteLoggato();

            // Verifichiamo che la sua lista di servizi prenotati esista e non sia vuota
            if (membroVip.getServiziPrenotati() != null) {

                // Scorriamo tutti i servizi che ha già acquistato
                for (ServizioWellness servizio : membroVip.getServiziPrenotati()) {

                    // Blocchiamo le caselle in base al nome del servizio che troviamo
                    if (servizio.getNomeServizio().equalsIgnoreCase("Sauna")) {
                        saunaCheckBox.setSelected(true);
                        saunaCheckBox.setEnabled(false);
                    }
                    else if (servizio.getNomeServizio().equalsIgnoreCase("Fisioterapia")) {
                        fisioterapiaCheckBox.setSelected(true);
                        fisioterapiaCheckBox.setEnabled(false);
                    }
                    else if (servizio.getNomeServizio().equalsIgnoreCase("Nutrizionista")) {
                        nutrizionistaCheckBox.setSelected(true);
                        nutrizionistaCheckBox.setEnabled(false);
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


                if (saunaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("SAUNA", "Sauna", true, null));
                }
                if (fisioterapiaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("FISIO", "Fisioterapia", true, null));
                }
                if (nutrizionistaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("NUTRI", "Nutrizionista", true, null));
                }


                if (serviziSelezionati.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Seleziona almeno un servizio prima di confermare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {

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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Si è verificato un errore durante la prenotazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
