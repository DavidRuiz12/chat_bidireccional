package com.example.chat_bidireccional.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_bidireccional.modells.MENSAJE;
import com.example.chat_bidireccional.R;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.RecyclerHolder>{
    List<MENSAJE> listamensajes;

    public RecyclerAdapter2(List<MENSAJE> listamensajes){
        this.listamensajes = listamensajes;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list,parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter2.RecyclerHolder holder, int position) {
        MENSAJE mensaje = listamensajes.get(position);
        holder.txtmensaje.setText(mensaje.getEscrito());

    }

    @Override
    public int getItemCount() {
        return listamensajes.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView txtmensaje;
        TextView you;

        public RecyclerHolder(View itemview) {
            super(itemview);
            txtmensaje = (TextView) itemview.findViewById(R.id.txt_item_desc);
            you = (TextView) itemview.findViewById(R.id.txt_you);

        }
    }
}
