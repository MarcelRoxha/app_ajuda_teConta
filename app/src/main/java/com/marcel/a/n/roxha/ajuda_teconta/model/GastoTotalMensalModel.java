package com.marcel.a.n.roxha.ajuda_teconta.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.ajuda_teconta.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GastoTotalMensalModel implements Serializable {

    private String id;
    private int mes_referencia;
    private double valor_total_gastos_mes_referencia;
    private double valor_total__ganhos_mes_referencia;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestore();
    private CollectionReference reference = db.collection("MONTANTES_MENSAIS");

    public GastoTotalMensalModel() {
    }

    public GastoTotalMensalModel(String id, int mes_referencia, double valor_total_mes_referencia, double valor_total__ganhos_mes_referencia) {
        this.id = id;
        this.mes_referencia = mes_referencia;
        this.valor_total_gastos_mes_referencia = valor_total_mes_referencia;
        this.valor_total__ganhos_mes_referencia = valor_total__ganhos_mes_referencia;
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

    public double getValor_total_gastos_mes_referencia() {
        return valor_total_gastos_mes_referencia;
    }

    public void setValor_total_gastos_mes_referencia(double valor_total_gastos_mes_referencia) {
        this.valor_total_gastos_mes_referencia = valor_total_gastos_mes_referencia;
    }

    public double getValor_total__ganhos_mes_referencia() {
        return valor_total__ganhos_mes_referencia;
    }

    public void setValor_total__ganhos_mes_referencia(double valor_total__ganhos_mes_referencia) {
        this.valor_total__ganhos_mes_referencia = valor_total__ganhos_mes_referencia;
    }


}
