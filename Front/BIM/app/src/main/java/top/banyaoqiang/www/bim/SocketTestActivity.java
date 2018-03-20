package top.banyaoqiang.www.bim;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static top.banyaoqiang.www.bim.LauncherActivity.TAG;

public class SocketTestActivity extends AppCompatActivity {


    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        start = findViewById(R.id.start_server);
        stop = findViewById(R.id.stop_server);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client client = new Client();
                client.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    class Client {
        private Socket socket;
        EditText input;
        Button send;
        BufferedWriter writer;
        BufferedReader reader;

        void start() {
            input = findViewById(R.id.socket_input);
            send = findViewById(R.id.socket_send);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("39.106.156.178", 54321);
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        startServerResponseListener(reader);
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String text = input.getText().toString();
                                if (text.length()>0) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                writer.write(text + "\n");
                                                writer.flush();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            }
                        });

                    } catch (Exception e) {
                        Log.d(TAG, "start: " + e.getMessage());
                    }
                }
            }).start();
        }

        private void startServerResponseListener(final BufferedReader reader) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String text;
                        while ((text = reader.readLine()) != null) {
                            final String textFinal = text;
                            Log.d(TAG, "run: from server: " +text);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SocketTestActivity.this, textFinal, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}


