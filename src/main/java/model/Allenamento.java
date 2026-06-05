package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Allenamento {
    private int idAllenamento;
    private LocalDate dataAllenamento;
    private LocalTime orario;

    private Campo campo;

    public int getIdAllenamento() { return idAllenamento; }

    public void setIdAllenamento(int idAllenamento) { this.idAllenamento = idAllenamento; }

    public LocalDate getDataAllenamento() { return dataAllenamento; }

    public void setDataAllenamento(LocalDate dataAllenamento) { this.dataAllenamento = dataAllenamento; }

    public LocalTime getOrario() { return orario; }

    public void setOrario(LocalTime orario) { this.orario = orario; }

    public Campo getCampo() { return campo; }

    public void setCampo(Campo campo) { this.campo = campo; }

    public Allenamento(int idAllenamento, LocalDate dataAllenamento, LocalTime orario, Campo campo)
    {
        this.idAllenamento = idAllenamento;
        this.dataAllenamento = dataAllenamento;
        this.orario = orario;
        this.campo = campo;
    }

    public int registraPresenza(Atleta a) {
        return 1;
    }

}