package com.tergech.nixon.foodappdeliver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fragment_orders extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_orders, parent, false);


        Database db=new Database(getActivity());
        String date=getNow();
        String food_order=db.get_food_Ordered(date);
        String [] food=convertStringToArray(food_order);







        return view ;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
    public static String[] convertStringToArray(String str)
    {
        String strSeparator=",";
        String[] arr=str.split(strSeparator);
        return arr;
    }


    private String getNow(){
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}