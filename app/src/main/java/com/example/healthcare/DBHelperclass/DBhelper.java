package com.example.healthcare.DBHelperclass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBhelper {

    public static SQLiteDatabase database;

    public static void db (Context context){

        if(database== null)
        {
            database=context.openOrCreateDatabase("health_care",context.MODE_PRIVATE,null);
        }

    }

    public static void createtable (Context context)
    {

        db(context);
        //table "test" contain the details of the consultant
        database.execSQL("create table if not exists test1 (Id varchar(45) PRIMARY KEY,Name varchar(45),email varchar(45), contact int,password varchar(45) )");
        //table "clinic2" contains the details of the clinic
        database.execSQL("create table if not exists clinic2 (Clid varchar(45) PRIMARY KEY,name varchar(45),location varchar(45) )");
        //table "chaneling2" contains the details of channeling
        database.execSQL("create table if not exists chaneling2 (Chid varchar(45) PRIMARY KEY,docname varchar(45),doccontact varchar(45)," +
                "docmail varchar(45),clinilocation varchar(45),clininame varchar(45),date varchar(45),time varchar(45),Clid varchar(45),Id varchar(45) )");
        //table register contains the details of customer register
        database.execSQL("create table if not exists register (Cusid varchar(45) PRIMARY KEY,name varchar(45),password varchar(45),email varchar(45),phone varchar(45) )");
        //table "appoinnt1" contains the details of appointment
        database.execSQL("create table if not exists appoinnt1 (Apid varchar(45) PRIMARY KEY,name varchar(45),doctorname varchar(45),clinic varchar(45),date varchar(45)" +
                ",time varchar(45),medhis varchar(45),imgpath varchar(45),Chid varchar(45),Cusid varchar(45) )");

    }

    public static void excute(String qury)
    {
        database.execSQL(qury);
    }

    public static Cursor search(String qury)
    {
        Cursor cursor= database.rawQuery(qury,null);
        return cursor;
    }

    public static void initdb(Context context)
    {
        createtable(context);
    }

}
