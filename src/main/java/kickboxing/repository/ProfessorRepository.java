package kickboxing.repository;

import kickboxing.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("SELECT p FROM Professor p WHERE p.cidadeProfessor = :cidade")
    List<Professor> findByCidadeProfessor(@Param("cidade") String cidade);
}