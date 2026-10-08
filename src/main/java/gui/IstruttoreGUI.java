package gui;

import controller.Controller;
import controller.Exception.CorsoGiaEsistenteException;
import controller.Exception.SchedaGiaEsistenteException;
import model.Iscritto;
import model.Istruttore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class IstruttoreGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField JtextNome;
    private JTextField JtextCapienza;
    private JButton JCreaCorso;
    private JComboBox<Iscritto> JcomboBoxIscritti; // Tipizzato con Iscritto
    private JTextArea textArea1;
    private JButton JAssegna;
    private JLabel JNomeCorso;
    private JLabel JCapienza;
    private JLabel JSeleziona;

    private Controller controller;

    public IstruttoreGUI(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);

        // Impostiamo il titolo con il nome dell'istruttore loggato
        if (controller.getUtenteLoggato() != null) {
            setTitle("Area Istruttore - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("Area Istruttore");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        inizializzaEventi();
        popolaTendinaIscritti();
    }

    private void popolaTendinaIscritti() {
        JcomboBoxIscritti.removeAllItems();
        // Questo metodo dovra essere aggiunto al Controller
        List<Iscritto> tuttiGliIscritti = controller.ottieniTuttiGliIscritti();

        if (tuttiGliIscritti != null) {
            for (Iscritto iscritto : tuttiGliIscritti) {
                JcomboBoxIscritti.addItem(iscritto);
            }
        }
    }

    private void inizializzaEventi() {

        // Azione per creare un nuovo corso
        JCreaCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCorso = JtextNome.getText().trim();
                String capienzaStr = JtextCapienza.getText().trim();

                if (nomeCorso.isEmpty() || capienzaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Compila tutti i campi per creare un corso.", "Campi vuoti", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    // Convertiamo la stringa della capienza in un numero intero
                    int capienza = Integer.parseInt(capienzaStr);

                    // Generiamo un ID univoco per il corso
                    String idCorso = "C_" + System.currentTimeMillis();
                    Istruttore istruttoreLoggato = (Istruttore) controller.getUtenteLoggato();

                    // Richiamiamo il controller per la creazione
                    controller.CreaCorso(idCorso, nomeCorso, capienza, istruttoreLoggato, new ArrayList<>());

                    JOptionPane.showMessageDialog(mainPanel, "Corso creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Puliamo i campi dopo la creazione
                    JtextNome.setText("");
                    JtextCapienza.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "La capienza deve essere un numero intero valido.", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                } catch (CorsoGiaEsistenteException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Corso Duplicato", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Errore durante la creazione del corso: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Azione per assegnare una scheda di allenamento
        JAssegna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iscritto iscrittoSelezionato = (Iscritto) JcomboBoxIscritti.getSelectedItem();
                String descrizioneScheda = textArea1.getText().trim();

                if (iscrittoSelezionato == null) {
                    JOptionPane.showMessageDialog(mainPanel, "Seleziona un iscritto dalla lista.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (descrizioneScheda.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Inserisci la descrizione/esercizi della scheda.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    String idScheda = "SCH_" + System.currentTimeMillis();
                    Istruttore istruttoreLoggato = (Istruttore) controller.getUtenteLoggato();

                    // Richiamiamo il metodo del controller per assegnare la scheda
                    controller.AssegnaScheda(idScheda, descrizioneScheda, istruttoreLoggato, iscrittoSelezionato, iscrittoSelezionato.getId_utente());

                    JOptionPane.showMessageDialog(mainPanel, "Scheda assegnata con successo all'iscritto " + iscrittoSelezionato.getUsername() + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    textArea1.setText("");

                } catch (SchedaGiaEsistenteException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Scheda Esistente", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Errore durante l'assegnazione della scheda: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}