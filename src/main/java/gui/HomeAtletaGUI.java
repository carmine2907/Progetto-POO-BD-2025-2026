package gui;

import controller.SistemaController;
import model.Atleta;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Home atleta gui.
 */
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

    /**
     * Instantiates a new Home atleta gui.
     *
     * @param controller the controller
     */
    public HomeAtletaGUI(SistemaController controller) {
        this.controller = controller;


        frame = new JFrame("Area Riservata - Atleta");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        if (SistemaController.isUtenteAutenticato()) {
            lblBenvenuto.setText("Benvenuto Atleta: " + SistemaController.getUtenteLoggato().getNome());
        }

        btnStatoPagamenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Utente utenteConnesso = SistemaController.getUtenteLoggato();

                    if (utenteConnesso instanceof Atleta) {
                        Atleta atleta = (Atleta) utenteConnesso;
                        int idAtleta = Integer.parseInt(atleta.getIdUtente());


                        String statoDB = controller.verificaStatoPagamento(idAtleta);


                        switch (statoDB) {
                            case "APPROVATO":
                                JOptionPane.showMessageDialog(frame,
                                        "Il tuo pagamento è in regola ('APPROVATO').\nSei regolarmente tesserato!",
                                        "Stato Pagamento", JOptionPane.INFORMATION_MESSAGE);
                                break;

                            case "IN_ATTESA":
                                JOptionPane.showMessageDialog(frame,
                                        "Il tuo pagamento risulta 'IN ATTESA' di verifica da parte della segreteria.",
                                        "Verifica in corso", JOptionPane.WARNING_MESSAGE);
                                break;

                            case "RIFIUTATO":
                                JOptionPane.showMessageDialog(frame,
                                        "Il tuo ultimo pagamento è stato 'RIFIUTATO'.\nContatta immediatamente la segreteria.",
                                        "Attenzione", JOptionPane.ERROR_MESSAGE);
                                break;

                            case "NESSUN_PAGAMENTO":
                                JOptionPane.showMessageDialog(frame,
                                        "Non risulta alcun pagamento registrato a tuo nome.",
                                        "Nessun dato", JOptionPane.WARNING_MESSAGE);
                                break;

                            default:
                                JOptionPane.showMessageDialog(frame,
                                        "Stato pagamento sconosciuto: " + statoDB,
                                        "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Errore durante il controllo: " + ex.getMessage(),
                            "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnVisualizzaPartite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                VisualizzaPartiteGUI moduloPartite = new VisualizzaPartiteGUI(controller);

                moduloPartite.mostra();
            }
        });


        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Logout effettuato con successo.");

                LoginGUI login = new LoginGUI(controller, new Home(controller));
                login.mostra();

            }
        });
    }

    /**
     * Mostra.
     */
    public void mostra() {
        frame.setVisible(true);
    }
}
