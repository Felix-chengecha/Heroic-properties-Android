package com.example.heroic_properties.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.heroic_properties.Login;
import com.example.heroic_properties.R;
import com.example.heroic_properties.Utils.Base_url;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class More_details extends Fragment {

    String latitude;
    String longitude;
    ImageSlider imageSlider;
    Toolbar toolbar;
    Button Book;
    TextView NAME,LOC,DESC,TCOST, CALL, WATSAPP,PHONE,CATEG,TYPE;
    ArrayList<SlideModel> imageList ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_details, container, false);

         imageSlider =view.findViewById(R.id.image_slider);
        CALL=view.findViewById(R.id.call);
        WATSAPP=view.findViewById(R.id.watsapp);
        Book=view.findViewById(R.id.booking);

        NAME=view.findViewById(R.id.prop_name);
        LOC=view.findViewById(R.id.prop_loc);
        DESC=view.findViewById(R.id.prop_desc);
        TCOST=view.findViewById(R.id.Prop_cost);
        PHONE=view.findViewById(R.id.prop_owner);
        CATEG=view.findViewById(R.id.prop_categ);
        TYPE=view.findViewById(R.id.prop_type);

        String prop_id = getArguments().getString("prop_id");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("email", null);

        toolbar=view.findViewById(R.id.toolbarr);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        fetchdetails(prop_id);
        fetchimages(prop_id);

        WATSAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String phe="+254"+phone;
                String phe="+2540708288862";
                String url = "https://api.whatsapp.com/send?phone="+phe;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        CALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String phe="0"+phone;
                String phe="+2540708288862";
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phe));
                startActivity(intent);
            }
        });

        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_id==null) {
                    showalertdialog();
                }
                else{
                    showBottomSheetDialog();

                }

            }
        });

        LOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate();
            }
        });

        return view;

    }


    private void fetchimages(String prop_id) {
        imageList =new  ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.getpropertyimages(prop_id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String image= object.getString("images");
                             imageList.add(new SlideModel(image, null));
                    }
                    imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);
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


    private void fetchdetails(String prop_id) {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Base_url.getproductdetails(prop_id), null,
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
                        String description = object.getString("description");
                        String contact = object.getString("owner");
                        String cost = object.getString("cost");
                        String location = object.getString("location");
                        longitude = object.getString("longitude");
                        latitude = object.getString("latitude");


                        NAME.setText(name);
                        LOC.setText(location);
                        DESC.setText(description);
                        TCOST.setText(cost+".KSH/");
                        PHONE.setText(contact);
                        CATEG.setText(category);
                        TYPE.setText(type);

                    }
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

    private void showalertdialog() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("login");
        builder.setMessage("login to book this property");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.book);
        bottomSheetDialog.show();
    }

    private void navigate(){
        final String geoIntentData = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoIntentData));
        startActivity(intent);
    }

}
