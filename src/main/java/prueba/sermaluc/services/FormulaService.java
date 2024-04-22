package prueba.sermaluc.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.sermaluc.models.Componente;
import prueba.sermaluc.models.Registro;
import prueba.sermaluc.repositories.ComponenteRepository;
import prueba.sermaluc.repositories.RegistroRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FormulaService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private ComponenteRepository componenteRepository;

    // Método para evaluar las fórmulas y devolver los resultados
    public Map<String, Double> evaluateFormulas(Long acoIdAsociacionComuna) {
        // Obtener registros y componentes
        List<Registro> registros = registroRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);
        List<Componente> componentes = componenteRepository.findByAcoIdAsociacionComuna(acoIdAsociacionComuna);

        // Mapear componentes a sus valores
        Map<String, Double> componentValues = componentes.stream()
                .collect(Collectors.toMap(Componente::getComponente, Componente::getValor));

        // Evaluar las fórmulas de los registros
        Map<String, Double> formulaResults = registros.parallelStream() // Utilizando parallelStream para paralelizar el proceso
                .collect(Collectors.toConcurrentMap(
                        registro -> registro.getAcaForFormulaDescompuesta(),
                        registro -> {
                            // Reemplazar símbolos con valores y evaluar fórmula
                            String formulaWithValues = replaceSymbolsWithValues(registro.getAcaForFormulaDescompuesta(), componentValues);
                            int decimalPlace = getDecimalPlaceFromFormula(formulaWithValues);
                            double result = evaluateFormula(formulaWithValues);
                            result = roundToDecimalPlace(result, decimalPlace);
                            return result;
                        }));

        return formulaResults;
    }

    // Método para generar un archivo Excel con los resultados para todos los IDs de asociación
    public void generateExcelFileForAllAcoIds(String outputFilePath) {
        Map<Long, Map<String, Double>> allFormulaResults = new HashMap<>();
        List<Long> acoIdList = registroRepository.findDistinctAcoIdAsociacionComuna();

        // Calcular resultados para cada ID de asociación
        for (Long acoId : acoIdList) {
            Map<String, Double> formulaResults = evaluateFormulas(acoId);
            allFormulaResults.put(acoId, formulaResults);
        }

        // Escribir los resultados en un archivo Excel
        writeExcelFile(outputFilePath, allFormulaResults);
    }

    // Método para escribir los resultados en un archivo Excel
    public void writeExcelFile(String outputFilePath, Map<Long, Map<String, Double>> allFormulaResults) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Resultados");

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("acoIdAsociacionComuna");
            headerRow.createCell(1).setCellValue("Fórmula");
            headerRow.createCell(2).setCellValue("Resultado");

            // Escribir los resultados en las filas del archivo Excel
            for (Map.Entry<Long, Map<String, Double>> entry : allFormulaResults.entrySet()) {
                Long acoId = entry.getKey();
                Map<String, Double> formulaResults = entry.getValue();
                for (Map.Entry<String, Double> formulaEntry : formulaResults.entrySet()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(acoId);
                    row.createCell(1).setCellValue(formulaEntry.getKey());
                    row.createCell(2).setCellValue(formulaEntry.getValue());
                }
            }

            // Guardar el archivo Excel
            FileOutputStream fileOut = new FileOutputStream(outputFilePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para generar un archivo Excel con los resultados para un ID de asociación específico
    public void generateExcelFile(Map<String, Double> formulaResults, String outputFilePath, Long id) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Resultados");

            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("acoIdAsociacionComuna");
            row.createCell(1).setCellValue("Fórmula");
            row.createCell(2).setCellValue("Resultado");

            // Escribir los resultados en el archivo Excel
            for (Map.Entry<String, Double> entry : formulaResults.entrySet()) {
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(id);
                row.createCell(1).setCellValue(entry.getKey());
                row.createCell(2).setCellValue(entry.getValue());
            }

            // Guardar el archivo Excel
            FileOutputStream fileOut = new FileOutputStream(outputFilePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para reemplazar símbolos con valores en la fórmula
    private String replaceSymbolsWithValues(String formula, Map<String, Double> componentValues) {
        // Eliminar los indicadores de redondeo
        formula = removeRoundingIndicators(formula);

        // Validar que la fórmula tenga un número igual de paréntesis de apertura y cierre
        if (!isFormulaValid(formula)) {
            // La fórmula no es válida, devolver un valor predeterminado o lanzar una excepción
            return "0.0";
        }

        // Modificar la expresión regular para que coincida con los nombres de los componentes, los parámetros y las expresiones round
        String[] parts = formula.split("(?<=[\\-+/()\\]\\*])|(?=[\\-+/()\\]\\*])|(?<!\\w)|(?=\\W)");
        StringBuilder result = new StringBuilder();

        // Reemplazar los componentes por sus valores numéricos
        for (String part : parts) {
            Double value = componentValues.get(part);
            if (value != null) {
                // Si la parte es un componente o parámetro, reemplazarlo por su valor
                result.append(value);
            } else {
                // Si la parte no es un componente ni un parámetro, mantenerla sin cambios
                result.append(part);
            }
        }

        // Reemplazar las expresiones round por Math.round()
        String formulaWithValues = result.toString();
        formulaWithValues = formulaWithValues.replaceAll("round\\(", "Math.round(");

        return formulaWithValues;
    }

    // Método para eliminar los indicadores de redondeo de la fórmula
    private String removeRoundingIndicators(String formula) {
        return formula.replaceAll(";\\d+", "");
    }

    // Método para redondear un valor al número de decimales especificado
    private double roundToDecimalPlace(double value, int decimalPlace) {
        double factor = Math.pow(10, decimalPlace);
        return Math.round(value * factor) / factor;
    }

    // Método para obtener el número de decimales de la fórmula
    private int getDecimalPlaceFromFormula(String formula) {
        Pattern pattern = Pattern.compile(";(\\d+)");
        Matcher matcher = pattern.matcher(formula);
        int decimalPlace = 0;
        while (matcher.find()) {
            decimalPlace = Math.max(decimalPlace, Integer.parseInt(matcher.group(1)));
        }
        return decimalPlace;
    }

    // Método para evaluar la fórmula con GraalVM
    private double evaluateFormula(String formulaWithValues) {
        try (Context context = Context.newBuilder("js").allowAllAccess(true).build()) {
            Value bindings = context.getBindings("js");
            Value result = context.eval("js", formulaWithValues);
            return result.asDouble();
        } catch (Exception e) {
            // Manejar la excepción apropiadamente
            e.printStackTrace();
            return Double.NaN;
        }
    }

    // Método para verificar si la fórmula tiene la misma cantidad de paréntesis de apertura y cierre
    private boolean isFormulaValid(String formula) {
        int openParentheses = 0;
        int closedParentheses = 0;

        for (char c : formula.toCharArray()) {
            if (c == '(') {
                openParentheses++;
            } else if (c == ')') {
                closedParentheses++;
            }
        }

        return openParentheses == closedParentheses;
    }
}
