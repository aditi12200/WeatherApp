package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    TextView location,updated,temp,des,curr_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        location=(TextView)findViewById(R.id.textView2);
        updated=(TextView)findViewById(R.id.textView3);
        temp=(TextView)findViewById(R.id.textView4);
        des=(TextView)findViewById(R.id.textView5);
        curr_city=(TextView)findViewById(R.id.textView6);

        find_weather();
    }

    public void find_weather(){
        String url="http://api.openweathermap.org/data/2.5/weather?q=mumbai&appid=07d1276b5795099b69cd970a6e80a5f9&units=metric";
        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_obj=response.getJSONObject("main");
                    JSONArray array= response.getJSONArray("weather");
                    JSONObject object=array.getJSONObject(0);
                    String temperature=String.valueOf(main_obj.getDouble("temp"));
                    String description= object.getString("description");
                    String city=response.getString("name");

                    temp.setText(temperature);
                    curr_city.setText(city);
                    des.setText(description);

                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                    String formatted_date=sdf.format(calendar.getTime());

                    updated.setText(formatted_date);


                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){

            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
