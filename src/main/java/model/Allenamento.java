package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Allenamento.
 */
public class Allenamento {
    private int idAllenamento;
    private LocalDate dataAllenamento;
    private LocalTime orario;

    private Campo campo;

    /**
     * Gets id allenamento.
     *
     * @return the id allenamento
     */
    public int getIdAllenamento() { return idAllenamento; }

    /**
     * Sets id allenamento.
     *
     * @param idAllenamento the id allenamento
     */
    public void setIdAllenamento(int idAllenamento) { this.idAllenamento = idAllenamento; }

    /**
     * Gets data allenamento.
     *
     * @return the data allenamento
     */
    public LocalDate getDataAllenamento() { return dataAllenamento; }

    /**
     * Sets data allenamento.
     *
     * @param dataAllenamento the data allenamento
     */
    public void setDataAllenamento(LocalDate dataAllenamento) { this.dataAllenamento = dataAllenamento; }

    /**
     * Gets orario.
     *
     * @return the orario
     */
    public LocalTime getOrario() { return orario; }

    /**
     * Sets orario.
     *
     * @param orario the orario
     */
    public void setOrario(LocalTime orario) { this.orario = orario; }

    /**
     * Gets campo.
     *
     * @return the campo
     */
    public Campo getCampo() { return campo; }

    /**
     * Sets campo.
     *
     * @param campo the campo
     */
    public void setCampo(Campo campo) { this.campo = campo; }

    /**
     * Instantiates a new Allenamento.
     *
     * @param idAllenamento   the id allenamento
     * @param dataAllenamento the data allenamento
     * @param orario          the orario
     * @param campo           the campo
     */
    public Allenamento(int idAllenamento, LocalDate dataAllenamento, LocalTime orario, Campo campo)
    {
        this.idAllenamento = idAllenamento;
        this.dataAllenamento = dataAllenamento;
        this.orario = orario;
        this.campo = campo;
    }

    /**
     * Registra presenza int.
     *
     * @param a the a
     * @return the int
     */
    public int registraPresenza(Atleta a) {
        return 1;
    }

}