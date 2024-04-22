package prueba.sermaluc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prueba.sermaluc.models.Registro;
import prueba.sermaluc.repositories.ComponenteRepository;
import prueba.sermaluc.repositories.RegistroRepository;
import prueba.sermaluc.services.FormulaService;

import java.io.File;
import java.net.MalformedURLException;
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

    @GetMapping("/generar-resultados-por-id")
    public ResponseEntity<String> obtenerResultadosFormulas(@RequestParam String acoIdAsociacionComuna) throws MalformedURLException {
        // Validar que el parámetro acoIdAsociacionComuna sea un Long válido
        Long acoId;
        try {
            acoId = Long.parseLong(acoIdAsociacionComuna);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El parámetro acoIdAsociacionComuna debe ser un número entero válido");
        }

        Map<String, Double> formulaResults = formulaService.evaluateFormulas(acoId);

        String userHome = System.getProperty("user.home");
        String outputFilePath = userHome + "/Downloads/resultados.xlsx";

        // Generar el archivo Excel
        formulaService.generateExcelFile(formulaResults, outputFilePath, acoId);

        // Configurar la respuesta con el archivo Excel
        return ResponseEntity.ok("Archivo creado correctamente");
    }


    @GetMapping("/generar-resultados")
    public ResponseEntity<String> generateExcel() {

        String userHome = System.getProperty("user.home");
        String outputFilePath = userHome + "/Downloads/resultados.xlsx";

        formulaService.generateExcelFileForAllAcoIds(outputFilePath);
        return ResponseEntity.ok("Archivo Excel generado correctamente");
    }


    /*@GetMapping("/registros/formulas")
    public Map<String, Double> obtenerResultadosFormulas(@RequestParam Long acoIdAsociacionComuna) {
        return formulaService.evaluateFormulas(acoIdAsociacionComuna);
    }*/

   /* @GetMapping("/registros/formula")
    public List<String> obtenerFormulaDescompuesta(@RequestParam Long acoIdAsociacionComuna) {
        List<Registro> registros = registroRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);
        return registros.stream()
                .map(Registro::getAcaForFormulaDescompuesta)
                .collect(Collectors.toList());
    }*/


}
