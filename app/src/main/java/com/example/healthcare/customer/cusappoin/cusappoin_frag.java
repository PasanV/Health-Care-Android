package com.example.healthcare.customer.cusappoin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;
import com.example.healthcare.navigation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.Environment;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class cusappoin_frag extends Fragment implements View.OnClickListener {

    DatePickerDialog picker;
    EditText name, docname, clinic,date,time,medhis;
    Spinner appoin;
    ImageButton btnimage;
    private static final int REQUEST_CODE = 100;
    String imageFilePath;
    TextView viewbtn;
    String idgen,role;
    int count=3;
    Button addapp;
    SharedPreferences pref;


    public cusappoin_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v=   inflater.inflate(R.layout.fragment_cusappoin_frag, container, false);

        Intent intent = getActivity().getIntent();
        role = intent.getStringExtra("role");
        Toast.makeText(getActivity(), "role :" + intent.getStringExtra("role"), Toast.LENGTH_LONG).show();

        DBhelper.initdb(this.getActivity());
        navigation.Fragname="Cushome";

        pref = getActivity().getApplicationContext().getSharedPreferences(MainActivity.MYPRF, 0);

        addapp=v.findViewById(R.id.btn_App);
        viewbtn=v.findViewById(R.id.viewimg);
        btnimage=v.findViewById(R.id.btn_img);
        name=v.findViewById(R.id.txtapp_name);
        docname=v.findViewById(R.id.txtapp_docname);
        clinic=v.findViewById(R.id.txtapp_clinic);
        date=v.findViewById(R.id.txtapp_date);
        time=v.findViewById(R.id.txtapp_time);
        medhis=v.findViewById(R.id.txtapp_history);
        appoin=v.findViewById(R.id.Spin_appoin);

        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);


        //loading to spinner
        final ArrayList<String> arrayList= new ArrayList<>();
        Cursor cursor = DBhelper.search("SELECT docname FROM chaneling2");
        arrayList.add("Please Select");
        while (cursor.moveToNext())
        {
            arrayList.add(cursor.getString(0));
        }

        //displaying in spinner and loading to database
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appoin.setAdapter(arrayAdapter);
        appoin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("test 3", "on item selected working");
                int did = (position);
                Log.e("test 3", "on item selected working"+did);
                Log.e("test 4", "Data "+arrayList.get(did));
                Cursor cursor = DBhelper.search("Select * from chaneling2 where docname='" + arrayList.get(did) + "'");
                Log.e("test 3", "Query executed"+cursor);

//                    StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {

                    Log.e("test 3", "cursor is working");

                    docname.setText(cursor.getString(1));
                    clinic.setText(cursor.getString(5));
                    date.setText(cursor.getString(6));
                    time.setText(cursor.getString(7));

                }
//                    ShowMessage("Data", buffer.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewbtn.setOnClickListener(this);
        btnimage.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        addapp.setOnClickListener(this);

        //creating the calender
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


        idgen = genarateID();

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==time)
        {
            //creating time selector
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

        else if(v==btnimage)
        {
            if(count==3|| count==0){
                openCameraIntent();
                count=count-1;
            }
           else if(count==2|| count==1)
            {
               showImage();
               count=count-1;
            }

        }

        else if (v==viewbtn)
        {
            showImage();
        }

        else if(v==addapp)
        {


            if(date.getText().length()==0|| time.getText().length()==0||
                    name.getText().toString().trim().length()==0|| docname.getText().toString().trim().length()==0||
                    clinic.getText().toString().trim().length()==0||
                    medhis.getText().toString().trim().length()==0)
            {
                Activity mActivity=this.getActivity();
                Toast.makeText(mActivity,"PLease Fill the Details",Toast.LENGTH_SHORT).show();

            }
            else
            {
                String id=null,patid= null;
                Cursor cursor=DBhelper.search("select * from chaneling2 where docname='"+docname.getText()+"' ");
                while (cursor.moveToNext())
                {
                    id = cursor.getString(cursor.getColumnIndex("docname"));
                }
                Drawable d = btnimage.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();

                DBhelper.excute("insert into appoinnt1 values ('"+idgen+"','"+name.getText()+"','"+docname.getText()+"'," +
                        " '"+clinic.getText()+"','"+date.getText()+"','"+time.getText()+"','"+medhis.getText()+"'" +
                        ",'"+imageFilePath+"','"+id+"','"+pref.getString("uid",null)+"' )");


                Toast. makeText(getActivity(),"Appointment Created",Toast.LENGTH_LONG).show();
            }

        }


    }

    //opening of the cammera
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_CODE);
        }
    }

    //creating the image path to save image
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                btnimage.setImageURI(Uri.parse(imageFilePath));
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //viewwing the iamge in a popup
    public void showImage() {
        Dialog builder = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageURI(Uri.parse(imageFilePath));
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


//auto gen id
    private String genarateID() {

        String did = null, sd = null;

        try {
            Cursor cursor = DBhelper.search("SELECT Apid FROM appoinnt1");
//            Activity activity=getActivity();
//            Toast.makeText(activity,"Cursor is there", Toast.LENGTH_SHORT).show();

            if (cursor.moveToLast())//move to last value
            {
                did = cursor.getString(cursor.getColumnIndex("Apid"));

                System.out.println("If error " + did);

                String sdid = did.substring(3);//check the how many charcters are avaliable in this id.

                int count = Integer.parseInt(sdid);//take integer value

                System.out.println("Int val: " + sdid);

                if (count >= 99) {
                    count += 1;
                    sd = "A-" + count;
                } else if (count < 99 && count >= 9) {
                    count += 1;
                    sd = "A-0" + count;
                } else if (count > 0 && count < 9) {
                    count += 1;
                    sd = "A-00" + count;
                }
            } else {
                sd = "A-001";
            }
        } catch (Exception e) {

            System.out.println("Auto gen error : " + e);
        }

        System.out.println("DID : " + sd);

        return sd;//return value

    }


}
