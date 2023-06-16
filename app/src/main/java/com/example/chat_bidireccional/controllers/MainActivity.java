package com.example.chat_bidireccional.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chat_bidireccional.modells.MENSAJE;
import com.example.chat_bidireccional.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import com.example.chat_bidireccional.adapter.RecyclerAdapter2;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter2 recyclerAdapter2;

    //Creacion de los atributos.
    TextView ip;
    TextView mensaje;
    Button enviar;
    Socket socket;
    String iptxt;
    String m;
    boolean aqui = false;
    boolean you = false;

    ArrayList<String> mensajes = new ArrayList<String>();
    ArrayList<MENSAJE> list = new ArrayList<MENSAJE>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaracion de los atributos.
        ip = (TextView) findViewById(R.id.txt_ip);
        mensaje = (TextView) findViewById(R.id.txt_mensaje);
        enviar = (Button) findViewById(R.id.btn_enviar);

        //Iniciamos el servidor. Este estara a la escucha siempre,
        recibir();
        
        //Creamos el listener de del boton enviar, que se encarga de introducir la Ip dentro de un string y ademas inicia el metodo enviar.
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iptxt = ip.getText().toString();
                enviar();
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerAdapter2 = new RecyclerAdapter2(list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recyclerAdapter2);
        recyclerView.setLayoutManager(layoutManager);

    }



    public void recibir() {
        Thread hilo1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Creacion del Socket del servidor.
                    ServerSocket servidor = new ServerSocket(9999);
                    //Creacion del bucle infinito para que el servdor este a la escucha siempre.
                    while (true) {
                        Socket misocket = servidor.accept();
                        DataInputStream dis = new DataInputStream(misocket.getInputStream());

                        String mensaje_recibido = dis.readUTF();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               mensajes.add(mensaje_recibido);
                                m = mensaje_recibido;
                                list.add(new MENSAJE(m));
                                aqui = true;
                                if (aqui == true){
                                    mostrarmensaje();
                                }
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
        //Creamos el hilo para poder enviar el mensaje.
            Thread hilo2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Dentro del hilo creamos e iniciamos el Socket.
                    Socket misocket;
                    try {
                            misocket = new Socket(iptxt,9999);
                            //Creacion del flujo por donde se van a mandar los mensajes.
                            DataOutputStream dos = new DataOutputStream(misocket.getOutputStream());
                            //Introducimos el mesaje que se envia dentro de un string.
                            String mensaje_enviado = mensaje.getText().toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Este hilo se utiliza para mostrar los mensajes recibidos dentro del area de texto.
                                    mensajes.add(mensaje_enviado);
                                    m = mensaje_enviado;
                                    list.add(new MENSAJE(m));
                                    aqui = true;
                                    if (aqui == true){
                                        mostrarmensaje();
                                    }
                                }
                            });

                            dos.writeUTF(mensaje_enviado);
                            dos.close();
                            misocket.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }


                }
            });
            //Volvemos a iniciar el hilo.
            hilo2.start();
            you = true;
        }

        //Metodo que se encarga de mostrar el mensaje enviado y recibido.
        private void mostrarmensaje(){
        recyclerAdapter2 = new RecyclerAdapter2(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter2);
        recyclerView.setLayoutManager(layoutManager);
        aqui = false;
        }

}
