package top.banyaoqiang.www.bim;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.widget.Toast;

public class AddressBookActivity extends AppCompatActivity {
    private static final String TAG = "Bim_debug";

    private BimUserList userList;
    private RecyclerView recycler;


    private static final int UPDATE_ADDRESS_BOOK = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_ADDRESS_BOOK:
                    recycler.setAdapter(new AddressBookAdapter(userList, AddressBookActivity.this));

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        Toolbar toolbar = findViewById(R.id.address_book_tool_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        TitleBar titleBar = findViewById(R.id.address_book_title_bar);
//        titleBar.setTitleText("通讯录");
//        titleBar.setMenuText("添加");

        recycler = findViewById(R.id.address_book_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        BimUserList list = new BimUserList();
        list.initForDebug();
        AddressBookAdapter adapter = new AddressBookAdapter(list,this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        initSwipeLayout();
    }

    private void initSwipeLayout(){
        final SwipeRefreshLayout swipe = findViewById(R.id.address_book_swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url = BimValues.BIM_PROJECT_SERVER_ADDRESS + "/friends.json";
                new SendGetRequest(url){
                    @Override
                    void onResponse(String response) {
                        Log.d(TAG, "AddressBook.onResponse: " + response);
                        BimUserList tmp = userList;
                        userList = new BimUserList();
                        boolean flag = userList.fromJSONString(response);
                        if(flag){
                            Message msg = new Message();
                            msg.what = UPDATE_ADDRESS_BOOK;
                            handler.sendMessage(msg);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipe.setRefreshing(false);
                                }
                            });
                        } else {
                            userList = tmp;
                            Toast.makeText(AddressBookActivity.this, "Wrong info returned.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    void onError(Exception e) {

                    }
                }.send();
            }
        });
    }

    private void fetchAddressBook(){

    }
}
