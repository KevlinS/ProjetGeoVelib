package com.example.b3geo.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.b3geo.MainActivity;
import com.example.b3geo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class GalleryFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;





    /* public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    } */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_gallery, null, false);



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(GalleryFragment.this);

        Button MyLocBtn = (Button) view.findViewById(R.id.myButton);
        MyLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //mMap.setMinZoomPreference(15);
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

            }
        });





        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);

        } else {

            locationListener = new LocationListener() {


                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(view.getContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality() + ":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null) {
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.5f));
                        } else {
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.5f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality()+":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null){
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.5f));
                        }
                        else{
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.5f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }
                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    protected void retrieveAndAddStations() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            String SERVICE_URL = "https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state";
            URL url = new URL(SERVICE_URL);
            System.out.println(url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e("", "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it"s a UI operation.
        createMarkersFromJson();
    }

    private void createMarkersFromJson() {
        String urlGetServerData = "https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state";
        System.out.print(urlGetServerData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetServerData,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("records");

                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                JSONObject fields = jsonObject.getJSONObject("fields");
                                String station = fields.getString("station");
                                String lat = station.substring(station.indexOf("latitude") + 11 , station.lastIndexOf(","));
                                String lon = station.substring(station.indexOf("longitude") + 11 , station.lastIndexOf("}}"));
                                double mlat = Double.parseDouble(lat);
                                double mlon = Double.parseDouble(lon);

                                mMap.addMarker(new MarkerOptions()
                                        .title(fields.getString("station_code") + " - " + fields.getString("station_name"))
                                        .position(new LatLng(
                                                mlat,
                                                mlon)

                                        ));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(jsonObjectRequest);
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


        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddStations();
                } catch (IOException e) {
                    Log.e("", "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f)); */
    }


}