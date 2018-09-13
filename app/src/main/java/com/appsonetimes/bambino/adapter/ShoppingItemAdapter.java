package com.appsonetimes.bambino.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.appsonetimes.bambino.DetailsProduitActivity;
import com.appsonetimes.bambino.ShoppingActivity;
import com.appsonetimes.bambino.R;
import com.appsonetimes.bambino.db.DatabaseHelper;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.CommandeViewHolder>{

    private static final String TAG = ShoppingActivity.class.getSimpleName();

    private List<ListeCommande> produits;
    private Context context;

    private boolean enablePopUp = true;

    public ShoppingItemAdapter(Context context) {
        this.context = context;
    }

    public ShoppingItemAdapter(Context context, boolean enablePopUp) {
        this.context = context;
        this.enablePopUp = enablePopUp;
    }

    public void setProduits(List<ListeCommande> produits){
        this.produits = produits;
        notifyDataSetChanged();
    }

    public void addProduit(ListeCommande produit){
        if (produits==null) produits = new ArrayList<>();
        produits.add(produit);
        notifyDataSetChanged();
    }

    @Override
    public CommandeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item_cart, parent, false);
        return new CommandeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CommandeViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return produits==null?0:produits.size();
    }

    public class CommandeViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_cart;
        private TextView code;
        private TextView priceU;
        private TextView qte;
        private ImageButton menu;

        private ListeCommande ligneProduit;
        private Produit produit;
        private int currentPosition;

        private PopupMenu popupMenu;

        public CommandeViewHolder(View v) {
            super(v);
            image_cart = (ImageView) v.findViewById(R.id.image_cart);
            code = (TextView) v.findViewById(R.id.code_cart);
            priceU = (TextView) v.findViewById(R.id.price_unit_cart);
            qte = (TextView) v.findViewById(R.id.quantite_cart);
            menu = v.findViewById(R.id.popup);

            if (enablePopUp){
                menu.setVisibility(View.VISIBLE);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(context, view);
                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_cart, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.edit_item:{
                                        //details produits
                                        Intent intent = new Intent(context, DetailsProduitActivity.class);
                                        intent.putExtra(Produit.class.getSimpleName(), produit);
                                        context.startActivity(intent);
                                    } break;
                                    case R.id.delete_item:{
                                        deleteLigneCommande();
                                    } break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
            else{
                menu.setVisibility(View.INVISIBLE);
            }

        }

        public void bind(int position){
            currentPosition = position;
            ligneProduit = produits.get(position);

            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            produit = databaseHelper.getProduit(ligneProduit.getCodeProduit());


            String s = ligneProduit.getCodeProduit()+"";
            code.setText(s.substring(0, 3)+"-"+s.substring(3, s.length()));
            if (produit!=null){
                priceU.setText(produit.getPrix()+"");
                qte.setText(ligneProduit.getQuantite()+"");
            }
        }

        private void deleteLigneCommande(){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.deleteElementCommande(0, produit.getCodeProduit());
            databaseHelper.deleteProduit(produit.getCodeProduit());

            produits.remove(ligneProduit);
            notifyDataSetChanged();
            //delete ligne commande
        }

    }

}

