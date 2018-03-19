package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class LauncherActivity extends AppCompatActivity {
    static final String USER_INFO_FILE_NAME = "b1392732";
    static final String TAG = "Bim_debug";
    static final String USER_NAME = "user_name";
    static final String PASSWORD = "password";
    static final String INIT_INFO = "init_info";
    static final String LOGIN_FAILED_INFO = "Your password has been changed, please login again.";
    static final String FILE_ERROR = "Something wrong with bim's user_info_file";
    static final String RETURN_INFO_ERROR = "Server returned illegal info.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        test();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent;
                JSONObject userJson;
                String userInfo = BimFile.readFromFile(LauncherActivity.this, USER_INFO_FILE_NAME);
                if (userInfo.length()>0) {
                    try {
                        userJson = new JSONObject(userInfo);
                        String userName = userJson.getString(USER_NAME);
                        String password = userJson.getString(PASSWORD);

                        String url = BimValues.BIM_PROJECT_SERVER_ADDRESS + "/login.php?name=" + userName + "&password=" + password;
                        new SendGetRequest(url){
                            @Override
                            void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                if(response.equals(ERROR)){
                                    Intent intent1 = new Intent(LauncherActivity.this, LoginActivity.class);
                                    intent1.putExtra(INIT_INFO, LOGIN_FAILED_INFO);
                                    startActivity(intent1);
                                } else {
                                    BimUser user = new BimUser();
                                    Boolean flag = user.fromJSONString(response);
                                    if(flag){
                                        Intent intent2 = new Intent(LauncherActivity.this, MainActivity.class);
                                        user.putToIntent(intent2);
                                        startActivity(intent2);
                                        finish();
                                    } else {
                                        Intent intent2 = new Intent(LauncherActivity.this, LoginActivity.class);
                                        intent2.putExtra(INIT_INFO, RETURN_INFO_ERROR);
                                        startActivity(intent2);
                                    }
                                }
                            }

                            @Override
                            void onError(Exception e) {

                            }
                        }.send();
                    } catch (Exception e) {
                        e.printStackTrace();
                        intent = new Intent(LauncherActivity.this, LoginActivity.class);
                        intent.putExtra(INIT_INFO, FILE_ERROR);
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(LauncherActivity.this, LoginActivity.class);
                    intent.putExtra(INIT_INFO, "Hello");
                    startActivity(intent);
                }
                finish();
            }
        }).start();
    }

    private void test(){
        BimDB db = new BimDB(this, "bim", null, 1);

        BimUser user = new BimUser("Tom", 100000, R.drawable.send_tmp);
        BimMsg msg = new BimMsg(user,user,"Hello", BimMsg.TYPE_SEND);

        SQLiteDatabase sqlDB = db.getWritableDatabase();

        msg.saveToDatabase(sqlDB);
        Cursor cursor = sqlDB.query("message", null, null, null, null, null, null);
        cursor.moveToNext();
        Log.d(TAG, "test: " + cursor.getString(cursor.getColumnIndex("text")));
        cursor.close();
    }
}
