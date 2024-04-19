package prueba.sermaluc.services;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.sermaluc.models.Componente;
import prueba.sermaluc.models.Registro;
import prueba.sermaluc.repositories.ComponenteRepository;
import prueba.sermaluc.repositories.RegistroRepository;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormulaService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private ComponenteRepository componenteRepository;

    public Map<String, Double> evaluateFormulas(Long acoIdAsociacionComuna) {
        List<Registro> registros = registroRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);
        List<Componente> componentes = componenteRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);

        Map<String, Double> componentValues = componentes.stream()
                .collect(Collectors.toMap(Componente::getComponente, Componente::getValor));

        Map<String, Double> formulaResults = new HashMap<>();
        for (Registro registro : registros) {
            String formula = registro.getAcaForFormulaDescompuesta();
            double resultado = evaluateFormula(formula, componentValues);
            formulaResults.put(formula, resultado);
        }

        return formulaResults;
    }

    private double evaluateFormula(String formula, Map<String, Double> componentValues) {
        ExpressionBuilder builder = new ExpressionBuilder(formula);

        // Crear un objeto Expression
        Expression expression = builder.build();

        // Asignar los valores de los componentes a las variables de la expresión
        for (Map.Entry<String, Double> entry : componentValues.entrySet()) {
            expression.setVariable(entry.getKey(), entry.getValue());
        }

        return expression.evaluate();
    }
}