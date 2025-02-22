package kickboxing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEvento;

    @Column(nullable = false)
    private String nomeEvento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoEvento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataEvento;

    @Column(nullable = false, columnDefinition = "TIME(0)")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaEvento;

    @Column(nullable = false)
    private String imagemEvento;

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public LocalTime getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(LocalTime horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getImagemEvento() {
        return imagemEvento;
    }

    public void setImagemEvento(String imagemEvento) {
        this.imagemEvento = imagemEvento;
    }
}