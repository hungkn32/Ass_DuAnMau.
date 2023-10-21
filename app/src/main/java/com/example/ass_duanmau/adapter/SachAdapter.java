package com.example.ass_duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_duanmau.R;
import com.example.ass_duanmau.dao.PhieuMuonDao;
import com.example.ass_duanmau.dao.SachDao;
import com.example.ass_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHoler> {
     ArrayList<Sach> list;
     Context context;

    List<Sach> list2;
    SachDao dao;

    public SachAdapter(ArrayList<Sach> list, Context context,ArrayList<HashMap<String, Object>> listHM,SachDao dao) {
        this.list = list;
        this.context = context;
        this.dao = dao;

    }
    public  void setFilteList(ArrayList<Sach> filteList,SachDao dao){
        this.dao = dao;
        this.list2 = filteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtmas.setText(String.valueOf( "Mã Sách: " + list.get(position).getMaSach()));
        holder.txttens.setText(("Tên Sách: "+list.get(position).getTenSach()));
        holder.txtgiat.setText(String.valueOf("Giá Thuê: " +list.get(position).getGiaThue()));
        holder.txtloais.setText(String.valueOf("Mã Loại:"+list.get(position).getMaLoai()));
        holder.txttenloai.setText("Tên Loại: "+list.get(position).getTenLoai());
        holder.txtnxb.setText(String.valueOf("Năm Xuất Bản: "+list.get(position).getNamXuatBan()));
        holder.imgdlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn thật sự muốn xóa phiếu mượn này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getMaSach());
                            switch (check) {
                                case 1:
                                    loadData();
                                    Toast.makeText(context, "Xóa thành công sách", Toast.LENGTH_SHORT).show();
                                    break;
                                case 0:
                                    Toast.makeText(context, "Xóa không thành công sách", Toast.LENGTH_SHORT).show();
                                    break;
                                case -1:
                                    Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }

                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }


        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                        dialogupdate();
                return false;
            }
        });

    }

    private void dialogupdate() {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_phieumuon,null);
        builder.setView(view);
        Dialog dialog =builder.create();
        dialog.show();
    }

    private void loadData(){
        list.clear();
        list = dao.getSachAll();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ViewHoler extends RecyclerView.ViewHolder{

        TextView txtmas,txttens,txtgiat,txtloais,txttenloai,txtnxb;

    ImageButton imgdlete ;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtmas = itemView.findViewById(R.id.txtmasach1);
            txttens = itemView.findViewById(R.id.txttensach1);
            txtgiat = itemView.findViewById(R.id.txtgiathue1);
            txtloais = itemView.findViewById(R.id.txtloaisach1);
            txttenloai = itemView.findViewById(R.id.txttenloaisach1);
            txtnxb = itemView.findViewById(R.id.txtnamxuatban);
            imgdlete = itemView.findViewById(R.id.imgdeleee);


        }
    }
}
