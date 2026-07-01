package model;
import java.util.ArrayList;


public class Squadra  {

    private int idSquadra;
    private String nome;
    private String categoria;
    private int maxGiocatori;

    private ArrayList<Atleta> atleti;

    public Squadra(int idSquadra, String nome, String categoria, int maxGiocatori)
    {

        this.idSquadra = idSquadra;
        this.nome = nome;
        this.categoria = categoria;
        this.maxGiocatori = maxGiocatori;
        atleti = new ArrayList<>();
    }

    public int getIdSquadra() {return idSquadra;}

    public void setIdSquadra(int idSquadra) {this.idSquadra = idSquadra;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCategoria() {return categoria;}

    public void setCategoria(String categoria) {this.categoria = categoria;}

    public boolean addAtleta(Atleta atleta)
    {

        if (atleti.size() < maxGiocatori)
        {
            atleti.add(atleta);
            return true;
        }

        return false;
    }

    public boolean removeAtleta(Atleta atleta) {return atleti.remove(atleta);}

    public boolean isCompleta() {return atleti.size() >= maxGiocatori;}

    public int getNumeroGiocatori() {return atleti.size();}

    public ArrayList<Atleta> getAtleti() { return atleti; }

    @Override
    public String toString() {return "Squadra {" +" " + nome  +" "+ '}';}
}