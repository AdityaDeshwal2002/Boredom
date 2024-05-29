package com.asn8.boredom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvPrompt,tvTaskHolder,tvTypeHolder;
    Button btGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPrompt = findViewById(R.id.tvPrompt);
        tvTaskHolder = findViewById(R.id.tvTaskHolder);
        btGenerate = findViewById(R.id.btGenerate);
        tvTypeHolder = findViewById(R.id.tvTypeHolder);

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, "http://www.boredapi.com/api/activity/",null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String task = response.getString("activity");
                            tvTaskHolder.setText("Activity : "+task);
                            String type = response.getString("type");
                            int key = response.getInt("key");
                            double price = response.getDouble("price");
                            String pricing = (price == 0.0) ? "No Money required" : "Money Required" ;
                            Toast.makeText(MainActivity.this, "Price "+price+pricing, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Id : "+key, Toast.LENGTH_SHORT).show();
                            tvTypeHolder.setText("Type : "+type);

                        } catch (JSONException e) {
                            Log.e("Run ", " Error "+e );
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvTaskHolder.setText("Oops : "+error);
                    }
                });
                requestQueue.add(jsonObjectRequest);

//                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.boredapi.com/api/activity/", new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        tvTaskHolder.setText("Your Task : " + response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        tvTaskHolder.setText("Something is wrong. Plz try again"+error);
//                        Log.e( "run ","onErrorResponse: "+error);
//
//                    }
//                });
//                requestQueue.add(stringRequest);
            }
        });
    }
}