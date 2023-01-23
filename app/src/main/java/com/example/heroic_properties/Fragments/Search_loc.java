package com.example.heroic_properties.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

public class Search_loc extends Fragment {

  RecyclerView search_rv;

    TextView value,LC,PR,TH,Rkes;
    AutoCompleteTextView loctextview, houtextview;
    Property_Adapter category_adapter;
    List<Property_model> nearby_models;
    RelativeLayout Srange,Others;

    RadioGroup radioGroup;
    RadioButton Rhouse,Rapartment,Roffice,Rhostel;
    SeekBar seekBar;
    Toolbar toolbar;
    Button apply;

    ArrayList locations,props;
    String category;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        locations=new ArrayList();
        props=new ArrayList();

        search_rv=view.findViewById(R.id.search_rv);
        LC=view.findViewById(R.id.lc);
        PR=view.findViewById(R.id.pr);
        TH=view.findViewById(R.id.th);
        Rkes=view.findViewById(R.id.rkes);
        Srange=view.findViewById(R.id.srange);
        Others=view.findViewById(R.id.others);

        Rhouse=view.findViewById(R.id.rhouse);
        Rhostel=view.findViewById(R.id.rhostel);
        Rapartment=view.findViewById(R.id.rapartment);
        Roffice=view.findViewById(R.id.roffice);

        loctextview=view.findViewById(R.id.acloc);
        houtextview=view.findViewById(R.id.htype);
        radioGroup=view.findViewById(R.id.rgroup);
        seekBar=view.findViewById(R.id.price);
        value=view.findViewById(R.id.sresult);
        apply=view.findViewById(R.id.apply);


        nearby_models = new ArrayList<>();
        search_rv.setHasFixedSize(true);
        search_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        category_adapter = new Property_Adapter(getContext(),    nearby_models);
        search_rv.setAdapter( category_adapter);



        toolbar=view.findViewById(R.id.toolbarr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        fetchlocs();
        fetchprops();

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.rhouse:
                       category="house";
                       break;

                   case R.id.rapartment:
                       category="apartment";
                       break;

                   case R.id.rhostel:
                       category="hostel";
                       break;

                   case R.id.roffice:
                       category="office";
                       break;
               }
           }
       });

       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

              value.setText("" + progress);

           }
           @Override
           public void onStartTrackingTouch(SeekBar seekBar) { }
           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
           }
       });

       apply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchvalues();
           }
       });

        return view;
    }


    private void searchvalues(){
        String lc=loctextview.getText().toString();
        String hou=houtextview.getText().toString();
        String price = value.getText().toString();

        if(lc==null ){
            loctextview.setError("please enter location");
        }
        else if (hou.equals(null)){
             houtextview.setError("please enter property type");
        }
        else if (price.equals("0")) {
            value.setError("choose a price margin");
        }
        else {
            hide();
            search(category,lc,hou,price);
            Toast.makeText(getContext(), hou, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), lc, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), category, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), price, Toast.LENGTH_SHORT).show();

        }
    }

    private void search(String category,String location, String house, String price) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("fetching data");
        progressDialog.setMessage("wait");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.searchprop(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    progressDialog.dismiss();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String location = object.getString("location");
                        String category = object.getString("category");
                        String type = object.getString("type");
                        String image = object.getString("display");
                        String cost = object.getString("cost");


                        Property_model nbmodel=new Property_model(id,name,location, image,cost, type);
                        nearby_models.add(nbmodel);
                        category_adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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
        }){
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("category", category);
                map.put("location", location);
                map.put("cost", price);
                map.put("type", house);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(0,-1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    private void fetchlocs() {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.getlocationnames(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                             String locs = object.getString("location");
                                     locations.add(locs);
                    }
                    //autocomplete locations
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, locations);
                    loctextview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void fetchprops() {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.getpropnames(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String types = object.getString("prop");
                        props.add(types);
                    }
                    //autocomplete locations
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, props);
                     houtextview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void replacefrag(@NonNull Fragment fragment, String pid) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ffframelayout1, fragment);
        Bundle args = new Bundle();
        args.putString("propid", pid);
        fragment.setArguments(args);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void hide() {
        search_rv.setVisibility(View.VISIBLE);
        LC.setVisibility(View.GONE);
        PR.setVisibility(View.GONE);
        TH.setVisibility(View.GONE);
        Rkes.setVisibility(View.GONE);
        Srange.setVisibility(View.GONE);
        Others.setVisibility(View.GONE);
        Rhouse.setVisibility(View.GONE);
        Rhostel.setVisibility(View.GONE);
        Rapartment.setVisibility(View.GONE);
        Roffice.setVisibility(View.GONE);
        loctextview.setVisibility(View.GONE);
        houtextview.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        seekBar.setVisibility(View.GONE);
        value.setVisibility(View.GONE);
        apply.setVisibility(View.GONE);
    }

    private void show() {
        search_rv.setVisibility(View.GONE);
        LC.setVisibility(View.VISIBLE);
        PR.setVisibility(View.VISIBLE);
        TH.setVisibility(View.VISIBLE);
        Rkes.setVisibility(View.VISIBLE);
        Srange.setVisibility(View.VISIBLE);
        Others.setVisibility(View.VISIBLE);
        Rhouse.setVisibility(View.VISIBLE);
        Rhostel.setVisibility(View.VISIBLE);
        Rapartment.setVisibility(View.VISIBLE);
        Roffice.setVisibility(View.VISIBLE);
        loctextview.setVisibility(View.VISIBLE);
        houtextview.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        seekBar.setVisibility(View.VISIBLE);
        value.setVisibility(View.VISIBLE);
        apply.setVisibility(View.VISIBLE);
    }

}