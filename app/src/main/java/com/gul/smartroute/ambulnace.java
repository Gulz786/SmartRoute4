package com.gul.smartroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gul.smartroute.vehiclestart.userfile;

public class ambulnace extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    DrawerLayout drawerLayout;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    TextView tvDistanceDuration;
    TextView tvDuration;

    Button btn1,btn2,btn3,btndone;

    public  String lat;
    public String lng;
    InputStream is=null;
    ArrayList<LatLng> MarkerPoints;
    Button btn;
    android.support.v7.app.ActionBarDrawerToggle actiionBarDrawerToggle;
    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    Button btnr;
    String spin;

    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 5000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.activity_ambulnace);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

     //   btn.setVisibility(View.GONE);



        tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);
        tvDuration=(TextView)findViewById(R.id.tvDuration);

        //imp
        toolbar=(Toolbar)findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);


        //This part is for navigation drawer Layout for creating Hamburger
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        actiionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actiionBarDrawerToggle);


// for setting fragment in any activity

/*fragmentTransaction=getSupportFragmentManager().beginTransaction();

fragmentTransaction.add(R.id.frame_container,new BlankFragment());
 fragmentTransaction.commit();
getSupportActionBar().setTitle("Home");
*/

        SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        String useid=shared.getString("user_id",null);
        if (useid!=null)
        {

            btn.performClick();
        }





        navigationView= (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.distance:


                        Intent intent2 = new Intent(getApplicationContext(),distanc.class);
                        startActivity(intent2);
                        finish();

                        break;
                    case R.id.vehi:


                        Intent intent1 = new Intent(getApplicationContext(),resetvehicle.class);
                        startActivity(intent1);
                        finish();




                        break;
                    case R.id.about:
             /*   fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,new BlankFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("check");
                item.setChecked(true);
                drawerLayout.closeDrawers();  */

                        Intent intent = new Intent(getApplicationContext(),About.class);
                        startActivity(intent);
                        finish();

                        break;



                    case R.id.contact:


                        Intent intent3 = new Intent(getApplicationContext(),contact.class);
                        startActivity(intent3);
                        finish();




                        break;

                    case R.id.log:
                        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = pref.edit();
                        ed.putBoolean("email_executed",false);
                        ed.commit();





                        SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=share.edit();
                        editor.putString("cnic",null);
                        editor.putBoolean("bool",false);
                        editor.putString("status","Unavailable");
                        editor.commit();
                        Intent intent5 = new Intent(getApplicationContext(),splash.class);
                        startActivity(intent5);
                        finish();




                        break;


                }

                return true;
            }
        });

        MarkerPoints = new ArrayList<>();

        btndone= (Button)findViewById(R.id.done);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=share.edit();


                editor.putString("status","Available");
                editor.putString("user_id",null);
                editor.commit();

                Intent refresh =new Intent(ambulnace.this,ambulnace.class);
                startActivity(refresh);


            }
        });



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }




        btn=(Button)findViewById(R.id.setButton);
        btnr = (Button) findViewById(R.id.btnHospital);



    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }




    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actiionBarDrawerToggle.syncState();
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }




        btnr.setOnClickListener(new View.OnClickListener() {
            String Hospital = "hospital";
            @Override
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                //Place current location marker


                Double lat1= mLastLocation.getLatitude();
                Double lng1=mLastLocation.getLongitude();
                LatLng latLng = new LatLng(lat1,lng1);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                mCurrLocationMarker = mMap.addMarker(markerOptions);

                String url = getUrl(lat1 , lng1, Hospital);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(ambulnace.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();







            }
        });





        // Setting onclick event listener for the map


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double lat1= mLastLocation.getLatitude();
                Double lng1=mLastLocation.getLongitude();
                MarkerPoints.add(new LatLng(lat1,lng1));
                SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);

                String user_id=shared.getString("user_id","");
                String late=shared.getString("late","");
                String longe=shared.getString("longi","");

                // Toast.makeText(getApplicationContext(),"value"+user_id+l1,Toast.LENGTH_LONG).show();
                //31.502797,74.366245

                Double lati=Double.parseDouble(late.trim());
                Double longi=Double.parseDouble(longe.trim());

                // Adding new item to the ArrayList
                MarkerPoints.add(new LatLng(lati,longi));

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(new LatLng(lati,longi));

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */

                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));



                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            Marker currentShown;

            public boolean onMarkerClick(Marker marker) {
                Double lat=marker.getPosition().latitude;
                Double lng=marker.getPosition().longitude;
                Toast.makeText(ambulnace.this,"Nearby Hospitals"+ lat+lng, Toast.LENGTH_LONG).show();
                Double lat1= mLastLocation.getLatitude();
                Double lng1=mLastLocation.getLongitude();
                MarkerPoints.add(new LatLng(lat1,lng1));






                // Adding new item to the ArrayList
                MarkerPoints.add(new LatLng(lat,lng));

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(new LatLng(lat,lng));

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */

                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));



                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }





                return true;
            }
        });

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {


        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyBDfSkw2vQwEfiakfZIKCbUGkmw_9bDN3k");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }




    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;




        return url;



    }




    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParsor parser = new DataParsor();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            String distance = "";
            String duration = "";

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
                tvDistanceDuration.setText(distance);
                tvDuration.setText(duration);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }







    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
 /*   public void onCheckedChanged() {

        // Checks, whether start and end locations are captured
        if (markerPoints.size() >= 2) {
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            ParserTask downloadTask = new ParserTask();downloadTask

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }

    }
*/




    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker

        //Place current location marker

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lat=location.getLatitude()+"";
        lng=location.getLongitude()+"";


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }






        SharedPreferences shared= getSharedPreferences(userfile, Context.MODE_PRIVATE);

        String regno=shared.getString("regid","");
        String email=shared.getString("email","");
        String status=shared.getString("status","");
        String cnic=shared.getString("cnic","");



        // Toast.makeText(this,spin,Toast.LENGTH_SHORT).show();







        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

        nameValuePairs.add(new BasicNameValuePair("regno",regno));
        nameValuePairs.add(new BasicNameValuePair("cnic",cnic));

        nameValuePairs.add(new BasicNameValuePair("status",status));
        nameValuePairs.add(new BasicNameValuePair("lat",lat));
        nameValuePairs.add(new BasicNameValuePair("lng",lng));
        nameValuePairs.add(new BasicNameValuePair("dept",spin));

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://lifesavior.pk/dataentrydriver.php?");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Toast.makeText(getApplicationContext(), "Data entered successfully",
                    Toast.LENGTH_LONG).show();
        }
        catch(ClientProtocolException e)
        {
            Log.e("ClientProtocol","Log_tag");
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error 1",
                    Toast.LENGTH_LONG).show();
        }
        catch(IOException e)
        {
            Log.e("Log_tag","IOException");
            System.out.println (e.toString());
            Toast.makeText(getApplicationContext(), "error 2" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }













        SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putString("lat",lat);
        editor.putString("lng",lng);
        editor.commit();





    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);



        }
    }

 /*   public void doit(View v)
    {

        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();

       // SharedPreferences share= getSharedPreferences(userfile, Context.MODE_PRIVATE);
       // SharedPreferences.Editor editor=share.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.commit();

       // String lat=share.getString("lat","");
      // String lng=share.getString("lng","");
      //  Toast.makeText(getApplicationContext()," dont know ",Toast.LENGTH_SHORT).show();
*/
//    }





    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}
