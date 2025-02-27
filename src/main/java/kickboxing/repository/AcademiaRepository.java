package kickboxing.repository;

import kickboxing.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcademiaRepository extends JpaRepository<Academia, Long> {
    @Query("SELECT DISTINCT a.cidadeAcademia FROM Academia a ORDER BY a.cidadeAcademia ASC")
    List<String> findDistinctCidades();
}