package com.example.heroic_properties.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.heroic_properties.Adapters.Home_Adapter;
import com.example.heroic_properties.Models.Property_model;
import com.example.heroic_properties.R;
import com.example.heroic_properties.Utils.Base_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends Fragment implements View.OnClickListener {
RecyclerView Nearbyview;
Home_Adapter property_adapter;
List<Property_model> nearby_models;
RelativeLayout r1,r2,r3,r4;
ImageView search_Link;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        Nearbyview=view.findViewById(R.id.nearbyview);
        search_Link=view.findViewById(R.id.s_link);
        r1=view.findViewById(R.id.row1);
        r1.setOnClickListener(this);
        r2=view.findViewById(R.id.row2);
        r2.setOnClickListener(this);
        r3=view.findViewById(R.id.row3);
        r3.setOnClickListener(this);
        r4=view.findViewById(R.id.row4);
        r4.setOnClickListener(this);

        nearby_models = new ArrayList<>();
        Nearbyview.setHasFixedSize(true);
        Nearbyview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        property_adapter = new Home_Adapter(getContext(),    nearby_models);
        Nearbyview.setAdapter( property_adapter);

        fetchnearby();

        search_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replacefr(new Search_loc());
            }
        });

        return view;
    }

    private void fetchnearby() {
        String loc="tena estate";


      StringRequest stringRequest=new StringRequest(Request.Method.POST, Base_url.getnearbyproperty(), new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String location = object.getString("location");
                                String category = object.getString("category");
                                String type = object.getString("type");
                                String image = object.getString("display");
                                String cost = object.getString("cost");

                                Property_model nbmodel = new Property_model(id, name, location, image, cost, type);
                                nearby_models.add(nbmodel);
                                property_adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        property_adapter.Layclick(new Home_Adapter.Moreclicklistener() {
                            @Override
                            public void onclicking(View v, String pid) {
                                replacefrg(new More_details(), pid);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = getString(R.string.network_error);
                } else if (error instanceof ServerError) {
                    message = getString(R.string.server_error);
                } else if (error instanceof AuthFailureError) {
                    message = getString(R.string.auth_error);
                } else if (error instanceof ParseError) {
                    message = getString(R.string.parse_error);
                } else if (error instanceof TimeoutError) {
                    message = getString(R.string.timeout_error);
                } else {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

            }
        })
      {
          @Nullable
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              HashMap<String, String> map=new HashMap<>();
              map.put("location", loc);
              return map;
          }
      };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    private void replacefrag(@NonNull Fragment fragment, String category) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1, fragment);
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void replacefrg(@NonNull Fragment fragment, String pid) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1, fragment);
        Bundle args = new Bundle();
        args.putString("prop_id", pid);
        fragment.setArguments(args);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void replacefr(@NonNull Fragment fragment) {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String Categoryy=" ";

        switch (v.getId()) {
            case R.id.row1:
                Categoryy="House";
                replacefrag(new Category(),Categoryy);
                break;
            case R.id.row2:
                Categoryy="Apartment";
                replacefrag(new Category(),Categoryy);
                break;
            case R.id.row3:
                Categoryy="Hostel";
                replacefrag(new Category(),Categoryy);
                break;
            case R.id.row4:
                Categoryy="Office";
                replacefrag(new Category(),Categoryy);
                break;

        }
    }

}







