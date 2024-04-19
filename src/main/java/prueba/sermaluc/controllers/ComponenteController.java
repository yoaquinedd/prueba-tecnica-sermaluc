package prueba.sermaluc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prueba.sermaluc.models.Componente;
import prueba.sermaluc.repositories.ComponenteRepository;

import java.util.List;

@RestController
public class ComponenteController {

    @Autowired
    private ComponenteRepository componenteRepository;


    @GetMapping("/componente")
    public List<Componente> getAllComponents(@RequestParam Long acoId){

        return componenteRepository.findByAcoIdAsociacionComuna(acoId);
    }
}
