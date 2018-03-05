package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoDetailActivity extends AppCompatActivity {
    private BimUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail_material);
        Intent intent = getIntent();
        user = new BimUser();
        user.fromIntent(intent);

        WebView webView = findViewById(R.id.info_detail_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("Http://39.106.156.178/imsg/signup.html");
        initToolbar(intent);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) actionBar.hide();
//
//        TitleBar titleBar = findViewById(R.id.info_detail_title_bar);
//        titleBar.setTitleText("资料卡");
//
//        Intent intent = getIntent();
//        final BimUser user = new BimUser();
//        user.fromIntent(intent);
//        setUserMessage(user);
//
//        Button sendMsg = findViewById(R.id.info_detail_send_message);
//        sendMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(InfoDetailActivity.this, MessageActivity.class);
//                user.putToIntent(intent1);
//                startActivity(intent1);
//            }
//        });

    }

    private void setUserMessage(BimUser user){
        ImageView image = findViewById(R.id.info_detail_image);
        TextView name = findViewById(R.id.info_detail_name);
        TextView id = findViewById(R.id.info_detail_id);

        image.setImageResource(user.getImage());
        name.setText(user.getName());
        id.setText(user.getStringId());

        Button sendMsg = findViewById(R.id.info_detail_send_message);
        Button addFriend = findViewById(R.id.info_detail_add_friend);
        Button deleteFriend = findViewById(R.id.info_detail_delete_friend);
        switch (user.getType()){
            case BimUser.FRIEND:
                sendMsg.setVisibility(View.VISIBLE);
                deleteFriend.setVisibility(View.VISIBLE);
                addFriend.setVisibility(View.GONE);
                break;
            case BimUser.STRANGER:
                sendMsg.setVisibility(View.GONE);
                deleteFriend.setVisibility(View.GONE);
                addFriend.setVisibility(View.VISIBLE);
                break;
            case BimUser.SELF:
                sendMsg.setVisibility(View.GONE);
                deleteFriend.setVisibility(View.GONE);
                addFriend.setVisibility(View.GONE);
                break;
        }
    }

    private void initToolbar(Intent intent){
        Toolbar toolbar = findViewById(R.id.info_detail_title_bar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.info_detail_collapsing_toolbar);
        ImageView image = findViewById(R.id.info_detail_image);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(user.getName());
    }
}
