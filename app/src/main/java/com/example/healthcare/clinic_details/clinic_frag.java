package com.example.healthcare.clinic_details;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.R;
import com.example.healthcare.home_frag;
import com.example.healthcare.navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class clinic_frag extends Fragment implements View.OnClickListener {

    Button btnaddcli;
    EditText txtid,txtname,txtlocation;

    public clinic_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


          View v = inflater.inflate(R.layout.fragment_clinic_frag, container, false);
        navigation.Fragname="back";
            DBhelper.initdb(this.getActivity());

          btnaddcli=v.findViewById(R.id.btn_addclinic);
          txtid=v.findViewById(R.id.txt_clid);
          txtname=v.findViewById(R.id.txt_cliname);
          txtlocation=v.findViewById(R.id.txt_cliloc);

          btnaddcli.setOnClickListener(this);

          String id=genarateID();
          txtid.setText(id);

        return v;
    }

    @Override
    public void onClick(View v) {

        if(v==btnaddcli)
        {
            if(txtid.getText().toString().trim().length()==0||
                    txtname.getText().toString().trim().length()==0||
                    txtlocation.getText().toString().trim().length()==0
            )
            {
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"PLease Fill the Details",Toast.LENGTH_SHORT).show();
            }
            else
            {

                DBhelper.excute(" insert into clinic2 values ('"+txtid.getText()+"','"+txtname.getText()+"','"+txtlocation.getText()+"')");
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"A new location is added to the system",Toast.LENGTH_SHORT).show();

            }
        }


        }


    private String genarateID() {

        String did = null, sd = null;

        try {
            Cursor cursor = DBhelper.search("SELECT Clid FROM clinic2");
//            Activity activity=getActivity();
//            Toast.makeText(activity,"Cursor is there", Toast.LENGTH_SHORT).show();

            if (cursor.moveToLast())//move to last value
            {
                did = cursor.getString(cursor.getColumnIndex("Clid"));

                System.out.println("If error " + did);

                String sdid = did.substring(3);//check the how many charcters are avaliable in this id.

                int count = Integer.parseInt(sdid);//take integer value

                System.out.println("Int val: " + sdid);

                if (count >= 99) {
                    count += 1;
                    sd = "C-" + count;
                } else if (count < 99 && count >= 9) {
                    count += 1;
                    sd = "C-0" + count;
                } else if (count > 0 && count < 9) {
                    count += 1;
                    sd = "C-00" + count;
                }
            } else {
                sd = "C-001";
            }
        } catch (Exception e) {

            System.out.println("Auto gen error : " + e);
        }

        System.out.println("DID : " + sd);

        return sd;//return value

    }



    }

