package com.gul.smartroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import static com.gul.smartroute.vehiclestart.userfile;

public class splash extends AppCompatActivity {
    ImageView mg;
    public static int splash=2000;
    boolean check=false;
    String email;
    String cnic;
    SharedPreferences pref;
    String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mg=(ImageView)findViewById(R.id.imageView);
      /*  Animation animatable= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.spl);
        mg.setAnimation(animatable);
        animatable.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        */


        Handler handl=new Handler();

    // pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);


    /*    if(pref.getBoolean("email_executed", false )){
            Intent intent = new Intent(this,splash.class);
            startActivity(intent);
            finish();
   }*/
        handl.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {


             //   SharedPreferences.Editor editor=shared.edit();
             //  Boolean bool=shared.getBoolean("bool","");

                pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);



                if(pref.getBoolean("email_executed", false )){

                   SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                    String spin = shared.getString("dept", "");
                    Toast.makeText(getApplicationContext(), spin  , Toast.LENGTH_SHORT).show();



                    if(spin.equals("Ambulance")) {

                        Intent intent1 = new Intent(splash.this,ambulnace.class);
                        startActivity(intent1);
                        finish();


                  }
                    else{
                    // if(spin1=="Lifters" || spin1=="Fire"){
                        Intent intent = new Intent(splash.this,MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }



                }

                else {

           /*       SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                   String spin=shared.getString("dept","");
                   Toast.makeText(getApplicationContext(),spin,Toast.LENGTH_SHORT).show();
                   if(spin!="Ambulance") {


                      Intent intent = new Intent(com.gul.smartroute.splash.this, Login.class);
                    startActivity(intent);
                    finish();


                   }
                   else{
                       Intent intent = new Intent(splash.this,ambulnace.class);
                       startActivity(intent);
                       finish();
                   }


*/
                    Intent intent = new Intent(com.gul.smartroute.splash.this, Login.class);
                    startActivity(intent);
                    finish();






                }
            }
        },splash);


    }


}
