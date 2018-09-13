package com.appsonetimes.bambino.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsonetimes.bambino.DetailsProduitActivity;
import com.appsonetimes.bambino.MainActivity;
import com.appsonetimes.bambino.R;
import com.appsonetimes.bambino.Session;
import com.appsonetimes.bambino.model.Produit;
import com.appsonetimes.bambino.network.NetworkAPI;

import java.util.ArrayList;
import java.util.List;

public class RecyclerProduitAdapter extends RecyclerView.Adapter<RecyclerProduitAdapter.ProduitViewHolder>{

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;

    private List<Produit> produits;
    private Context context;

    public RecyclerProduitAdapter(Context context) {
        this.context = context;
    }

    public void setProduits(List<Produit> produits){
        this.produits = produits;
        notifyDataSetChanged();
    }

    public void add(Produit produit){
        if (produits==null) produits = new ArrayList<>();
        produits.add(produit);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerProduitAdapter.ProduitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item_produit, parent, false);
        return new ProduitViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerProduitAdapter.ProduitViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return produits==null?0:produits.size();
    }

    public class ProduitViewHolder extends RecyclerView.ViewHolder {
        private ViewPager imagesViewPager;
        private ImageButton shop;
        private TextView nom;
        private TextView price;
        private TextView code;
        private ImageView noImage;
        private FloatingActionButton next;
        private FloatingActionButton previous;

        private int imagePos = 0;

        private Produit prod;
        private int currentPosition;

        public ProduitViewHolder(View v) {
            super(v);
            imagesViewPager = v.findViewById(R.id.viewpager_images_produit);
            shop = v.findViewById(R.id.shop);
            nom = v.findViewById(R.id.nom);
            code = v.findViewById(R.id.code);
            price = v.findViewById(R.id.price);
            noImage = v.findViewById(R.id.no_image);
            next = v.findViewById(R.id.next);
            previous = v.findViewById(R.id.previous);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsProduitActivity.class);
                    intent.putExtra(Produit.class.getSimpleName(), prod);
                    context.startActivity(intent);
                }
            });

            shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prod.setQuantite(1);
                    Session.Lprod.add(prod);
                    Toast.makeText(context, nom.getText().toString() + " à été ajouté au panier!", Toast.LENGTH_SHORT).show();
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (prod.getPhotos()!=null && prod.getPhotos().size()>0){
                        if (imagePos==prod.getPhotos().size()-1) imagePos=0;
                        else imagePos++;
                        imagesViewPager.setCurrentItem(imagePos, true);
                    }
                }
            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (prod.getPhotos()!=null && prod.getPhotos().size()>0){
                        if (imagePos==0) imagePos=prod.getPhotos().size()-1;
                        else imagePos--;
                        imagesViewPager.setCurrentItem(imagePos, true);
                    }
                }
            });
        }

        private void bindViewPager(){

            if (prod.getPhotos()!=null && prod.getPhotos().size()>0){
                imagesViewPager.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                previous.setVisibility(View.VISIBLE);
                noImage.setVisibility(View.GONE);

                ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity)context).getSupportFragmentManager());
                for (String s: prod.getPhotos()){
                    ProductItemImagesFragment productItemImagesFragment = ProductItemImagesFragment.newInstance(s, ""+prod.getCodeProduit());
                    adapter.addFragment(productItemImagesFragment, s);
                }
                imagesViewPager.setAdapter(adapter);
                imagesViewPager.setCurrentItem(imagePos);
            }
            else{
                imagesViewPager.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                noImage.setVisibility(View.VISIBLE);
            }
        }

        private void bind(int position){
            currentPosition = position;
            prod = produits.get(position);
            try {
                nom.setText(prod.getNom());
                String p = prod.getPrix() + " FCFA";
                price.setText(p);
                String s = prod.getCodeProduit() + "";
                code.setText(s.substring(0, 3)+"-"+s.substring(3, s.length()));
                bindViewPager();
                if (!(prod.getPhotos()!=null && prod.getPhotos().size()>0)){
                    if (prod.getImage()!=null){
                        Session.showImage(context, noImage, NetworkAPI.IMAGE_BASE_URL+prod.getCodeProduit()+"/"+prod.getImage());
                    }
                }
            }catch (Exception e){
                Log.e("ERROR", e.toString());
            }
        }


    }

    public String getMonth(String str){
        String[] tab = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        String[] rslt = {"Jan", "Feb", "Mar", "April", "May", "Jun",
                "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        int pos=-1;
        for (int i =0; i< 12; i++){
            if (str.equals(tab[i])){
                pos = i;
            }
        }

        if (pos>=0){
            return rslt[pos];
        }

        return str;
    }


}

