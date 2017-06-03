package com.gul.smartroute;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class vehiclestart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    Button bt;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    public static final String userfile="My file";
    Spinner spinner;
    String chkno="false";
    String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclestart);
        toolbar=(Toolbar)findViewById(R.id.app_bar);





        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/future.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/america.ttf");
        spinner=(Spinner)findViewById(R.id.spinner);



        ArrayAdapter adapter=ArrayAdapter.createFromResource(this, R.array.dept,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //fonts check










               SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);


        if(pref.getBoolean("activity_executed", false)  ){
            Intent intent = new Intent(this,splash.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }



        bt= (Button)findViewById(R.id.press);
        // Get Reference to variables
       etEmail = (EditText) findViewById(R.id.etEmail);




        TextView txt=(TextView)findViewById(R.id.textView);
        txt.setTypeface(custom_font1);




        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email);

            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        dept=parent.getItemAtPosition(position).toString();

        SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putString("dept",dept);

        editor.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(vehiclestart.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://www.lifesavior.pk/vehicleidchk.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("truemap"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */


                Toast.makeText(vehiclestart.this,"Registeration Id is set",Toast.LENGTH_SHORT).show();

                SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();
                editor.putString("regid",etEmail.getText().toString());


                editor.putBoolean(chkno,true);


                editor.commit();


                Toast.makeText(vehiclestart.this,"Registeration Id is set"+ etEmail.getText().toString() + dept,Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(vehiclestart.this,splash.class);
                startActivity(intent);
                vehiclestart.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(vehiclestart.this, "Invalid Registration Id , Vehicle Id doesnot Exist ", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(vehiclestart.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vehi, menu);
        return true;
    }
    public boolean onOptionItemsSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id== R.id.action_setting)
        {
            Toast.makeText(getApplicationContext()," You Clicked on settings",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
