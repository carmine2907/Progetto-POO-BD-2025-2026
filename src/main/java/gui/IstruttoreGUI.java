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

/**
 * The type Istruttore gui.
 */
public class IstruttoreGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField JtextNome;
    private JTextField JtextCapienza;
    private JButton JCreaCorso;
    private JComboBox<Iscritto> JcomboBoxIscritti;
    private JTextArea textArea1;
    private JButton JAssegna;
    private JButton JLogout;
    private JLabel JNomeCorso;
    private JLabel JCapienza;
    private JLabel JSeleziona;

    private Controller controller;

    /**
     * Instantiates a new Istruttore gui.
     *
     * @param controller the controller
     */
    public IstruttoreGUI(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);

        if (controller.getUtenteLoggato() != null) {
            setTitle("Area Istruttore - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("Area Istruttore");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        inizializzaEventi();
        popolaTendinaIscritti();
    }

    private void popolaTendinaIscritti() {
        JcomboBoxIscritti.removeAllItems();
        List<Iscritto> tuttiGliIscritti = controller.ottieniTuttiGliIscritti();

        if (tuttiGliIscritti != null) {
            for (Iscritto iscritto : tuttiGliIscritti) {
                JcomboBoxIscritti.addItem(iscritto);
            }
        }
    }

    private void inizializzaEventi() {


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

                    int capienza = Integer.parseInt(capienzaStr);

                    if (capienza <= 0) {
                        JOptionPane.showMessageDialog(mainPanel, "La capienza del corso deve essere un numero maggiore di zero.", "Valore non valido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String idCorso = "C_" + System.currentTimeMillis();
                    Istruttore istruttoreLoggato = (Istruttore) controller.getUtenteLoggato();

                    controller.CreaCorso(idCorso, nomeCorso, capienza, istruttoreLoggato, new ArrayList<>());

                    JOptionPane.showMessageDialog(mainPanel, "Corso creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);


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


        JAssegna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iscritto iscrittoSelezionato = (Iscritto) JcomboBoxIscritti.getSelectedItem();
                String descrizioneScheda = textArea1.getText().trim();

                if (descrizioneScheda.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Inserisci la descrizione/esercizi della scheda.", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    String idScheda = "SCH_" + System.currentTimeMillis();
                    Istruttore istruttoreLoggato = (Istruttore) controller.getUtenteLoggato();


                    controller.creaeAssegnaScheda(idScheda, descrizioneScheda, istruttoreLoggato, iscrittoSelezionato, iscrittoSelezionato.getId_utente());

                    JOptionPane.showMessageDialog(mainPanel, "Scheda assegnata con successo all'iscritto " + iscrittoSelezionato.getUsername() + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    textArea1.setText("");

                } catch (SchedaGiaEsistenteException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Scheda Esistente", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Errore durante l'assegnazione della scheda: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int scelta = JOptionPane.showConfirmDialog(mainPanel, "Sei sicuro di voler effettuare il logout?", "Conferma Logout", JOptionPane.YES_NO_OPTION);

                if (scelta == JOptionPane.YES_OPTION) {
                  controller.logout();
                    dispose();
                    new Home();
                }
            }
        });
    }
}