package adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_bidireccional.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    public static ArrayList<String> mensajes;

    public RecyclerAdapter(ArrayList<String> arraymensajes) {
        this.mensajes = arraymensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recadapter_items,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        String msg = mensajes.get(position);

        //OBTENGO EL NUMERO POR EL QUE EMPIEZA MI MENSAJE.
        String usuario = msg.substring(0,1);
        Log.d("mensaje",msg );

        //SI EL MENSAJE EMPIEZA POR 1 LO HE RECIBIDO YO SI EMPIEZA POR 0 LO HE ENVIADO.
        if(usuario.equals("R")){
            Log.d("recibido", msg.substring(1));
            holder.txtRecibido.setText( msg.substring(1));
            holder.txtEnviado.setVisibility(View.GONE);
        }if (usuario.equals("E")) {
            Log.d("enviado", msg.substring(1));
            holder.txtEnviado.setText(msg.substring(1));
            holder.txtRecibido.setVisibility(View.GONE);
        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView txtRecibido;
        TextView  txtEnviado;



        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            txtRecibido = (TextView)  itemView.findViewById(R.id.txt_recibido);
            txtEnviado  = (TextView)  itemView.findViewById(R.id.txt_enviado);
        }
    }
}

