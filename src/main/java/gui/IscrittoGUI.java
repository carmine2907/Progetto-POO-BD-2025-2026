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

/**
 * The type Iscritto gui.
 */
public class IscrittoGUI extends JFrame {
    private JPanel mainPanel;


    private JButton JVisualizzaScheda;
    private JTextArea JtextVisualizzaS;
    private JComboBox<Corso> JSelezionaCorso;
    private JButton JPrenota;
    private JButton JPaga;
    private JButton JAreaWellness;
    private JLabel JLabelSeleziona;

    private Controller controller;


    /**
     * Instantiates a new Iscritto gui.
     *
     * @param controller the controller
     */
    public IscrittoGUI(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);


        if (controller.getUtenteLoggato() != null) {
            setTitle("Area Iscritto - " + controller.getUtenteLoggato().getUsername());
        } else {
            setTitle("Area Iscritto");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);

        inizializzaEventi();
        popolaTendinaCorsi();


        bloccaFunzionalita();
    }

    private void bloccaFunzionalita() {
        JVisualizzaScheda.setEnabled(false);
        JSelezionaCorso.setEnabled(false);
        JPrenota.setEnabled(false);
        JAreaWellness.setEnabled(false);
        JPaga.setEnabled(true);
    }

    private void sbloccaFunzionalita() {
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

                SchedaAllenamento scheda = controller.visualizzaSchedaPersonale(utenteLoggato);

                if (scheda != null) {


                    JtextVisualizzaS.setText(scheda.getDescrizione());
                } else {

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


                        controller.prenotaCorso(utenteLoggato, corsoSelezionato);
                        JOptionPane.showMessageDialog(mainPanel, "Iscrizione al corso confermata con successo!");

                    } catch (CorsoAlCompletoException ex) {

                        JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Errore", JOptionPane.WARNING_MESSAGE);

                    } catch (CorsoGiaPrenotatoException ex) {

                        JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Prenotazione Duplicata", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        JAreaWellness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    boolean accessoConsentito = controller.AccessoAreaWellness();


                    if (accessoConsentito) {
                        JOptionPane.showMessageDialog(mainPanel, "Verifica completata. Benvenuto nell'Area Wellness VIP!", "Accesso Consentito", JOptionPane.INFORMATION_MESSAGE);

                        AreaWellnessGUI areaWellnessGUI = new AreaWellnessGUI(controller,IscrittoGUI.this);
                        areaWellnessGUI.setVisible(true);
                        setVisible(false);

                    }
                } catch (AccessoAreaWellnessException ex) {

                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Errore!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
