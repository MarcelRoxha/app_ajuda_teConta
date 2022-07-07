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
import java.net.PortUnreachableException;
import java.util.HashMap;
import java.util.Map;

public class GastoModel implements Serializable {

    private String id;
    private String nome;
    private String data;
    private String montanteReferencia;
    private int mesReferencia;
    private double valor;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestore();
    private CollectionReference reference = db.collection("MONTANTES_MENSAIS");

    public GastoModel() {
    }

    public GastoModel(String id, String nome, String data, double valor, int mesReferencia, String montanteReferencia) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.valor = valor;
        this.mesReferencia = mesReferencia;
        this.montanteReferencia = montanteReferencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(int mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public String getMontanteReferencia() {
        return montanteReferencia;
    }

    public void setMontanteReferencia(String montanteReferencia) {
        this.montanteReferencia = montanteReferencia;
    }

    public void salvarGastoAvulso(String idMontante, Context context){

        CollectionReference referenceGastoAvulso = reference.document(idMontante).collection("GASTOS_AVULSOS");

        Map<String, Object> gastoAvulsoSalvo = new HashMap<>();

        gastoAvulsoSalvo.put("nome", getNome());
        gastoAvulsoSalvo.put("data", getData());
        gastoAvulsoSalvo.put("valor", getValor());
        gastoAvulsoSalvo.put("mesReferencia", getMesReferencia());

        referenceGastoAvulso.add(gastoAvulsoSalvo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "Sucesso ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " para organizar suas finanças avulsas", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Erro ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " verifique as informações", Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void atualizarGastoAvulso(String id, String idRefe, Context context){


        DocumentReference referenceGastoAvulso = reference.document(id).collection("GASTOS_AVULSOS").document(idRefe);


        Map<String, Object> gastoAvulsoAtualizar = new HashMap<>();

        gastoAvulsoAtualizar.put("nome", getNome());
        gastoAvulsoAtualizar.put("data", getData());
        gastoAvulsoAtualizar.put("valor", getValor());
        gastoAvulsoAtualizar.put("mesReferencia", getMesReferencia());

        referenceGastoAvulso.update(gastoAvulsoAtualizar).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(context, "Sucesso ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " para organizar suas finanças", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Erro ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " verifique as informações", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void deletarGastoAvulso(String id, String idRefe, Context context){
        Task<Void> referenceGastoAvulso = reference.document(id).collection("GASTOS_AVULSOS").document(idRefe).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Gasto Avulso: " + getNome() + " deletado", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public void salvarGastoFixo(String idMontante, Context context){

        CollectionReference referenceGastoAvulso = reference.document(idMontante).collection("GASTOS_FIXOS");

        Map<String, Object> gastoFixoSalvo = new HashMap<>();

        gastoFixoSalvo.put("nome", getNome());
        gastoFixoSalvo.put("data", getData());
        gastoFixoSalvo.put("valor", getValor());
        gastoFixoSalvo.put("mesReferencia", getMesReferencia());

        referenceGastoAvulso.add(gastoFixoSalvo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }

    public void atualizarGastoFixo(String id, String idRefe, Context context){


        DocumentReference referenceGastoAvulso = reference.document(id).collection("GASTOS_FIXOS").document(idRefe);


        Map<String, Object> gastoFixoAtualizar = new HashMap<>();

        gastoFixoAtualizar.put("nome", getNome());
        gastoFixoAtualizar.put("data", getData());
        gastoFixoAtualizar.put("valor", getValor());
        gastoFixoAtualizar.put("mesReferencia", getMesReferencia());

        referenceGastoAvulso.update(gastoFixoAtualizar).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(context, "Sucesso ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " para organizar suas finanças", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Erro ao adicionar: " + getNome() + " no valor de R$: " + getValor() + " verifique as informações", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void deletarGastoFixo(String id, String idRefe, Context context){

        Task<Void> referenceGastoFixo = reference.document(id).collection("GASTOS_FIXOS").document(idRefe).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Gasto fixo: " + getNome() + " deletado", Toast.LENGTH_SHORT).show();
            }
        });



    }


}
