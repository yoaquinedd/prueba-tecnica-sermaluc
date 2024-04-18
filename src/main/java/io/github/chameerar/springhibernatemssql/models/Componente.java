package io.github.chameerar.springhibernatemssql.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Componente {

    @Id
    private Long acoIdAsociacionComuna;
    private String componente;
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
