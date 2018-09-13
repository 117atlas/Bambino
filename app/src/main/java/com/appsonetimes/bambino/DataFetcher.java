package com.appsonetimes.bambino;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.appsonetimes.bambino.model.Produit;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DataFetcher extends AsyncTask<Void, Integer, Void> {

    private Context mContext;

    public DataFetcher (Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext, "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        /*String res = null;
        int[] tab_image = { R.drawable.bambi1, R.drawable.a
                , R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e
                , R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i
                , R.drawable.j, R.drawable.k};
        MainActivity.liste.clear();Log.e("msg", "beguinnnnnnnnnnnnn");
        try {
            //res = MainActivity.get("http://mbogni.boutique-bambino.com/apis/listeProduits.php");
            String[] jsons = res.substring(1, res.length()-1).split("\\},");Log.e("substring", res.substring(1, res.length()));
            //(int codeProduit, String categorie, int prix, String nom, int photo1, int photo2, int photo3, int photo4, int photo5)
            for (String e:
                    jsons) {
                if (!jsons[jsons.length-1].equals(e)) e+="}";
                Log.e("JSON String", e);
                int i = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
                int j = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
                int k = Integer.parseInt(Math.round(Math.random()*(tab_image.length-1))+"");
                JSONObject o = null;
                try {
                    o = new JSONObject(e);
                    Produit p = new Produit( Integer.parseInt(o.getString("0")), "Veston", 5200, o.getString("1"), tab_image[i]/*o.getString("3"), tab_image[j], tab_image[k], 0, 0);
                    System.out.println("un produit "+p.getNom());
                    Log.e("un new produit", p.getNom());
                    MainActivity.liste.add(p);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(mContext, "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
    }
}
