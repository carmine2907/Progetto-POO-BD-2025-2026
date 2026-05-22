package controller;

import model.Utente;

public class AutenticazioneController {
    private static Utente utenteLoggato; // Mantiene la sessione attiva in memoria

    public AutenticazioneController() {}

    /**
     * Esegue il login dell'utente previa verifica delle credenziali.
     * (In futuro interagirà con il rispettivo DAO per la verifica sul DB)
     */
    public boolean login(String username, String password) throws Exception {
        // Validazione dell'input dell'utente (richiesta dalle specifiche)
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username e password non possono essere vuoti.");
        }

        // NOTA: Qui inseriremo la chiamata al DAO. Per ora simuliamo un controllo basilare.
        if ("admin".equals(username) && "password".equals(password)) {
            // utenteLoggato = utenteDAO.findByLogin(username);
            return true;
        }

        return false;
    }

    public void logout() {
        utenteLoggato = null;
    }

    public static Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public static boolean isUtenteAutenticato() {
        return utenteLoggato != null;
    }
}