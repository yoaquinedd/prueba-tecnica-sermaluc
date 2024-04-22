package prueba.sermaluc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.sermaluc.models.Componente;
import prueba.sermaluc.models.Registro;
import prueba.sermaluc.repositories.ComponenteRepository;
import prueba.sermaluc.repositories.RegistroRepository;

@Service
public class ExcelDataService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private ComponenteRepository componenteRepository;

    // Método para cargar datos desde un archivo Excel
    public void cargarDatosDesdeExcel() {
        // Ruta del archivo Excel
        String rutaArchivo = "prueba.xlsx";
        // Tamaño del lote para la inserción por lotes en la base de datos
        int tamañoLote = 100;

        // Listas temporales para almacenar los registros antes de insertarlos en la base de datos
        List<Componente> registrosTemporalesHoja1 = new ArrayList<>();
        List<Registro> registrosTemporalesHoja2 = new ArrayList<>();

        try (InputStream archivo = new FileInputStream(rutaArchivo);
             XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {

            // Obtener la primera hoja del libro Excel
            Sheet hoja1 = workbook.getSheetAt(0);

            // Obtener la segunda hoja del libro Excel
            Sheet hoja2 = workbook.getSheetAt(1);

            // Iterar sobre cada fila en la primera hoja del libro Excel
            for (Row fila : hoja1) {
                // Saltar la primera fila si contiene encabezados
                if (fila.getRowNum() == 0) continue;

                // Crear un objeto Componente y asignar los valores de la fila correspondiente
                Componente componente = new Componente();
                componente.setAcoIdAsociacionComuna((long) fila.getCell(0).getNumericCellValue());
                componente.setComponente(fila.getCell(1).getStringCellValue());
                componente.setValor((double) fila.getCell(2).getNumericCellValue());

                // Agregar el componente a la lista temporal
                registrosTemporalesHoja1.add(componente);

                // Insertar los registros en la base de datos en lotes
                if (registrosTemporalesHoja1.size() >= tamañoLote) {
                    componenteRepository.saveAll(registrosTemporalesHoja1);
                    registrosTemporalesHoja1.clear(); // Vaciar la lista
                }
            }

            // Insertar los registros restantes que no formaron un lote completo
            if (!registrosTemporalesHoja1.isEmpty()) {
                componenteRepository.saveAll(registrosTemporalesHoja1);
            }

            // Iterar sobre cada fila en la segunda hoja del libro Excel
            for (Row fila : hoja2) {
                // Saltar la primera fila si contiene encabezados
                if (fila.getRowNum() == 0) continue;

                // Crear un objeto Registro y asignar los valores de la fila correspondiente
                Registro registro = new Registro();
                registro.setAcoIdAsociacionComuna((long) fila.getCell(0).getNumericCellValue());
                registro.setEmpNomEmpresa(fila.getCell(1).getStringCellValue());
                registro.setComNomComuna(fila.getCell(2).getStringCellValue());
                registro.setSubNombreSec(fila.getCell(3).getStringCellValue());
                registro.setOpcTarifariaId((long) fila.getCell(4).getNumericCellValue());
                registro.setOpcTarifariaNombre(fila.getCell(5).getStringCellValue());
                registro.setAcaForFormulaDescompuesta(fila.getCell(6).getStringCellValue());
                registro.setCarDescCargo(fila.getCell(7).getStringCellValue());

                // Agregar el registro a la lista temporal
                registrosTemporalesHoja2.add(registro);

                // Insertar los registros en la base de datos en lotes
                if (registrosTemporalesHoja2.size() >= tamañoLote) {
                    registroRepository.saveAll(registrosTemporalesHoja2);
                    registrosTemporalesHoja2.clear(); // Vaciar la lista
                }
            }

            // Insertar los registros restantes que no formaron un lote completo
            if (!registrosTemporalesHoja2.isEmpty()) {
                registroRepository.saveAll(registrosTemporalesHoja2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
