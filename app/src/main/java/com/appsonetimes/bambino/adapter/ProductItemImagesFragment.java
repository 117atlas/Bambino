package com.appsonetimes.bambino.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appsonetimes.bambino.R;
import com.appsonetimes.bambino.Session;
import com.appsonetimes.bambino.network.NetworkAPI;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductItemImagesFragment extends Fragment {

    private ImageView image;
    private String imageUrl;
    private String code;

    public ProductItemImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_item_images, container, false);
    }

    public static ProductItemImagesFragment newInstance(String imageUrl, String code){
        ProductItemImagesFragment productItemImagesFragment = new ProductItemImagesFragment();
        Bundle args = new Bundle();
        args.putString("IMAGE", imageUrl);
        args.putString("CODE", code);
        productItemImagesFragment.setArguments(args);
        return productItemImagesFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageUrl = getArguments().getString("IMAGE");
        code = getArguments().getString("CODE");
        image = view.findViewById(R.id.image_produit);
        Session.showImage(getContext(), image, NetworkAPI.IMAGE_BASE_URL+code+"/"+imageUrl);
    }
}
