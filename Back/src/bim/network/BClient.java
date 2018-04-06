package bim.network;

import java.net.Socket;

public class BClient {
    private Socket client;

    public void sendRequest(BRequest request, OnResponseListener listener) {
        new Thread(() -> {

        }).start();
    }

    public BRequest acceptRequest() {
        // TODO
        return null;
    }
}
