package com.example.b3geo.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3geo.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ArrayList<Rvdata> proSearch = new ArrayList<Rvdata>();
    RecyclerView recyclerView;
    RvAdapter rvAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


        getServerData();
        return root;
    }
    private void getServerData() {
        String urlGetServerData = "https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-disponibilite-en-temps-reel&facet=overflowactivation&facet=creditcard&facet=kioskstate&facet=station_state";
        System.out.print(urlGetServerData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetServerData,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("records");

                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                Rvdata rvdata = gson.fromJson(String.valueOf(jsonObject), Rvdata.class);
                                JSONObject fields = jsonObject.getJSONObject("fields");

                                rvdata.setName(fields.getString("station_name"));

                                proSearch.add(rvdata);
                            }
                            rvAdapter = new RvAdapter(getContext().getApplicationContext(), proSearch);
                            recyclerView.setAdapter(rvAdapter);


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

}