package com.appsonetimes.bambino;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.appsonetimes.bambino.adapter.RecyclerProduitAdapter;
import com.appsonetimes.bambino.model.Produit;
import com.appsonetimes.bambino.network.InterfaceProduits;
import com.appsonetimes.bambino.network.NetworkAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private RecyclerProduitAdapter adapter;

    public static List<Produit> liste = new ArrayList<Produit>();

    //ApiInterface apiService;

    /*int[] tab_image = { R.drawable.bambi1, R.drawable.a
            , R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e
            , R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i
            , R.drawable.j, R.drawable.k};*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.home));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerV);
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new RecyclerProduitAdapter(MainActivity.this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        getProduits();
    }


    public List<Produit> trier(String cat){
        List<Produit> liste2 = new ArrayList<>();
        liste.clear();
        getProduits();

        for(Produit p:liste){
            if (p.getCategorie().equalsIgnoreCase(cat)) liste2.add(p);
        }
        Log.e("zsd", "taille "+liste2.size());
        return liste2;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        if (id == R.id.action_refresh) {
            getProduits();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            //initData();
            //adapter.notifyDataSetChanged();
            //recyclerView.setAdapter(adapter);
            getProduits();

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_commande) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            final BottomSheetDialog box = new BottomSheetDialog(MainActivity.this);
            View v = LayoutInflater.from(this).inflate(R.layout.model_dialog_contact, null);
            box.setContentView(v);
            box.show();
        } else if (id == R.id.nav_chaussure) {
            liste = trier("Chaussure");
            adapter.setProduits(liste);
            //recyclerView.setAdapter(new RecyclerProduitAdapter(liste, this));
        } else if (id == R.id.nav_pantalon) {
            liste = trier("Pantalon");
            adapter.setProduits(liste);
            //recyclerView.setAdapter(new RecyclerProduitAdapter(liste, this));
        } else if (id == R.id.nav_tshirt) {
            liste = trier("T-Shirt");
            //recyclerView.setAdapter(new RecyclerProduitAdapter(liste, this));
            adapter.setProduits(liste);
        } else if (id == R.id.nav_robe) {
            liste = trier("Robette");
            //recyclerView.setAdapter(new RecyclerProduitAdapter(liste, this));
            adapter.setProduits(liste);
        } else if (id == R.id.nav_veston) {
            liste = trier("Veston");
            adapter.setProduits(liste);
            //recyclerView.setAdapter(new RecyclerProduitAdapter(liste, this));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getProduits(){
        InterfaceProduits interfaceProduits = NetworkAPI.getClient().create(InterfaceProduits.class);
        Call<InterfaceProduits.ResponseProduits> getProduits = interfaceProduits.listeProduits();
        showProgressBar();
        getProduits.enqueue(new Callback<InterfaceProduits.ResponseProduits>() {
            @Override
            public void onResponse(Call<InterfaceProduits.ResponseProduits> call, Response<InterfaceProduits.ResponseProduits> response) {
                hideProgressBar();
                System.out.println(response);
                if (response.body()==null){
                    showSnackBar("Une erreur s'est produite lors de la recuperation des produits. Erreur reponse vide");
                }
                else if (response.body().isError()){
                    showSnackBar("Une erreur s'est produite lors de la recuperation des produits");
                }
                else{
                    liste = response.body().getProduits();
                    adapter.setProduits(liste);
                }
            }
            @Override
            public void onFailure(Call<InterfaceProduits.ResponseProduits> call, Throwable t) {
                hideProgressBar();
                t.printStackTrace();
            }
        });
    }

    private void showSnackBar(String message){
        Snackbar.make(MainActivity.this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    private void showProgressBar(){
        if (progressBar!=null){
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void hideProgressBar(){
        if (progressBar!=null && progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    /*public static String get(String url) throws IOException {

        String source ="";
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            source +=inputLine;
        in.close();
        return source;
    }*/

    /*public JSONObject creerJson() throws JSONException {
        JSONObject cmd = new JSONObject();
        cmd.put("ADRESSECLIENT", "essos");
        cmd.put("COMMENTAIRE", "blabla");
        cmd.put("LIVRE", "0");
        cmd.put("MONTANT", "50000");
        cmd.put("NOMCLIENT", "tata");
        cmd.put("TELEPHONECLIENT", "678877887");

        JSONArray list = new JSONArray();
        //list.add(cmd);

        JSONArray lcs = new JSONArray();
        //for()
        JSONObject lc = new JSONObject();
        lc.put("CODEPRODUIT", "123453");
        lc.put("QUANTITE", "6");
        //lcs.add(lc);

        //return lcs

    }*/


    /*public void initData() {

        DataFetcher df=new DataFetcher(getApplicationContext());
        df.execute();

        //JSONArray tab = new JSONArray(res);
        //System.out.println(tab.length());
/*
        int i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        int j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        int k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(247767, "Talons", 8000, "Talons Aiguille Niska", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(455678, "Chaussure", 20000, "Stan Smith 2KD", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(785422, "Pantalon", 9000, "Jean Bleu", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(589456, "T-Shirt", 10000, "T-Shirt Unkut", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(244125, "Veston", 15000, "Veston Suedoi", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(984125, "Chapeau", 5000, "Chapeau de Co-boy", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(564125, "Robette", 4500, "Robette Roulette Mexique", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(474125, "Vetement Homme", 17500, "Tunique Noire", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(244165, "Vetement Femme", 35000, "Jupe Feroce Femal", tab_image[i], tab_image[j], tab_image[k], 0, 0));

        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(788544, "Talons", 12000, "Talons Russe", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(456787, "Chaussure", 17500, "Vans 2000", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(711232, "Pantalon", 11000, "Pierre Cardin", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(599845, "T-Shirt", 5500, "T-Shirt SWAGG", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(233205, "Veston", 18500, "Veston Cuire Caosar", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(900125, "Chapeau", 6500, "Casquette Wati-B", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(578785, "Robette", 6850, "Soleil Levant", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(433055, "Vetement Homme", 22500, "Costume bleu marine", tab_image[i], tab_image[j], tab_image[k], 0, 0));
        i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
        liste.add(new Produit(277855, "Vetement Femme", 18500, "Ensemble Noir", tab_image[i], tab_image[j], tab_image[k], 0, 0));
    }*/

}
