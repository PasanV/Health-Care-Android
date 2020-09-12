package com.example.healthcare.managedoctors;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.example.healthcare.R;
import com.example.healthcare.navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class manage_doctors extends Fragment implements View.OnClickListener {

    RelativeLayout register,update,view;
    public manage_doctors() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_manage_doctors, container, false);

        navigation.Fragname="back";

        register=v.findViewById(R.id.R_adddoc);
        update=v.findViewById(R.id.R_updatedoct);
        view=v.findViewById(R.id.R_viewdoc);

        register.setOnClickListener(this);
        update.setOnClickListener(this);
        view.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

//
        Fragment frag=null;
        if(v==register)
        {
            frag = new Adddoctors();

        }
        else if(v==update)
        {
            frag = new updatedoctor_farg();
        }
        else if(v==view)
        {
            frag = new Viewdoc_frag();
        }

       if(frag!=null){
            android.app.FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.frag_holder,frag).commit();
        }

    }
}
