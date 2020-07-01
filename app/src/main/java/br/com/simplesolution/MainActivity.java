package br.com.simplesolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextEmailHome;
    FirebaseFirestore db;
    Boolean novo;
    ConstraintLayout backgound;
    ProgressBar loading;
    String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        backgound = findViewById(R.id.graybackground);
        loading = findViewById(R.id.progressBar);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmailHome = findViewById(R.id.editTextEmailHome);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final String emailHome = editTextEmailHome.getText().toString().trim();

                if (TextUtils.isEmpty(emailHome)) {
                    editTextEmailHome.setError("É necessário um email válido");
                    return;
                }

                backgound.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);


                DocumentReference docRef = db.collection("Users").document(emailHome);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                novo = document.getBoolean("novo");
                                fullname = document.getString("nome");
                                if (novo.equals(true)){
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.putExtra("email", emailHome);
                                    intent.putExtra("novo", novo);
                                    intent.putExtra("fullname", fullname);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.putExtra("email", emailHome);
                                    intent.putExtra("fullname", fullname);
                                    startActivity(intent);
                                }
                            } else {
                                Intent intent = new Intent(MainActivity.this, SemContaActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Erro! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                backgound.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
}
