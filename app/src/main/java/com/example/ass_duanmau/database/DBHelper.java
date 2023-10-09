package com.example.ass_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbName = "PNLib";
    public static final  int dbverdion = 4;
    public DBHelper(Context context) {
        super(context, dbName, null, dbverdion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_thuthu = "Create table THUTHU(maTT text primary key," +
                "hoTen text,matKhau text)";
        db.execSQL(tb_thuthu);

        String tb_thanhvien = "Create table THANHVIEN(maTV integer primary key autoincrement," +
                "hoTen text" +
                ",namSinh text)";
        db.execSQL(tb_thanhvien);
        String tb_loaisach = "Create table LOAISACH(maLoai integer primary key autoincrement ," +
                "tenLoai text)";
        db.execSQL(tb_loaisach);
        String tb_sach = "Create Table SACH(maSach integer primary key autoincrement," +
                "tenSach text," +
                "tienThue integer," +
                "maLoai integer references LOAISACH(maLoai))";
        db.execSQL(tb_sach);

        String tb_phieumuon = "Create table PHIEUMUON(maPM integer primary key autoincrement," +
                "maTT references THUTHU(maTT)," +
                "maTV integer references THANHVIEN(maTV)," +
                "maSach integr references SACH(maSach)," +
                "ngay text," +
                "traSach integer," +
                "tienThue integr)";
        db.execSQL(tb_phieumuon);


        db.execSQL("INSERT INTO LOAISACH VALUES(1,'Thiếu Nhi'),(2,'Tình Cảm'),(3,'Giáo Khoa'),(4,'Tham Khảo')");
        db.execSQL("INSERT INTO SACH Values(1,'Ba Con Cừu',3000,1),(2,'Anh Và Em',4000,2),(3,'Lập Trình Android',1000,3),(4,'Sách Giải Toán',5000,4)");
        db.execSQL("INSERT INTO THUTHU values(1,'admin','admin'),(2,'hung','123ab'),(3,'ab','cd')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Kiều Ngọc Hưng','2004'),(2,'Nguyễn Ngọc Hoàng','2006')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,1,1, '19/03/2022', 1, 2500),(2,2,2,3, '19/03/2022', 0, 2000),(3,3,3, 1, '19/03/2022', 1, 2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            onCreate(db);
        }
    }
}
