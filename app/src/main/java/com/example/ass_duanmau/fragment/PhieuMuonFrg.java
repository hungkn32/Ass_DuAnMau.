package com.example.ass_duanmau.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_duanmau.R;
import com.example.ass_duanmau.adapter.PhieuMuonAdapter;
import com.example.ass_duanmau.dao.PhieuMuonDao;
import com.example.ass_duanmau.dao.SachDao;
import com.example.ass_duanmau.dao.ThanhVienDao;
import com.example.ass_duanmau.model.PhieuMuon;
import com.example.ass_duanmau.model.Sach;
import com.example.ass_duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PhieuMuonFrg extends Fragment {

    PhieuMuonAdapter adapter;
    PhieuMuonDao dao;
    RecyclerView rcv;
    EditText edt_maPM, edSearch;
    ArrayList<PhieuMuon> listPhieuMuon = new ArrayList<>();
    ArrayList<PhieuMuon> tempListPhieuMuon = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.phieumuonfrg,container,false);
        rcv = view.findViewById(R.id.rcvpm);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fltaddphieumuon);
        dao =  new PhieuMuonDao(getContext());
        ArrayList<PhieuMuon> list = dao.getDSPhieuMuon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new PhieuMuonAdapter(list,getContext());
        rcv.setAdapter(adapter);
        edSearch = view.findViewById(R.id.edSearch);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listPhieuMuon.clear();
                for (PhieuMuon pm:tempListPhieuMuon) {
                    if(String.valueOf(pm.getMaPM()).
                            contains(charSequence.toString())){
                        listPhieuMuon.add(pm);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddPM();
            }
        });
        return view;
    }
     void dialogAddPM() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_phieumuon,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button PM_add = view.findViewById(R.id.PM_add);
        Button PM_cancel = view.findViewById(R.id.PM_Cancel);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);

        PM_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("maTV");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("maSach");
                int tien = (int) hsSach.get("tienThue");
                AddPM(matv,masach,tien);

            }
        });
    }

    private void AddPM(int matv, int masach, int tien) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("Username","");

        Date currenTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currenTime);

        PhieuMuon phieuMuon = new PhieuMuon(matt,matv,masach,tien,0,ngay);
        boolean check = dao.insert(phieuMuon);
        if(check){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        }else{
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien> list = thanhVienDao.getalltv();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV",tv.getMaTV());
            hs.put("hoTen",tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(getContext());
        ArrayList<Sach> list = sachDao.getSachAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maSach",sc.getMaSach());
            hs.put("tenSach",sc.getTenSach());
            hs.put("tienThue",sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }
    private void loadData(){
        listPhieuMuon.clear();
        listPhieuMuon.addAll(dao.getDSPhieuMuon());
        adapter.notifyDataSetChanged();
    }
}
