package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class screen extends AppCompatActivity {

    private static int splashscreen=5000;
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        text=findViewById(R.id.txt_load);
        image=findViewById(R.id.img_load);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.anmi);
        text.startAnimation(a);
        image.startAnimation(a);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(screen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashscreen);

    }
}
