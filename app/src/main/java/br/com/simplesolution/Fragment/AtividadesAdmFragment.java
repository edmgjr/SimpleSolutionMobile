package br.com.simplesolution.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.simplesolution.AtividadesAdapter;
import br.com.simplesolution.AtividadesList;
import br.com.simplesolution.R;
import br.com.simplesolution.TurmasAdapter;


public class AtividadesAdmFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference atividadesRef = db.collection("Atividades");
    private AtividadesAdapter adapter;
    public View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_atividades_adm, container, false);
        setUpRecyclerView();

            return v;
    }



    private void setUpRecyclerView(){
        Query query = atividadesRef.orderBy("descrip", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<AtividadesList> options = new FirestoreRecyclerOptions.Builder<AtividadesList>()
                .setQuery(query, AtividadesList.class)
                .build();

        adapter = new AtividadesAdapter(options);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_viewAtividades);
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
