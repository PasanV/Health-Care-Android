package com.example.healthcare;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.healthcare.R;
import com.example.healthcare.chanelling_details.channeling_frag;
import com.example.healthcare.clinic_details.clinic_frag;
import com.example.healthcare.managedoctors.manage_doctors;


/**
 * A simple {@link Fragment} subclass.
 */
public class home_frag extends Fragment implements View.OnClickListener{


    RelativeLayout r_doctors,r_chanelling,r_hospital,r_treatments;


    public home_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home_frag, container, false);


        navigation.Fragname="Home";


        r_doctors=v.findViewById(R.id.R_doctorslayout);
        r_chanelling=v.findViewById(R.id.R_chanellinglayout);
        r_hospital=v.findViewById(R.id.R_hospitallayout);


        r_doctors.setOnClickListener(this);
        r_chanelling.setOnClickListener(this);
        r_hospital.setOnClickListener(this);



return v;
    }

    @Override
    public void onClick(View v) {

        Fragment frag=null;

        if(v==r_doctors)
        {
            frag = new manage_doctors();

        }
        else if(v==r_chanelling)
        {
            frag = new channeling_frag();

        }
        else if(v==r_hospital)
        {
            frag = new clinic_frag();

        }

        if(frag!=null){
            android.app.FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.frag_holder,frag).commit();
        }

    }







}
