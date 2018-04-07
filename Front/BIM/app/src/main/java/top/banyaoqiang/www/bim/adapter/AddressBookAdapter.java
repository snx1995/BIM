package top.banyaoqiang.www.bim.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import top.banyaoqiang.www.bim.R;
import top.banyaoqiang.www.bim.activity.InfoDetailActivity;
import top.banyaoqiang.www.bim.data.BimUser;
import top.banyaoqiang.www.bim.data.BimUserList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder> {
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
                // TODO user.putToIntent(intent);
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