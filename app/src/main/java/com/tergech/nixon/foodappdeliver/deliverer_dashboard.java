package com.tergech.nixon.foodappdeliver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class deliverer_dashboard extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, new fragment_deliverer_main());
                    ft.commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.content, new fragment_deliverer_orders());
                    ft1.commit();
                    return true;
               /* case R.id.navigation_notifications:
                    FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
                    ftt.replace(R.id.content, new fragment_deliverer_commission());
                    ftt.commit();
                    return true;*/
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_dashboard);
       int x=1;
        if (x==1)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, new fragment_deliverer_main());
            ft.commit();
        }
      /*  Toast toast=Toast.makeText(getApplicationContext(), "Hello Welcome,Please choose an order to deliver to the customer.Thank you", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
