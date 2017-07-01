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
    Map mMap = new HashMap();

    // URL to get fetcing JSON
    private static String url = "http://nixontonui.net16.net/MyDB/fetchfood.php";
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
              String data="";
                /*  for (int i=0;i<myarray.length;i++)
                {
                    data=data+myarray[i];
                }
                Toast.makeText(foodlist.this,"Saved are "+data,Toast.LENGTH_LONG).show();*/

                Iterator iter = mMap.entrySet().iterator();


               int x=0;
               int z= mMap.size();//getting the size of hashmap
                String myfood[]=new String[z];
                while (iter.hasNext()) {
                    Map.Entry mEntry = (Map.Entry) iter.next();
                    //System.out.println(mEntry.getKey() + " : " + mEntry.getValue());
                     data=""+mEntry.getKey();
                    try{
                        myfood[x]=data;
                    }catch (Exception ex)
                    {
                        Toast.makeText(foodlist.this,"Error"+ex,Toast.LENGTH_LONG).show();
                    }
                    x=x+1;
                }
                int y=myfood.length;
                String[]test={"Hello","Test data","Thanks"};
                Toast.makeText(foodlist.this,"Saved are \n"+z,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(foodlist.this)
                        .setTitle("Confirm:This is what you have ordered ")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(foodlist.this, "Your order is being processed", Toast.LENGTH_SHORT).show();


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
                        String food_name = c.getString("food_name");
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
    public void alert(String Message)
    {
        final EditText taskEditText = new EditText(foodlist.this);
        taskEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        AlertDialog dialog = new AlertDialog.Builder(foodlist.this)
                .setTitle("\n\n\nQuantify")
                .setMessage("How many "+Message+" do you want?")
                .setView(taskEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String no = String.valueOf(taskEditText.getText());

                       if(no==" ")
                       {

                           Toast.makeText(foodlist.this,"Please Specify the Quantity ",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           int Cups=Integer.parseInt(no);
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
