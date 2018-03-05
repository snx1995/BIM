package top.banyaoqiang.www.bim;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

public class MessageActivity extends AppCompatActivity {
    private BimUser friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        String TAG = "Bim_debug";

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        TitleBar titleBar = findViewById(R.id.message_title_bar);

        friend = new BimUser();
        friend.fromIntent(getIntent());

        titleBar.setTitleText(friend.getName());


        BimMsgList msgList = new BimMsgList();
        msgList.initForDebug();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        MessageAdapter adapter = new MessageAdapter(this,msgList);
        RecyclerView recyclerView = findViewById(R.id.message_recycler);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
