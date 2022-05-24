package com.android.lost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.android.lost.R;
import com.android.lost.adapter.LAFAdapter;
import com.android.lost.entity.LostAndFound;
import com.android.lost.sqlite.DAOService;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

public class LostAndFoundListActivity extends MyBaseActivity{

    LAFAdapter lafAdapter;
    TextView emptyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_list);

        emptyTv = findViewById(R.id.empty_tv);

        final List<LostAndFound> list = DAOService.getInstance().searchHealthInfoByStartAndEnd();
        if(list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
        lafAdapter = new LAFAdapter(list);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lafAdapter);

        lafAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LostAndFound lostAndFound = (LostAndFound) adapter.getData().get(position);
                Intent intent = new Intent(getApplicationContext(),LostAndFoundDetailActivity.class);
                intent.putExtra("healthId",lostAndFound.getId());
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LostAndFound> list = DAOService.getInstance().searchHealthInfoByStartAndEnd();
        lafAdapter.setNewData(list);
        if (list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
    }
}
