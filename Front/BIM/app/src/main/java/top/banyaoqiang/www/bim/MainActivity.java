package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Bim_debug";

    private SwipeRefreshLayout refreshLayout;
    private DrawerLayout drawerLayout;

    private BimUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        user = new BimUser();
        user.fromIntent(getIntent());

        android.support.v7.widget.Toolbar toolbar =findViewById(R.id.main_drawer_tool_bar);
        setSupportActionBar(toolbar);

        initDrawerLayout();
        initButtons();

        BimMsgList msgList = new BimMsgList();
        msgList.initForDebug(BimMsgList.TYPE_HISTORY);

        initRecyclerView(msgList);
        initSwipeRefreshLayout();

    }

    private void initButtons(){
        Button address = findViewById(R.id.main_address_book);
        Button clearMsg = findViewById(R.id.main_clear_message);
        Button search = findViewById(R.id.main_search);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddressBookActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(BimMsgList msgList){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        MessageAdapter adapter = new MessageAdapter(this,msgList);
        RecyclerView recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = findViewById(R.id.main_swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMsgs();
            }
        });
    }

    private void refreshMsgs(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    Log.d(TAG, "run: " + e.getMessage());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initDrawerLayout(){
        drawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_btn_icon);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
