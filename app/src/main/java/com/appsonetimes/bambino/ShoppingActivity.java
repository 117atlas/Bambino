package com.appsonetimes.bambino;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsonetimes.bambino.adapter.ShoppingItemAdapter;
import com.appsonetimes.bambino.db.DatabaseHelper;
import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.model.Produit;
import com.appsonetimes.bambino.network.BaseResponse;
import com.appsonetimes.bambino.network.InterfaceCommande;
import com.appsonetimes.bambino.network.NetworkAPI;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ShoppingItemAdapter adapter;

    private List<ListeCommande> lignesProduits;
    private List<Produit> produits;
    private Commande commande;
    int t = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTotal();
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                //Validate Order
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerV);


        adapter = new ShoppingItemAdapter(ShoppingActivity.this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.setProduits(new DatabaseHelper(this).getAllProduitOfCommande(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commande, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_vider) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Voulez vous vraiment vider votre panier?");
            builder.setTitle("Vider le panier");
            builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(ShoppingActivity.this);
                    databaseHelper.clearBasket();
                    adapter.setProduits(databaseHelper.getAllProduitOfCommande(0));
                    Toast.makeText(ShoppingActivity.this, "Panier vidé", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("NON",null);
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialogTotal(){
        final BottomSheetDialog box = new BottomSheetDialog(ShoppingActivity.this);
        View v = LayoutInflater.from(this).inflate(R.layout.model_dialog_valid_commande, null);
        TextView totalAchat = (TextView) v.findViewById(R.id.total_achats);
        TextView total = v.findViewById(R.id.soustotal);
        Button commande = (Button) v.findViewById(R.id.commande);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        this.commande = databaseHelper.getCommande(0);
        lignesProduits = databaseHelper.getAllProduitOfCommande(0);

        t = 0;
        for (int i = 0; i < lignesProduits.size(); i++){
            Produit produit = databaseHelper.getProduit(lignesProduits.get(i).getCodeProduit());
            t = t + lignesProduits.get(i).getQuantite() * produit.getPrix();
        }

        this.commande.setMontant(t);
        t = t +1500;
        String str = t +" FCFA";
        total.setText(str);
        totalAchat.setText((t-1500)+" FCFA");

        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Commande c = new Commande(t, "Firming Kateu", "690626884",
                        "Yaoundé, Mendong", "Content", 0);
                DatabaseHelper db = new DatabaseHelper(ShoppingActivity.this);
                int ID = db.insertCommande(c);
                Log.e("az", "id commande "+ID);
                for (Produit p: liste){
                    //int i = db.insertElementCommande(ID, p.getCodeProduit(), p.getQuantite(), p.getPrix(), p.getPhoto1());
                    //Log.e("bz", "id element "+i);
                }
                liste.clear();
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                Session.Lprod.clear();
                box.dismiss();
                Toast.makeText(ShoppingActivity.this, "Commande validée et envoyée!", Toast.LENGTH_SHORT).show();*/

                //Envoie requete https
                final InterfaceCommande.AjouterCommandeBody body = new InterfaceCommande.AjouterCommandeBody();
                body.setCommande(ShoppingActivity.this.commande);
                body.setLignesCommandes(lignesProduits);
                InterfaceCommande interfaceCommande = NetworkAPI.getClient().create(InterfaceCommande.class);
                retrofit2.Call<BaseResponse> ajouterCommande = interfaceCommande.ajouterCommande(body);

                final ProgressDialog progressDialog = new ProgressDialog(ShoppingActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Veillez patienter...");
                progressDialog.show();

                ajouterCommande.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
                        if (response.body()==null){
                            Toast.makeText(ShoppingActivity.this, "Erreur body null", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.body().isError()){
                            Toast.makeText(ShoppingActivity.this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseHelper.deleteCommande(0);
                            databaseHelper.deleteAllElementsCommande(0);

                            //Intent intent = new Intent(ShoppingActivity.this, OrderActivity)
                            onBackPressed();
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<BaseResponse> call, Throwable t) {
                        if (progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
                        t.printStackTrace();
                        try {
                            long requestBody = call.request().body().contentLength();
                            System.out.println();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println();
                    }
                });

                //Sauvegarde locale
                //clear

                box.dismiss();
                //Toast.makeText(ShoppingActivity.this, "Commande validée et envoyée!", Toast.LENGTH_SHORT).show();
            }
        });
        box.setContentView(v);
        box.show();
    }

}
