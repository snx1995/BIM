package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        String TAG = "Bim_debug";

        BimUser user = new BimUser("Tom",1234,R.drawable.ic_launcher_background);
        BimMsgList list = new BimMsgList(user);
        for(int i=0;i<10;i++){
            list.add(new BimMsg(user,user,"Hello" + i,BimMsg.TYPE_SEND));
        }

        String listJson = list.toJSONString();

        Log.d(TAG, "onCreate: " + listJson);
        Log.d(TAG, "onCreate: " + list.toJSONString());

        BimMsgList list2 = new BimMsgList();
        list2.fromJSONString(listJson);
        Log.d(TAG, "onCreate: " + list2.toJSONString());

        Intent intent = new Intent(LauncherActivity.this,MessageActivity.class);
        startActivity(intent);
    }
}
