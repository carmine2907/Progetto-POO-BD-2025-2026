package model;

public class ServizioWellness {
    private String id_ServizioWellness;
    private String nomeServizio;
    private Boolean disponibile;

    private MembroVip membroPrenotante;



    public ServizioWellness(String id_ServizioWellness, String nomeServizio, Boolean disponibile, MembroVip membroPrenotante) {
        this.id_ServizioWellness = id_ServizioWellness;
        this.nomeServizio = nomeServizio;
        this.disponibile = disponibile;
        this.membroPrenotante = membroPrenotante;
    }

    public String getId_ServizioWellness() { return id_ServizioWellness; }
    public void setId_ServizioWellness(String id_ServizioWellness) { this.id_ServizioWellness = id_ServizioWellness; }

    public String getNomeServizio() { return nomeServizio; }
    public void setNomeServizio(String nomeServizio) { this.nomeServizio = nomeServizio; }

    public Boolean getDisponibile() { return disponibile; }
    public void setDisponibile(Boolean disponibile) { this.disponibile = disponibile; }

    public MembroVip getMembroPrenotante() { return membroPrenotante; }
    public void setMembroPrenotante(MembroVip membroPrenotante) { this.membroPrenotante = membroPrenotante; }
}