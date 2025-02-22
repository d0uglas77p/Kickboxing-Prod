package kickboxing.repository;

import kickboxing.model.Patrocinador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatrocinadorRepository extends JpaRepository<Patrocinador, Long> {
}