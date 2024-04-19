package prueba.sermaluc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prueba.sermaluc.models.Componente;

import java.util.List;

public interface ComponenteRepository extends JpaRepository<Componente, Long>{


    @Query("SELECT c FROM Componente c WHERE c.acoIdAsociacionComuna = :acoId")
    List<Componente> findByAcoIdAsociacionComuna(@Param("acoId") Long acoId);
}
