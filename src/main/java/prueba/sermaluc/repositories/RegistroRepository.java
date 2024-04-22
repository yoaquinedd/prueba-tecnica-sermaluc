package prueba.sermaluc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prueba.sermaluc.models.Registro;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByAcoIdAsociacionComuna(Long acoIdAsociacionComuna);

    @Query("SELECT DISTINCT r.acoIdAsociacionComuna FROM Registro r")

    List<Long> findDistinctAcoIdAsociacionComuna();

}
