package kickboxing.repository;

import kickboxing.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademiaRepository extends JpaRepository<Academia, Long> {
}