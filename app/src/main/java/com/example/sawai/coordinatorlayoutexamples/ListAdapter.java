package com.example.sawai.coordinatorlayoutexamples;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sawai on 30/09/16.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> mList;
    Context mContext;


    public ListAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = ((MyViewHolder) holder);
        myViewHolder.tv.setText(mList.get(position));

        myViewHolder.itemView.setOnClickListener(myViewHolder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @Override
        public void onClick(View view) {
            ((AdapterCallback) mContext).onClick(getAdapterPosition());
        }
    }

    public interface AdapterCallback {
        void onClick(int position);
    }
}
