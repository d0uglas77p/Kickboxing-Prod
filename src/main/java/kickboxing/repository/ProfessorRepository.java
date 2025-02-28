package kickboxing.repository;

import kickboxing.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByCidadeProfessor(String cidadeProfessor);
}