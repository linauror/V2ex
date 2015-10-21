package com.aurorlin.v2ex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aurorlin on 2015/10/19.
 */
public class HotFragment extends Fragment {
    private ListView post_list;
    private Handler handler;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        View view = inflater.inflate(R.layout.activity_post_list, container, false);
        post_list = (ListView) view.findViewById(R.id.post_list);

        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://www.v2ex.com/api/topics/hot.json", new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ListAdapter listAdapter = new ListAdapter(getContext(), requestQueue, response);
                                post_list.setAdapter(listAdapter);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error", error.getMessage());
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();

        post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject item = (JSONObject) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), Show.class);
                try {
                    intent.putExtra("url", item.getString("url"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
