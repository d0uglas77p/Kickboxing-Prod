package kickboxing.repository;

import kickboxing.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("SELECT a FROM Aluno a WHERE a.cidadeAluno = :cidade")
    List<Aluno> findByCidadeAluno(@Param("cidade") String cidade);
}