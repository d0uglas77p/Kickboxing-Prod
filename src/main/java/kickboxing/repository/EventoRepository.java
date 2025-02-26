package kickboxing.repository;

import kickboxing.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query("SELECT e FROM Evento e WHERE MONTH(e.dataEvento) = :mes ORDER BY e.dataEvento ASC")
    List<Evento> findByMes(@Param("mes") int mes);
}