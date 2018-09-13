package com.appsonetimes.bambino;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.model.Produit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class Session extends Application{

    public static List<Produit> Lprod= new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Lprod = new ArrayList<>();
    }

    public static void showImage(Context context, ImageView imageView, String url){
        Glide.with(context).load(url)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
