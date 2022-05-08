package com.example.lost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lost.R;
import com.example.lost.entity.LostFind;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LostFind> mDatas;
    private CallBack mCallBack;

    public interface CallBack{
        void onItemClick(LostFind lostFind);
    }

    public RecyclerAdapter(Context context, List<LostFind> datas,CallBack callBack) {
        mContext = context;
        mDatas = datas;
        mCallBack = callBack;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.traffic_item, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position).getName());
        normalHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.onItemClick(mDatas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {

        public TextView mTV;
        public View rootView;

        public NormalHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mTV = itemView.findViewById(R.id.tv);
        }
    }

    public void setmDatas(List<LostFind> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}