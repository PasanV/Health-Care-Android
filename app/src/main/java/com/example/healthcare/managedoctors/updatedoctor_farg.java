package com.example.healthcare.managedoctors;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.R;
import com.example.healthcare.home_frag;
import com.example.healthcare.navigation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class updatedoctor_farg extends Fragment implements View.OnClickListener {

    Spinner spin_id;
    Button btnupdate;
    EditText txtid,txtname,txtemail,txtphone,txtpass;
    public updatedoctor_farg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_updatedoctor_farg, container, false);

        navigation.Fragname="doc";

        DBhelper.initdb(this.getActivity());

       spin_id=v.findViewById(R.id.Spin_update);
       btnupdate=v.findViewById(R.id.btn_updatedoc);
        txtid=v.findViewById(R.id.txtup_id);
        txtname=v.findViewById(R.id.txtup_name);
        txtemail=v.findViewById(R.id.txtup_email);
        txtphone=v.findViewById(R.id.txtup_phone);
        txtpass=v.findViewById(R.id.txtup_pass);

        final ArrayList<String> arrayList= new ArrayList<>();
        Cursor cursor = DBhelper.search("SELECT Id FROM test1");
        arrayList.add("Please Select");
        while (cursor.moveToNext())
        {
            arrayList.add(cursor.getString(0));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_id.setAdapter(arrayAdapter);
        spin_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("test 3", "on item selected working");
                int did = (position);
                Log.e("test 3", "on item selected working"+did);
                Log.e("test 4", "Data "+arrayList.get(did));
                Cursor cursor = DBhelper.search("Select * from test1 where Id='" + arrayList.get(did) + "'");
                Log.e("test 3", "Query executed"+cursor);

//                    StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {

                    Log.e("test 3", "cursor is working");

                    txtid.setText(cursor.getString(0));
                    txtname.setText(cursor.getString(1));
                    txtemail.setText(cursor.getString(2));
                    txtphone.setText(cursor.getString(3));
                    txtpass.setText(cursor.getString(4));

                }
//                    ShowMessage("Data", buffer.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnupdate.setOnClickListener(this);

       return  v;
    }

    @Override
    public void onClick(View v) {

        if(v==btnupdate)
        {
            if (txtid.getText().toString().trim().length() == 0 ||
                    txtname.getText().toString().trim().length() == 0 ||
                    txtpass.getText().toString().trim().length() == 0 ||
                    txtemail.getText().toString().trim().length() == 0
                    ||
                    txtphone.getText().toString().trim().length() == 0
            ) {
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"PLease Fill the Details",Toast.LENGTH_SHORT).show();

            }
            else
            {

                DBhelper.excute("update test1 set Name='" + txtname.getText() + "',email='" + txtemail.getText() + "',contact='"
                        + txtphone.getText() + "',password='" + txtpass.getText() +"' where Id = '"+txtid.getText()+"'");

                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"Selected Consultant Updated",Toast.LENGTH_SHORT).show();

            }
        }






    }
}
