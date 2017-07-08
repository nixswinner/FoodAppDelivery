package com.tergech.nixon.foodappdeliver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //checking if user is logged in
   /*     String name=SaveSharedPreference.getUserName(getApplicationContext());
        if(name.length() == 0)
        {
            try {
                // call Login Activity
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                startActivity(i);

            }catch (Exception ex)
            {

            }
        }
        else
        {
            // Stay at the current activity.
            Toast.makeText(getApplicationContext(),"Hi,Welcome "+name,Toast.LENGTH_LONG).show();
        }*/


// Replace the contents of the container with the new login_fragment
        //ft.replace(R.id.your_placeholder, new daily_progress());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new fragment_main());
        ft.commit();
    }
    //navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText(R.string.title_home);
                   /* ft.replace(R.id.content, new fragment_main());
                    ft.commit();*/
                    try{
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, new fragment_main());
                        ft.commit();
                    } catch (Exception ex)
                    {
                        Toast.makeText(MainActivity.this,"Error: "+ex,Toast.LENGTH_LONG);
                    }
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                      ft.replace(R.id.content, new fragment_orders());
                      ft.commit();

                    return true;
                case R.id.navigation_notifications:
                   // mTextMessage.setText(R.string.title_notifications);
                   // ft.replace(R.id.content, new fragment_loyaltypts());
                   // ft.commit();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.content, new fragment_loyaltypts());
                        ft2.commit();

                    return true;
            }
            return false;
        }

    };

}
