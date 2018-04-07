package top.banyaoqiang.www.bim.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import top.banyaoqiang.www.bim.R;
import top.banyaoqiang.www.bim.activity.InfoDetailActivity;
import top.banyaoqiang.www.bim.data.BimUser;
import top.banyaoqiang.www.bim.data.BimUserList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private BimUserList searchResult;
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

    public SearchResultAdapter(BimUserList searchResult) {
        this.searchResult = searchResult;
    }

    public SearchResultAdapter(BimUserList searchResult, Context context) {
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
        final BimUser user = searchResult.get(position);
        holder.image.setImageResource(R.drawable.receive_tmp);
        holder.name.setText(user.getName());
        holder.sex.setText(user.stringId());
        holder.birthday.setText(user.stringId());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                // TODO user.toBimUser().putToIntent(intent);
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

