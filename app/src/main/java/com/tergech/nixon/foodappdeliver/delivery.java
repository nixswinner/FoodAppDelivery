package com.tergech.nixon.foodappdeliver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tonui on 6/22/2017.
 */

public class delivery extends AppCompatActivity {

    private ProgressDialog pDialog ,progressDialog;
    private ListView listView;
     String[] outputStrArr[];
    Button btnOrder;
    String [] myarray={};
    String destination;
    int Total_cost;
    //Map mMap = new HashMap();
    HashMap<String,String> mMap = new HashMap<String,String >();
    //to store the value and the quantity of food selected
    HashMap<String,Integer> map_food_quantity = new HashMap<String,Integer >();
    
    // URL to get fetcing JSON
    private static String url = "http://192.168.137.1/Api/Food_Delivery/public/index.php/api/getOrders";
    private String url_post="http://192.168.137.1/Api/Food_Delivery/public/index.php/api/getAnOrdersToDeliver";
    String delivery_id="1";
    ArrayList<HashMap<String, String>> foodlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        // Progress dialog
        progressDialog = new ProgressDialog(delivery.this);
        progressDialog.setCancelable(false);
        //getting reference to listview
        foodlist = new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        progressDialog = new ProgressDialog(delivery.this);
        progressDialog.setCancelable(false);

        //executing method to get orders
        try {
            new GetOrders().execute();
        }
          catch (Exception ex)
          {
              Toast.makeText(delivery.this,"Error "+ex,Toast.LENGTH_SHORT).show();
          }
    }



    private class GetOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(delivery.this);
            pDialog.setMessage("Please wait getting customer orders....");
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
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("food_ordered", food_ordered);
                        contact.put("cost", cost);
                        contact.put("destination", destination);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        foodlist.add(contact);
                    }
                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(delivery.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(delivery.this,
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
                       delivery.this, foodlist,
                       R.layout.delivery_list, new String[]{"id", "food_ordered","cost","destination"}, new int[]{
                       R.id.txt_deliver_order_id, R.id.txt_deliver_food_ordered,R.id.txt_deliver_total_cost,
                       R.id.txt_deliver_destination});

               listView.setAdapter(adapter);
               listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
           }catch(Exception ex)
           {
               Toast.makeText(delivery.this,"Error "+ex,Toast.LENGTH_LONG).show();
           }




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(delivery.this,"You have selected  "+foodlist.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps


                    try{
                        final String order_id=foodlist.get(pos).get("id");
                        String destination=foodlist.get(pos).get("destination");
                       // Toast.makeText(delivery.this,"You have selected  "+food_name,Toast.LENGTH_SHORT).show();
                        String data=foodlist.get(pos).toString();
                        //myarray[0]=calories;



                        AlertDialog dialog = new AlertDialog.Builder(delivery.this)
                                .setTitle("\n\n\nDelivery Alert")
                                .setMessage("Do you want to deliver Order ID  "+order_id +" to "+destination+" ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //update in the database that the order has been taken by a particular delivey
                                        //method to deliver 
                                        deliverOrder(order_id,delivery_id);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .create();
                        dialog.show();

                    }catch (Exception ex)
                    {
                        Toast.makeText(delivery.this,"Error "+ex,Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }

    private void deliverOrder(final String order_id, final String deliverer_id) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        progressDialog.setMessage("Requesting for delivery");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url_post, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response.toString());
                Toast.makeText(getApplicationContext(), " "+response.toString(), Toast.LENGTH_SHORT).show();

                hideDialog();
                //Toast.makeText(getApplicationContext(), "Response "+response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


                    // String user = jObj.getJSONObject("user").getString("name");
                    // Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Registered!", Toast.LENGTH_SHORT).show();

                    // Launch login activity
                    Toast.makeText(getApplicationContext(), "Delivery request successfull,Please deliver now ", Toast.LENGTH_SHORT).show();
                /*    Intent intent = new Intent(
                            getApplicationContext(),
                            driver.class);
                    startActivity(intent);
                    getApplicationContext().finish();*/

                } catch (JSONException e) {
                    e.printStackTrace();
                   /* Toast.makeText(getApplicationContext(),
                            "Error Occured Try again"+e, Toast.LENGTH_LONG).show();*/
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id", order_id);
                params.put("deliverer_id", deliverer_id);

                return params;
            }
        };
        // Adding request to request queue
        //requestQueue.add(strReq);
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }


    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



}
