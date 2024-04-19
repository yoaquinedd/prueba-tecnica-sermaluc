package prueba.sermaluc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Componente {

    @Id()
    @GeneratedValue
    private Long id;

    @Column(length = 25)
    private Long acoIdAsociacionComuna;
    @Column(length = 15)
    private String componente;
    @Column(length = 25)
    private double valor;

    public Long getAcoIdAsociacionComuna() {
        return acoIdAsociacionComuna;
    }

    public void setAcoIdAsociacionComuna(Long acoIdAsociacionComuna) {
        this.acoIdAsociacionComuna = acoIdAsociacionComuna;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
