package prueba.sermaluc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prueba.sermaluc.models.Registro;
import prueba.sermaluc.repositories.ComponenteRepository;
import prueba.sermaluc.repositories.RegistroRepository;
import prueba.sermaluc.services.FormulaService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

public class RegistroController {

    @Autowired
    private FormulaService formulaService;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private ComponenteRepository componenteRepository;


    @GetMapping("/registros/formulas")
    public Map<String, Double> obtenerResultadosFormulas(@RequestParam Long acoIdAsociacionComuna) {
        return formulaService.evaluateFormulas(acoIdAsociacionComuna);
    }

   /* @GetMapping("/registros/formula")
    public List<String> obtenerFormulaDescompuesta(@RequestParam Long acoIdAsociacionComuna) {
        List<Registro> registros = registroRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);
        return registros.stream()
                .map(Registro::getAcaForFormulaDescompuesta)
                .collect(Collectors.toList());
    }*/


}