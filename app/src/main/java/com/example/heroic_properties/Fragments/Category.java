package com.example.heroic_properties.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.heroic_properties.Adapters.Property_Adapter;
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

public class Category extends Fragment {

    Toolbar toolbar;
    RecyclerView propertyView;
    TextView tt;
    ImageView src;
    Property_Adapter category_adapter;
    List<Property_model> nearby_models;
    SwipeRefreshLayout swipeGrid;
    ImageView TsrC;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories, container, false);

        TsrC=view.findViewById(R.id.Tsrc);
        tt=view.findViewById(R.id.Ttitle);
        swipeGrid=view.findViewById(R.id.recycleSwipe);

        propertyView=view.findViewById(R.id.category_rv);
        nearby_models = new ArrayList<>();
        propertyView.setHasFixedSize(true);
        propertyView.setLayoutManager(new LinearLayoutManager(getContext()));
        category_adapter = new Property_Adapter(getContext(),    nearby_models);
        propertyView.setAdapter( category_adapter);

        String Category = getArguments().getString("category");

        toolbar=view.findViewById(R.id.toolbarr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tt.setText(Category);

        fetchcategory(Category);


        TsrC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid=" ";
                replacefrag(new Search_loc(),pid);
            }
        });

        return view;
    }

    private void fetchcategory(String Category) {
        nearby_models.clear();
        swipeGrid.setRefreshing(true);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Base_url.getallproperties(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    swipeGrid.setRefreshing(false);
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
                        category_adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                category_adapter.Layclick(new Property_Adapter.Moreclicklistener() {
                    @Override
                    public void onclicking(View v, String pid) {
                        replacefrag(new More_details(),pid);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeGrid.setRefreshing(true);
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
                HashMap<String, String>map=new HashMap<>();
                map.put("category", Category);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void replacefrag(@NonNull Fragment fragment, String pid) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1, fragment);
        Bundle args = new Bundle();
        args.putString("prop_id", pid);
        fragment.setArguments(args);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
