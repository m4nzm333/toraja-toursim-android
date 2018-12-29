package com.example.unlistedi.torajatourism.data.index;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.unlistedi.torajatourism.DetailWisata;
import com.example.unlistedi.torajatourism.IndexUtama;
import com.example.unlistedi.torajatourism.R;
import com.example.unlistedi.torajatourism.data.RequestParemeter;
import com.example.unlistedi.torajatourism.data.attacher.DownloadImageTask;

import java.net.URL;
import java.util.LinkedList;

import static android.support.v4.content.ContextCompat.startActivity;

public class TujuanPopulerAdapter extends RecyclerView.Adapter<TujuanPopulerAdapter.TujuanPopulerViewHolder> {
    private final LinkedList<String> mIdWisata;
    private final LinkedList<String> mNamaWisata;
    private final LinkedList<String> mRating;
    private final LinkedList<String> mBanyakDilihat;
    private final LinkedList<String> mNamaGambar;

    public TujuanPopulerAdapter(LinkedList<String> mIdWisata, LinkedList<String> mNamaWisata, LinkedList<String> mRating, LinkedList<String> mBanyakDilihat, LinkedList<String> mNamaGambar) {
        this.mIdWisata = mIdWisata;
        this.mNamaWisata = mNamaWisata;
        this.mRating = mRating;
        this.mBanyakDilihat = mBanyakDilihat;
        this.mNamaGambar = mNamaGambar;
    }

    @NonNull
    @Override
    public TujuanPopulerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_tujuan_populer, viewGroup, false);

        return new TujuanPopulerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TujuanPopulerViewHolder tujuanPopulerViewHolder, final int i) {
        tujuanPopulerViewHolder.rvTPNamaWisata.setText(mNamaWisata.get(i));
        tujuanPopulerViewHolder.rvTPRating.setText(mRating.get(i));
        tujuanPopulerViewHolder.rvTPBanyakDilihat.setText(mBanyakDilihat.get(i)+ " orang telah melihat");

        String urlku = RequestParemeter.URL+"assets/img/lokasiwisata/"+mNamaGambar.get(i);
        Glide.with(tujuanPopulerViewHolder.ivWisataPopuler.getContext())
                .load(urlku)
                .into(tujuanPopulerViewHolder.ivWisataPopuler);
        tujuanPopulerViewHolder.cvWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = tujuanPopulerViewHolder.cvWisata.getContext();
                Intent intent = new Intent(context, DetailWisata.class);
                intent.putExtra("iniIdLokasiWisata", mIdWisata.get(i));
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIdWisata.size();
    }

    public static class TujuanPopulerViewHolder extends RecyclerView.ViewHolder{
        public TextView rvTPNamaWisata, rvTPRating, rvTPBanyakDilihat;
        public ImageView ivWisataPopuler;
        public CardView cvWisata;
        public TujuanPopulerViewHolder(@NonNull View view) {
            super(view);

            rvTPNamaWisata = view.findViewById(R.id.rvTPNamaWisata);
            rvTPRating = view.findViewById(R.id.rvTPRating);
            rvTPBanyakDilihat = view.findViewById(R.id.rvTPBanyakDilihat);
            cvWisata = view.findViewById(R.id.cvWisata);
            ivWisataPopuler = view.findViewById(R.id.ivWisataPopuler);
        }
    }
}
