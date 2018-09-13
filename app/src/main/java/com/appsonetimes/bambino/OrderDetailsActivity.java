package com.appsonetimes.bambino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appsonetimes.bambino.adapter.ShoppingItemAdapter;
import com.appsonetimes.bambino.db.DatabaseHelper;
import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView comId, montant, date, livre;
    private RecyclerView recyclerView;

    private Commande commande;
    private List<ListeCommande> produits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details de la commande");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        comId = findViewById(R.id.commandeid);
        montant = findViewById(R.id.mont);
        date = findViewById(R.id.date);
        livre = findViewById(R.id.livré);
        recyclerView = findViewById(R.id.recyclerV);

        commande = (Commande) getIntent().getSerializableExtra(Commande.class.getSimpleName());
        produits = new DatabaseHelper(this).getAllProduitOfCommande(commande.getIdCommande());

        bind();
    }

    private void bind(){
        comId.setText(commande.getIdCommande()+"");
        montant.setText(commande.getMontant()+" FCFA");
        date.setText(commande.getDate());
        livre.setText(commande.isLivre()==1?"OUI":"NON");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShoppingItemAdapter shoppingItemAdapter = new ShoppingItemAdapter(this, false);
        shoppingItemAdapter.setProduits(produits);
        recyclerView.setAdapter(shoppingItemAdapter);
    }
}
