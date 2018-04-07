package top.banyaoqiang.www.bim.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import top.banyaoqiang.www.bim.network.Acceptable;
import top.banyaoqiang.www.bim.network.BimNet;

import static top.banyaoqiang.www.bim.Manager.TAG;

public class BimLocalUser extends BimUser {
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;

    public BimLocalUser(Socket client) {
        this.client = client;
        try {
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (Exception e) {
            Log.d(TAG, "BimLocalUser: " + e.getMessage());
        }
    }

    public Acceptable accept() {
        return BimNet.accept(reader);
    }

    public void send(Acceptable a) {
        try {
            writer.write(a.toString());
            writer.flush();
        } catch (Exception e) {
            Log.d(TAG, "send: " + e.getMessage());
        }
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
        try {
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (Exception e) {
            Log.d(TAG, "BimLocalUser: " + e.getMessage());
        }
    }
}
