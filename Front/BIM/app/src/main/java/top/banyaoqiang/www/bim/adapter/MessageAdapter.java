package top.banyaoqiang.www.bim.adapter;

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

import top.banyaoqiang.www.bim.R;
import top.banyaoqiang.www.bim.activity.MessageActivity;
import top.banyaoqiang.www.bim.data.BimMsg;
import top.banyaoqiang.www.bim.data.BimMsgList;

import static top.banyaoqiang.www.bim.Manager.TAG;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
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
                        // TODO msg1.getFriend().putToIntent(intent);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                Log.d(TAG, "setMessage: There is an invalid message type!!!");
        }
    }
}
