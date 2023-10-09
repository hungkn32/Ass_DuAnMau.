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
import com.example.ass_duanmau.adapter.PhieuMuonAdapter;
import com.example.ass_duanmau.dao.PhieuMuonDao;
import com.example.ass_duanmau.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhieuMuonFrg extends Fragment {

    PhieuMuonAdapter adapter;
    PhieuMuonDao dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.phieumuonfrg,container,false);
        RecyclerView rcv = view.findViewById(R.id.rcvpm);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fltaddphieumuon);
        dao =  new PhieuMuonDao(getContext());
        ArrayList<PhieuMuon> list = dao.getDSPhieuMuon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new PhieuMuonAdapter(list,getContext());
        rcv.setAdapter(adapter);
        return view;
    }
}
