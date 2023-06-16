package com.example.chat_bidireccional.controllers;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Clase donde se crea el servidor
public class Servidor implements Runnable {
    //Declaracion de variables
    Thread mihilo;
    ServerSocket serverSocket;
    Socket misocket;

    public Servidor() {
        mihilo = new Thread(this);
        mihilo.start();
    }


    //Creamos un hilo.
    @Override
    public void run() {
        try {
            //Creamos el socket del servidor y le asignamos un puerto.
            serverSocket = new ServerSocket(9999);
            //Cuando se establece una conexion exitosa, devuelve un objeto socket
            misocket = serverSocket.accept();
            //Obtiene el flujo de entrada desde el socket del cliente.
            DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
            //Se lee el mesnsaje del cliente enviado.
            String mensaje_texto = flujo_entrada.readUTF();

            Log.d("servidor", mensaje_texto);
            //Se cierra el socket.
            misocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

