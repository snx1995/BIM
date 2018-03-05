package top.banyaoqiang.www.bim;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AddressBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        TitleBar titleBar = findViewById(R.id.address_book_title_bar);
        titleBar.setTitleText("通讯录");
        titleBar.setMenuText("添加");

        RecyclerView recycler = findViewById(R.id.address_book_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        BimUserList list = new BimUserList();
        list.initForDebug();
        AddressBookAdapter adapter = new AddressBookAdapter(list,this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
