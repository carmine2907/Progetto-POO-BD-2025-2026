package gui;
import controller.Exception.*;
import model.Corso;
import model.Iscritto;
import model.Pagamento;
import model.SchedaAllenamento;
import controller.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IscrittoGUI extends JFrame {

    // Ricordati di assegnare "mainPanel" come nome del pannello principale nell'editor visivo di IntelliJ (file .form)
    private JPanel mainPanel;

    // Componenti grafici dichiarati
    private JButton JVisualizzaScheda;
    private JTextArea JtextVisualizzaS;
    private JComboBox<Corso> JSelezionaCorso;
    private JButton JPrenota;
    private JButton JPaga;
    private JButton JAreaWellness;
    private JLabel JLabelSeleziona;

    private Controller controller;


    public IscrittoGUI(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);

        // Imposta il titolo includendo l'username dell'utente loggato
        if (controller.getUtenteLoggato() != null) {
            setTitle("Area Iscritto - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("Area Iscritto");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        inizializzaEventi();
        popolaTendinaCorsi();

        // Blocca la finestra all'avvio finché non viene effettuato il pagamento
        bloccaFunzionalita();
    }

    private void bloccaFunzionalita() {
        // Disabilita tutti i bottoni e i menu
        JVisualizzaScheda.setEnabled(false);
        JSelezionaCorso.setEnabled(false);
        JPrenota.setEnabled(false);
        JAreaWellness.setEnabled(false);

        // Lascia attivo solo il pagamento
        JPaga.setEnabled(true);
    }

    private void sbloccaFunzionalita() {
        // Riattiva i componenti dopo il pagamento
        JVisualizzaScheda.setEnabled(true);
        JSelezionaCorso.setEnabled(true);
        JPrenota.setEnabled(true);
        JAreaWellness.setEnabled(true);

        JPaga.setEnabled(true);
    }

    private void popolaTendinaCorsi() {
        JSelezionaCorso.removeAllItems();

        List<Corso> corsiDisponibili = controller.ottieniTuttiICorsi();

        if (corsiDisponibili != null) {
            for (Corso corso : corsiDisponibili) {
                JSelezionaCorso.addItem(corso);
            }
        }
    }

    private void inizializzaEventi() {


        JVisualizzaScheda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iscritto utenteLoggato = (Iscritto) controller.getUtenteLoggato();

                // 1. Controlliamo se l'utente in memoria ha un ID valido
                System.out.println("DEBUG GUI: Ricerca scheda per ID Utente: '" + utenteLoggato.getId_utente() + "'");

                SchedaAllenamento scheda = controller.visualizzaSchedaPersonale(utenteLoggato);

                if (scheda != null) {
                    // 2. Controlliamo se il DAO ha estratto la descrizione
                    System.out.println("DEBUG GUI: Scheda trovata! Descrizione estratta: '" + scheda.getDescrizione() + "'");
                    JtextVisualizzaS.setText(scheda.getDescrizione());
                } else {
                    // 3. Confermiamo che il DAO ha restituito il vuoto
                    System.out.println("DEBUG GUI: ERRORE - Il metodo del DAO ha restituito null.");
                    JtextVisualizzaS.setText("Nessuna scheda attualmente assegnata.");
                }
            }
        });


        JPaga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPagamento = "PAG_" + System.currentTimeMillis();

                    Pagamento nuovoPagamento =controller.pagaAbbonamento(idPagamento);
                    String stampaRicevuta= controller.StampaRicevuta(nuovoPagamento);
                    JOptionPane.showMessageDialog(mainPanel,stampaRicevuta,"Ricevuta", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(mainPanel, "Le funzionalità sono state sbloccate.");
                    sbloccaFunzionalita();

                } catch (PagamentoGiaEffettuatoException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Errore:", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPrenota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Corso corsoSelezionato = (Corso) JSelezionaCorso.getSelectedItem();

                if (corsoSelezionato != null) {
                    try {
                        Iscritto utenteLoggato = (Iscritto) controller.getUtenteLoggato();

                        // Richiamiamo il controller. Se qualcosa va storto, salterà direttamente ai "catch"
                        controller.prenotaCorso(utenteLoggato, corsoSelezionato);
                        JOptionPane.showMessageDialog(mainPanel, "Iscrizione al corso confermata con successo!");

                    } catch (CorsoAlCompletoException ex) {
                        // Catturiamo l'eccezione del corso pieno
                        JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Posti Esauriti", JOptionPane.WARNING_MESSAGE);

                    } catch (CorsoGiaPrenotatoException ex) {
                        // Catturiamo l'eccezione della prenotazione duplicata
                        JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Prenotazione Duplicata", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Seleziona un corso dalla lista prima di prenotare.");
                }
            }
        });

        JAreaWellness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Chiediamo al controller se l'utente ha i permessi
                    boolean accessoConsentito = controller.AccessoAreaWellness();

                    // 2. Se non viene lanciata nessuna eccezione, l'accesso è consentito
                    if (accessoConsentito) {
                        JOptionPane.showMessageDialog(mainPanel, "Verifica completata. Benvenuto nell'Area Wellness VIP!", "Accesso Consentito", JOptionPane.INFORMATION_MESSAGE);

                        AreaWellnessGUI areaWellnessGUI = new AreaWellnessGUI(controller,IscrittoGUI.this);
                        areaWellnessGUI.setVisible(true);
                        setVisible(false);

                    }
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Errore!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
