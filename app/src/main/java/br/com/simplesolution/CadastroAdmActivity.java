package br.com.simplesolution;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroAdmActivity extends AppCompatActivity {

    EditText editTextNomeCompleto, editTextCelular, editTextEmail, editTextSenha;
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buttonSalvar;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_adm);

        editTextNomeCompleto = findViewById(R.id.editTextNomeCompletoAdmin);
        editTextEmail = findViewById(R.id.editTextEmailAdmin);
        editTextSenha = findViewById(R.id.editTextSenha);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        buttonSalvar = findViewById(R.id.buttonSalvar);


        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomeCompleto = editTextNomeCompleto.getText().toString();
                final String celular = editTextCelular.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();


            }

        });
    }

}