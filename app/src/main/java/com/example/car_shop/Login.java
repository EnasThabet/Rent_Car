package com.example.car_shop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextID, editTextPassword;
    private ImageButton buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Make sure to create this layout file

        // Initialize the EditText fields
        editTextID = findViewById(R.id.admin_id);
        editTextPassword = findViewById(R.id.pass);

        // Initialize the Button and ensure it is cast correctly
        buttonLogin = findViewById(R.id.admin_login);

        // Initialize the ProgressDialog
        progressDialog = new ProgressDialog(this);

        // Set the click listener for the Button
        buttonLogin.setOnClickListener(this);
    }

    private void loginUser() {
        final String ID = editTextID.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        String url = "http://192.168.0.111/Android/V1/loginAdmins.php"; // Update this to your login URL

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("Response", "Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");

                            // Show the message in an AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setTitle(error ? "Login Failed" : "Login Successful");
                            builder.setMessage(message);
                            builder.setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        String errorMessage = "Error: " + error.toString();
                        if (error.networkResponse != null) {
                            errorMessage += "\nStatus Code: " + error.networkResponse.statusCode;
                            try {
                                errorMessage += "\nResponse Data: " + new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", ID);
                params.put("Password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            loginUser();
        }
    }
}
