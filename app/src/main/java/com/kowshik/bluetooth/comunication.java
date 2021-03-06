package com.kowshik.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    BluetoothAdapter bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    int REQUEST_ENABLE_BLUETOOTH = 1;
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
        msg_box.setText("Helllllllooooo");

        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= new String("hi");//writeMsg.getText());
                sendReceive.write(string.getBytes(),string.getBytes().length);
            }
        });
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= new String("hello");//writeMsg.getText());

                sendReceive.write(string.getBytes(),string.getBytes().length);
            }
        });
        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= new String("lala");//writeMsg.getText());
                sendReceive.write(string.getBytes(),string.getBytes().length);
            }
        });
        send4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= new String("bye");//writeMsg.getText());
                sendReceive.write(string.getBytes(),string.getBytes().length);
            }
        });
        send5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string= new String("yea");//writeMsg.getText());
                sendReceive.write(string.getBytes(),string.getBytes().length);
            }
        });
    }
    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;
        private  int aa;

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


            while (true)
            {
                aa=1024;
                byte[] buffer=new byte[aa];
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

        public void write(byte[] bytes,int a)
        {
            try
            {
                aa=a;
                outputStream.write(bytes);
            } catch (IOException e)

            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bluetoothAdapter.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
        }

    }
}
