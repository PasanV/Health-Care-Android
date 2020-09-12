package com.example.healthcare.chanelling_details;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.R;
import com.example.healthcare.navigation;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class channeling_frag extends Fragment implements View.OnClickListener{
    DatePickerDialog picker;
    EditText date,time,txtname,txtcontact,txtmail,txtclinic,txtclinname;
    Spinner docname_spin,loc_spin;
    Button addchan;
    String id;



    final ArrayList<String> arrayList= new ArrayList<>();

    public channeling_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=  inflater.inflate(R.layout.fragment_managechanneling, container, false);

        DBhelper.initdb(this.getActivity());
        navigation.Fragname="back";

        txtclinname=v.findViewById(R.id. txtloca_name );
        loc_spin=v.findViewById(R.id. spin_loc );
        txtclinic= v.findViewById(R.id. txtclin_loc);
        addchan=v.findViewById(R.id.btn_addchan);
        docname_spin=v.findViewById(R.id.spin_name);
        date=v. findViewById(R.id.txt_date);
        time=v.findViewById(R.id.txt_time);
        txtname=v.findViewById(R.id.txtdoc_name);
        txtcontact=v.findViewById(R.id.txtdoc_contact);
        txtmail=v.findViewById(R.id.txtdoc_email);


        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(this);
        addchan.setOnClickListener(this);

        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(this);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Activity m= getActivity();
                picker = new DatePickerDialog(m,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        time.setText(currentTime);

        String dat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date.setText(dat);

        //spinner loading
        Cursor cursor = DBhelper.search("SELECT Id FROM test1");
        arrayList.add("Please Select");
        while (cursor.moveToNext())
        {
            arrayList.add(cursor.getString(0));
        }
        //spinner loading
        final ArrayList<String> arrayList2= new ArrayList<>();
        Cursor cursor2 = DBhelper.search("SELECT Clid FROM clinic2");
        arrayList2.add("Please Select");
        while (cursor2.moveToNext())
        {
            arrayList2.add(cursor2.getString(0));
        }
        //spinner loading
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, arrayList2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docname_spin.setAdapter(arrayAdapter);
        loc_spin.setAdapter(arrayAdapter2);

        //loading of the doctor spinner
        docname_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test 3", "on item selected working");
                int did = (position);
                Log.e("test 3", "on item selected working" + did);
                Log.e("test 4", "Data " + arrayList.get(did));
                Cursor cursor = DBhelper.search("Select * from test1 where Id='" + arrayList.get(did) + "'");
                Log.e("test 3", "Query executed" + cursor);

                while (cursor.moveToNext()) {

                    Log.e("test 3", "cursor is working");

                    txtname.setText(cursor.getString(1));
                    txtcontact.setText(cursor.getString(3));
                    txtmail.setText(cursor.getString(2));

                }
            }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //loading of the loacation spinner
            loc_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test 3", "on item selected working");
                int did = (position);
                Log.e("test 3", "on item selected working" + did);
                Log.e("test 4", "Data " + arrayList2.get(did));
                Cursor cursor = DBhelper.search("Select * from clinic2 where Clid='" + arrayList2.get(did) + "'");
                Log.e("test 3", "Query executed" + cursor);

                while (cursor.moveToNext()) {

                    Log.e("test 3", "cursor is working");

                    txtclinname.setText(cursor.getString(1));
                    txtclinic.setText(cursor.getString(2));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        id= genarateID();

        return v;
    }


    @Override
    public void onClick(View v) {
        if(v==time)
        {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            Activity tim= getActivity();
            TimePickerDialog timePickerDialog = new TimePickerDialog(tim,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }

        else if(v==addchan)
        {

            if(date.getText().length()==0|| time.getText().length()==0||
                    txtname.getText().toString().trim().length()==0|| txtcontact.getText().toString().trim().length()==0||
                    txtmail.getText().toString().trim().length()==0|| txtclinic.getText().toString().trim().length()==0||
                    txtclinname.getText().toString().trim().length()==0
            )
            {
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"PLease Fill the Details",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String did= null,lid = null;
                Cursor cursor3 = DBhelper.search("Select * from test1 where Id='" + docname_spin.getSelectedItem().toString() + "'");
                while (cursor3.moveToNext()) {
                    Log.e("test 3", "cursor is working");
                    did = cursor3.getString(cursor3.getColumnIndex("Id"));
//                    Activity activity=getActivity();
//                    Toast.makeText(activity, ""+did, Toast.LENGTH_SHORT).show();
                }

                Cursor cursor4 = DBhelper.search("Select * from clinic2 where Clid='" + loc_spin.getSelectedItem().toString() + "'");
                while (cursor4.moveToNext()) {
                    Log.e("test 3", "cursor is working");

                    lid = cursor4.getString(cursor4.getColumnIndex("Clid"));
//                    Activity activity=getActivity();
//                    Toast.makeText(activity, ""+lid, Toast.LENGTH_SHORT).show();

                }

                DBhelper.excute("insert into chaneling2 values ('" +id+ "','" + txtname.getText() + "','"
                        + txtcontact.getText() + "','" + txtmail.getText() +"', '"+txtclinic.getText()+"'," +
                        "'"+txtclinname.getText()+"','"+date.getText()+"', '"+time.getText()+"', '"+lid+"','"+did+"' )");

//                Cursor cr1 =  DBhelper.search("select * from chaneling2");
//                StringBuffer buffer = new StringBuffer();
//                while(cr1.moveToNext())
//                {
//                    buffer.append("Id :" + cr1.getString(0) + "\n");
//                    buffer.append("Name :" + cr1.getString(1) + "\n");
//                    buffer.append("Surname:" + cr1.getString(2) + "\n");
//                    buffer.append("Marks :" + cr1.getString(3) + "\n");
//                    buffer.append("Surname:" + cr1.getString(8) + "\n");
//                    buffer.append("Surname:" + cr1.getString(9) + "\n");
//
//                }
//                ShowMessage("Data", buffer.toString());

                Activity activity=getActivity();
                Toast.makeText(activity, "Data Inserted", Toast.LENGTH_LONG).show();
            }



        }
    }

    public void ShowMessage(String title, String Message) {
        Activity activity=this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    private String genarateID() {

        String did = null, sd = null;

        try {
            Cursor cursor = DBhelper.search("SELECT Chid FROM chaneling2");
//            Activity activity=getActivity();
//            Toast.makeText(activity,"Cursor is there", Toast.LENGTH_SHORT).show();

            if (cursor.moveToLast())//move to last value
            {
                did = cursor.getString(cursor.getColumnIndex("Chid"));

                System.out.println("If error " + did);

                String sdid = did.substring(3);//check the how many charcters are avaliable in this id.

                int count = Integer.parseInt(sdid);//take integer value

                System.out.println("Int val: " + sdid);

                if (count >= 99) {
                    count += 1;
                    sd = "CH-" + count;
                } else if (count < 99 && count >= 9) {
                    count += 1;
                    sd = "CH-0" + count;
                } else if (count > 0 && count < 9) {
                    count += 1;
                    sd = "CH-00" + count;
                }
            } else {
                sd = "CH-001";
            }
        } catch (Exception e) {

            System.out.println("Auto gen error : " + e);
        }

        System.out.println("DID : " + sd);

        return sd;//return value

    }




}
