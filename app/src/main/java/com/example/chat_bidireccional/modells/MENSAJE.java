package com.example.chat_bidireccional.modells;

//Clase mensaje.
public class MENSAJE {
    private String escrito;

    public MENSAJE(String escrito){
        this.escrito = escrito;
    }

    public String getEscrito() {
        return escrito;
    }

    public void setEscrito(String escrito) {
        this.escrito = escrito;
    }
}
