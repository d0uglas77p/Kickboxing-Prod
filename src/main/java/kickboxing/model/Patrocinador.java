package kickboxing.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Patrocinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPatrocinador;

    @Column(nullable = false)
    private String linkPatrocinador;

    @Column(nullable = false)
    private String imagemPatrocinador;

    public long getIdPatrocinador() {
        return idPatrocinador;
    }

    public void setIdPatrocinador(long idPatrocinador) {
        this.idPatrocinador = idPatrocinador;
    }

    public String getLinkPatrocinador() {
        return linkPatrocinador;
    }

    public void setLinkPatrocinador(String linkPatrocinador) {
        this.linkPatrocinador = linkPatrocinador;
    }

    public String getImagemPatrocinador() {
        return imagemPatrocinador;
    }

    public void setImagemPatrocinador(String imagemPatrocinador) {
        this.imagemPatrocinador = imagemPatrocinador;
    }
}