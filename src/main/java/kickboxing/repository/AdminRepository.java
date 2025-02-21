package kickboxing.repository;

import kickboxing.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    Admin findByEmail(String email);

    @Modifying
    @Query("UPDATE Admin u SET u.tokenRecuperacao = :token, u.tokenExpiracao = :tokenExpiracao WHERE u.email = :email")
    void salvarTokenRecuperacao(
            @Param("email") String email,
            @Param("token") String token,
            @Param("tokenExpiracao") LocalDateTime tokenExpiracao
    );

    @Query("SELECT u FROM Admin u WHERE u.tokenRecuperacao = :token AND u.tokenExpiracao > :dataAtual")
    Admin findByTokenRecuperacao(
            @Param("token") String token,
            @Param("dataAtual") LocalDateTime dataAtual
    );
}