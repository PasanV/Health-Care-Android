package com.example.healthcare.customer;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.healthcare.MainActivity;
import com.example.healthcare.R;
import com.example.healthcare.customer.cusappoin.cusappoin_frag;
import com.example.healthcare.customer.viewappoin.cusviewappoin_frag;
import com.example.healthcare.customer.viewappoin.viewappoinmant_frag;
import com.example.healthcare.navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class cushome_frag extends Fragment implements View.OnClickListener {

    public cushome_frag() {
        // Required empty public constructor
    }

    RelativeLayout appoin,viewappoin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_cushome_frag, container, false);

        navigation.Fragname="homefrag";

        appoin=v.findViewById(R.id.R_appoinlayout);
        viewappoin=v.findViewById(R.id.R_viewappoinlayout);



        appoin.setOnClickListener(this);
        viewappoin.setOnClickListener(this);




        return v;
    }

    @Override
    public void onClick(View v) {

        Fragment frag= null;

        if(v==appoin)
        {
            frag=new cusappoin_frag();
        }
        else if(v==viewappoin)
        {
            frag=new viewappoinmant_frag();
        }
        if(frag!=null){
            android.app.FragmentManager fm=getFragmentManager();
            fm.beginTransaction().replace(R.id.frag_holder,frag).commit();
        }


    }
}
