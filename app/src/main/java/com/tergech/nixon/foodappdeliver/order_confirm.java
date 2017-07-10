package com.tergech.nixon.foodappdeliver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Tonui on 7/8/2017.
 */

public class order_confirm extends AppCompatActivity {
    String food_ordered,cost,amount,destination;
    TextView txtcost,txtfood,txtamount,txtdestination,txtwhere;
    ListView listView;
    ArrayList<HashMap<String, String>> orderlist;
    private Spinner sp;
    EditText edtdetails;
    String[] delivery_destinations = { "Sunrise ", "Tamals", "Laduvet ", "Adison", "Mt.Kenya ", "Batian",
            "Nyandarua ", "Congo", "Ngamia ", "Kens" };
    Button complete_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        listView=(ListView)findViewById(R.id.listView);
        orderlist = new ArrayList<>();

        //getting ordered food
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        food_ordered=bundle.getString("food");
        cost=bundle.getString("cost");
        amount=bundle.getString("food_quantity");
        txtcost=(TextView)findViewById(R.id.txtcost);
        txtfood=(TextView)findViewById(R.id.txtfood);
        txtamount=(TextView)findViewById(R.id.txtamount);

        txtdestination=(TextView)findViewById(R.id.txtwhere ) ;
        txtwhere=(TextView)findViewById(R.id.txtwhere);
        edtdetails=(EditText)findViewById(R.id.edtdestination) ;


        //converting quantity and food order to arrays
        String[] ordered_food,order_amount;
        ordered_food=convertStringToArray(food_ordered);
        order_amount=convertStringToArray(amount);
        int size=ordered_food.length;

        for(int x=0;x<size;x++)
        {
            // tmp hash map for single food_item
            HashMap<String, String> food_order_list = new HashMap<>();

            // adding each child node to HashMap key => value
            food_order_list.put("food_name", ordered_food[x]);
            food_order_list.put("amount", order_amount[x]);
            // adding contact to contact list
            orderlist.add(food_order_list);
        }



        //setting text
        txtfood.setText(food_ordered);
        txtcost.setText("Your total cost: "+cost);
        txtamount.setText(amount);

        //populating the orders in a list view
        ListAdapter adapter = new SimpleAdapter(
                order_confirm.this, orderlist,
                R.layout.order_list_item, new String[]{"food_name", "amount"}, new int[]{
                R.id.foodname, R.id.txtquantity});

        listView.setAdapter(adapter);


        //adding spinner of location of deliveries
        //adding dropdown for the activeness
        sp=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,delivery_destinations);
        sp.setAdapter(adapter1);

        complete_order=(Button)findViewById(R.id.save);
        //saving data to sqlite
        complete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database(getApplicationContext());
                String date=getNow();
                db.save_orders(food_ordered,cost,date);

            }
        });

    }
    //selected item on spinner
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        switch (position)
        {
            case 0:
                Toast.makeText(getApplicationContext(),"Sunrise",Toast.LENGTH_SHORT).show();
                destination="Sunrise";
                break;
            case 1:
                Toast.makeText(getApplicationContext(),"Tamals",Toast.LENGTH_SHORT).show();
                destination="Tamals";
            case 2:
                Toast.makeText(getApplicationContext(),"Laduvet",Toast.LENGTH_SHORT).show();
                destination="Laduvet";
            case 3:
                Toast.makeText(getApplicationContext(),"Mt.Kenya",Toast.LENGTH_SHORT).show();
                destination="Mt.Kenya";


        }
        txtdestination.setVisibility(View.VISIBLE);
        txtdestination.setText("Which place specifically in: "+destination);
        txtwhere.setVisibility(View.VISIBLE);

    }
    //converting string to array
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
