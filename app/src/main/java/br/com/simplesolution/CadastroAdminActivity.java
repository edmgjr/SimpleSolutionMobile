package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.Duration;

public class CadastroAdminActivity extends AppCompatActivity {

    EditText editTextNomeCompleto, editTextCelular, editTextEmail, editTextRg,
            editTextCpf, editTextNasc, editTextTurma, editTextNomeResp, editTextRgResp, editTextCpfResp, editTextCelularResp,
            editTextEmailResp, editTextNascResp;
    TextView textViewResp;

    Boolean menor = false;
    FirebaseFirestore db;
    DocumentReference docRef;
    RadioButton radioAdmin, radioProf, radioAluno;
    RadioGroup radioUser;
    FirebaseAuth auth;
    Button buttonSalvar;
    String uid, nomeCompleto, celular, email, rg, cpf, dataNasc, turma, nomeCompletoResp, dataNascResp, rgResp, cpfResp,
            emailResp, celularResp, tuser, datebday, turmaid, cadAtual = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_admin);
        editTextNomeCompleto = findViewById(R.id.editTextNomeCompleto);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextRg = findViewById(R.id.editTextRg);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextNasc = findViewById(R.id.editTextNasc);
        editTextTurma = findViewById(R.id.editTextTurma);
        editTextNomeResp = findViewById(R.id.editTextNomeResp);
        editTextNascResp = findViewById(R.id.editTextNascResp);
        editTextRgResp = findViewById(R.id.editTextRgResp);
        editTextCpfResp = findViewById(R.id.editTextCpfResp);
        editTextCelularResp = findViewById(R.id.editTextCelularResp);
        editTextEmailResp = findViewById(R.id.editTextEmailResp);
        textViewResp = findViewById(R.id.textViewResponsavel);
        radioAdmin = findViewById(R.id.radioAdmin);
        radioProf = findViewById(R.id.radioProf);
        radioAluno = findViewById(R.id.radioAluno);
        buttonSalvar = findViewById(R.id.buttonSave);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        editTextNomeCompleto.setVisibility(View.INVISIBLE);
        editTextCelular.setVisibility(View.INVISIBLE);
        editTextEmail.setVisibility(View.INVISIBLE);
        editTextRg.setVisibility(View.INVISIBLE);
        editTextCpf.setVisibility(View.INVISIBLE);
        editTextNasc.setVisibility(View.INVISIBLE);
        editTextTurma.setVisibility(View.INVISIBLE);
        editTextNomeResp.setVisibility(View.INVISIBLE);
        editTextNascResp.setVisibility(View.INVISIBLE);
        editTextRgResp.setVisibility(View.INVISIBLE);
        editTextCpfResp.setVisibility(View.INVISIBLE);
        editTextCelularResp.setVisibility(View.INVISIBLE);
        editTextEmailResp.setVisibility(View.INVISIBLE);
        textViewResp.setVisibility(View.INVISIBLE);
        editTextNasc.setKeyListener(null);
        editTextNascResp.setKeyListener(null);
        editTextTurma.setKeyListener(null);


        addListenerOnButton();

        editTextTurma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(CadastroAdminActivity.this, SearchProfessorActivity.class);
                    String tpesq = "turma";
                    intent.putExtra("tpesq", tpesq);
                    startActivityForResult(intent,3);
                }

                return false;
            }
        });

        editTextNascResp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroAdminActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            datebday = dayOfMonth + "/" + (month + 1) + "/" + year;
                            editTextNascResp.setText(datebday);
                            editTextNascResp.setError(null);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime()-568025136000L);
                    datePickerDialog.show();
                }

                return false;
            }
        });

        editTextNasc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroAdminActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            datebday = dayOfMonth + "/" + (month + 1) + "/" + year;
                            editTextNasc.setText(datebday);
                            editTextNasc.setError(null);
                            if (cadAtual.equals("aluno")) {
                                calculateAlunoAge(dayOfMonth, month, year);
                            }
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                    datePickerDialog.show();
                }

                return false;
            }
        });


        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cadAtual.equals("admin")){
                    salvarAdmin();
                }else if(cadAtual.equals("prof")){
                    salvarProf();
                }else if (cadAtual.equals("aluno")) {
                    salvarAluno();
                }else {
                    Toast.makeText(CadastroAdminActivity.this, "Por favor escolha um tipo de usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    public void addListenerOnButton() {
        radioUser = (RadioGroup) findViewById(R.id.radioUsers);
        radioUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioAdmin) {
                    cadAtual = "admin";
                    adminSelected();
                } else if (checkedId == R.id.radioProf) {
                    cadAtual = "prof";
                    profSelected();
                } else {
                    cadAtual = "aluno";
                    alunoSelected();
                }
            }
        });
    }

    public void adminSelected() {
        editTextNomeCompleto.setVisibility(View.VISIBLE);
        editTextCelular.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextRg.setVisibility(View.INVISIBLE);
        editTextCpf.setVisibility(View.INVISIBLE);
        editTextNasc.setVisibility(View.INVISIBLE);
        editTextNomeResp.setVisibility(View.INVISIBLE);
        editTextNascResp.setVisibility(View.INVISIBLE);
        editTextCelularResp.setVisibility(View.INVISIBLE);
        editTextCpfResp.setVisibility(View.INVISIBLE);
        editTextRgResp.setVisibility(View.INVISIBLE);
        editTextEmailResp.setVisibility(View.INVISIBLE);
        textViewResp.setVisibility(View.INVISIBLE);

    }

    public void profSelected() {
        editTextNomeCompleto.setVisibility(View.VISIBLE);
        editTextCelular.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextRg.setVisibility(View.VISIBLE);
        editTextCpf.setVisibility(View.VISIBLE);
        editTextNasc.setVisibility(View.VISIBLE);
        editTextNomeResp.setVisibility(View.INVISIBLE);
        editTextNascResp.setVisibility(View.INVISIBLE);
        editTextCelularResp.setVisibility(View.INVISIBLE);
        editTextCpfResp.setVisibility(View.INVISIBLE);
        editTextRgResp.setVisibility(View.INVISIBLE);
        editTextEmailResp.setVisibility(View.INVISIBLE);
        textViewResp.setVisibility(View.INVISIBLE);
    }

    public void alunoSelected() {

        editTextNomeCompleto.setVisibility(View.VISIBLE);
        editTextCelular.setVisibility(View.VISIBLE);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextRg.setVisibility(View.VISIBLE);
        editTextCpf.setVisibility(View.VISIBLE);
        editTextNasc.setVisibility(View.VISIBLE);
        editTextTurma.setVisibility(View.VISIBLE);

    }

    public void respVisibilityEnable() {

        editTextNomeResp.setVisibility(View.VISIBLE);
        editTextNascResp.setVisibility(View.VISIBLE);
        editTextRgResp.setVisibility(View.VISIBLE);
        editTextCpfResp.setVisibility(View.VISIBLE);
        editTextCelularResp.setVisibility(View.VISIBLE);
        editTextEmailResp.setVisibility(View.VISIBLE);
        textViewResp.setVisibility(View.VISIBLE);

    }

    public void respVisibilityDisable() {

        editTextNomeResp.setVisibility(View.INVISIBLE);
        editTextNascResp.setVisibility(View.INVISIBLE);
        editTextRgResp.setVisibility(View.INVISIBLE);
        editTextCpfResp.setVisibility(View.INVISIBLE);
        editTextCelularResp.setVisibility(View.INVISIBLE);
        editTextEmailResp.setVisibility(View.INVISIBLE);
        textViewResp.setVisibility(View.INVISIBLE);

    }





    public void calculateAlunoAge(int dayB, int monthB, int yearB) {
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayNow = calendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearB;
            if (monthB > monthNow) {
                age--;
            } else if (monthNow == monthB) {
                if (dayNow > dayB) {
                    age--;
                }
            }

            if (age < 18) {
                menor = true;
                respVisibilityEnable();
            } else {
                respVisibilityDisable();
            }
        }

        public void salvarAdmin(){
            nomeCompleto = editTextNomeCompleto.getText().toString();
            celular = editTextCelular.getText().toString();
            email = editTextEmail.getText().toString();

            if (TextUtils.isEmpty(nomeCompleto)){
                editTextNomeCompleto.setError("Por favor preencher este campo");
                editTextNomeCompleto.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(celular)){
                editTextCelular.setError("Por favor preencher este campo");
                editTextCelular.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)){
                editTextEmail.setError("Por favor preencher este campo");
                editTextEmail.requestFocus();
                return;
            }

            uid = auth.getCurrentUser().getUid();

            final Map<String, Object> newAdmin = new HashMap<>();
            newAdmin.put("admin", uid);
            newAdmin.put("nome", nomeCompleto);
            newAdmin.put("celular", celular);
            newAdmin.put("email", email);
            newAdmin.put("novo", true);
            newAdmin.put("status", true);
            newAdmin.put("tuser", cadAtual);
            newAdmin.put("uid", "");


            docRef = db.collection("Users").document(email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(CadastroAdminActivity.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                        } else {
                            db.collection("Users").document(email).set(newAdmin);
                            Toast.makeText(CadastroAdminActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(CadastroAdminActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });





        }

        public void salvarProf(){
            nomeCompleto = editTextNomeCompleto.getText().toString();
            celular = editTextCelular.getText().toString();
            email = editTextEmail.getText().toString();
            rg = editTextRg.getText().toString();
            cpf = editTextCpf.getText().toString();
            dataNasc = editTextNasc.getText().toString();

            if (TextUtils.isEmpty(nomeCompleto)){
                editTextNomeCompleto.setError("Por favor preencher este campo");
                editTextNomeCompleto.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(celular)){
                editTextCelular.setError("Por favor preencher este campo");
                editTextCelular.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)){
                editTextEmail.setError("Por favor preencher este campo");
                editTextEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(rg)){
                editTextRg.setError("Por favor preencher este campo");
                editTextRg.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(cpf)){
                editTextCpf.setError("Por favor preencher este campo");
                editTextCpf.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(dataNasc)){
                editTextNasc.setError("Por favor preencher este campo");
                editTextNasc.requestFocus();
                return;
            }

            uid = auth.getCurrentUser().getUid();

            final Map<String, Object> newProf = new HashMap<>();
            newProf.put("admin", uid);
            newProf.put("nome", nomeCompleto);
            newProf.put("celular", celular);
            newProf.put("email", email);
            newProf.put("rg", rg);
            newProf.put("cpf", cpf);
            newProf.put("datanasc", dataNasc);
            newProf.put("novo", true);
            newProf.put("status", true);
            newProf.put("tuser", cadAtual);
            newProf.put("uid", "");


            docRef = db.collection("Users").document(email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(CadastroAdminActivity.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                        } else {
                            db.collection("Users").document(email).set(newProf);
                            Toast.makeText(CadastroAdminActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(CadastroAdminActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

        public void salvarAluno() {

            if (menor.equals(false)) {

                nomeCompleto = editTextNomeCompleto.getText().toString();
                celular = editTextCelular.getText().toString();
                email = editTextEmail.getText().toString();
                rg = editTextRg.getText().toString();
                cpf = editTextCpf.getText().toString();
                dataNasc = editTextNasc.getText().toString();
                turma = editTextTurma.getText().toString();


                if (TextUtils.isEmpty(nomeCompleto)){
                    editTextNomeCompleto.setError("Por favor preencher este campo");
                    editTextNomeCompleto.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(celular)){
                    editTextCelular.setError("Por favor preencher este campo");
                    editTextCelular.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Por favor preencher este campo");
                    editTextEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(rg)){
                    editTextRg.setError("Por favor preencher este campo");
                    editTextRg.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cpf)){
                    editTextCpf.setError("Por favor preencher este campo");
                    editTextCpf.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(dataNasc)){
                    editTextNasc.setError("Por favor preencher este campo");
                    editTextNasc.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(turma)){
                    editTextTurma.setError("Por favor preencher este campo");
                    editTextTurma.requestFocus();
                    return;
                }

                uid = auth.getCurrentUser().getUid();

                final Map<String, Object> newAluno = new HashMap<>();
                newAluno.put("admin", uid);
                newAluno.put("nome", nomeCompleto);
                newAluno.put("celular", celular);
                newAluno.put("email", email);
                newAluno.put("rg", rg);
                newAluno.put("cpf", cpf);
                newAluno.put("datanasc", dataNasc);
                newAluno.put("turma", turma);
                newAluno.put("turmaid", turmaid);
                newAluno.put("novo", true);
                newAluno.put("status", true);
                newAluno.put("tuser", cadAtual);
                newAluno.put("uid", "");


                docRef = db.collection("Users").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(CadastroAdminActivity.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                            } else {
                                db.collection("Users").document(email).set(newAluno);
                                Toast.makeText(CadastroAdminActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(CadastroAdminActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else{

                nomeCompleto = editTextNomeCompleto.getText().toString();
                celular = editTextCelular.getText().toString();
                email = editTextEmail.getText().toString();
                rg = editTextRg.getText().toString();
                cpf = editTextCpf.getText().toString();
                dataNasc = editTextNasc.getText().toString();
                turma = editTextTurma.getText().toString();
                nomeCompletoResp = editTextNomeResp.getText().toString();
                dataNascResp = editTextNascResp.getText().toString();
                rgResp = editTextRgResp.getText().toString();
                cpfResp = editTextCpfResp.getText().toString();
                emailResp = editTextEmailResp.getText().toString();
                celularResp = editTextCelularResp.getText().toString();

                if (TextUtils.isEmpty(nomeCompleto)){
                    editTextNomeCompleto.setError("Por favor preencher este campo");
                    editTextNomeCompleto.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(celular)){
                    editTextCelular.setError("Por favor preencher este campo");
                    editTextCelular.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Por favor preencher este campo");
                    editTextEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(rg)){
                    editTextRg.setError("Por favor preencher este campo");
                    editTextRg.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cpf)){
                    editTextCpf.setError("Por favor preencher este campo");
                    editTextCpf.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(dataNasc)){
                    editTextNasc.setError("Por favor preencher este campo");
                    editTextNasc.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(nomeCompletoResp)) {
                    editTextNomeResp.setError("Por favor preencher este campo");
                    editTextNomeResp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(celularResp)) {
                    editTextCelularResp.setError("Por favor preencher este campo");
                    editTextCelularResp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(emailResp)) {
                    editTextEmailResp.setError("Por favor preencher este campo");
                    editTextEmailResp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(rgResp)) {
                    editTextRgResp.setError("Por favor preencher este campo");
                    editTextRgResp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cpfResp)) {
                    editTextCpfResp.setError("Por favor preencher este campo");
                    editTextCpfResp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(dataNascResp)) {
                    editTextNascResp.setError("Por favor preencher este campo");
                    editTextNascResp.requestFocus();
                    return;
                }

                uid = auth.getCurrentUser().getUid();

                final Map<String, Object> newAlunoResp = new HashMap<>();
                newAlunoResp.put("admin", uid);
                newAlunoResp.put("nome", nomeCompleto);
                newAlunoResp.put("celular", celular);
                newAlunoResp.put("email", email);
                newAlunoResp.put("rg", rg);
                newAlunoResp.put("cpf", cpf);
                newAlunoResp.put("datanasc", dataNasc);
                newAlunoResp.put("turma", turma);
                newAlunoResp.put("turmaid", turmaid);
                newAlunoResp.put("nomeresp", nomeCompletoResp);
                newAlunoResp.put("celularresp", celularResp);
                newAlunoResp.put("rgresp", rgResp);
                newAlunoResp.put("cpfresp", cpfResp);
                newAlunoResp.put("datanascresp", dataNascResp);
                newAlunoResp.put("novo", true);
                newAlunoResp.put("status", true);
                newAlunoResp.put("tuser", cadAtual);
                newAlunoResp.put("uid", "");


                docRef = db.collection("Users").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(CadastroAdminActivity.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                            } else {
                                db.collection("Users").document(email).set(newAlunoResp);
                                Toast.makeText(CadastroAdminActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(CadastroAdminActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3){
            if (resultCode==RESULT_OK){
                String turma = data.getStringExtra("descrip");
                turmaid = data.getStringExtra("id");
                editTextTurma.setText(turma);
            }
        }
    }
}





