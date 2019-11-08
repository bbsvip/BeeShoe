package com.bbs.mr.beeshoe.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {

    ViewFlipper vf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        vf = view.findViewById(R.id.vf_home);
        SetViewFliper();

        return view;
    }

    private void SetViewFliper() {
        ArrayList<String> banners = new ArrayList<>();
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Frunning-shoes-banner-1.jpg?alt=media&token=5548a173-d6d2-4f24-8a8a-35ba331077f1");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Fslides_1573098147.png?alt=media&token=8835fe7e-58d7-4121-bf03-174809604842");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Ftstg-products-banner-new.jpg?alt=media&token=18e982a9-95e3-4ddb-b044-86b1a7dbfe2c");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Fwomen-running-shoes-banner-1.jpg?alt=media&token=9fa08250-f93c-4881-b5c5-16439a41a9e0");
        for (int i = 0;i<banners.size();i++){
            ImageView img = new ImageView(getContext());
            Picasso.get().load(banners.get(i)).into(img);
            //img.setScaleType(ImageView.ScaleType.FIT_XY);
            vf.addView(img);
        }
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        vf.setFlipInterval(3000); // 3s
        vf.setInAnimation(in);
        vf.setOutAnimation(out);
        vf.setAutoStart(true);
    }
}
