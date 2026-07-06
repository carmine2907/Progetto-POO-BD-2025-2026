package model;

/**
 * The type Campo.
 */
public class Campo {
    private int idCampo;
    private String nome;
    private String tipo;
    private boolean disponibile;

    /**
     * Instantiates a new Campo.
     *
     * @param idCampo     the id campo
     * @param nome        the nome
     * @param tipo        the tipo
     * @param disponibile the disponibile
     */
    public Campo(int idCampo, String nome, String tipo, boolean disponibile) {
        this.idCampo = idCampo;
        this.nome = nome;
        this.tipo = tipo;
        this.disponibile = disponibile;
    }

    /**
     * Gets id campo.
     *
     * @return the id campo
     */
    public int getIdCampo() { return idCampo; }

    /**
     * Sets id campo.
     *
     * @param idCampo the id campo
     */
    public void setIdCampo(int idCampo) {
        this.idCampo = idCampo;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets tipo.
     *
     * @param tipo the tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Is disponibile boolean.
     *
     * @return the boolean
     */
    public boolean isDisponibile() {
        return disponibile;
    }

    /**
     * Sets disponibile.
     *
     * @param disponibile the disponibile
     */
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }

    @Override
    public String toString() {
        return "Campo {"+ " "+ nome  + " "+ '}';}
}