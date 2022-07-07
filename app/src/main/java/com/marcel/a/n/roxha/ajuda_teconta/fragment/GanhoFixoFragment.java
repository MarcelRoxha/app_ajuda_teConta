package com.marcel.a.n.roxha.ajuda_teconta.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.ajuda_teconta.R;
import com.marcel.a.n.roxha.ajuda_teconta.activity.HomeActivity;
import com.marcel.a.n.roxha.ajuda_teconta.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.ajuda_teconta.model.GanhoModel;
import com.marcel.a.n.roxha.ajuda_teconta.model.GastoModel;
import com.marcel.a.n.roxha.ajuda_teconta.model.GastoTotalMensalModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GanhoFixoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GanhoFixoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GanhoFixoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GanhoFixoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GanhoFixoFragment newInstance(String param1, String param2) {
        GanhoFixoFragment fragment = new GanhoFixoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    private Button botao_salvar_ganho_fixo;
    private Button botao_voltar_ganho_fixo;
    private CalendarView calendario_ganho_fixo;
    private TextInputEditText nome_ganho_fixo;
    private TextInputEditText valor_ganho_fixo;
    private TextView texto_data_prevista_ganho_fixo;
    private double valorConvertGanhoFixo;
    private String data_ganho_fixo;
    private int mes_referencia;
    private double valorConvertGastoFixo;

    private String idNovoMontate;


    private List<String> listCont = new ArrayList<>();

    private GanhoModel ganhoModel = new GanhoModel();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_ganho_fixo, container, false);

        botao_salvar_ganho_fixo = view.findViewById(R.id.botao_salvar_ganho_fixo_id);
        botao_voltar_ganho_fixo = view.findViewById(R.id.botao_voltar_ganho_fixo_id);
        calendario_ganho_fixo = view.findViewById(R.id.calendario_ganho_fixo_id);
        nome_ganho_fixo = view.findViewById(R.id.nome_ganho_fixo_id);
        valor_ganho_fixo = view.findViewById(R.id.valor_ganho_fixo_id);

        texto_data_prevista_ganho_fixo = view.findViewById(R.id.texto_data_ganho_fixo_id);

        calendario_ganho_fixo.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                data_ganho_fixo = i2 + "/" + i1 + "/" + i;
                mes_referencia = i1;
                texto_data_prevista_ganho_fixo.setText("Dia previsto para ganho fixo é: " + i2);

                FirebaseFirestore.getInstance().collection("MONTANTES_MENSAIS").whereEqualTo("mes_referencia", mes_referencia)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


                        for (DocumentSnapshot snapshot : snapshotList) {
                            String id = snapshot.getId();
                            listCont.add(id);
                            recuperaId(id);
                        }

                        if (listCont.size() > 0) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("INFO: TOTAL MENSAL EXISTENTE");
                            alert.setMessage("Vimos que você já criou um montante referente ao mês:  " + mes_referencia + ", verifique o valor total, e veja se é realmente necessário esse gasto!");
                            alert.setCancelable(false);

                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listCont.clear();

                                }
                            });

                            alert.create();
                            alert.show();


                        } else if (listCont.size() < 1) {

                            GastoTotalMensalModel montanteNovo = new GastoTotalMensalModel();

                            montanteNovo.setMes_referencia(mes_referencia);


                            Map<String, Object> montanteMensalSalvo = new HashMap<>();

                            montanteMensalSalvo.put("mes_referencia", montanteNovo.getMes_referencia());
                            montanteMensalSalvo.put("valor_total_gastos_mes_referencia", montanteNovo.getValor_total_gastos_mes_referencia());
                            montanteMensalSalvo.put("valor_total_ganhos_mes_referencia", montanteNovo.getValor_total__ganhos_mes_referencia());


                            FirebaseFirestore.getInstance().collection("MONTANTES_MENSAIS").add(montanteMensalSalvo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {


                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setTitle("INFO: TOTAL MENSAL NÃO EXISTE");
                                    alert.setMessage("Vimos que você não tem nenhum montante criado referente ao mês: " + mes_referencia + ", criaremos um para você se organizar!");
                                    alert.setCancelable(false);

                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            String idMontante = documentReference.getId();
                                            recuperaId(idMontante);

                                            Toast.makeText(getActivity(), "Montante criado com sucesso!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    alert.create();
                                    alert.show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                }
                            });

                        }

                    }
                });

            }
        });

                botao_salvar_ganho_fixo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nome_gasto_digitado = nome_ganho_fixo.getText().toString();
                        String valor_gasto_digitado = valor_ganho_fixo.getText().toString();

                        if (nome_gasto_digitado.isEmpty()) {
                            Toast.makeText(getActivity(), "Favor digite o nome do gasto avulso", Toast.LENGTH_SHORT).show();
                        } else if (valor_gasto_digitado.isEmpty()) {
                            Toast.makeText(getActivity(), "Favor digite o valor do gasto avulso", Toast.LENGTH_SHORT).show();
                        } else if (mes_referencia <= 0) {
                            Toast.makeText(getActivity(), "Favor escolha uma data no calendário", Toast.LENGTH_SHORT).show();
                        } else {

                            valorConvertGastoFixo = Double.parseDouble(valor_gasto_digitado);

                            String nome = nome_gasto_digitado;
                            double valor = valorConvertGastoFixo;
                            String data = data_ganho_fixo;
                            int mesRef = mes_referencia;


                            GastoModel gasAvulsoSalvo = new GastoModel();

                            gasAvulsoSalvo.setNome(nome);
                            gasAvulsoSalvo.setValor(valor);
                            gasAvulsoSalvo.setData(data);
                            gasAvulsoSalvo.setMesReferencia(mesRef);
                            gasAvulsoSalvo.setMontanteReferencia(idNovoMontate);

                            salvarGastoFixoMontante(gasAvulsoSalvo);

                            atualizarIdMontanteReferencia(idNovoMontate, valorConvertGastoFixo);

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);

                        }

                    }
                });


        return view;
    }

    public void atualizarIdMontanteReferencia(String idRecuperado, double valorRecebido){

        FirebaseFirestore.getInstance().collection("MONTANTES_MENSAIS")
                .document(idNovoMontate).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        GastoTotalMensalModel gastoAtualizar = new GastoTotalMensalModel();

                        GastoTotalMensalModel gastoRecuperado = documentSnapshot.toObject(GastoTotalMensalModel.class);

                        double gastoExistente = gastoRecuperado.getValor_total__ganhos_mes_referencia();

                        double valorConvertido = valorRecebido;
                        double calcularMontante = gastoExistente + valorConvertido;

                        gastoAtualizar.setValor_total__ganhos_mes_referencia(calcularMontante);
                        gastoAtualizar.setMes_referencia(mes_referencia);

                        Map<String, Object> montanteAtualizar = new HashMap<>();


                        montanteAtualizar.put("mes_referencia", gastoAtualizar.getMes_referencia());
                        //montanteAtualizar.put("valor_total_gastos_mes_referencia", gastoAtualizar.getValor_total_gastos_mes_referencia());
                        montanteAtualizar.put("valor_total_ganhos_mes_referencia", gastoAtualizar.getValor_total__ganhos_mes_referencia());;

                        FirebaseFirestore.getInstance().collection("MONTANTES_MENSAIS").document(documentSnapshot.getId())
                                .update(montanteAtualizar).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Montante atualizado com o valor informado", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Não atualizou", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void recuperaId(String idRecuperado){

        String id = idRecuperado;

        this.idNovoMontate = id;

    }

    public String getIdRecuperado(){
        return  idNovoMontate;
    }


    public void salvarGastoFixoMontante(GastoModel gastoModelRecuperado){

        String nomeRecuperado = gastoModelRecuperado.getNome();
        double valorRecuperado = gastoModelRecuperado.getValor();
        String dataRecuperada = gastoModelRecuperado.getData();
        int mesReferenciaRecuperado = gastoModelRecuperado.getMesReferencia();
        String montanteReferencia = gastoModelRecuperado.getMontanteReferencia();

        GastoModel gastoRecuperadoAdicionar = new GastoModel();

        gastoRecuperadoAdicionar.setNome(nomeRecuperado);
        gastoRecuperadoAdicionar.setValor(valorRecuperado);
        gastoRecuperadoAdicionar.setData(dataRecuperada);
        gastoRecuperadoAdicionar.setMesReferencia(mesReferenciaRecuperado);
        gastoRecuperadoAdicionar.setMontanteReferencia(montanteReferencia);

        FirebaseFirestore db = ConfiguracaoFirebase.getFirestore();
        CollectionReference referenceGastoAvulso = db.collection("MONTANTES_MENSAIS").document(getIdRecuperado()).collection("GANHOS_FIXOS");


        Map<String, Object> gastoAvulsoSalvo = new HashMap<>();


        gastoAvulsoSalvo.put("nome", gastoRecuperadoAdicionar.getNome());
        gastoAvulsoSalvo.put("data", gastoRecuperadoAdicionar.getData());
        gastoAvulsoSalvo.put("valor", gastoRecuperadoAdicionar.getValor());
        gastoAvulsoSalvo.put("mesReferencia", gastoRecuperadoAdicionar.getMesReferencia());
        gastoAvulsoSalvo.put("montanteReferencia", gastoRecuperadoAdicionar.getMontanteReferencia());

        referenceGastoAvulso.add(gastoAvulsoSalvo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(getActivity(), "Gasto fixo salvo com sucesso!", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Erro ao salvar gasto fixo!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}