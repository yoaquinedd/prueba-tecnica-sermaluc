package prueba.sermaluc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Registro {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 25)
    private Long acoIdAsociacionComuna;
    @Column(length = 25)
    private String empNomEmpresa;
    @Column(length = 20)
    private String comNomComuna;
    @Column(length = 50)
    private String subNombreSec;
    @Column(length = 255)
    private Long opcTarifariaId;
    @Column(length = 25)
    private String opcTarifariaNombre;
    @Column(length = 1000)
    private String acaForFormulaDescompuesta;
    @Column(length = 500)
    private String carDescCargo;

    public String getEmpNomEmpresa() {
        return empNomEmpresa;
    }

    public void setEmpNomEmpresa(String empNomEmpresa) {
        this.empNomEmpresa = empNomEmpresa;
    }

    public Long getAcoIdAsociacionComuna() {
        return acoIdAsociacionComuna;
    }

    public void setAcoIdAsociacionComuna(Long acoIdAsociacionComuna) {
        this.acoIdAsociacionComuna = acoIdAsociacionComuna;
    }

    public String getComNomComuna() {
        return comNomComuna;
    }

    public void setComNomComuna(String comNomComuna) {
        this.comNomComuna = comNomComuna;
    }

    public String getSubNombreSec() {
        return subNombreSec;
    }

    public void setSubNombreSec(String subNombreSec) {
        this.subNombreSec = subNombreSec;
    }

    public Long getOpcTarifariaId() {
        return opcTarifariaId;
    }

    public void setOpcTarifariaId(Long opcTarifariaId) {
        this.opcTarifariaId = opcTarifariaId;
    }

    public String getOpcTarifariaNombre() {
        return opcTarifariaNombre;
    }

    public void setOpcTarifariaNombre(String opcTarifariaNombre) {
        this.opcTarifariaNombre = opcTarifariaNombre;
    }

    public String getAcaForFormulaDescompuesta() {
        return acaForFormulaDescompuesta;
    }

    public void setAcaForFormulaDescompuesta(String acaForFormulaDescompuesta) {
        this.acaForFormulaDescompuesta = acaForFormulaDescompuesta;
    }

    public String getCarDescCargo() {
        return carDescCargo;
    }

    public void setCarDescCargo(String carDescCargo) {
        this.carDescCargo = carDescCargo;
    }
}
