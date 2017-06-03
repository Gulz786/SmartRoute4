package com.gul.smartroute;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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

import static com.gul.smartroute.vehiclestart.userfile;

public class cnic extends AppCompatActivity {


    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;

    boolean check;
    Button btn;



    private Toolbar toolbar;
    String dept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnic);
      //  FirebaseMessaging.getInstance().subscribeToTopic("test");
       // String token= FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        toolbar=(Toolbar)findViewById(R.id.app_bar);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //toolbar.setLogo(R.drawable.logof);



            getSupportActionBar().setTitle("Cnic");
        }




        // Get Reference to variables
       // etEmail = (EditText) findViewById(R.id.etEmail);


        Button btn=(Button)findViewById(R.id.Submit);

        TextView txt=(TextView)findViewById(R.id.textView);


        etEmail = (EditText) findViewById(R.id.email);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);

                dept=shared.getString("dept","");
                final String email = etEmail.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email,dept);

            }
        });












        // setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Get Reference to variables


    }



    // Triggers when LOGIN Button clicked




    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(cnic.this);
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
                url = new URL("http://www.lifesavior.pk/chksqli2.php?");

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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("dept",params[1]);

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
            /*    check=true;
                String status="Available";
                Toast.makeText(cnic.this,"Map cnic",Toast.LENGTH_SHORT).show();
                SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();
                editor.putString("email",etEmail.getText().toString());

                editor.putBoolean("bool",check);
                editor.putString("status",status);

                editor.commit();








*/


            /*    SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();

                editor.putString("cnic",etEmail.getText().toString());

                editor.commit();


                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("cnic_executed",false );
                ed.commit();*/



                Intent intent = new Intent(cnic.this,Login.class);
                startActivity(intent);
                finish();

            }
            else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(cnic.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(cnic.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }
    }

    // @Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
        if(id== R.id.action_about)
        {
            startActivity(new Intent(this,About.class));
        }

        return super.onOptionsItemSelected(item);
    }*/






}

    

