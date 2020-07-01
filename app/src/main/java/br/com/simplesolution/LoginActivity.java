package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {

    TextView ola, textViewCool;
    ImageView buttonVolta;
    EditText editTextSenha, editTextConfirmaSenha, editTextUser;
    FirebaseAuth fAuth;
    Button buttonLogin2;
    FirebaseFirestore db;
    String admin, prof, aluno, tuser;
    Intent intent;
    Boolean novo;
    ConstraintLayout background;
    ProgressBar loading;
    int uni15 = 0x1F60D, uni32 = 0x1F914;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textViewCool = findViewById(R.id.textViewCool);
        ola = findViewById(R.id.textViewOla);
        background = findViewById(R.id.graybackground);
        loading = findViewById(R.id.progressBar);
        intent = getIntent();
        novo = intent.getBooleanExtra("novo", false);
        buttonVolta = findViewById(R.id.buttonVolta);
        editTextUser = findViewById(R.id.editTextUser);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmaSenha = findViewById(R.id.editTextConfirmaSenha);
        buttonLogin2 = findViewById(R.id.buttonLogin2);
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        admin = "admin";
        prof = "prof";
        aluno = "aluno";
        String emoji15 = getEmoji(uni15);
        String emoj32 = getEmoji(uni32);

        final String email = intent.getStringExtra("email");
        String fullname = intent.getStringExtra("fullname");
        String firstname = fullname.substring(0, fullname.indexOf(' '));
        ola.setText("Olá " + firstname + "!");

        editTextUser.setText(email);
        editTextUser.setFocusable(false);

        if (novo.equals(false)) {

            editTextConfirmaSenha.setVisibility(View.INVISIBLE);
            textViewCool.setText("Que bom te ver aqui de novo "+emoji15);
        } else {
            editTextConfirmaSenha.setVisibility(View.VISIBLE);
            textViewCool.setText("Humm... " +emoj32+ "\nParece que você ainda não tem uma senha. Precisamos criar uma.");
        }

        final DocumentReference ref = db.collection("Users").document(email);

        buttonLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (novo.equals(false)) {


                    String user = editTextUser.getText().toString().trim();
                    String senha = editTextSenha.getText().toString().trim();

                    if (TextUtils.isEmpty((senha))) {
                        editTextSenha.setError("É necessário uma senha válida");
                        editTextSenha.requestFocus();
                        return;
                    }


                    background.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    // autenticação do usuário

                    fAuth.signInWithEmailAndPassword(user, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if (documentSnapshot.exists()) {
                                                tuser = (String) documentSnapshot.getData().get("tuser");

                                                if (tuser.equals(admin)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeAdmActivity.class);
                                                    startActivity(intent);

                                                } else if (tuser.equals(prof)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeProfActivity.class);
                                                    startActivity(intent);
                                                } else if (tuser.equals(aluno)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeAlunoActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

                                        finish();
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                } else {
                    String user = editTextUser.getText().toString().trim();
                    final String senha = editTextSenha.getText().toString().trim();
                    String confirmaSenha = editTextConfirmaSenha.getText().toString().trim();


                    if (TextUtils.isEmpty(senha)) {
                        editTextSenha.setError("É necessário uma senha válida");
                        editTextSenha.requestFocus();
                        return;
                    }

                    if (!confirmaSenha.equals(senha)) {
                        editTextConfirmaSenha.setError("Senha divergente");
                        editTextConfirmaSenha.requestFocus();
                        return;


                    }

                    fAuth.createUserWithEmailAndPassword(email, confirmaSenha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = fAuth.getCurrentUser().getUid();
                                ref.update("uid", uid);
                                ref.update("novo", false);
                                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if (documentSnapshot.exists()) {
                                                tuser = (String) documentSnapshot.getData().get("tuser");

                                                if (tuser.equals(admin)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeAdmActivity.class);
                                                    startActivity(intent);

                                                } else if (tuser.equals(prof)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeProfActivity.class);
                                                    startActivity(intent);
                                                } else if (tuser.equals(aluno)) {
                                                    Intent intent = new Intent(LoginActivity.this, HomeAlunoActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

                                        finish();
                                    }
                                });


                            }


                        }
                    });
                }


            }


        });


        buttonVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public String getEmoji(int uni) {
        return new String(Character.toChars(uni));
    }
}