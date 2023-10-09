package com.example.ass_duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ass_duanmau.R;
import com.example.ass_duanmau.dao.ThuThuDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UserFrg extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.add_user,container,false);
        TextInputEditText inmatt = view.findViewById(R.id.ed_txtMaTT);
        TextInputEditText inht = view.findViewById(R.id.ed_txtHoTenTT);
        TextInputEditText inmk = view.findViewById(R.id.ed_txtPassTT);
        ThuThuDao dao = new ThuThuDao(getContext());
        Button btnsave = view.findViewById(R.id.btn_CreateTT);
            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tt = inmatt.getText().toString();
                    String user = inht.getText().toString();
                    String pass = inmk.getText().toString();
                    boolean check = dao.Register(tt, user, pass);
                    if (check) {
                        Toast.makeText(getContext(), "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Đăng Kí Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        return view;
    }

}
