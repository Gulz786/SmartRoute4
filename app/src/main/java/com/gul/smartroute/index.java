package com.gul.smartroute;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
import java.util.ArrayList;
import java.util.List;

public class index extends AppCompatActivity {
    Button btamb,btfire,btlift;
    private Toolbar toolbar;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    public static final String userfile="My file";
    TextView tx;

    String chkno="false";
    SharedPreferences pref;
    String deptment,dept;
    InputStream is=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_index);
        toolbar=(Toolbar)findViewById(R.id.app_bar);




        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Smart Route");
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);


       if(pref.getBoolean("activity_executed", false )){
            Intent intent = new Intent(this,splash.class);
            startActivity(intent);
            finish();


        }





        etEmail = (EditText) findViewById(R.id.etEmail);


       //  pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        btamb= (Button)findViewById(R.id.aa);
        btlift= (Button)findViewById(R.id.lifters);
        btfire= (Button)findViewById(R.id.fire);
     //   if(pref.getBoolean("activity_executed", false )){
         //   Intent intent = new Intent(this,splash.class);
          //  startActivity(intent);
         //   finish();


       // }



        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/Unique.ttf");



        btamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptment="vehamb";
                final String email = etEmail.getText().toString();


                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email,deptment);
              /*  FirebaseMessaging.getInstance().subscribeToTopic("test");
                String token= FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("token",token));
                nameValuePairs.add(new BasicNameValuePair("regno",email));
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://lifesavior.pk/register.php?token=" +token  + "&regno" +email);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Toast.makeText(getApplicationContext(), name+age,
                    //      Toast.LENGTH_LONG).show();
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                 //   Toast.makeText(getApplicationContext(), "Data entered successfully",
                        //    Toast.LENGTH_LONG).show();

                }
                catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error 1",Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    System.out.println(e.toString());
                    Toast.makeText(getApplicationContext(), "error 2" + e.toString(),Toast.LENGTH_LONG).show();
                }
*/

            }
        });

        btlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptment="vehicle";

                final String email = etEmail.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email,deptment);
            /*    FirebaseMessaging.getInstance().subscribeToTopic("test");
                String token= FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("token",token));
                nameValuePairs.add(new BasicNameValuePair("regno",email));
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://lifesavior.pk/register.php?token=" +token  + "&regno" +email);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Toast.makeText(getApplicationContext(), name+age,
                    //      Toast.LENGTH_LONG).show();
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                   // Toast.makeText(getApplicationContext(), "Data entered successfully",
                     //       Toast.LENGTH_LONG).show();

                }
                catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error 1",Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    System.out.println(e.toString());
                    Toast.makeText(getApplicationContext(), "error 2" + e.toString(),Toast.LENGTH_LONG).show();
                }
*/

            }
        });


        btfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptment="vehfire";

                final String email = etEmail.getText().toString();

                // Initialize  AsyncLogin() class with email and password
                new AsyncLogin().execute(email,deptment);
            /*    FirebaseMessaging.getInstance().subscribeToTopic("test");
                String token= FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("token",token));
                nameValuePairs.add(new BasicNameValuePair("regno",email));
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://lifesavior.pk/register.php?token=" +token  + "&regno" +email);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Toast.makeText(getApplicationContext(), name+age,
                    //      Toast.LENGTH_LONG).show();
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                //    Toast.makeText(getApplicationContext(), "Data entered successfully",
                  //          Toast.LENGTH_LONG).show();

                }
                catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error 1",Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    System.out.println(e.toString());
                    Toast.makeText(getApplicationContext(), "error 2" + e.toString(),Toast.LENGTH_LONG).show();
                }
*/
            }
        });












    }




    class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(index.this);
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
                url = new URL("http://www.lifesavior.pk/vehicleidchk2.php");

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

                chkno="true";
                if(deptment=="vehicle")
                {
                    dept="Lifters";
                }
                else if(deptment=="vehamb")
                {
                    dept="Ambulance";
                }
                else if(deptment=="vehfire")
                {
                    dept="Fire";
                }
                else
                {
                    dept="zero";
                }







  Toast.makeText(index.this,"Registeration Id is set" +etEmail.getText().toString()+dept,Toast.LENGTH_SHORT).show();

                SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();
                editor.putString("dept",dept);
                editor.putString("regid",etEmail.getText().toString());



                editor.putString("user_id",null);


                editor.apply();


                pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("activity_executed", true);
                ed.apply();

                FirebaseMessaging.getInstance().subscribeToTopic("test");
                String token= FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("token",token));
                nameValuePairs.add(new BasicNameValuePair("regno",etEmail.getText().toString()));
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://lifesavior.pk/register.php?token=" +token  + "&regno" +etEmail.getText().toString());
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Toast.makeText(getApplicationContext(), name+age,
                    //      Toast.LENGTH_LONG).show();
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    //   Toast.makeText(getApplicationContext(), "Data entered successfully",
                    //    Toast.LENGTH_LONG).show();

                }
                catch (ClientProtocolException e) {
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error 1",Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("Log_tag", "IOException");
                    System.out.println(e.toString());
                    Toast.makeText(getApplicationContext(), "error 2" + e.toString(),Toast.LENGTH_LONG).show();
                }






                Toast.makeText(index.this,"Registeration Id is set"+ etEmail.getText().toString() ,Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(index.this,splash.class);
                startActivity(intent);
                index.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(index.this, "Invalid Registration Id , Vehicle Id doesnot Exist or Key doesnot match with same department ", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(index.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



}
