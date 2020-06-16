package com.kowshik.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.kowshik.bluetooth.MainActivity.bsocket;

public class comunication extends AppCompatActivity {

    Button send1,send2,send3,send4,send5;
    TextView msg_box;
    SendReceive sendReceive;

    DrawerLayout mydrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_nav);

        mydrawerLayout=findViewById(R.id.drawerlayout);
        NavigationView mynavigationView;
        mynavigationView=findViewById(R.id.nav_view);
        mynavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                mydrawerLayout.closeDrawers();
                return true;
            }
        });


        BluetoothSocket socket=par_socket.getSocket();
        sendReceive=new SendReceive(socket);


        sendReceive.start();


        send1=(Button) findViewById(R.id.send1);
        send2=(Button) findViewById(R.id.send2);
        send3=(Button) findViewById(R.id.send3);
        send4=(Button) findViewById(R.id.send4);
        send5=(Button) findViewById(R.id.send5);
        msg_box =(TextView) findViewById(R.id.msg);

        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String string= String.valueOf("hi");//writeMsg.getText());
                sendReceive.write(string.getBytes());
            }
        });
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= String.valueOf("hello");//writeMsg.getText());
                sendReceive.write(string.getBytes());
            }
        });
        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= String.valueOf("lalaa");//writeMsg.getText());
                sendReceive.write(string.getBytes());
            }
        });
        send4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= String.valueOf("bubye");//writeMsg.getText());
                sendReceive.write(string.getBytes());
            }
        });
        send5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= String.valueOf("yeah!");//writeMsg.getText());
                sendReceive.write(string.getBytes());
            }
        });
    }
    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive (BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;

            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=tempIn;
            outputStream=tempOut;
        }

        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes;

            while (true)
            {
                try {

                    inputStream.read(buffer);
                    String tempMsg=new String(buffer, StandardCharsets.UTF_8);
                    msg_box.setText(tempMsg);

                   // String tempMsg =new String(readBuff,0,msg.arg1);
                   // msg_box.setText(tempMsg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try
            {
                outputStream.write(bytes);
            } catch (IOException e)

            {
                e.printStackTrace();
            }
        }
    }

}
