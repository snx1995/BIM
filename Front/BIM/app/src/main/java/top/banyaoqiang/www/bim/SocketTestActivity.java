package top.banyaoqiang.www.bim;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static top.banyaoqiang.www.bim.LauncherActivity.TAG;

public class SocketTestActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String clientRequest = (String) msg.obj;
            request.setText(clientRequest);
        }
    };

    private Button start;
    private Button stop;
    private TextView request;
    private SocketServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        request = findViewById(R.id.client_request);
        start = findViewById(R.id.start_server);
        stop = findViewById(R.id.stop_server);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server = new SocketServer();
                server.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (server != null) server.stop();
            }
        });

    }

    class SocketServer {
        private ServerSocket server;
        private BufferedReader reader;
        private Socket socket;

        void start(){
            try {
                server = new ServerSocket(54321);
                Log.d(TAG, "SocketServer started, waiting for client..");
                socket = server.accept();
                Log.d(TAG, "Client " + socket.hashCode() + " connected..");
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = reader.readLine();
                Message msg = new Message();
                msg.obj = request;
                handler.sendMessage(msg);
            } catch (Exception e) {
                Log.d(TAG, "start: exception " + e.getMessage());
            } finally {
                try {
                    server.close();
                    socket.close();
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        void stop(){

            try {
                if (socket != null) socket.close();
                if (reader != null) reader.close();
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


