package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CadastroTurmaActivity extends AppCompatActivity {

    Button buttonSalvar;

    EditText editTextNomeProfessor,editTextNomeIdioma,editTextNomeTurma,editTextHInicio,editTextHTermino;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String profuid, idiomaid;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turma);

        editTextNomeIdioma = findViewById(R.id.editTextNomeIdioma);
        editTextNomeTurma = findViewById(R.id.editTextNomeTurma);
        editTextHInicio = findViewById(R.id.editTextHorarioInicio);
        editTextHTermino = findViewById(R.id.editTextHorarioTermino);
        editTextNomeProfessor = findViewById(R.id.editTextNomeProfessor);
        buttonSalvar = findViewById(R.id.buttonSave);
        editTextNomeProfessor.setKeyListener(null);
        editTextNomeIdioma.setKeyListener(null);


        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeTurma = editTextNomeTurma.getText().toString();
                String hInicio = editTextHInicio.getText().toString();
                String hTermino = editTextHTermino.getText().toString();
                String professor = editTextNomeProfessor.getText().toString();
                String idioma = editTextNomeIdioma.getText().toString();


                if (TextUtils.isEmpty(nomeTurma)) {
                    editTextNomeTurma.setError("Por favor preencher este campo");
                    return;
                }
                if (TextUtils.isEmpty(hInicio)) {
                    editTextHInicio.setError("Por favor preencher este campo");
                    return;
                }
                if (TextUtils.isEmpty(hTermino)) {
                    editTextHTermino.setError("Por favor preencher este campo");
                    return;
                }
                if (TextUtils.isEmpty(professor)) {
                    editTextNomeProfessor.setError("Por favor preencher este campo");
                    return;
                }

                if (TextUtils.isEmpty(idioma)) {
                    editTextNomeIdioma.setError("Por favor preencher este campo");
                    return;
                }


                String uid = auth.getCurrentUser().getUid();
                final Map<String, Object> novaTurma = new HashMap<>();
                novaTurma.put("admin", uid);
                novaTurma.put("descrip", nomeTurma);
                novaTurma.put("idioma", idioma);
                novaTurma.put("profname", professor);
                novaTurma.put("profuid", profuid);
                novaTurma.put("idiomaid", idiomaid);
                novaTurma.put("horarioInicio", hInicio);
                novaTurma.put("horarioTermino", hTermino);

                db.collection("Turmas").add(novaTurma).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CadastroTurmaActivity.this, "Turma criada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroTurmaActivity.this, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        editTextNomeIdioma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(CadastroTurmaActivity.this, SearchProfessorActivity.class);
                    String tpesq = "idioma";
                    intent.putExtra("tpesq", tpesq);
                    startActivityForResult(intent,2);


                }


                return false;
            }
        });

        editTextNomeProfessor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(CadastroTurmaActivity.this, SearchProfessorActivity.class);
                    String tpesq = "prof";
                    intent.putExtra("tpesq", tpesq);
                    startActivityForResult(intent,1);


                }
                return false;
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode==RESULT_OK){
                String professor = data.getStringExtra("nome");
                profuid = data.getStringExtra("uid");
                editTextNomeProfessor.setText(professor);
            }
        }else if(requestCode == 2){
            if (resultCode==RESULT_OK) {
                String idioma = data.getStringExtra("idioma");
                idiomaid = data.getStringExtra("id");
                editTextNomeIdioma.setText(idioma);
            }
        }
    }
}
