package com.example.ass_duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_duanmau.R;
import com.example.ass_duanmau.adapter.Top10Adapter;
import com.example.ass_duanmau.dao.ThongKeDao;
import com.example.ass_duanmau.model.Sach;

import java.util.ArrayList;

public class Top10Frg extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.top10_frg,container,false);
        RecyclerView rcv = view.findViewById(R.id.rcvtop10);
        ThongKeDao thongKeDao = new ThongKeDao(getContext());
        ArrayList<Sach> list = thongKeDao.getTop10();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        Top10Adapter adapter = new Top10Adapter(list,getContext());
        rcv.setAdapter(adapter);
        return view;
    }
}
