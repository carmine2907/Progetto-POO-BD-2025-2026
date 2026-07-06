package model;
import java.util.ArrayList;


/**
 * The type Squadra.
 */
public class Squadra  {

    private int idSquadra;
    private String nome;
    private String categoria;
    private int maxGiocatori;

    private ArrayList<Atleta> atleti;

    /**
     * Instantiates a new Squadra.
     *
     * @param idSquadra    the id squadra
     * @param nome         the nome
     * @param categoria    the categoria
     * @param maxGiocatori the max giocatori
     */
    public Squadra(int idSquadra, String nome, String categoria, int maxGiocatori)
    {

        this.idSquadra = idSquadra;
        this.nome = nome;
        this.categoria = categoria;
        this.maxGiocatori = maxGiocatori;
        atleti = new ArrayList<>();
    }

    /**
     * Gets id squadra.
     *
     * @return the id squadra
     */
    public int getIdSquadra() {return idSquadra;}

    /**
     * Sets id squadra.
     *
     * @param idSquadra the id squadra
     */
    public void setIdSquadra(int idSquadra) {this.idSquadra = idSquadra;}

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {return nome;}

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {this.nome = nome;}

    /**
     * Gets categoria.
     *
     * @return the categoria
     */
    public String getCategoria() {return categoria;}

    /**
     * Sets categoria.
     *
     * @param categoria the categoria
     */
    public void setCategoria(String categoria) {this.categoria = categoria;}

    /**
     * Add atleta boolean.
     *
     * @param atleta the atleta
     * @return the boolean
     */
    public boolean addAtleta(Atleta atleta)
    {

        if (atleti.size() < maxGiocatori)
        {
            atleti.add(atleta);
            return true;
        }

        return false;
    }

    /**
     * Remove atleta boolean.
     *
     * @param atleta the atleta
     * @return the boolean
     */
    public boolean removeAtleta(Atleta atleta) {return atleti.remove(atleta);}

    /**
     * Is completa boolean.
     *
     * @return the boolean
     */
    public boolean isCompleta() {return atleti.size() >= maxGiocatori;}

    /**
     * Gets numero giocatori.
     *
     * @return the numero giocatori
     */
    public int getNumeroGiocatori() {return atleti.size();}

    /**
     * Gets atleti.
     *
     * @return the atleti
     */
    public ArrayList<Atleta> getAtleti() { return atleti; }

    @Override
    public String toString() {return "Squadra {" +" " + nome  +" "+ '}';}
}