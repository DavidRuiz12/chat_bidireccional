package com.example.chat_bidireccional;

import static adapter.RecyclerAdapter.mensajes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import adapter.RecyclerAdapter;

public class MainActivity extends AppCompatActivity  {

    TextView nombre;
    TextView ip;
    TextView area;
    TextView mensaje;
    Button enviar;
    Socket socket;
    String iptxt;
    RecyclerView recview;
    RecyclerAdapter recAdapter;
    ArrayList<String> lista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recview = (RecyclerView) findViewById(R.id.area);
        nombre = (TextView) findViewById(R.id.txt_nombre);
        ip = (TextView) findViewById(R.id.txt_ip);
        mensaje = (TextView) findViewById(R.id.txt_mensaje);
        enviar = (Button) findViewById(R.id.btn_enviar);
        lista = new ArrayList<String>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recview.setLayoutManager(layoutManager);
        recAdapter = new RecyclerAdapter(lista);
        recview.setAdapter(recAdapter);
        //El servidor esta activo.
        recibir();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iptxt = ip.getText().toString();
               enviar();

            }
        });

    }


    public void recibir() {
        Thread hilo1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket servidor = new ServerSocket(9999);
                    while (true) {
                        Socket misocket = servidor.accept();
                        DataInputStream dis = new DataInputStream(misocket.getInputStream());

                        String mensaje_recibido = dis.readUTF();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mensajes.add("R" + mensaje_recibido);
                                recAdapter.setMensajes(mensajes);
                                Log.d("recibido", mensajes.toString());
                                recAdapter.notifyDataSetChanged();
                            }
                        });


                        misocket.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        hilo1.start();
    }

    public void enviar(){
            Thread hilo2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket misocket;
                    try {
                            misocket = new Socket(iptxt,9999);
                            DataOutputStream dos = new DataOutputStream(misocket.getOutputStream());
                            String mensaje_enviado = mensaje.getText().toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mensajes.add("E" + mensaje_enviado );
                                    recAdapter.setMensajes(mensajes);
                                    recAdapter.notifyDataSetChanged();
                                    mensaje.setText("");
                                }
                            });

                            dos.writeUTF(mensaje_enviado);
                            dos.close();
                            misocket.close();

                    } catch (IOException ex) {
                        Log.d("Hola","entra");
                        System.out.println(ex.getMessage());
                    }
                }
            });
            hilo2.start();
        }


    }
