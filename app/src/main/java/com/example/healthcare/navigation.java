package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.DBHelperclass.DBhelper;
import com.example.healthcare.customer.cushome_frag;
import com.example.healthcare.customer.register_form;
import com.example.healthcare.managedoctors.manage_doctors;
import com.google.android.material.navigation.NavigationView;

public class navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String Fragname = "Home";


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String role;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DBhelper.initdb(this);


        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        Toast.makeText(this, "role :" + intent.getStringExtra("role"), Toast.LENGTH_LONG).show();

        pref = getApplicationContext().getSharedPreferences(MainActivity.MYPRF, 0);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.appbar_scrolling_view_behavior, R.string.hide_bottom_view_on_scroll_behavior);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        Menu menu = navigationView.getMenu();
        MenuItem homemenu = menu.findItem(R.id.nav_home);
        onNavigationItemSelected(homemenu);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment frag = null;

        switch (item.getItemId()) {

            case R.id.nav_home:

                if(role.equals("admin"))
                {
                    frag = new home_frag();
                    Toast. makeText(this,"Welcome Admin",Toast.LENGTH_LONG).show();
                }
                if(role.equals("user"))
                {  String cusname=null;

                   frag=new cushome_frag();

                    Cursor cursor=DBhelper.search("select * from register where Cusid='"+pref.getString("uid",null)+"'");

                    while (cursor.moveToNext())
                    {
                        cusname=cursor.getString(cursor.getColumnIndex("name"));
                    }
                    //Toast. makeText(this,"Welcome "+cusname,Toast.LENGTH_LONG).show();
                    //Toast. makeText(this,"Home ",Toast.LENGTH_LONG).show();
                    Toast. makeText(this,"Welcome User "+pref.getString("uid",null),Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.nav_logout:
                Intent intent= new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                break;
        }

        if(frag!= null)
        {
           android.app.FragmentManager fm=getFragmentManager();
           fm.beginTransaction().replace(R.id.frag_holder,frag).commit();
        }


        drawerLayout.closeDrawer(GravityCompat.START);


        return false;
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Fragment frag=null;
        if(Fragname=="doc")
        {

           frag=new manage_doctors();

        }
        if(Fragname=="back")
        {

            frag=new home_frag();

        }
        if(Fragname=="Cushome")
        {

            frag=new cushome_frag();

        }


        if(frag!=null){
            android.app.FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.frag_holder,frag).commit();
        }

    }
}
