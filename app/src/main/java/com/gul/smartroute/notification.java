package com.gul.smartroute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {
    TextView textElement;
    public static final String userfile="My file";
    InputStream is=null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("com.gul.smartroute_FCM_MESSAGE"));
        setContentView(R.layout.activity_notification);
        textElement=(TextView)findViewById(R.id.textView3);
        Intent intent_o = getIntent();
        final String user_id = intent_o.getStringExtra("req");
        String late = intent_o.getStringExtra("late");
        String longi = intent_o.getStringExtra("longi");
       // Toast.makeText(getApplicationContext(),user_id+late+longi, Toast.LENGTH_SHORT).show();
        //  public void open(View view){
        SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putString("user_id",user_id);
        editor.putString("late",late);
        editor.putString("longi",longi);
        editor.commit();
       /* SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);

        String user_id1=shared.getString("user_id","");
        String late1=shared.getString("late","");
        String longi1=shared.getString("longi","");
        Toast.makeText(getApplicationContext(),"value"+user_id1+late1,Toast.LENGTH_LONG).show();*/

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,Do you want to accept the request");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
              //  FirebaseMessaging.getInstance().subscribeToTopic("test");
                //String token= FirebaseInstanceId.getInstance().getToken();
                //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                String spin=shared.getString("dept","");
                String spin1=shared.getString("regid","");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("request_id",user_id));
                nameValuePairs.add(new BasicNameValuePair("department",spin));
                nameValuePairs.add(new BasicNameValuePair("regno",spin1));
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://lifesavior.pk/update_status.php?request_id=" +user_id  + "&department" +spin+"&regno"+spin1);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Toast.makeText(getApplicationContext(), name+age,
                    //      Toast.LENGTH_LONG).show();
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Toast.makeText(getApplicationContext(), "Data entered successfully",Toast.LENGTH_LONG).show();

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
                Toast.makeText(notification.this, "You clicked yes button"+user_id, Toast.LENGTH_LONG).show();

               //         I entered this for adding both activities


                SharedPreferences shared1= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                String spinchk=shared1.getString("dept","");
                Toast.makeText(getApplicationContext(),spinchk,Toast.LENGTH_SHORT).show();
                if(spinchk.equals("Ambulance")) {


                    Intent intent = new Intent(notification.this,ambulnace.class);
                    startActivity(intent);
                    finish();


                }
                else{
                    Intent intent = new Intent(notification.this,MapsActivity.class);
                    startActivity(intent);
                    finish();
                }





              //  Intent intent= new Intent(notification.this,MapsActivity.class);
               // startActivity(intent);

            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String user_id = intent.getStringExtra("user_id");
            textElement.setText(user_id);
            Toast.makeText(getApplicationContext(),user_id, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }
}
