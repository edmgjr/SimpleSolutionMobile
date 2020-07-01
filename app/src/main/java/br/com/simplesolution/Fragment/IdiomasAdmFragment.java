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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.simplesolution.IdiomasAdapter;
import br.com.simplesolution.IdiomasList;
import br.com.simplesolution.R;
import br.com.simplesolution.UsuariosAdapter;


public class IdiomasAdmFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference idiomasRef = db.collection("Idiomas");
    private IdiomasAdapter adapter;
    public View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_idiomas_adm, container, false);

        setUpRecyclerView();

        return v;
    }

    private void setUpRecyclerView(){
        Query query = idiomasRef.orderBy("idioma", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<IdiomasList> options = new FirestoreRecyclerOptions.Builder<IdiomasList>()
                .setQuery(query, IdiomasList.class)
                .build();

        adapter = new IdiomasAdapter(options);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler_viewIdiomas);
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
