package model;

public class Campo
{
    public Campo(int idCampo, String nome, String tipo)
    {
        this.idCampo = idCampo;
        Nome = nome;
        Tipo = tipo;
    }

    public int getIdCampo() {return idCampo;}
    public void setIdCampo(int idCampo) {this.idCampo = idCampo;}

    public String getNome() {return Nome;}
    public void setNome(String nome) {Nome = nome;}

    public String getTipo() {return Tipo;}
    public void setTipo(String tipo) {Tipo = tipo;}

    public boolean IsDisponibile(boolean Disponibilita)
    {
        if(Disponibilita==true) {
            System.out.println("Il Campo è libero");
            return true;
        }
        System.out.println("Il Campo non è libero");
        return false;
    }
    private int idCampo;
    private String Nome;
    private String Tipo;
    boolean Disponibilita = false;
}
