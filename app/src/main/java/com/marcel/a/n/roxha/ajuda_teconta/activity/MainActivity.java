package com.marcel.a.n.roxha.ajuda_teconta.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.marcel.a.n.roxha.ajuda_teconta.R;
import com.marcel.a.n.roxha.ajuda_teconta.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.ajuda_teconta.model.UsuarioModel;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText email_login;
    private TextInputEditText senha_login;

    private Button botao_logar;

    private TextView abrir_tela_cadastro;

    private FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
    private UsuarioModel usuarioModel = new UsuarioModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getWindow().getDecorView();

        view.setSystemUiVisibility(View.INVISIBLE);

        verificarUsuarioLogado();

        email_login = findViewById(R.id.email_login_id);
        senha_login = findViewById(R.id.senha_login_id);

        botao_logar = findViewById(R.id.botao_logar_id);

        abrir_tela_cadastro = findViewById(R.id.text_abrir_tela_cadastro_id);

        botao_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailUserInserido = email_login.getText().toString();
                String senhaUserInserido = senha_login.getText().toString();

                if(!emailUserInserido.isEmpty() && !senhaUserInserido.isEmpty()){


                    usuarioModel.seteMailUser(emailUserInserido);
                    usuarioModel.setSenhaUser(senhaUserInserido);
                    validarLogin();
                }else{

                    Toast.makeText(MainActivity.this, "Favor insira os dados de login", Toast.LENGTH_SHORT).show();
                }


            }
        });

     /*   view.setSystemUiVisibility(View.INVISIBLE);
        getSupportActionBar().hide();*/
    }



    public void verificarUsuarioLogado(){

        auth = ConfiguracaoFirebase.getAuth();
        if (auth.getCurrentUser() != null){

            abrirTelaPrincipal();


        }
    }

    private void validarLogin(){

        auth = ConfiguracaoFirebase.getAuth();

        auth.signInWithEmailAndPassword(
                usuarioModel.geteMailUser()
                ,usuarioModel.getSenhaUser()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Sucesso ao fazer login", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }



    public void abrirTelaPrincipal(){

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);


    }
}