package gui;

import controller.SistemaController;
import model.Campo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PianificaPartitaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JTextField txtIdPartita;
    private JTextField txtData;      // Formato atteso: GG/MM/AAAA
    private JTextField txtOra;       // Formato atteso: HH:mm
    private JComboBox<Campo> cmbCampi;
    private JButton btnConferma;
    private JButton btnAnnulla;
    private JLabel lblPartita;
    private JLabel lblData;
    private JLabel lblCampo;
    private JLabel lblOra;

    private JFrame frame;
    private SistemaController controller;

    public PianificaPartitaGUI(SistemaController controller) {
        this.controller = controller;

        frame = new JFrame("Modulo Pianificazione Partita");
        frame.setContentPane(panelMain);
        // Usiamo DISPOSE_ON_CLOSE per chiudere solo questa finestrella e non tutto il programma
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        // 1. Popoliamo il menu a tendina dei campi
        caricaCampiNelleTendine();

        btnConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Recupero e validazione ID Partita
                    int idPartita = Integer.parseInt(txtIdPartita.getText().trim());

                    // 2. Recupero e validazione Data
                    DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate dataPartita = LocalDate.parse(txtData.getText().trim(), formatData);

                    // 3. Recupero e validazione Ora
                    DateTimeFormatter formatOra = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime oraPartita = LocalTime.parse(txtOra.getText().trim(), formatOra);

                    // 4. Recupero Campo selezionato
                    Campo campoSelezionato = (Campo) cmbCampi.getSelectedItem();

                    if (campoSelezionato == null) {
                        JOptionPane.showMessageDialog(frame, "Selezionare un Campo valido.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 5. Chiamata al metodo del Controller
                    controller.pianificaPartita(idPartita, dataPartita, oraPartita, campoSelezionato);

                    JOptionPane.showMessageDialog(frame, "Partita pianificata con successo nel campo: " + campoSelezionato.getNome(), "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Chiudiamo la finestrella
                    frame.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "L'ID Partita deve essere un numero intero valido.", "Errore di Formato", JOptionPane.WARNING_MESSAGE);

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Formato Data o Ora non valido.\nUsa GG/MM/AAAA per la data e HH:mm per l'ora.", "Errore di Formato", JOptionPane.WARNING_MESSAGE);

                } catch (IllegalStateException ex) {
                    // Cattura l'eccezione lanciata se il campo non Ã¨ disponibile a livello logico nel Java
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Campo non disponibile", JOptionPane.WARNING_MESSAGE);

                } catch (Exception ex) {
                    // 6. MODIFICA: Catturiamo l'errore del Trigger dal Database e lo mostriamo in modo chiaro!
                    JOptionPane.showMessageDialog(frame,
                            "Impossibile pianificare l'evento:\n" + ex.getMessage(),
                            "Errore di Pianificazione (Database)",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    /**
     * Metodo di supporto per riempire la ComboBox dei Campi.
     * Assicurati che la classe Campo abbia un buon metodo toString() implementato!
     */
    private void caricaCampiNelleTendine() {

        List<Campo> listaCampi = controller.getTuttiICampi();
        for (Campo c : listaCampi) {
            cmbCampi.addItem(c);
        }

    }

    public void mostra() {
        frame.setVisible(true);
    }
}