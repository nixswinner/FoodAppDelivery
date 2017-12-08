package com.tergech.nixon.foodappdeliver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tonui on 6/22/2017.
 */

public class foodlist extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ListView lv;
     String[] outputStrArr[];
    Button btnOrder;
    String [] myarray={};
    String destination;
    int Total_cost;
    //Map mMap = new HashMap();
    HashMap<String,String> mMap = new HashMap<String,String >();
    //to store the value and the quantity of food selected
    HashMap<String,Integer> map_food_quantity = new HashMap<String,Integer >();

    String[] delivery_destinations = { "Sunrise ", "Tamals", "Laduvet ", "Adison", "Mt.Kenya ", "Batian",
            "Nyandarua ", "Congo", "Ngamia ", "Kens" };
    // URL to get fetcing JSON
    private static String url = "http://192.168.137.1/Api/Food_Delivery/public/index.php/api/getFoodList";
    ArrayList<HashMap<String, String>> foodlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_food_list);

        foodlist = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listView);

        new GetFood().execute();
        btnOrder=(Button)findViewById(R.id.order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(foodlist.this,order_confirm.class);
                startActivity(intent);*/
              String data="";
                /*  for (int i=0;i<myarray.length;i++)
                {
                    data=data+myarray[i];
                }
                Toast.makeText(foodlist.this,"Saved are "+data,Toast.LENGTH_LONG).show();*/

                Iterator iter = mMap.entrySet().iterator();


               int x=0;
               int z= mMap.size();//getting the size of hashmap
                final String myfood[]=new String[z];
                final String myfood_quantity[]=new String[z];
                //getting the key from the hashmap of the selected food
                while (iter.hasNext()) {
                    Map.Entry mEntry = (Map.Entry) iter.next();
                    //System.out.println(mEntry.getKey() + " : " + mEntry.getValue());

                     data=""+mEntry.getKey();//+"-"+mEntry.getKey();

                        String c=mMap.get(data);
                        int cost=Integer.parseInt(c);
                        int quantity=map_food_quantity.get(data);
                        int t=quantity*cost;

                        Total_cost=Total_cost+t;

                    try{
                        String food=quantity+" "+data;
                        myfood[x]=food;
                        myfood_quantity[x]=""+quantity;

                    }catch (Exception ex)
                    {
                        Toast.makeText(foodlist.this,"Error"+ex,Toast.LENGTH_LONG).show();
                    }
                    x=x+1;
                }
                int y=myfood.length;


                final ArrayAdapter<String> adp = new ArrayAdapter<String>(foodlist.this,
                        android.R.layout.simple_spinner_item, delivery_destinations);

              /*  //TextView tx= (TextView)findViewById(R.id.txt1);
                final Spinner sp = new Spinner(foodlist.this);
                sp.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                sp.setAdapter(adp);*/


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(foodlist.this)
                        .setTitle("Confirm:Is this is what you have ordered?")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(foodlist.this);
                                builder.setTitle("Choose Delivery Destination ");
                                builder.setView(sp);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(foodlist.this, "Your order is being processed and shall be delivered in a while Total Cost "+Total_cost, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.create().show();*/
                                //openning activity
                                String food=ConvertArrayToString(myfood,myfood.length);
                                //getting individual quanties
                                String food_quantity=ConvertArrayToString(myfood_quantity,myfood_quantity.length);
                                Intent intent =new Intent(foodlist.this,order_confirm.class);
                                intent.putExtra("food",food);
                                intent.putExtra("food_quantity",food_quantity);
                                intent.putExtra("cost",""+Total_cost);
                                startActivity(intent);


                                Toast.makeText(foodlist.this, "Your order is being processed and shall be delivered in a while Total Cost "+Total_cost, Toast.LENGTH_SHORT).show();
                                Total_cost=0;
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(foodlist.this, "You have cancled your order,You can reorder", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setItems(myfood, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(getActivity(), outputStrArr[which], Toast.LENGTH_SHORT).show();
                            }
                        });


                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

            }
        });
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
        return str+".";
    }
/*    //selected item on spinner
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch (position)
        {
            case 0:
                Toast.makeText(foodlist.this,"Sunrise",Toast.LENGTH_SHORT).show();
                destination="Sunrise";
                break;
            case 1:
                Toast.makeText(foodlist.this,"Tamals",Toast.LENGTH_SHORT).show();
                destination="Tamals";
            case 2:
                Toast.makeText(foodlist.this,"Laduvet",Toast.LENGTH_SHORT).show();
                destination="Laduvet";
            case 3:
                Toast.makeText(foodlist.this,"Mt.Kenya",Toast.LENGTH_SHORT).show();
                destination="Mt.Kenya";
        }
    }*/

    //.......................................Start of fetch from db................................................
    private class GetFood extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(foodlist.this);
            pDialog.setMessage("Please wait...");
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

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String food_name = c.getString("name");
                        String cost = c.getString("cost");
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("food_name", food_name);
                        contact.put("cost", cost);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        foodlist.add(contact);
                    }
                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(foodlist.this,
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
                        Toast.makeText(foodlist.this,
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
                    foodlist.this, foodlist,
                    R.layout.list_item, new String[]{"food_name", "cost"}, new int[]{
                    R.id.foodname, R.id.cost});

            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    //Toast.makeText(foodlist.this,"You have selected  "+foodlist.get(pos),Toast.LENGTH_LONG).show();
                    //hashmaps


                    try{
                        String food_name=foodlist.get(pos).get("food_name");
                        String cost=foodlist.get(pos).get("cost");
                        alert(food_name);
                        Toast.makeText(foodlist.this,"You have selected  "+food_name,Toast.LENGTH_SHORT).show();
                        String data=foodlist.get(pos).toString();
                        //myarray[0]=calories;

                        //storing on Hashmaps
                        mMap.put(food_name, cost);

                    }catch (Exception ex)
                    {
                        Toast.makeText(foodlist.this,"Error "+ex,Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

    }
    //.......................................end of fetch from db................................................
    public void add_food_quantity(String food_name,int x)
    {
        map_food_quantity.put(food_name, x);
    }


    public void alert(final String Message)
    {

        final EditText taskEditText = new EditText(foodlist.this);
        taskEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        AlertDialog dialog = new AlertDialog.Builder(foodlist.this)
                .setTitle("\n\n\nQuantify")
                .setMessage("How many "+Message+"(s)"+" do you want?")
                .setView(taskEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String no = String.valueOf(taskEditText.getText());

                       if(no ==" ")
                       {


                           Toast.makeText(foodlist.this,"Please Specify the Quantity ",Toast.LENGTH_SHORT).show();
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
