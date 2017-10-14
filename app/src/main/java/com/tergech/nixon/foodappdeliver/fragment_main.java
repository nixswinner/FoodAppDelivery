package com.tergech.nixon.foodappdeliver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class fragment_main extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_main, parent, false);
        Button order=(Button)view.findViewById(R.id.btnorder);
        Button list=(Button)view.findViewById(R.id.btn);
        Button deliver=(Button)view.findViewById(R.id.btn_deliver);

        //deliver temporary
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),deliverer_dashboard.class);
                startActivity(intent);
            }
        });

        //adding animations
        final Animation animTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_translate);
        order.startAnimation(animTranslate);
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


    order.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //view.startAnimation(animTranslate);
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