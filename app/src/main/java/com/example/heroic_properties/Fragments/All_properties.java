package com.example.heroic_properties.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.heroic_properties.Adapters.Child_Adapter;
import com.example.heroic_properties.Adapters.Parent_Adapter;
import com.example.heroic_properties.Models.Model_Child_all;
import com.example.heroic_properties.Models.Model_parent_all;
import com.example.heroic_properties.R;
import com.example.heroic_properties.Utils.Base_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class All_properties extends Fragment {
    RecyclerView recyclerView;
    Child_Adapter child_adapter;
    Parent_Adapter parent_adapter;
    Toolbar toolbar;
    ImageView TsrC;

    ArrayList<Model_parent_all> model_parent_list;
    ArrayList<Model_Child_all> model_child_list;
    ArrayList<Model_Child_all> houseslist;
    ArrayList<Model_Child_all> apartmentlist;
    ArrayList<Model_Child_all> hostellist;
    ArrayList<Model_Child_all> officelist;
    String Category="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_properties, container, false);

        TsrC=view.findViewById(R.id.Tsrc);
        recyclerView=view.findViewById(R.id.rv_parent);
        model_child_list=new ArrayList<>();
        model_parent_list=new ArrayList<>();
        houseslist=new ArrayList<>();
        apartmentlist=new ArrayList<>();
        officelist=new ArrayList<>();
        hostellist=new ArrayList<>();

        toolbar=view.findViewById(R.id.toolbarr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

       parent_adapter=new Parent_Adapter(getActivity(), model_parent_list);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       recyclerView.setAdapter(parent_adapter);

       TsrC.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               replacefr(new Search_loc());
           }
       });

        houses();
        apartments();
        office();
        hostels();

        return  view;
    }


    private void houses(){
        String  Category="House";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Base_url.getcategory(Category), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String type = object.getString("type");
                        String cost = object.getString("cost");
                        String location = object.getString("location");
                        String image = object.getString("display");

                houseslist.add(new Model_Child_all(id, image,name,cost,location,type));
                    }
                    model_parent_list.add(new Model_parent_all("houses",houseslist));
                    parent_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);

    }

    private void apartments(){
        Category="Apartment";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Base_url.getcategory(Category), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String category = object.getString("category");
                        String type = object.getString("type");
                        String image = object.getString("display");
                        String location = object.getString("location");
                        String cost = object.getString("cost");

                        apartmentlist.add(new Model_Child_all(id,image,name,cost,location,type));

                    }
                    model_parent_list.add(new Model_parent_all("apartments",apartmentlist));
                    parent_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    private void office(){
        Category="Office";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Base_url.getcategory(Category), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String category = object.getString("category");
                        String type = object.getString("type");
                        String image = object.getString("display");
                        String location = object.getString("location");
                        String cost = object.getString("cost");

                        officelist.add(new Model_Child_all(id,image,name,cost,location,type));
                    }
                    model_parent_list.add(new Model_parent_all("office",officelist));
                    parent_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    private void hostels(){
        Category="Hostel";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.getcategory(Category), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String category = object.getString("category");
                        String type = object.getString("type");
                        String image = object.getString("display");
                        String location = object.getString("location");
                        String cost = object.getString("cost");

                        hostellist.add(new Model_Child_all(id,image,name,cost,location,type));

                    }
                    model_parent_list.add(new Model_parent_all("hostel",hostellist));
                    parent_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }


    private void replacefr(@NonNull Fragment fragment) {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    }




