package gui;

import controller.Exception.PagamentoNonValidoException;
import controller.SistemaController;
import model.Atleta;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeAtletaGUI {
    // Componenti grafici (da collegare nel file .form di IntelliJ)
    private JPanel panelMain;
    private JLabel lblBenvenuto;
    private JButton btnVisualizzaPartite;
    private JButton btnStatoPagamenti;
    private JButton btnLogout;

    private JFrame frame;
    private SistemaController controller;
    private JPanel mainPanel;
    private JButton btnVisualizzaPartita;

    public HomeAtletaGUI(SistemaController controller) {
        this.controller = controller;

        // Inizializzazione del JFrame
        frame = new JFrame("Area Riservata - Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        if (SistemaController.isUtenteAutenticato()) {
            lblBenvenuto.setText("Benvenuto Atleta: " + SistemaController.getUtenteLoggato().getNome());
        }

        // --- AZIONE: STATO PAGAMENTI ---
        btnStatoPagamenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Recuperiamo l'utente attualmente connesso
                    Utente utenteConnesso = SistemaController.getUtenteLoggato();

                    // 2. Verifichiamo che sia effettivamente un Atleta
                    if (utenteConnesso instanceof Atleta) {
                        Atleta atleta = (Atleta) utenteConnesso;

                        // 3. Controllo dello stato del pagamento
                        if (atleta.isPagamentoInRegola()) {
                            // Caso di successo
                            JOptionPane.showMessageDialog(frame,
                                    "Il tuo pagamento è in regola.\nSei regolarmente tesserato per le attività!",
                                    "Stato Pagamento",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Caso di fallimento: Lanciamo la tua eccezione personalizzata
                            throw new PagamentoNonValidoException("La tua quota di iscrizione risulta 'IN ATTESA' o 'RIFIUTATA'. Contatta la segreteria.");
                        }
                    } else {
                        // Sicurezza aggiuntiva nel caso il bottone venga premuto da un ruolo errato
                        JOptionPane.showMessageDialog(frame,
                                "Questa funzione è riservata esclusivamente agli atleti.",
                                "Accesso Negato",
                                JOptionPane.WARNING_MESSAGE);
                    }

                } catch (PagamentoNonValidoException ex) {
                    // 4. Catturiamo la tua eccezione e mostriamo l'errore a schermo
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Pagamento Irregolare",
                            JOptionPane.ERROR_MESSAGE);

                } catch (Exception ex) {
                    // Gestione di eventuali altri errori imprevisti
                    JOptionPane.showMessageDialog(frame,
                            "Errore di sistema: " + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- AZIONE: VISUALIZZA PARTITE (nella HomeAtletaGUI) ---
        btnVisualizzaPartite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creiamo la nuova finestrella passando il controller
                VisualizzaPartiteGUI moduloPartite = new VisualizzaPartiteGUI(controller);
                // Mostriamo la finestrella in sovrimpressione
                moduloPartite.mostra();
            }
        });

        // --- AZIONE: LOGOUT ---
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Logout effettuato con successo.");

                LoginGUI login = new LoginGUI(controller, new Home(controller));
                login.mostra();
                System.exit(0);
            }
        });
    }

    public void mostra() {
        frame.setVisible(true);
    }
}
