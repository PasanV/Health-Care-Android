package com.example.healthcare.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.MainActivity;
import com.example.healthcare.R;

import java.util.Random;

public class register_form extends AppCompatActivity implements View.OnClickListener {

    EditText name,pass,confpass,email,phone;
    Button login,register;
    LinearLayout lay;
    String cusid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        DBhelper.initdb(this);



        name=findViewById(R.id.txt_regname);
        pass=findViewById(R.id.txt_regpass);
        confpass=findViewById(R.id.txt_regconfpass);
        email=findViewById(R.id.txt_regemail);
        phone=findViewById(R.id.txt_regphone);
        login=findViewById(R.id.btn_login);
        register=findViewById(R.id.btn_register);
        lay=findViewById(R.id.linear_layout);

        Animation appear= AnimationUtils.loadAnimation(this,R.anim.anmi);
        lay.startAnimation(appear);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        email.setOnClickListener(this);

        cusid=genarateID();

        int length=4;
        pass.setText(Getpassword(length));

    }

    @Override
    public void onClick(View v) {

        if(v==login)
        {
            Intent intent= new Intent(register_form.this, MainActivity.class);
            startActivity(intent);
        }

        else if (v==register)
        {
            if (name.getText().length() == 0 || pass.getText().length() == 0|| pass.getText().length() == 0
                    || confpass.getText().length() == 0|| email.getText().length() == 0) {
                Toast.makeText(this, "Please Fill Details", Toast.LENGTH_SHORT).show();
            }
            if(!pass.getText().toString().equals(confpass.getText().toString()))
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                confpass.setTextColor(Color.RED);
                confpass.setError("password and confirm password dosent match");
            }



            else
            {
                String cus;
                DBhelper.excute("insert into register values ( '"+cusid+"', '" +name.getText()+"', '"+pass.getText()+"', '"+email.getText()+"'" +
                        " ,'"+phone.getText()+"' )");

                Toast.makeText(this,"Data Registered",Toast.LENGTH_LONG).show();
            }


        }




    }

    //random passwors builder
    public String Getpassword(int length)
    {
        char[] chars="123456789".toCharArray();
        StringBuilder stringBuilder=new StringBuilder();

        Random random= new Random();

        for (int i=0;i<length;i++)
        {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();

    }


    private String genarateID() {

        String did = null, sd = null;

        try {
            Cursor cursor = DBhelper.search("SELECT Cusid FROM register");
//            Activity activity=getActivity();
//            Toast.makeText(activity,"Cursor is there", Toast.LENGTH_SHORT).show();

            if (cursor.moveToLast())//move to last value
            {
                did = cursor.getString(cursor.getColumnIndex("Cusid"));

                System.out.println("If error " + did);

                String sdid = did.substring(3);//check the how many charcters are avaliable in this id.

                int count = Integer.parseInt(sdid);//take integer value

                System.out.println("Int val: " + sdid);

                if (count >= 99) {
                    count += 1;
                    sd = "CU-" + count;
                } else if (count < 99 && count >= 9) {
                    count += 1;
                    sd = "CU-0" + count;
                } else if (count > 0 && count < 9) {
                    count += 1;
                    sd = "CU-00" + count;
                }
            } else {
                sd = "CU-001";
            }
        } catch (Exception e) {

            System.out.println("Auto gen error : " + e);
        }

        System.out.println("DID : " + sd);

        return sd;//return value

    }



}
