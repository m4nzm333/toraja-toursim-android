package com.example.unlistedi.torajatourism.data.index;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.unlistedi.torajatourism.R;
import com.example.unlistedi.torajatourism.data.RequestParemeter;

import java.util.ArrayList;

public class IndexHeaderAdapter extends PagerAdapter {
//    private String[] urls;
    private ArrayList<String> urls;
    private LayoutInflater inflater;
    private Context context;

//    public IndexHeaderAdapter(Context context, String[] urls) {
    public IndexHeaderAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimage_index_header_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.imageViewIndexHeader);


        Glide.with(context)
//                .load(urls[position])
                .load(urls.get(position).toString())
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
