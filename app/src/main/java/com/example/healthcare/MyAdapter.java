package com.example.healthcare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Dataobj> set;

    public MyAdapter(Context context, ArrayList<Dataobj> ar) {
        super(context, R.layout.row, ar);
        this.context = context;
        set = ar;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.row, null, true);


        TextView txt = (TextView) convertView.findViewById(R.id.viewrno);
        TextView txt1 = (TextView) convertView.findViewById(R.id.viewdname);
        TextView txt2 = (TextView) convertView.findViewById(R.id.viewdate);
        TextView txt3 = (TextView) convertView.findViewById(R.id.viewtime);
        TextView txt4 = (TextView) convertView.findViewById(R.id.problem);
        TextView txt5= (TextView) convertView.findViewById(R.id.clinic);
        TextView txt6 = (TextView) convertView.findViewById(R.id.patename);
        ImageView img = (ImageView) convertView.findViewById(R.id.vhistorypic);

        Dataobj dob = set.get(position);

        txt6.setText("Patient Nmber :" + dob.txtpnam);
        txt.setText("Patient Name :" + dob.txtrno);
        txt1.setText("Doctor Name :" + dob.txtdrname);
        txt2.setText("Appoinment date :" + dob.txtdate);
        txt3.setText("Apppoinment time :" + dob.txttime);
        txt4.setText("Your health issue :" + dob.txtproblem);
        txt5.setText("Clinic :" + dob.txtclinic);
        Log.e("IMG","BitMap : "+dob.image_path);


        File img_location = new File(dob.image_path);
        Bitmap bitmap = BitmapFactory.decodeFile(img_location.getAbsolutePath());

        img.setImageBitmap(bitmap);
//        img.setImageURI(Uri.parse(img_location.getAbsolutePath()));

        Log.e("Inside Adapter", "................ Awa " + dob.txtdrname + " Pos " + position);

        return convertView;
    }
}
