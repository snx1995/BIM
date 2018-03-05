package top.banyaoqiang.www.bim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static top.banyaoqiang.www.bim.BimValues.TAG;

/**
 * Created by byq on 18-3-1.
 */

public class BimAdapter{
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HISTORY = 1;
}

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private Context context;
    private BimMsgList msgList;

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout sendLayout,receiveLayout,historyLayout;

        public ViewHolder(View view) {
            super(view);
            sendLayout = view.findViewById(R.id.bubble_send_layout);
            receiveLayout = view.findViewById(R.id.bubble_receive_layout);
            historyLayout = view.findViewById(R.id.bubble_history_layout);
        }
    }

    public MessageAdapter() {

    }

    public MessageAdapter(Context context, BimMsgList msgList) {
        this.context = context;
        this.msgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bubble,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BimMsg msg = msgList.get(position);
        setMessage(msg,holder);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    private void setMessage(BimMsg msg, ViewHolder holder){
        switch (msg.getType()){
            case BimMsg.TYPE_RECEIVE:
                LinearLayout receive = holder.receiveLayout;
                receive.setVisibility(View.VISIBLE);
                holder.historyLayout.setVisibility(View.GONE);
                holder.sendLayout.setVisibility(View.GONE);
                ((ImageView)receive.findViewById(R.id.bubble_receiver_image)).setImageResource(msg.getSender().getImage());
                ((TextView)receive.findViewById(R.id.bubble_receiver_text)).setText(msg.getText());
                break;
            case BimMsg.TYPE_SEND:
                LinearLayout send = holder.sendLayout;
                send.setVisibility(View.VISIBLE);
                holder.historyLayout.setVisibility(View.GONE);
                holder.receiveLayout.setVisibility(View.GONE);
                ((ImageView)send.findViewById(R.id.bubble_sender_image)).setImageResource(msg.getSender().getImage());
                ((TextView)send.findViewById(R.id.bubble_sender_text)).setText(msg.getText());
                break;
            case BimMsg.TYPE_HISTORY:
                LinearLayout history = holder.historyLayout;
                history.setVisibility(View.VISIBLE);
                holder.receiveLayout.setVisibility(View.GONE);
                holder.sendLayout.setVisibility(View.GONE);
                ((ImageView)history.findViewById(R.id.bubble_history_image)).setImageResource(msg.getSender().getImage());
                ((TextView)history.findViewById(R.id.bubble_history_name)).setText(msg.getSender().getName());
                ((TextView)history.findViewById(R.id.bubble_history_text)).setText(msg.getText());
                final BimMsg msg1 = msg;
                history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MessageActivity.class);
                        msg1.getFriend().putToIntent(intent);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                Log.d(TAG, "setMessage: There is an invalid message type!!!");
        }
    }
}

class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder> {
    private BimUserList userList;
    private Context context;

    public AddressBookAdapter(BimUserList userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_address_book_image);
            name = itemView.findViewById(R.id.item_address_book_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_book, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BimUser user = userList.get(position);
        setFriend(user, holder);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                user.putToIntent(intent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void setFriend(BimUser user, ViewHolder holder){
        holder.image.setImageResource(user.getImage());
        holder.name.setText(user.getName());
    }

}

class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private BimUserDetailList searchResult;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView image;
        TextView name;
        TextView sex;
        TextView birthday;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_search_result_linear_layout);
            image = itemView.findViewById(R.id.item_search_result_image);
            name = itemView.findViewById(R.id.item_search_result_name);
            sex = itemView.findViewById(R.id.item_search_result_sex);
            birthday = itemView.findViewById(R.id.item_search_result_birthday);
        }
    }

    public SearchResultAdapter(BimUserDetailList searchResult) {
        this.searchResult = searchResult;
    }

    public SearchResultAdapter(BimUserDetailList searchResult, Context context) {
        this.searchResult = searchResult;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BimUserDetail user = searchResult.get(position);
        holder.image.setImageResource(R.drawable.receive_tmp);
        holder.name.setText(user.getName());
        holder.sex.setText(user.getStringId());
        holder.birthday.setText(user.getStringId());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                user.toBimUser().putToIntent(intent);
                context.startActivity(intent);
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "You have long clicked this", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResult.size();
    }
}

