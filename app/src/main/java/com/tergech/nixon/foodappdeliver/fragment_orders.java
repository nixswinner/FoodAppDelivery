package com.tergech.nixon.foodappdeliver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class fragment_orders extends Fragment {
    private  int delay=1000;
    private static int time=20;
    private String url="http://192.168.137.1/Api/Food_Delivery/public/index.php/api/getMyOrders/";
    private ProgressDialog pDialog;
    ProgressDialog progressDialog;
    ListView lv;
    String user_id;
    ArrayList<HashMap<String, String>> myorderList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment

        View view=inflater.inflate(R.layout.fragment_orders, parent, false);


//

        myorderList = new ArrayList<>();
        lv=(ListView) view.findViewById(R.id.listView);
        user_id="10";//SaveSharedPreference.getUserId(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        try
        {
            new getMyOrders().execute();;

        }catch (Exception ex)
        {
            Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_SHORT).show();
        }


     /*   ListView listView=(ListView)view.findViewById(R.id.listView);

        Database db=new Database(getActivity());
        String date=getNow();
        String food_order=db.get_food_Ordered(date);
        String food_cost=db.get_food_cost(date);
        String [] food=convertStringToArray(food_order);
        ArrayList<HashMap<String, String>> orderlist;
        orderlist = new ArrayList<>();

        int size=food.length;

        if(size==0)
        {
            Toast.makeText(getActivity(),"You haven't make any order",Toast.LENGTH_SHORT).show();
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("\n\n\n\n\nAttention")
                    .setCancelable(false)
                    .setMessage("You haven't make any order yet!\nDo you want to make a food order now?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //open the food order activity
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft .replace(R.id.content,new fragment_main());
                            ft .addToBackStack(null);
                            ft  .commit();


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //still go back to ordering page
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft .replace(R.id.content,new fragment_main());
                            ft .addToBackStack(null);
                            ft  .commit();

                        }
                    })
                    .create();
            dialog.show();
        }*/

     /*   for(int x=0;x<size;x++)
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

        listView.setAdapter(adapter);*/

        return view ;
    }
    public void time()
    {
        time=time-1;
    }





    //.......................................Start of fetch from db................................................
    private class getMyOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait loading my orders ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url+user_id);

            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String user_id = c.getString("user_id");
                        String food_ordered = c.getString("food_ordered");
                        String Amount = c.getString("Amount");
                        String total_cost = c.getString("total_cost");
                        String delivery_status=c.getString("delivery_status");
                        String delivery_destination=c.getString("delivery_destination");
                        String ordered_at=c.getString("ordered_at");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("user_id", user_id);
                        contact.put("food_ordered", food_ordered);
                        contact.put("Amount", Amount);
                        contact.put("total_cost", total_cost);
                        contact.put("delivery_status", delivery_status);
                        contact.put("delivery_destination", delivery_destination);
                        contact.put("ordered_at", ordered_at);//order time
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        myorderList.add(contact);
                    }

                    Toast.makeText(getActivity(),
                            "Cost " ,
                            Toast.LENGTH_LONG)
                            .show();


                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), myorderList,
                    R.layout.myorders_list_item, new String[]{"food_ordered","total_cost","ordered_at"}, new int[]{
                    R.id.foodname,R.id.cost, R.id.txtquantity});

            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


//long press to cancle order
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                    // builderSingle.setIcon(R.drawable.green_tick_add);
                    builderSingle.setTitle("Cancle Order");
                    builderSingle.setMessage("Do you want to cancle this order ?");

                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //makebooking(driver_id,passID);
                        }
                    });


                    builderSingle.show();
                    return false;
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(getActivity(),"You have selected  "+myorderList.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps

            /*        try{
                        String route_name=myorderList.get(pos).get("routeName");
                        final String driver_id=myorderList.get(pos).get("driver_id");
                        String time=myorderList.get(pos).get("_time");
                        String date=myorderList.get(pos).get("_date");
                        //alert confirming booking
                        String data=myorderList.get(pos).toString();
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                        // builderSingle.setIcon(R.drawable.green_tick_add);
                        builderSingle.setTitle("Confirm Booking");
                        builderSingle.setMessage("Do you want to complete your booking for "+route_name+" route at "+time+" on "+date+" ?");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               //makebooking(driver_id,passID);
                            }
                        });


                        builderSingle.show();


                        //myarray[0]=calories;

                        //storing on Hashmaps
                        // mMap.put(food_name, cost);

                    }catch (Exception ex)
                    {
                        Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_LONG).show();
                    }*/

                }
            });


        }

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