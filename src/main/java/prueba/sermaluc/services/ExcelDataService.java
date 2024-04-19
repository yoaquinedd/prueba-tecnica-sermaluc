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



    public void cargarDatosDesdeExcel() {

        String rutaArchivo = "prueba.xlsx";
        int tamañoLote = 100; // Tamaño del lote

        List<Componente> registrosTemporalesHoja1 = new ArrayList<>();
        List<Registro> registrosTemporalesHoja2 = new ArrayList<>();


        try (InputStream archivo = new FileInputStream(rutaArchivo);
             XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {

            Sheet hoja1 = workbook.getSheetAt(0);

            Sheet hoja2 = workbook.getSheetAt(1);


            for(Row fila : hoja1){
                if(fila.getRowNum() == 0) continue;

                Componente componente = new Componente();
                componente.setAcoIdAsociacionComuna((long) fila.getCell(0).getNumericCellValue());
                componente.setComponente(fila.getCell(1).getStringCellValue());
                componente.setValor((double)fila.getCell(2).getNumericCellValue());

                registrosTemporalesHoja1.add(componente);

                if(registrosTemporalesHoja1.size()>=tamañoLote){
                    componenteRepository.saveAll(registrosTemporalesHoja1);
                    registrosTemporalesHoja1.clear();
                }
            }

            if(!registrosTemporalesHoja1.isEmpty()){
                componenteRepository.saveAll(registrosTemporalesHoja1);
            }



            for (Row fila : hoja2) {
                if (fila.getRowNum() == 0) continue; // Saltar la primera fila si contiene encabezados

                Registro registro = new Registro();
                registro.setAcoIdAsociacionComuna((long) fila.getCell(0).getNumericCellValue());
                registro.setEmpNomEmpresa(fila.getCell(1).getStringCellValue());
                registro.setComNomComuna(fila.getCell(2).getStringCellValue());
                registro.setSubNombreSec(fila.getCell(3).getStringCellValue());
                registro.setOpcTarifariaId((long) fila.getCell(4).getNumericCellValue());
                registro.setOpcTarifariaNombre(fila.getCell(5).getStringCellValue());
                registro.setAcaForFormulaDescompuesta(fila.getCell(6).getStringCellValue());
                registro.setCarDescCargo(fila.getCell(7).getStringCellValue());

                registrosTemporalesHoja2.add(registro);

                // Insertar los registros en lotes
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

