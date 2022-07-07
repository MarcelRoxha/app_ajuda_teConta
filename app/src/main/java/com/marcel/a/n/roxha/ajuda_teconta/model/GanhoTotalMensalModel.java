package com.marcel.a.n.roxha.ajuda_teconta.model;

import java.io.Serializable;

public class GanhoTotalMensalModel implements Serializable {
    private String id;
    private int mes_referencia;
    private double valor_total_mes_referencia;


    public GanhoTotalMensalModel() {
    }

    public GanhoTotalMensalModel(String id, int mes_referencia, double valor_total_mes_referencia) {
        this.id = id;
        this.mes_referencia = mes_referencia;
        this.valor_total_mes_referencia = valor_total_mes_referencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMes_referencia() {
        return mes_referencia;
    }

    public void setMes_referencia(int mes_referencia) {
        this.mes_referencia = mes_referencia;
    }

    public double getValor_total_mes_referencia() {
        return valor_total_mes_referencia;
    }

    public void setValor_total_mes_referencia(double valor_total_mes_referencia) {
        this.valor_total_mes_referencia = valor_total_mes_referencia;
    }
}