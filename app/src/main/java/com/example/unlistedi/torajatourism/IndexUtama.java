package com.example.unlistedi.torajatourism;

import android.app.DownloadManager;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.unlistedi.torajatourism.data.RequestParemeter;
import com.example.unlistedi.torajatourism.data.attacher.DownloadImageTask;
import com.example.unlistedi.torajatourism.data.index.IndexHeaderAdapter;
import com.example.unlistedi.torajatourism.data.index.TujuanPopulerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class IndexUtama extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<String> urlku = new ArrayList<String>();

    ImageView iconGunung, iconAirTerjun, iconGua, iconEvent;

    RequestQueue queue;
    LinkedList<String> listTPIdWisata = new LinkedList<String>();
    LinkedList<String> listTPNamaWisata = new LinkedList<String>();
    LinkedList<String> listTPRating = new LinkedList<String>();
    LinkedList<String> listTPBanyakDilihat = new LinkedList<String>();
    LinkedList<String> listTPNamaGambar = new LinkedList<String>();

    RecyclerView rvTujuanPopuler;
    RecyclerView.Adapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_utama);


        RecyclerView.LayoutManager mLayoutManager;

        iconGunung = findViewById(R.id.iconGunung);
        iconAirTerjun = findViewById(R.id.iconAirTerjun);
        iconGua = findViewById(R.id.iconGua);
        iconEvent = findViewById(R.id.iconEvent);

        rvTujuanPopuler = findViewById(R.id.rvTujuanPopuler);
        mLayoutManager = new LinearLayoutManager(this);
        rvTujuanPopuler.setLayoutManager(mLayoutManager);


        RequestSliderIndexHeader();
        RequestTujuanPopuler();

        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/icon/goal.png")
                .into(iconGunung);
        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/icon/waterfall.png")
                .into(iconAirTerjun);
        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/icon/cave.png")
                .into(iconGua);
        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/icon/stage.png")
                .into(iconEvent);
    }

    private void createSliderIndexHeader() {

        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(new IndexHeaderAdapter(IndexUtama.this,urls));
        mPager.setAdapter(new IndexHeaderAdapter(IndexUtama.this,urlku));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urlku.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    void RequestSliderIndexHeader(){
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RequestParemeter.get_all_index_header(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject json_index_header = response.getJSONObject(i);
                        Log.d("IndexHeader", json_index_header.getString("gambar"));
                        urlku.add(RequestParemeter.get_assets_index_header()+json_index_header.getString("gambar"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                createSliderIndexHeader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    void RequestTujuanPopuler(){
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RequestParemeter.get_tujuan_populer(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject json_tujuan_populer = response.getJSONObject(i);
                        Log.d("TujuanPopuler", json_tujuan_populer.getString("nama_lokasi"));
                        listTPIdWisata.add(json_tujuan_populer.getString("id_lokasi"));
                        listTPNamaWisata.add(json_tujuan_populer.getString("nama_lokasi"));
                        listTPRating.add(json_tujuan_populer.getString("nilai"));
                        listTPBanyakDilihat.add(json_tujuan_populer.getString("banyak_dilihat"));
                        listTPNamaGambar.add(json_tujuan_populer.getString("nama_gambar"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //MengAttach Adapter RecycleView
                mAdapter = new TujuanPopulerAdapter(listTPIdWisata, listTPNamaWisata, listTPRating, listTPBanyakDilihat, listTPNamaGambar);
                rvTujuanPopuler.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

}
