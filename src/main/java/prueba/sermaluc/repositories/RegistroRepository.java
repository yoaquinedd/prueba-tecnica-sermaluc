package prueba.sermaluc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.sermaluc.models.Registro;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByAcoIdAsociacionComuna(Long acoIdAsociacionComuna);

}
