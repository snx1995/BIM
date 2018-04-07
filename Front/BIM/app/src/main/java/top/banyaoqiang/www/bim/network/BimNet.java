package top.banyaoqiang.www.bim.network;

import android.util.Log;

import java.io.BufferedReader;

import static top.banyaoqiang.www.bim.Manager.TAG;

public class BimNet {

    /**
     * receive request or response or any other object implemented
     * Acceptable interface from server
     * @param reader
     * @return
     */
    public static Acceptable accept(BufferedReader reader) {
        String s;
        try {
            s = reader.readLine();
            String[] params;
            if (s.equals(Acceptable.TYPE_REQUEST)) {
                BimRequest request = new BimRequest();
                s = reader.readLine();
                request.setAction(s);
                s = reader.readLine();
                params = s.split(" ");
                request.setProtocolVersion(params[0]);
                request.setAppVersion(params[1]);
                s = reader.readLine();
                request.setConnect(s);
                s = reader.readLine();
                request.setBody(s);
                return request;
            } else if (s.equals(Acceptable.TYPE_RESPONSE)) {
                BimResponse response = new BimResponse();
                s = reader.readLine();
                response.setAction(s);
                s = reader.readLine();
                params = s.split(" ");
                response.setProtocolVersion(params[0]);
                response.setAppVersion(params[1]);
                s = reader.readLine();
                response.setBody(s);
                return response;
            } else {
                Log.d(TAG, "invalid msg received..");
            }
        } catch (Exception e) {

            Log.d(TAG, "accept: " + e.getMessage());
        }
        return null;
    }
}
