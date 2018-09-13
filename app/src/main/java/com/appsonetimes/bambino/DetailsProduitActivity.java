package com.appsonetimes.bambino;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsonetimes.bambino.R;
import com.appsonetimes.bambino.adapter.ProductItemImagesFragment;
import com.appsonetimes.bambino.adapter.ViewPagerAdapter;
import com.appsonetimes.bambino.db.DatabaseHelper;
import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.model.Produit;

import java.util.ArrayList;

public class DetailsProduitActivity extends AppCompatActivity {

    private String codeS, nomS, prixS;
    int photoS;

    private TextView nom, code, price;
    private EditText quantite;
    private ViewPager images;
    private FloatingActionButton previous, next;
    private ImageView add, remove;
    private Button add_to_cart;

    private Produit produit;
    private ListeCommande ligneProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_produit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*final ImageView cover = (ImageView) findViewById(R.id.image_produit);
        final ImageView image1 = (ImageView) findViewById(R.id.image_1);
        ImageView image2 = (ImageView) findViewById(R.id.image_2);
        ImageView image3 = (ImageView) findViewById(R.id.image_3);
        ImageView image4 = (ImageView) findViewById(R.id.image_4);*/

        toolbar.setTitle("Détails du produit");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nom = (TextView) findViewById(R.id.nom_produit);
        code = (TextView) findViewById(R.id.code_produit);
        price = (TextView) findViewById(R.id.price_produit);
        quantite = (EditText) findViewById(R.id.quantity_prod);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        images = findViewById(R.id.viewpager_images_produit);

        add = (ImageView) findViewById(R.id.add_prod);
        remove = (ImageView) findViewById(R.id.remove_prod);
        add_to_cart = (Button) findViewById(R.id.add_to_cart);

        final Intent intent = getIntent();

        if (intent != null){
            try {
                /*photoS = intent.getIntExtra("PH1", 0);
                this.codeS = intent.getIntExtra("C", 0)+"";
                this.nomS = intent.getStringExtra("N");
                this.prixS = intent.getIntExtra("P", 0) + "";

                cover.setImageResource(intent.getIntExtra("PH1", 0));
                image1.setImageResource(intent.getIntExtra("PH1", 0));
                image2.setImageResource(intent.getIntExtra("PH2", 0));
                image3.setImageResource(intent.getIntExtra("PH3", 0));


                nom.setText(intent.getStringExtra("N"));
                String s = intent.getIntExtra("C", 0)+"";
                code.setText(s.substring(0, 3)+"-"+s.substring(3, s.length()));

                String p = intent.getIntExtra("P", 0) + " FCFA";
                price.setText(p);*/

                produit = (Produit) intent.getSerializableExtra(Produit.class.getSimpleName());
                if (produit==null){
                    Toast.makeText(this, "Produit null", Toast.LENGTH_LONG).show();
                    finish();
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                ligneProduit = databaseHelper.getLigneProduit(0, produit.getCodeProduit());

                bindViewPager();
                bind();
            }catch (Exception e){
                Log.e("ERROR", e.toString());
                e.printStackTrace();
            }

        }else {
            finish();
            setResult(RESULT_CANCELED);
        }

        /*image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cover.setImageResource();
                cover.setImageResource(intent.getIntExtra("PH1", 0));
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover.setImageResource(intent.getIntExtra("PH2", 0));
            }
        });


        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover.setImageResource(intent.getIntExtra("PH3", 0));
            }
        });*/

        quantite.setEnabled(false);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nber = Integer.parseInt(quantite.getText().toString());
                nber++;
                String str = nber+"";
                quantite.setText(str);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nber = Integer.parseInt(quantite.getText().toString());
                if (nber > 1){
                    nber--;
                    String str = nber+"";
                    quantite.setText(str);
                }
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void bindViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (produit.getPhotos()==null || produit.getPhotos().size()==0){
            produit.setPhotos(new ArrayList<String>());
            if (produit.getImage()!=null) produit.getPhotos().add(produit.getImage());
            else produit.getPhotos().add("0");

        }
        for (String s: produit.getPhotos()){
            ProductItemImagesFragment productItemImagesFragment = ProductItemImagesFragment.newInstance(s, produit.getCodeProduit()+"");
            adapter.addFragment(productItemImagesFragment, s);
        }
        images.setAdapter(adapter);
        images.setCurrentItem(0);
    }

    private void bind(){
        nom.setText(produit.getNom());
        price.setText(produit.getPrix() + " FCFA");

        String sCode = String.valueOf(produit.getCodeProduit());
        code.setText(sCode.substring(0, 3)+"-"+sCode.substring(3, sCode.length()));

        if (ligneProduit!=null){
            quantite.setText(ligneProduit.getQuantite()+"");
        }
        else quantite.setText("0");
    }

    private void addToCart(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Veillez patienter...");
        progressDialog.show();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Commande basket = databaseHelper.getCommande(0);

        if (basket==null){
            basket = new Commande();
            AppPreferences appPreferences = AppPreferences.getPreferences(this);
            basket.setIdCommande(0); basket.setNomClient(appPreferences.getUserName());
            basket.setAddresseClient(appPreferences.getUserAddress()); basket.setTelephoneclient(appPreferences.getUserMobile());
            basket.setCommentaire(""); basket.setLivre(0); basket.setMontant(0);
            databaseHelper.insertCommande(basket);
            basket = databaseHelper.getCommande(0);
        }

        ListeCommande listeCommande = new ListeCommande();
        listeCommande.setCodeProduit(produit.getCodeProduit());
        listeCommande.setIdCommande(basket.getIdCommande());
        listeCommande.setIdLCommande(0);
        listeCommande.setPrixU(produit.getPrix());
        listeCommande.setQuantite(Integer.parseInt(quantite.getText().toString()));

        ListeCommande existing = databaseHelper.getLigneProduit(0, produit.getCodeProduit());
        if (existing==null) databaseHelper.insertElementCommande(listeCommande);
        else databaseHelper.updateElementCommande(listeCommande);

        databaseHelper.insertProduit(produit);

        if (progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();

        addToCartMessage();
        onBackPressed();
    }

    private void addToCartMessage(){
        int nber = Integer.parseInt(quantite.getText().toString());
        String text = nber + " " + nom.getText().toString();
        if (nber > 1){
            text = text + " ont ";
        }else {
            text = text + " a ";
        }
        text = text + " " + getString(R.string.warning_prod_add_to_cart);
        Toast.makeText(DetailsProduitActivity.this, text , Toast.LENGTH_SHORT).show();
    }
}
