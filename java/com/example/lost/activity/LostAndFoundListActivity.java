package com.example.lost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lost.R;
import com.example.lost.adapter.RecyclerAdapter;
import com.example.lost.entity.LostFind;
import com.example.lost.sqlite.DAOService;

import java.util.List;

public class LostAndFoundListActivity extends MyBaseActivity implements RecyclerAdapter.CallBack {

    RecyclerAdapter adapter;
    TextView emptyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found_list);

        emptyTv = findViewById(R.id.empty_tv);

        final List<LostFind> list = DAOService.getInstance().searchHealthInfoByStartAndEnd();
        if(list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
        adapter = new RecyclerAdapter(this,list,this);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LostFind> list = DAOService.getInstance().searchHealthInfoByStartAndEnd();
        adapter.setmDatas(list);
        if (list.isEmpty()) {
            emptyTv.setVisibility(View.VISIBLE);
        } else {
            emptyTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(LostFind lostFind) {
        Intent intent = new Intent(getApplicationContext(),LostAndFoundDetailActivity.class);
        intent.putExtra("healthId", lostFind.getId());
        startActivityForResult(intent,100);
    }
}
