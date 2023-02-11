package com.example.chat_bidireccional;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    Thread mihilo;
    ServerSocket serverSocket;
    Socket misocket;

    public Servidor() {
        mihilo = new Thread(this);
        mihilo.start();
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9999);

            misocket = serverSocket.accept();

            DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());

            String mensaje_texto = flujo_entrada.readUTF();

            Log.d("servidor", mensaje_texto);

            misocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

