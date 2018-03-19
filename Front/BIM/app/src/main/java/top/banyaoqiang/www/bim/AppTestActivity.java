package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AppTestActivity extends AppCompatActivity {

    private static final String TAG = "Bim_debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_app);

        final BimUser user = new BimUser("Tom", 1234, R.drawable.send_tmp);

        Log.d(TAG, "onCreate: " + user.toJSONString());

        Button webView, launcher, login, main, message, address, detail, findFriend;
        message = findViewById(R.id.test_message_activity);
        launcher = findViewById(R.id.test_launcher_activity);
        webView = findViewById(R.id.test_web_activity);
        login = findViewById(R.id.test_login_activity);
        main = findViewById(R.id.test_main_activity);
        address = findViewById(R.id.test_address_book_activity);
        detail = findViewById(R.id.test_info_detail_activity);
        findFriend = findViewById(R.id.test_find_friend);

        message.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, MessageActivity.class);
                user.putToIntent(intent);
                startActivity(intent);
            }
        });

        launcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, LauncherActivity.class);
                startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, MainActivity.class);
                user.putToIntent(intent);
                startActivity(intent);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, AddressBookActivity.class);
                startActivity(intent);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, InfoDetailActivity.class);
                user.putToIntent(intent);
                startActivity(intent);
            }
        });

        findFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppTestActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
