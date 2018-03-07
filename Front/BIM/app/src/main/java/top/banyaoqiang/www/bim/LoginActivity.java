package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final int LOGIN_FAILED = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_FAILED:
                    Toast.makeText(LoginActivity.this, R.string.wrong_name_or_password, Toast.LENGTH_SHORT).show();
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    LinearLayout mainLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.login_button);
        final EditText name = findViewById(R.id.login_user_id);
        final EditText password = findViewById(R.id.login_password);
        TextView forget = findViewById(R.id.login_forget_password);
        TextView signUp = findViewById(R.id.login_sign_up);

        mainLayout = findViewById(R.id.login_main_layout);
        progressBar = findViewById(R.id.login_progress_bar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                String userName = name.getText().toString();
                String passwd = BimSafe.passwordEncode(password.getText().toString());
                String url = BimValues.BIM_PROJECT_SERVER_ADDRESS + "/login.php?" +
                        "name=" + userName +
                        "&password=" + passwd;

                Log.d("Bim_debug", "onResponse: " + url);
                new SendGetRequest(url){
                    @Override
                    void onResponse(String response) {
                        Log.d("Bim_debug", "onResponse: " + response);
                        if(response.equals(ERROR)){
                            Log.d("Bim_debug", "onResponse: 111111");
                            Message msg = new Message();
                            msg.what = LOGIN_FAILED;
                            handler.sendMessage(msg);
                        } else{
                          BimUser user = new BimUser();
                          user.fromJSONString(response);
                          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          user.putToIntent(intent);
                          BimFile.saveToFile(LoginActivity.this, LauncherActivity.USER_INFO_FILE_NAME, user);
                          startActivity(intent);
                          finish();
                        }
                    }

                    @Override
                    void onError(Exception e) {

                    }
                }.send();

            }
        });
    }
}
