package com.example.healthcare.customer.viewappoin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class cusviewappoin_frag extends Fragment {

    ListView list;
    SharedPreferences pref;
    public cusviewappoin_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=  inflater.inflate(R.layout.fragment_cusviewappoin_frag, container, false);

        DBhelper.initdb(this.getActivity());

        pref = getActivity().getApplicationContext().getSharedPreferences(MainActivity.MYPRF, 0);

        list=v.findViewById(R.id.lsitview);

        Cursor cr =  DBhelper.search("select * from appoinnt1 where Cusid='"+pref.getString("uid",null)+"' ");

        ArrayList<String> ar = new ArrayList<>();

        while (cr.moveToNext()){

            ar.add("\n"+"ID : "+cr.getString(2)+ "\n"+"Name : "+cr.getString(3)
                    + "\n"+"E-Mail : "+cr.getString(4)+ "\n"+"Contact : "+cr.getString(5)
                    + "\n"+"E-Mail : "+cr.getString(6)+ "\n"+"E-Mail : "+cr.getString(7)+ "\n"+
                    "E-Mail : "+cr.getString(8)+ "\n");

        }

        ArrayAdapter<String> ad = new ArrayAdapter<>
                (this.getActivity(),android.R.layout.simple_list_item_1,ar);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            }
        });



        return v;
    }


    public void ShowMessage(String title, String Message) {
        Activity activity=this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
