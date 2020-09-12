package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.customer.register_form;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String MYPRF="ABC";
    EditText username, password;
    Button login;
    LinearLayout linearlayout;
    TextView text;
    ImageView img;
    SharedPreferences shar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBhelper.initdb(this);

        login = findViewById(R.id.login);
        linearlayout = findViewById(R.id.linear_lay);
        text = findViewById(R.id.txtregister);
        img = findViewById(R.id.imageViewdoc);
        username = findViewById(R.id.txt_name);
        password = findViewById(R.id.txt_pass);

        shar=getApplicationContext().getSharedPreferences(MYPRF,0);


        text.setOnClickListener(this);
        login.setOnClickListener(this);

        Animation appear = AnimationUtils.loadAnimation(this, R.anim.anmi);
        linearlayout.startAnimation(appear);
    }

    @Override
    public void onClick(View v) {

        if (v == login)
        {
            Log.e("test1", "onClick: is working");

            if (username.getText().length() == 0 || password.getText().length() == 0) {
                Toast.makeText(this, "Please Fill the Username and password", Toast.LENGTH_SHORT).show();
            }
            else if (username.getText().toString().equals("admin") && password.getText().toString().equals("123"))
            //   else if( username.getText()=="Admin" && password =="123" )

            {
                Intent intent = new Intent(MainActivity.this, navigation.class);
                intent.putExtra("role","admin");
                startActivity(intent);
                Animation move = AnimationUtils.loadAnimation(this, R.anim.move);
                img.startAnimation(move);
            }

            else
            {
                Log.e("test2", "onClick: else part " );

                Cursor cursor=DBhelper.search("select * from register where name='"+username.getText()+"' " +
                        "AND password ='"+password.getText()+"'");
                if(cursor.moveToNext())
                {
                    Intent intent = new Intent(MainActivity.this, navigation.class);
                    intent.putExtra("role","user");

                    SharedPreferences.Editor editor=shar.edit();

                    editor.putString("uid",cursor.getString(0));


                    editor.commit();

                    startActivity(intent);
                    Animation move = AnimationUtils.loadAnimation(this, R.anim.move);
                    img.startAnimation(move);
                }

            }
            Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_LONG).show();
            username.setText("");
            password.setText("");

        } else if (v == text) {
            Intent intent = new Intent(MainActivity.this, register_form.class);
            startActivity(intent);
        }
    }



}

