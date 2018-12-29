package com.example.unlistedi.torajatourism;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.unlistedi.torajatourism.data.RequestParemeter;
import com.example.unlistedi.torajatourism.data.attacher.DownloadImageTask;
import com.example.unlistedi.torajatourism.data.index.IndexHeaderAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DetailWisata extends AppCompatActivity {
    TextView tvDeskripsi;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<String> urlku = new ArrayList<String>();

    RequestQueue queue;

    ImageView iconJam, iconHari;

    TextView tvJamOperasional, tvHariOperasional;

    ArrayList<String> hari_operasional = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        tvDeskripsi = findViewById(R.id.tvDeskripsi);

        iconJam = findViewById(R.id.iconJam);
        iconHari = findViewById(R.id.iconHari);

        tvJamOperasional = findViewById(R.id.tvJamOperasional);
        tvHariOperasional = findViewById(R.id.tvHariOperasional);

        int id_lokasi_wisata = Integer.parseInt(getIntent().getStringExtra("iniIdLokasiWisata"));

        AmbilDetailWisata(id_lokasi_wisata);

        RequestSliderGambarWisata(id_lokasi_wisata);

        RequestJamOperasional(id_lokasi_wisata);

        RequestHariOperasional(id_lokasi_wisata);

        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/waktuop/clock.png")
                .into(iconJam);
        Glide.with(getApplicationContext())
                .load(RequestParemeter.URL+"assets/img/waktuop/weeks.png")
                .into(iconHari);


    }

    void AmbilDetailWisata(int id){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RequestParemeter.get_lokasi_wisata_by_id(id), null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String deskripsi = response.getString("deskripsi");

                            tvDeskripsi.setText(Html.fromHtml(deskripsi , Html.FROM_HTML_MODE_LEGACY));
                            Log.d("Responku", deskripsi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error", error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void createSliderGambarWisata() {

        mPager = (ViewPager) findViewById(R.id.pager_wisata);
//        mPager.setAdapter(new IndexHeaderAdapter(IndexUtama.this,urls));
        mPager.setAdapter(new IndexHeaderAdapter(DetailWisata.this,urlku));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator_wisata);

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

    void RequestSliderGambarWisata(int id_lokasi_wisata){
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RequestParemeter.get_all_ambar_wisata(id_lokasi_wisata), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject json_index_header = response.getJSONObject(i);
//                        Log.d("IndexHeader", json_index_header.getString("gambar"));
                        String NamaGambar = json_index_header.getString("nama_gambar");
                        urlku.add(RequestParemeter.get_asset_gambar_wisata()+NamaGambar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                createSliderGambarWisata();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    void RequestJamOperasional(int id_lokasi_wisata){
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RequestParemeter.get_jam_operasional(id_lokasi_wisata), null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String waktu_buka, waktu_tutup;
                try {
                    waktu_buka = response.getString("waktu_buka");
                    waktu_tutup = response.getString("waktu_tutup");

                    tvJamOperasional.setText(waktu_buka + "\n s.d. \n"+waktu_tutup);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
    void RequestHariOperasional(int id_lokasi_wisata) {
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RequestParemeter.get_hari_operasional(id_lokasi_wisata), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        hari_operasional.add(response.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AttachHaridiTV(hari_operasional);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    void AttachHaridiTV(ArrayList<String> datanya){
        String result = new String();
        for (int i = 0; i < datanya.size(); i++){
            result = result + datanya.get(i) + ", ";
        }
        tvHariOperasional.setText(result);
    }


}

