package com.android.lost.adapter;

import androidx.annotation.Nullable;

import com.android.lost.R;
import com.android.lost.entity.LostAndFound;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;


public class LAFAdapter extends BaseQuickAdapter<LostAndFound, BaseViewHolder> {
    public LAFAdapter(@Nullable List<LostAndFound> data) {
        super(R.layout.traffic_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LostAndFound item) {
        helper.setText(R.id.tv,item.getName());
    }
}
