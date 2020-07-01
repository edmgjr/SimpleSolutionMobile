package br.com.simplesolution.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.simplesolution.CadastroAdminActivity;
import br.com.simplesolution.CadastroTurmaActivity;
import br.com.simplesolution.R;
import br.com.simplesolution.TurmasAdapter;
import br.com.simplesolution.TurmasList;


public class TurmasAdmFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference turmasRef = db.collection("Turmas");
    private TurmasAdapter adapter;
    public View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_turmas_adm, container, false);


        Button novaTurma = (Button)v.findViewById(R.id.buttonNovaTurma);


        novaTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CadastroTurmaActivity.class);
                startActivity(intent);

            }
        });

        setUpRecyclerView();


        return v;

    }

    private void setUpRecyclerView(){
        Query query = turmasRef.orderBy("descrip", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<TurmasList> options = new FirestoreRecyclerOptions.Builder<TurmasList>()
                .setQuery(query, TurmasList.class)
                .build();

        adapter = new TurmasAdapter(options);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_viewTurma);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
