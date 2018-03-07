package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {
    static final String USER_INFO_FILE_NAME = "b1392732";
    static final String TAG = "Bim_debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Intent intent;

        String userInfo =
                BimFile.readFromFile(this, USER_INFO_FILE_NAME);

        Log.d(TAG, "onCreate: readFromFile: " + userInfo);
        BimUser user = new BimUser();
        Boolean result = user.fromJSONString(userInfo);
        if(result) {
            intent = new Intent(this, MainActivity.class);
            user.putToIntent(intent);
            startActivity(intent);
        } else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
