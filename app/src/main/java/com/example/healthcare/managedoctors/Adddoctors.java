package com.example.healthcare.managedoctors;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;
import com.example.healthcare.home_frag;
import com.example.healthcare.navigation;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class Adddoctors extends Fragment implements View.OnClickListener {

    EditText name,password,id,email,contact;
    Button btnreg;
    String num="ABC";

    public Adddoctors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_adddoctors, container, false);

        DBhelper.initdb(this.getActivity());

        navigation.Fragname="doc";


        contact=v.findViewById(R.id.txtcontact);
        name=v.findViewById(R.id.txtname);
        id=v.findViewById(R.id.txtid);
        email=v.findViewById(R.id.txtemail);
        password=v.findViewById(R.id.txtpass);
        btnreg=(Button)v.findViewById(R.id.btn_registerdoc);


        //password.setText(num+Getpassword(length));

       btnreg.setOnClickListener(this);

       //catching the generated id and assigning it to a variable
       String genid= genarateID();
       id.setText(genid);

        return v;
    }



    @Override
    public void onClick(View v) {



        if(v==btnreg)
        {
            //data insert querry
            if (id.getText().toString().trim().length() == 0 ||
                    name.getText().toString().trim().length() == 0 ||
                    password.getText().toString().trim().length() == 0 ||
                    email.getText().toString().trim().length() == 0
                    ||
                    contact.getText().toString().trim().length() == 0
            ) {
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"PLease Fill the Details",Toast.LENGTH_SHORT).show();

            } else {
                DBhelper.excute("insert into test1 values ('" + id.getText() + "','" + name.getText() + "','"
                        + email.getText() + "','" + contact.getText() +"', '"+password.getText()+"' )");
                Activity mActivity=this.getActivity();

                Toast.makeText(mActivity,"New Consultant added to the system",Toast.LENGTH_SHORT).show();

            }

        }

    }

    //auto generate id
    private String genarateID() {

        String did = null, sd = null;

        try {
            Cursor cursor = DBhelper.search("SELECT Id FROM test1");
            if (cursor.moveToLast())//move to last value
            {
                did = cursor.getString(cursor.getColumnIndex("Id"));

                System.out.println("If error " + did);

                String sdid = did.substring(3);//check the how many charcters are avaliable in this id.

                int count = Integer.parseInt(sdid);//take integer value

                System.out.println("Int val: " + sdid);

                if (count >= 99) {
                    count += 1;
                    sd = "D-" + count;
                } else if (count < 99 && count >= 9) {
                    count += 1;
                    sd = "D-0" + count;
                } else if (count > 0 && count < 9) {
                    count += 1;
                    sd = "D-00" + count;
                }
            } else {
                sd = "D-001";
            }
        } catch (Exception e) {

            System.out.println("Auto gen error : " + e);
        }

        System.out.println("DID : " + sd);

        return sd;//return value

    }





}
