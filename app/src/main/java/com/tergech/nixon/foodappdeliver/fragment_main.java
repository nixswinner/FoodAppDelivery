package com.tergech.nixon.foodappdeliver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class fragment_main extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_main, parent, false);

        Button list=(Button)view.findViewById(R.id.btn);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(getActivity(),foodlist.class);
                    startActivity(intent);
                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error :"+ex,Toast.LENGTH_LONG).show();
                }
            }
        });




        return view ;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}