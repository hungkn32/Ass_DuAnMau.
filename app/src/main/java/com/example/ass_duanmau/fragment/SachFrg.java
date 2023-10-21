package com.example.ass_duanmau.fragment;

import android.app.Dialog;
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
import com.example.ass_duanmau.adapter.SachAdapter;
import com.example.ass_duanmau.dao.SachDao;
import com.example.ass_duanmau.dao.TheLoaiDao;
import com.example.ass_duanmau.model.PhieuMuon;
import com.example.ass_duanmau.model.Sach;
import com.example.ass_duanmau.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SachFrg extends Fragment {
    RecyclerView rcv;
    FloatingActionButton fltaddsach;
     SachDao dao;
     SachAdapter adapter;
     List<Sach> list;
     EditText edtseach;
    ArrayList<Sach> listPhieuMuon = new ArrayList<>();
    ArrayList<Sach> tempListPhieuMuon = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sach_frg, container, false);
        rcv = view.findViewById(R.id.rcvsach);
        fltaddsach = view.findViewById(R.id.fltaddsach);
        edtseach = view.findViewById(R.id.edSearch);
        edtseach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listPhieuMuon.clear();
                for (Sach pm:tempListPhieuMuon) {
                    if((pm.getTenSach().toLowerCase()).
                            contains(s.toString().toLowerCase())){
                        listPhieuMuon.add(pm);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dao = new SachDao(getContext());
        loadData();

        fltaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSach();
            }
        });

        return view;
    }



    public void addSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText txttens = view.findViewById(R.id.ed_addTenS);
        TextInputEditText txtgiathue = view.findViewById(R.id.ed_addGiaThue);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        TextInputEditText txtnsb =view.findViewById(R.id.ed_addnsx);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);

        Button btnluu = view.findViewById(R.id.S_add);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tens = txttens.getText().toString();
                String gia = txtgiathue.getText().toString();
                String nxb = txtnsb.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                int tien = Integer.parseInt(gia);
                int n = Integer.parseInt(nxb);
                boolean check = dao.insert(tens,tien,n);
                if(check){
                    loadData();
                    Toast.makeText(getContext(), "Thêm thành công sách", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "Thêm không thành công sách", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }
    private ArrayList<HashMap<String , Object>> getDSLoaiSach(){
        TheLoaiDao loaisach = new TheLoaiDao(getContext());
        ArrayList<TheLoai> list1 = loaisach.getalltheloai();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (TheLoai ls : list1){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", ls.getMaLoai());
            hs.put("tenLoai", ls.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }
    private void loadData(){
        ArrayList<Sach> list = dao.getSachAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new SachAdapter(list,getContext() ,getDSLoaiSach(),dao);
        rcv.setAdapter(adapter);
    }
}
