package top.banyaoqiang.www.bim;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    private static final int UPDATE_RESULT = 0;

    private String TAG = "Bim_debug";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_RESULT:
                    recyclerView.setAdapter((SearchResultAdapter) msg.obj);
            }
        }
    };

    private TitleBar titleBar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        titleBar = findViewById(R.id.search_title_bar);
        titleBar.setTitleText(R.string.search);
        titleBar.setMenuButtonEnabled(false);

        final Button search = findViewById(R.id.search_btn);
        final EditText input = findViewById(R.id.search_edit_text);
        recyclerView = findViewById(R.id.search_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(manager);
        SearchResultAdapter adapter = new SearchResultAdapter(new BimUserDetailList());
        recyclerView.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, "搜索中..", Toast.LENGTH_SHORT).show();
                String text = input.getText().toString();
                String url = BimValues.SERVER_ADDRESS +
                        "/bim/search.php?key="+text;
                new SendGetRequest(url){
                    @Override
                    void onResponse(String response) {
                        if(response.equals("null"))
                            Toast.makeText(SearchActivity.this, "未找到相关用户，请重试!", Toast.LENGTH_SHORT).show();
                        else{
                            BimUserDetailList userList = new BimUserDetailList();
                            userList.fromJSONString(response);
                            Log.d(TAG, "onResponse: " + response);
                            Message msg = new Message();
                            msg.what = UPDATE_RESULT;
                            msg.obj = new SearchResultAdapter(userList, SearchActivity.this);
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    void onError(Exception e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                }.send();
            }
        });
    }
}
