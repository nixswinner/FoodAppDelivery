package com.tergech.nixon.foodappdeliver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class fragment_orders extends Fragment {
    private  int delay=1000;
    private static int time=20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_orders, parent, false);
        ListView listView=(ListView)view.findViewById(R.id.listView);

        Database db=new Database(getActivity());
        String date=getNow();
        String food_order=db.get_food_Ordered(date);
        String food_cost=db.get_food_cost(date);
        String [] food=convertStringToArray(food_order);
        ArrayList<HashMap<String, String>> orderlist;
        orderlist = new ArrayList<>();

        int size=food.length;

        for(int x=0;x<size;x++)
        {
            // tmp hash map for single food_item
            HashMap<String, String> food_order_list = new HashMap<>();

            // adding each child node to HashMap key => value
            food_order_list.put("food_name", ""+food[x]);
            food_order_list.put("cost", "Total Cost:Ksh"+food_cost);
            food_order_list.put("date", "Date:"+"21-07-2017");
            // adding contact to contact list
            orderlist.add(food_order_list);
        }

        //populating the orders in a list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), orderlist,
                R.layout.myorders_list_item, new String[]{"food_name","cost","date"}, new int[]{
                R.id.foodname,R.id.cost, R.id.txtquantity});

        listView.setAdapter(adapter);

        return view ;
    }
    public void time()
    {
        time=time-1;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
    public static String[] convertStringToArray(String str)
    {
        String strSeparator="_";
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