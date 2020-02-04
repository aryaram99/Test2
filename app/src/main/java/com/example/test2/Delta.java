package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.ContentHandler;
import java.util.ArrayList;

public class Delta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delta);

        ambil_data();
    }

    void ambil_data()
    {
        String link="http://magangubd17.000webhostapp.com/delta.php";
        StringRequest respon=new StringRequest(
                Request.Method.POST,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("hasil");
                            ArrayList<Get_data4> list_data;
                            list_data=new ArrayList<>();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject hasil=jsonArray.getJSONObject(i);
                                String id=hasil.getString("id");
                                String status=hasil.getString("status");
                                String nama=hasil.getString("nama");
                                String tanggal=hasil.getString("tanggal");
                                list_data.add(new Get_data4(
                                        id,
                                        status,
                                        nama,
                                        tanggal
                                ));
                            }

                            ListView listView=findViewById(R.id.list);
                            Custom_adapter4 adapter=new Custom_adapter4(Delta.this, list_data);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Delta.this, "eror", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(respon);
    }
}

class Get_data4{
    String id="", status="", nama="", tanggal="";
    Get_data4( String id, String status, String nama, String tanggal)
    {
        this.id=id;
        this.status=status;
        this.nama=nama;
        this.tanggal=tanggal;

    }

    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }

    public String getNama() {
        return nama;
    }

    public String getTanggal() {
        return tanggal;
    }
}

class Custom_adapter4 extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Get_data4> model;

    Custom_adapter4(Context context, ArrayList<Get_data4> model) {
        layoutInflater = layoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();

    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list, null);
        TextView id, status, nama, tanggal;
        id = view.findViewById(R.id.id);
        status = view.findViewById(R.id.status);
        nama = view.findViewById(R.id.nama);
        tanggal = view.findViewById(R.id.tanggal);

        id.setText(model.get(position).getId());
        status.setText(model.get(position).getStatus());
        nama.setText(model.get(position).getNama());
        tanggal.setText(model.get(position).getTanggal());
        return view;
    }
}

