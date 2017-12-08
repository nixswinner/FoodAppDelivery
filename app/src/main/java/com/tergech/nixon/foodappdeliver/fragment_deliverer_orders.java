package com.tergech.nixon.foodappdeliver;

import android.app.ProgressDialog;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class fragment_deliverer_orders extends Fragment {
    private ProgressDialog pDialog ,progressDialog;
    private ListView listView;
    private static String delivery_status;
    private TextView txtDelivery_status;

    // URL to get fetcing JSON
    private static String url = "http://192.168.137.1/Api/Food_Delivery/public/index.php/api/getDelivererOrders";
    String delivery_id="1";
    ArrayList<HashMap<String, String>> orderlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the login_fragment
        View view=inflater.inflate(R.layout.fragment_deliverer_orders, parent, false);
        // Progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        //getting reference to listview
        orderlist = new ArrayList<>();
        listView=(ListView)view.findViewById(R.id.listView);
        txtDelivery_status=(TextView)view.findViewById(R.id.txt_deliver_status);
       // txtDelivery_status.setText("Pending");

        //executing method to get orders
        try {
            new GetOrders().execute();
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_SHORT).show();
        }



        return view ;
    }

    private class GetOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait getting your orders....");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through food orders
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String food_ordered = c.getString("food_ordered");
                        String cost = c.getString("total_cost");
                        String destination = c.getString("delivery_destination");
                        String recepient_name = c.getString("name");
                        String recepient_phone = c.getString("phone");//delivery_status
                        delivery_status = c.getString("delivery_status");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("food_ordered", food_ordered);
                        contact.put("cost", cost);
                        contact.put("destination", destination);
                        contact.put("name", recepient_name);
                        contact.put("phone", recepient_phone);
                        contact.put("delivery_status", delivery_status);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        orderlist.add(contact);
                    }
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
            
            try {
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), orderlist,
                        R.layout.myorders_delivery_list, new String[]{"id", "food_ordered","cost","destination","name","phone","delivery_status"}, new int[]{
                        R.id.txt_deliver_order_id, R.id.txt_deliver_food_ordered,R.id.txt_deliver_total_cost,
                        R.id.txt_deliver_destination,R.id.txt_deliver_Recepient,R.id.txt_deliver_recephone,R.id.txt_deliver_status});

                int status=Integer.parseInt(delivery_status);

              //colors #FF69DC71 delivered #FFE1AB96 undelivered color

                listView.setAdapter(adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                if (status==0)
                {
                    //txtDelivery_status.setText("Pending");
                    listView.setBackgroundColor(Color.parseColor("#FFF1DDD0"));
                }else if(status==1)
                {
                    listView.setBackgroundColor(Color.parseColor("#FF69DC71"));
                    //txtDelivery_status.setText("Delivered");
                }
            }catch(Exception ex)
            {
                Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_LONG).show();
            }




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(getActivity(),"You have selected  "+orderlist.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps


                    try{
                        final String order_id=orderlist.get(pos).get("id");
                        String destination=orderlist.get(pos).get("destination");
                        // Toast.makeText(getActivity(),"You have selected  "+food_name,Toast.LENGTH_SHORT).show();
                        String data=orderlist.get(pos).toString();
                        //myarray[0]=calories;


/*
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("\n\n\nDelivery Alert")
                                .setMessage("Do you want to deliver Order ID  "+order_id +" to "+destination+" ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //update in the database that the order has been taken by a particular delivey
                                        //method to deliver
                                       // deliverOrder(order_id,delivery_id);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .create();
                        dialog.show();*/

                    }catch (Exception ex)
                    {
                        Toast.makeText(getActivity(),"Error "+ex,Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

}