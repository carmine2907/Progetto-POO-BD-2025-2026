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
    }

    private void GestioneEventi() {


        JConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Creo la lista degli oggetti ServizioWellness in base alle caselle spuntate
                List<ServizioWellness> serviziSelezionati = new ArrayList<>();

                // Sostituisci gli ID fittizi ("ID_SAUNA", ecc.) con quelli reali del tuo database PostgreSQL
                if (saunaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("ID_SAUNA", "Sauna", true, null));
                }
                if (fisioterapiaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("ID_FISIO", "Fisioterapia", true, null));
                }
                if (nutrizionistaCheckBox.isSelected()) {
                    serviziSelezionati.add(new ServizioWellness("ID_NUTRI", "Nutrizionista", true, null));
                }

                // 2. Controllo che almeno un servizio sia stato selezionato
                if (serviziSelezionati.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Seleziona almeno un servizio prima di confermare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // 3. Eseguo il cast dell'utente loggato a MembroVip (il blocco di accesso l'abbiamo già fatto nell'IscrittoGUI)
                    MembroVip membroVip = (MembroVip) controller.getUtenteLoggato();

                    // 4. Richiamo il metodo del controller per l'aggiornamento sul database
                    boolean successo = controller.prenotaServizi(membroVip, serviziSelezionati);

                    if (successo) {
                        JOptionPane.showMessageDialog(mainPanel, "Servizi prenotati con successo!", "Conferma", JOptionPane.INFORMATION_MESSAGE);

                        if (saunaCheckBox.isSelected()) {
                            saunaCheckBox.setEnabled(false);
                            saunaCheckBox.setSelected(false); // Opzionale: rimuove visivamente la spunta
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
