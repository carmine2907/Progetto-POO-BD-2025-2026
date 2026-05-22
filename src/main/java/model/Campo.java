package model;

public class Campo {
    private int idCampo;
    private String nome;
    private String tipo;
    private boolean disponibile;

    public Campo(int idCampo, String nome, String tipo, boolean disponibile) {
        this.idCampo = idCampo;
        this.nome = nome;
        this.tipo = tipo;
        this.disponibile = disponibile;
    }

    public int getIdCampo() { return idCampo; }
    public void setIdCampo(int idCampo) { this.idCampo = idCampo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }
}