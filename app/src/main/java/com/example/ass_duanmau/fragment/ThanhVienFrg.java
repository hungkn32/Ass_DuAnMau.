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
import com.example.ass_duanmau.adapter.ThanhVienAdapter;
import com.example.ass_duanmau.dao.ThanhVienDao;
import com.example.ass_duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThanhVienFrg extends Fragment {

    RecyclerView rcvtv;
    FloatingActionButton fltaddtv;

    ThanhVienDao dao;

    ThanhVienAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.thanhvien_frg,container,false);
        rcvtv = view.findViewById(R.id.rcvthanhvien);
        fltaddtv = view.findViewById(R.id.fltaddtv);
        dao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien>  list = dao.getalltv();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvtv.setLayoutManager(layoutManager);
        adapter = new ThanhVienAdapter(list,getContext());
        rcvtv.setAdapter(adapter);
        return view;
    }
}
