package top.banyaoqiang.www.bim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by byq on 18-3-14.
 */

public class AddressBookFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppCompatActivity parentActivity;

    public void setParentActivity(AppCompatActivity parentActivity) {
        this.parentActivity = parentActivity;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);
        initRecyclerView(view);
        initSwipeRefreshView(view);
        return view;
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.fragment_address_book_recycler);
        BimUserList userList = new BimUserList();
        userList.initForDebug();
        AddressBookAdapter adapter = new AddressBookAdapter(userList, parentActivity);
        LinearLayoutManager manager = new LinearLayoutManager(parentActivity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void initSwipeRefreshView(View view){
        swipeRefreshLayout = view.findViewById(R.id.fragment_address_book_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        parentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
