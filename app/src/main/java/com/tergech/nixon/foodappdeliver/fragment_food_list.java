package com.tergech.nixon.foodappdeliver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class fragment_food_list extends Fragment {

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://nixontonui.net16.net/MyDB/fetch.php";

    ArrayList<HashMap<String, String>> foodlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_food_list, parent, false);


        foodlist = new ArrayList<>();

        lv = (ListView) view.findViewById(R.id.listView);

                //new GetFood().execute();




        return view ;
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }





}