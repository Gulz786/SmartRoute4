package com.gul.smartroute;



import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.InputStream;

import static com.gul.smartroute.vehiclestart.userfile;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    InputStream is = null;
    public static final String SHARED_PREF = "ah_firebase";
    Context context;

    @Override

    public void onTokenRefresh() {

        SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        final   String regno=shared.getString("regno","");


        String token   = FirebaseInstanceId.getInstance().getToken();
        super.onTokenRefresh();

        registerToken(token,regno);





    }

    private void registerToken(String token,String regno) {
     /*  OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()

                .add("Token",token)
                .add("regno",regno)
                .build();

        Request request = new Request.Builder()
                .url("http://lifesavior.pk/register3.php?")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }


    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

    public static String getFirebaseInstanceId(Context context) {
        // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //  SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        SharedPreferences preferences = context.getSharedPreferences(userfile, Context.MODE_PRIVATE);
        String regno=preferences.getString("regid","");
        return regno;
    }

}


