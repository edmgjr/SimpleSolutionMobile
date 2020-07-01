package br.com.simplesolution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class TurmasAdapter extends FirestoreRecyclerAdapter<TurmasList, TurmasAdapter.TurmasHolder> {

    private OnItemClickListiner listener;


    public TurmasAdapter(@NonNull FirestoreRecyclerOptions<TurmasList>options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TurmasHolder holder, int position, @NonNull TurmasList model) {

        holder.turmaNome.setText(String.valueOf(model.getDescrip()));
        holder.idiomaNome.setText(String.valueOf(model.getIdioma()));
        holder.responsavelTurma.setText(String.valueOf(model.getProfname()));

    }

    @NonNull
    @Override
    public TurmasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.turmas_display_layout,parent,false);


        return new TurmasHolder(v);
    }


    class TurmasHolder extends RecyclerView.ViewHolder{
        TextView turmaNome, idiomaNome, responsavelTurma;


        public TurmasHolder(@NonNull View itemView) {
            super(itemView);

            turmaNome = itemView.findViewById(R.id.turmaNome);
            idiomaNome = itemView.findViewById(R.id.idiomaNome);
            responsavelTurma = itemView.findViewById(R.id.responsavelTurma);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListiner{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListiner listener){
        this.listener = listener;

    }



}
