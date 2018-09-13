package com.appsonetimes.bambino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appsonetimes.bambino.adapter.RecyclerAdapterCart;
import com.appsonetimes.bambino.db.DatabaseHelper;
import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.network.InterfaceCommande;
import com.appsonetimes.bambino.network.NetworkAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = OrderActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private RecyclerAdapterCart adapter;

    List<Commande> liste = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setTitle("Mes Commandes");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerV);
        progressBar = findViewById(R.id.progress);

        getCommandes();

        bind();
    }

    private void bind(){
        DatabaseHelper db = new DatabaseHelper(this);
        liste = db.getAllCommande();
        adapter = new RecyclerAdapterCart(liste, this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void getCommandes(){
        InterfaceCommande interfaceCommande = NetworkAPI.getClient().create(InterfaceCommande.class);
        retrofit2.Call<InterfaceCommande.ResponseCommandes> getCommmandes = interfaceCommande.listeCommande();
        showProgress();
        getCommmandes.enqueue(new Callback<InterfaceCommande.ResponseCommandes>() {
            @Override
            public void onResponse(retrofit2.Call<InterfaceCommande.ResponseCommandes> call, Response<InterfaceCommande.ResponseCommandes> response) {
                if (response.body()==null){
                    hideProgress();
                    Toast.makeText(OrderActivity.this, "Erreur body null", Toast.LENGTH_SHORT).show();
                }
                else if (response.body().isError()){
                    hideProgress();
                    Toast.makeText(OrderActivity.this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseHelper databaseHelper = new DatabaseHelper(OrderActivity.this);
                    List<Commande> commandes = response.body().getCommandes();
                    if (commandes!=null) for (Commande commande: commandes){

                        Commande existingCom = databaseHelper.getCommande(commande.getIdCommande());
                        if (existingCom==null) databaseHelper.insertCommande(commande);
                        else databaseHelper.updateCommande(commande);

                        if (commande.getProduits()!=null) for (ListeCommande listeCommande: commande.getProduits()){
                            listeCommande.setIdCommande(commande.getIdCommande());
                            ListeCommande existingLCom = databaseHelper.getLigneProduit(commande.getIdCommande(), listeCommande.getCodeProduit());
                            if (existingLCom==null) databaseHelper.insertElementCommande(listeCommande);
                            else databaseHelper.updateElementCommande(listeCommande);
                        }
                    }
                    hideProgress();

                    bind();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<InterfaceCommande.ResponseCommandes> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private void showProgress(){
        if (progressBar!=null){
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void hideProgress(){
        if (progressBar!=null && progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}
