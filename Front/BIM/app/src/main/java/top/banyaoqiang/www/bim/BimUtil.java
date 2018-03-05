package top.banyaoqiang.www.bim;

import android.widget.EditText;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by byq on 18-2-28.
 */

public class BimUtil {
}

abstract class SendGetRequest{
    private String url;

    public SendGetRequest(String url) {
        this.url = url;
    }

    public void send(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    onResponse(responseString);
                }catch (Exception e){
                    onError(e);
                }
            }
        }).start();
    }

    abstract void onResponse(String response);
    abstract void onError(Exception e);
}

class SendPostRequest{

}