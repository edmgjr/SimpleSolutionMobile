package br.com.simplesolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchProfessorActivity extends AppCompatActivity {

    EditText editTextSearchProfessor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("Users");
    private CollectionReference idiomasRef = db.collection("Idiomas");
    private CollectionReference turmasRef = db.collection("Turmas");
    private UsuariosAdapter usuariosAdapter;
    private IdiomasAdapter idiomasAdapter;
    TurmasAdapter turmasAdapter;
    String tpesq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_professor);

        Intent intent = getIntent();
        tpesq = intent.getStringExtra("tpesq");

        if (tpesq.equals("prof")) {
            setUpRecyclerViewUserProf();

        }else if(tpesq.equals("idioma")){
            setUpRecyclerViewIdioma();
        }else if(tpesq.equals("turma")){
            setUpRecyclerViewTurmas();
        }

        editTextSearchProfessor = findViewById(R.id.editTextSearchProfessor);

        editTextSearchProfessor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(tpesq.equals("prof")) {
                    afterTextChangedUserProf();
                }else if(tpesq.equals("idioma")){
                    afterTextChangedIdiomas();
                }else if(tpesq.equals("turma")){
                    afterTextChangedTurmas();
                }
            }
        });
    }


    private void setUpRecyclerViewUserProf() {
        Query query = usersRef.whereEqualTo("tuser", "prof").orderBy("nome", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<UsuariosList> options = new FirestoreRecyclerOptions.Builder<UsuariosList>()
                .setQuery(query, UsuariosList.class)
                .build();
        usuariosAdapter = new UsuariosAdapter(options);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(usuariosAdapter);


        usuariosAdapter.setOnItemClickListener(new UsuariosAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String nome = documentSnapshot.getString("nome");
                String uid = documentSnapshot.getString("uid");

                Intent resultIntent = new Intent();
                resultIntent.putExtra("nome", nome);
                resultIntent.putExtra("uid", uid);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    private void setUpRecyclerViewIdioma() {
        Query query = idiomasRef.orderBy("idioma", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<IdiomasList> options = new FirestoreRecyclerOptions.Builder<IdiomasList>()
                .setQuery(query, IdiomasList.class)
                .build();
        idiomasAdapter = new IdiomasAdapter(options);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(idiomasAdapter);


        idiomasAdapter.setOnItemClickListener(new IdiomasAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String idioma = documentSnapshot.getString("idioma");
                String id = documentSnapshot.getId();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("idioma", idioma);
                resultIntent.putExtra("id", id);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    public void setUpRecyclerViewTurmas() {
        Query query = turmasRef.orderBy("descrip", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<TurmasList> options = new FirestoreRecyclerOptions.Builder<TurmasList>()
                .setQuery(query, TurmasList.class)
                .build();
        turmasAdapter = new TurmasAdapter(options);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(turmasAdapter);


        turmasAdapter.setOnItemClickListener(new TurmasAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String descrip = documentSnapshot.getString("descrip");
                String id = documentSnapshot.getId();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("descrip", descrip);
                resultIntent.putExtra("id", id);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (tpesq.equals("prof")) {
            usuariosAdapter.startListening();
        }else if (tpesq.equals("idioma")){
            idiomasAdapter.startListening();
        }else if(tpesq.equals("turma")){
            turmasAdapter.startListening();
        }


    }

    public void afterTextChangedUserProf(){
        String search = editTextSearchProfessor.getText().toString();

        Query query = usersRef.whereEqualTo("tuser", "prof").orderBy("nome").startAt(search).endAt(search + '\uf8ff');

        FirestoreRecyclerOptions<UsuariosList> options = new FirestoreRecyclerOptions.Builder<UsuariosList>()
                .setQuery(query, UsuariosList.class)
                .build();

        usuariosAdapter = new UsuariosAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(usuariosAdapter);
        usuariosAdapter.startListening();

        usuariosAdapter.setOnItemClickListener(new UsuariosAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String nome = documentSnapshot.getString("nome");
                String uid = documentSnapshot.getString("uid");

                Intent resultIntent = new Intent();
                resultIntent.putExtra("nome", nome);
                resultIntent.putExtra("uid", uid);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });


    }

    public void afterTextChangedIdiomas(){
        String search = editTextSearchProfessor.getText().toString();

        Query query = idiomasRef.orderBy("idioma").startAt(search).endAt(search + '\uf8ff');

        FirestoreRecyclerOptions<IdiomasList> options = new FirestoreRecyclerOptions.Builder<IdiomasList>()
                .setQuery(query, IdiomasList.class)
                .build();
        idiomasAdapter = new IdiomasAdapter(options);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(idiomasAdapter);


        idiomasAdapter.setOnItemClickListener(new IdiomasAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String idioma = documentSnapshot.getString("idioma");
                String id = documentSnapshot.getId();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("idioma", idioma);
                resultIntent.putExtra("id", id);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    public void afterTextChangedTurmas(){
        String search = editTextSearchProfessor.getText().toString();

        Query query = turmasRef.orderBy("descrip").startAt(search).endAt(search + '\uf8ff');

        FirestoreRecyclerOptions<TurmasList> options = new FirestoreRecyclerOptions.Builder<TurmasList>()
                .setQuery(query, TurmasList.class)
                .build();
        turmasAdapter = new TurmasAdapter(options);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearchProfessor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchProfessorActivity.this));
        recyclerView.setAdapter(turmasAdapter);


        turmasAdapter.setOnItemClickListener(new TurmasAdapter.OnItemClickListiner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String descrip = documentSnapshot.getString("descrip");
                String id = documentSnapshot.getId();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("descrip", descrip);
                resultIntent.putExtra("id", id);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}
