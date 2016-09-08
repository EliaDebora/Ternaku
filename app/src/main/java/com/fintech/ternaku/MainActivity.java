package com.fintech.ternaku;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fintech.ternaku.adapters.ItemClickListener;
import com.fintech.ternaku.adapters.Section;
import com.fintech.ternaku.adapters.SectionStateChangeListener;
import com.fintech.ternaku.adapters.SectionedExpandableLayoutHelper;
import com.fintech.ternaku.model.Item;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {

    private String suhu="",angin="",kelembaban="";
    private Bitmap icon;
    RecyclerView mRecyclerView;
    Weather cuaca;
    SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;
    double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(getString(R.string.userpref), Context.MODE_PRIVATE);

        TextView nav_user = (TextView)hView.findViewById(R.id.drawer_title);
        nav_user.setText(sharedpreferences.getString("keyNama",null));
        TextView nav_role = (TextView)hView.findViewById(R.id.drawer_subtitle);
        nav_role.setText(sharedpreferences.getString("keyNamaRole",null));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumIntegerDigits(0);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLocation();
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_logout){
            SharedPreferences preferences = getSharedPreferences(getString(R.string.userpref), 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(MainActivity.this, LoginActivity2.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);
                // Let's retrieve the icon
               // weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
                weather.iconBmp = ( (new WeatherHttpClient()).getBitmapFromURL(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if (weather.iconBmp != null) {
               //icon = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
               //icon = BitmapFactory.decodeStream(new FlushedInputStream(weather.iconData));
                icon = weather.iconBmp;
            }
            if(weather.iconData!=null) {
                Log.d("ICO", String.valueOf(weather.iconData.length));
            }

            /*cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");*/
            char tmp = 0x00B0;
            DecimalFormat df = new DecimalFormat("###.###");

            suhu = "Suhu\n" + Math.round((weather.temperature.getTemp() - 273.15)) + tmp+"C";
            angin = "Kec. Angin\n" + df.format(weather.wind.getSpeed()*3.6) + " km/h";
            kelembaban = "Humidity\n" + weather.currentCondition.getHumidity() + "%";

            cuaca = weather;
            setData();
        }
    }
    @Override
    public void itemClicked(Item item) {
        Toast.makeText(this, "Item: " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(Section section) {
        Toast.makeText(this, "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    private void setData()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, 3);

        char tmp = 0x00B0;
        DecimalFormat df = new DecimalFormat("###.###");

        //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        /*
        arrayList.add(new Item("iPhone", 0,null));
        arrayList.add(new Item("iPad", 1,null));
        arrayList.add(new Item("iPod", 2,null));
        arrayList.add(new Item("iMac", 3,null));
        sectionedExpandableLayoutHelper.addSection("Ternak", arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("LG", 0,null));
        arrayList.add(new Item("Apple", 1,null));
        arrayList.add(new Item("Samsung", 2,null));
        arrayList.add(new Item("Motorola", 3,null));
        arrayList.add(new Item("Sony", 4,null));
        arrayList.add(new Item("Nokia", 5,null));
        sectionedExpandableLayoutHelper.addSection("Rangkuman", arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0,null));
        arrayList.add(new Item("Strawberry", 1,null));
        arrayList.add(new Item("Vanilla", 2,null));
        arrayList.add(new Item("Butterscotch", 3,null));
        arrayList.add(new Item("Grape", 4,null));
        sectionedExpandableLayoutHelper.addSection("Grafik Ternak", arrayList);
        */

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Suhu\n" + Math.round((cuaca.temperature.getTemp() - 273.15)) + tmp+"C", 0,null));
        arrayList.add(new Item("Kec. Angin\n" + df.format(cuaca.wind.getSpeed()*3.6) + " km/h", 1,null));
        arrayList.add(new Item("Kelembaban\n" + cuaca.currentCondition.getHumidity() + "%", 2,null));
        arrayList.add(new Item(cuaca.currentCondition.getCondition() + "(" + cuaca.currentCondition.getDescr() + ")", 3,null));
        sectionedExpandableLayoutHelper.addSection("Cuaca", arrayList);

        sectionedExpandableLayoutHelper.notifyDataSetChanged();

        //checking if adding single item works
        //sectionedExpandableLayoutHelper.addItem("Ice cream", new Item("Tutti frutti",5));
        //sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }

    private void GetLocation(){
        StringBuilder builder = new StringBuilder();
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Log.d("LATLNG",String.valueOf(latitude)+", "+String.valueOf(longitude));
        }catch(SecurityException se){se.printStackTrace();}

        try {

            List<Address> address = geoCoder.getFromLocation(-7.795580, 110.369490, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }
            String fnialAddress = builder.toString(); //This is the complete address.
            Log.d("Loc",address.get(0).getSubAdminArea());
        } catch (IOException e) {}
        catch (NullPointerException e) {}

        double precision =  Math.pow(10, 2);

        String latlng = "lat="+String.valueOf(((int)(precision * latitude))/precision)+"&lon="+String.valueOf(((int)(precision * longitude))/precision);
        Log.d("LATLNG2",String.valueOf(latlng));
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{latlng});
    }
}
