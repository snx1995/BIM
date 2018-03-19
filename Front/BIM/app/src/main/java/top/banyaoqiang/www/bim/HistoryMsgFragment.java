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

public class HistoryMsgFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_msg, container, false);
        initRecyclerView(view);
        initSwipeRefreshLayout(view);
        return view;
    }

    private void initSwipeRefreshLayout(View view){
        refreshLayout = view.findViewById(R.id.fragment_history_msg_swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.fragment_history_msg_recycler);
        BimMsgList msgList = new BimMsgList();
        msgList.initForDebug(BimMsgList.TYPE_HISTORY);
        MessageAdapter adapter = new MessageAdapter(parentActivity ,msgList);
        LinearLayoutManager manager = new LinearLayoutManager(parentActivity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    public void setParentActivity(AppCompatActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
