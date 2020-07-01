package br.com.simplesolution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class IdiomasAdapter extends FirestoreRecyclerAdapter<IdiomasList, IdiomasAdapter.IdiomasHolder> {

    private OnItemClickListiner listener;

    public IdiomasAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull IdiomasHolder holder, int position, @NonNull IdiomasList model) {
        holder.idiomaNome.setText(String.valueOf(model.getIdioma()));
        holder.nivelNome.setText(String.valueOf(model.getNivel()));

    }

    @NonNull
    @Override
    public IdiomasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.idiomas_display_layout, parent, false);

        return new IdiomasHolder(v);
    }


    class IdiomasHolder extends RecyclerView.ViewHolder {
        TextView idiomaNome, nivelNome;

        public IdiomasHolder(@NonNull View itemView) {
            super(itemView);

            idiomaNome = itemView.findViewById(R.id.idiomaNome);
            nivelNome = itemView.findViewById(R.id.nivelNome);

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

    public void setOnItemClickListener(IdiomasAdapter.OnItemClickListiner listener){
        this.listener = listener;

    }
}
