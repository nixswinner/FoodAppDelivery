package com.tergech.nixon.foodappdeliver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tonui on 6/22/2017.
 */

public class delivery extends AppCompatActivity {

    private ProgressDialog pDialog;
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
    private static String url = "http://nixontonui.net16.net/MyDB/fetch_deliveries.php";
    ArrayList<HashMap<String, String>> foodlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        //getting reference to listview
        listView=(ListView)findViewById(R.id.listView);

        //executing method to get orders
        new GetOrders();
          
    }

    //converting array to string
    public  String ConvertArrayToString(String[] array,int size)
    {
        String strSeparator=",";

        String str="";
        for (int i=0;i<size;i++)
        {
            str=str+array[i];
            if (i<size-1)
            {
                str=str+strSeparator;
            }
        }
        return str+"_";
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
                        String cost = c.getString("cost");
                        String destination = c.getString("destination");
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
            ListAdapter adapter = new SimpleAdapter(
                    delivery.this, foodlist,
                    R.layout.delivery_list, new String[]{"id", "food_ordered","cost","destination"}, new int[]{
                    R.id.txtorder_id, R.id.txtfood_ordered,R.id.txtcost,R.id.txtdestination});

            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(foodlist.this,"You have selected  "+foodlist.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps


                    try{
                        String order_id=foodlist.get(pos).get("id");
                        String destination=foodlist.get(pos).get("destination");
                       // Toast.makeText(delivery.this,"You have selected  "+food_name,Toast.LENGTH_SHORT).show();
                        String data=foodlist.get(pos).toString();
                        //myarray[0]=calories;



                        AlertDialog dialog = new AlertDialog.Builder(delivery.this)
                                .setTitle("\n\n\nDelivery Alert")
                                .setMessage("Do you want to deliver Order  "+order_id +" to "+destination+" ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //update in the database that the order has been taken by a particular delivey
                                        String delivery;


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
    public void add_food_quantity(String food_name,int x)
    {
        map_food_quantity.put(food_name, x);
    }


    public void alert(final String Message)
    {

        final EditText taskEditText = new EditText(delivery.this);
        taskEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        AlertDialog dialog = new AlertDialog.Builder(delivery.this)
                .setTitle("\n\n\nQuantify")
                .setMessage("How many "+Message+"(s)"+" do you want?")
                .setView(taskEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String no = String.valueOf(taskEditText.getText());

                       if(no ==" ")
                       {


                           Toast.makeText(delivery.this,"Please Specify the Quantity ",Toast.LENGTH_SHORT).show();
                       }
                       else {
                          // Quantity=no;
                           int x =Integer.parseInt(no);
                           add_food_quantity(Message,x);

                       }

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);


    }

}
