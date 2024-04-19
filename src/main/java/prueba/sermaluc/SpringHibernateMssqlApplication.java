package prueba.sermaluc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import prueba.sermaluc.services.ExcelDataService;

@SpringBootApplication
public class SpringHibernateMssqlApplication {

	private final ExcelDataService excelDataService;

	public SpringHibernateMssqlApplication(ExcelDataService excelDataService) {
		this.excelDataService = excelDataService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringHibernateMssqlApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void cargarDatosDesdeExcel() {
		excelDataService.cargarDatosDesdeExcel();
	}
}
