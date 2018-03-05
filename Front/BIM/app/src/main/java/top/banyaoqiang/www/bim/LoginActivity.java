package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_button);
        EditText name = findViewById(R.id.login_user_id);
        EditText password = findViewById(R.id.login_password);
        TextView forget = findViewById(R.id.login_forget_password);
        TextView signUp = findViewById(R.id.login_sign_up);

        final LinearLayout mainLayout = findViewById(R.id.login_main_layout);
        final ProgressBar progressBar = findViewById(R.id.login_progress_bar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
