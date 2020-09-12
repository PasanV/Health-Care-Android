package com.example.healthcare.managedoctors;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;
import android.widget.Button;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.R;
import com.example.healthcare.navigation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Viewdoc_frag extends Fragment implements View.OnClickListener {
    ListView s_list;
//    Button btnview;
//    Spinner spin_id;
//    EditText textid;
    public Viewdoc_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_viewdoc_frag, container, false);

       DBhelper.initdb(this.getActivity());
        navigation.Fragname="doc";
        s_list=v.findViewById(R.id.lsitview);

        Cursor cr =  DBhelper.search("select * from test1");

        ArrayList<String> ar = new ArrayList<>();

        while (cr.moveToNext()){

            ar.add("\n"+"ID : "+cr.getString(0)+ "\n"+"Name : "+cr.getString(1)
                    + "\n"+"E-Mail : "+cr.getString(2)+ "\n"+"Contact : "+cr.getString(3)
                    + "\n");
//            String record = cr.getString(0)+" "+cr.getString(1)+" "+cr.getString(2);
//            ar.add(record);
        }

        ArrayAdapter<String> ad = new ArrayAdapter<>
                (this.getActivity(),android.R.layout.simple_list_item_1,ar);
        s_list.setAdapter(ad);
        s_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                StringBuffer buffer = new StringBuffer();
                buffer.append(adapterView.getItemAtPosition(i)+ "\n");

            ShowMessage("Data", buffer.toString());

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

    @Override
    public void onClick(View v) {

//

        }


    }

