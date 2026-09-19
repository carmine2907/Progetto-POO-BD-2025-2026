package model;

import java.util.List;

public class Istruttore extends Utente {
    private List<Corso> corsiGestiti;
    private List<SchedaAllenamento> schedeCreate;



    public Istruttore(String id_Utente, String nome, String cognome, String username, String password, List<Corso> corsiGestiti, List<SchedaAllenamento> schedeCreate) {
        super(id_Utente, nome, cognome, username, password);
        this.corsiGestiti = corsiGestiti;
        this.schedeCreate = schedeCreate;
    }

    public List<Corso> getCorsiGestiti() { return corsiGestiti; }
    public void setCorsiGestiti(List<Corso> corsiGestiti) { this.corsiGestiti = corsiGestiti; }

    public List<SchedaAllenamento> getSchedeCreate() { return schedeCreate; }
    public void setSchedeCreate(List<SchedaAllenamento> schedeCreate) { this.schedeCreate = schedeCreate; }
}