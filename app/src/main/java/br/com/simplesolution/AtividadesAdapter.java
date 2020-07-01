package br.com.simplesolution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class AtividadesAdapter extends FirestoreRecyclerAdapter<AtividadesList, AtividadesAdapter.AtividadesHolder > {

    public AtividadesAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AtividadesHolder holder, int position, @NonNull AtividadesList model) {

        holder.atividadeNome.setText(String.valueOf(model.getDescrip()));
        holder.idiomaNomeAtividade.setText(String.valueOf(model.getIdioma()));
        holder.turmaNomeAtividade.setText(String.valueOf(model.getTurmanome()));
    }

    @NonNull
    @Override
    public AtividadesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.atividades_display_layout, parent, false);

        return new AtividadesHolder(v);
    }


    class AtividadesHolder extends RecyclerView.ViewHolder{

        TextView atividadeNome, turmaNomeAtividade, idiomaNomeAtividade;

        public AtividadesHolder(@NonNull View itemView) {
            super(itemView);

            atividadeNome = itemView.findViewById(R.id.atividadeNome);
            turmaNomeAtividade = itemView.findViewById(R.id.turmaNomeAtividade);
            idiomaNomeAtividade = itemView.findViewById(R.id.idiomaNomeAtividade);

        }

    }


}
