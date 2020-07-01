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

public class UsuariosAdapter extends FirestoreRecyclerAdapter <UsuariosList, UsuariosAdapter.UsuariosHolder> {

    private OnItemClickListiner listener;

    public UsuariosAdapter(@NonNull FirestoreRecyclerOptions<UsuariosList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UsuariosHolder holder, int position, @NonNull UsuariosList model) {
        holder.nomeUser.setText(String.valueOf(model.getNome()));
        holder.tUser.setText(String.valueOf(model.getTuser()));
        String statusString = String.valueOf(model.getStatus());
        holder.statusUser.setText("Ativo? " + statusString);
    }

    @NonNull
    @Override
    public UsuariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_display_layout, parent, false);

        return new UsuariosHolder(v);
    }

    class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView nomeUser, tUser, statusUser;

        public UsuariosHolder(@NonNull View itemView) {
            super(itemView);

            nomeUser = itemView.findViewById(R.id.userNome);
            tUser = itemView.findViewById(R.id.userTipoUser);
            statusUser = itemView.findViewById(R.id.userStatus);

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
