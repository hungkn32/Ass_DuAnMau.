package com.example.ass_duanmau.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_duanmau.R;
import com.example.ass_duanmau.adapter.ThanhVienAdapter;
import com.example.ass_duanmau.dao.ThanhVienDao;
import com.example.ass_duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        fltaddtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    dialogAddTV();
            }
        });
        return view;
    }
    private void dialogAddTV(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_thanhvien,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ed_txtTenTV = view.findViewById(R.id.ed_addTenTV);
        TextInputEditText ed_txtNamSinh = view.findViewById(R.id.ed_addNamSinh);
        TextInputLayout in_txtTenTV = view.findViewById(R.id.in_addTenTV);
        TextInputLayout in_txtNamSinh = view.findViewById(R.id.in_addNamSinh);
        Button btn_add = view.findViewById(R.id.TV_add);
        Button btn_cancel = view.findViewById(R.id.TV_Cancel);

        ed_txtTenTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_txtTenTV.setError("Vui lòng nhập tên thành viên");
                }else{
                    in_txtTenTV.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtNamSinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_txtNamSinh.setError("Vui lòng không để trống năm sinh");
                }else{
                    in_txtNamSinh.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = ed_txtTenTV.getText().toString();
                String namsinh = ed_txtNamSinh.getText().toString();
                boolean check = dao.insert(hoten,namsinh);

                if(hoten.isEmpty() || namsinh.isEmpty()){
                    if(hoten.equals("")){
                        in_txtTenTV.setError("Vui lòng nhập đầy đủ tên thành viên");
                    }else{
                        in_txtTenTV.setError(null);
                    }

                    if(namsinh.equals("")){
                        in_txtNamSinh.setError("Vui lòng nhập đầy đủ năm sinh thành viên");
                    }else{
                        in_txtNamSinh.setError(null);
                    }
                }else{
                    if(check){
                        ArrayList<ThanhVien> list =new ArrayList<>();
                        list.clear();
                        list.addAll(dao.getalltv());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm Thành Viên Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm Thành Viên Không Thành Công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_txtTenTV.setText("");
                ed_txtNamSinh.setText("");
            }
        });
    }
}
