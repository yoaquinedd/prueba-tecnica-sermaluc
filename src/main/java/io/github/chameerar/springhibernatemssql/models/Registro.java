package io.github.chameerar.springhibernatemssql.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Registro {

    @Id
    private Long acoIdAsociacionComuna;
    private String empNomEmpresa;
    private String comNomComuna;
    private String subNombreSec;
    private Long opcTarifariaId;
    private String opcTarifariaNombre;
    private String acaForFormulaDescompuesta;
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
