package com.marcel.a.n.roxha.ajuda_teconta.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.ajuda_teconta.R;
import com.marcel.a.n.roxha.ajuda_teconta.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.ajuda_teconta.model.GanhoModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;

    private Button botaoGastos;
    private Button botaoGanhos;
    private ImageButton botaoDeslogarUser;
    private List<Double> listaGasto = new ArrayList<>();
    private double valorTotalGanhos;
    private double resultadoGAnhos ;;
    private GanhoModel ganhoRecuperado = new GanhoModel();

    ArrayList<BarEntry> list ;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestore();
    private CollectionReference reference_ganhos = db.collection("GANHOS");
    private CollectionReference reference_gastos = db.collection("GASTOS");
    private  DocumentReference referenceGanhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        carregarInformacoes();

        barChart = findViewById(R.id.grafico_geral_id);
        botaoDeslogarUser = findViewById(R.id.botao_deslogar_user_id);
        botaoGastos = findViewById(R.id.botao_adicionar_ganhos_fixos_id);
        botaoGanhos = findViewById(R.id.botao_adicionar_ganhos_id);


        barDataSet = new BarDataSet(list, "Olhar Geral");
        barData = new BarData(barDataSet);

        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);





botaoGastos.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(HomeActivity.this, GastoActivity2.class);
        startActivity(intent);
    }
});


botaoDeslogarUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("DESLOGANDO");
        dialog.setMessage("Deseja realmente sair do aplicativo? ");
        dialog.setCancelable(false);
        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
                auth.signOut();
                Toast.makeText(HomeActivity.this, "Usuário deslogado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.create();
        dialog.show();

    }

});


botaoGanhos.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(HomeActivity.this, GanhosActivity.class);
        startActivity(intent);
        
    }
});


    }

    public void percorrerDados(){

        Query query = reference_ganhos.orderBy("nome");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot lista: snapshotList){
                   String recu = lista.getId();
                    recuperarValorGanhos(recu);
                }


            }
        });
    }

    public void recuperarValorGanhos(String idRecuperad){

         referenceGanhos = reference_ganhos.document(idRecuperad);
        referenceGanhos.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                GanhoModel ganhoModelRecuperado =  documentSnapshot.toObject(GanhoModel.class);
                valorTotalGanhos = ganhoModelRecuperado.getValor();
                ganhoRecuperado.setValor(valorTotalGanhos);

            }
        });

        Toast.makeText(HomeActivity.this, "Dentro "+ ganhoRecuperado.getValor(), Toast.LENGTH_SHORT).show();

    }

    private void carregarInformacoes(){

       // Toast.makeText(this, "Recuperado dentro do carregar informacao " + getValoresRecuperados(), Toast.LENGTH_SHORT).show();

        double valorRecuperado = 23;

        list =  new ArrayList();
        list.add(new BarEntry(1, (float) valorRecuperado));
        list.add(new BarEntry(2, 3f));


    }

    public Double getValoresRecuperados(){
        return valorTotalGanhos;

    }

    public void setValoresRecuperados(double valor){

        resultadoGAnhos =+ valor;

        valorTotalGanhos = resultadoGAnhos;

    }

    @Override
    protected void onStart() {
        super.onStart();
        percorrerDados();
    }
}