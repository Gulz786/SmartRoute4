package com.gul.smartroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import static com.gul.smartroute.vehiclestart.userfile;

public class About extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar=(Toolbar)findViewById(R.id.app_bar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setLogo(R.drawable.logof);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("About Us");
        }








    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent;
                SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                String spin = shared.getString("dept", "");
                if (spin == "Ambulance") {


                    intent = new Intent(getApplicationContext(), ambulnace.class);


                } else {
                    intent = new Intent(getApplicationContext(), MapsActivity.class);

                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
