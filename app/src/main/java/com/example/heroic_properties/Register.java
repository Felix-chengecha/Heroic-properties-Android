package com.example.heroic_properties;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.heroic_properties.Utils.Base_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText EmailRT, PasswordRT, NameRT;
    TextView register,login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login= findViewById(R.id.lg);
        NameRT = findViewById(R.id.username);
        EmailRT =  findViewById(R.id.useremail);
        PasswordRT = findViewById(R.id.userpassword);
        register = findViewById(R.id.reg);

        //various buttons for clicking
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

    }

    //validate username
    private boolean checkusername(String uname) {
        if (TextUtils.isEmpty(uname)) {
            NameRT.setError("please enter you name");
            NameRT.requestFocus();
            return false;
        }
        return true;
    }

    //validate email
    private boolean checkemail(String email) {
        if (TextUtils.isEmpty(email)) {
            EmailRT.setError("please enter you email");
            EmailRT.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            EmailRT.setError("please enter a valid email address");
            EmailRT.requestFocus();
            return false;
        }
        return true;
    }

    //validate password
    private boolean checkpassword(String pword) {
        if (TextUtils.isEmpty(pword)) {
            PasswordRT.setError("please enter your password");
            PasswordRT.requestFocus();
            return false;
        }
        return true;
    }

    public void verify() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        String name = NameRT.getText().toString().trim();
        String email = EmailRT.getText().toString().trim();
        String pword = PasswordRT.getText().toString().trim();
        if (  checkusername(name) && checkemail(email) && checkpassword(pword)) {

            progressDialog.show();
            progressDialog.setTitle("Registration");
            progressDialog.setMessage("adding new user");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Base_url.loginurl(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String success = response.getString("MSG");
                                if (success.equalsIgnoreCase("user registration successful")) {
                                    EmailRT.setText("");
                                    PasswordRT.setText("");
                                    NameRT.setText("");
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, success, Toast.LENGTH_SHORT).show();

                                    Intent Rintent = new Intent(Register.this, Login.class);
                                    startActivity(Rintent);
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, "user already exist ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(Login.this, "exception", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                protected HashMap<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name",name);
                    map.put("email", email);
                    map.put("password", pword);
                    return map;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            jsonObjectRequest.setRetryPolicy(
                    new DefaultRetryPolicy(0,-1,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjectRequest);

        }

    }


}