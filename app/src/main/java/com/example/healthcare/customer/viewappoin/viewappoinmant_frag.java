package com.example.healthcare.customer.viewappoin;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.Dataobj;
import com.example.healthcare.MainActivity;
import com.example.healthcare.MyAdapter;
import com.example.healthcare.R;
import com.example.healthcare.navigation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class viewappoinmant_frag extends Fragment {

    ListView lv;
    SharedPreferences pref;
    public viewappoinmant_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_viewappoinmant_frag, container, false);

        navigation.Fragname="Cushome";

        lv = v.findViewById(R.id.listview_for_appoinment);
        DBhelper.initdb(this.getActivity());
        pref = getActivity().getApplicationContext().getSharedPreferences(MainActivity.MYPRF, 0);

        Cursor cr =  DBhelper.search("select * from appoinnt1 where Cusid='"+pref.getString("uid",null)+"' ");

        ArrayList<Dataobj> ar = new ArrayList<Dataobj>();


        while (cr.moveToNext()) {

            Dataobj data = new Dataobj();

            data.txtpnam = cr.getString(0);
            data.txtrno = cr.getString(1);
            data.txtdrname = cr.getString(2);
            data.txtdate = cr.getString(4);
            data.txttime = cr.getString(5);
            data.txtproblem= cr.getString(6);
            data.txtclinic=cr.getString(3);
            data.image_path=cr.getString(7);

            ar.add(data);

        }
        MyAdapter adapter = new MyAdapter(this.getActivity(), ar);

        lv.setAdapter(adapter);


        return v;
    }
}
