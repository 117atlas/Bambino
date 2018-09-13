package com.appsonetimes.bambino.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appsonetimes.bambino.model.Categorie;
import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.appsonetimes.bambino.model.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "bambino_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Commande.CREATE_TABLE);
        db.execSQL(ListeCommande.CREATE_TABLE);
        db.execSQL(Produit.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Commande.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ListeCommande.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Produit.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public int insertProduit(Produit p){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Produit.COLUMN_CODE, p.getCodeProduit());
        values.put(Produit.COLUMN_CATEGORIE, p.getCat().getNomCategorie());
        values.put(Produit.COLUMN_PRIX, p.getPrix());
        values.put(Produit.COLUMN_QUANTITE, p.getQuantite());
        values.put(Produit.COLUMN_NOM, p.getNom());
        values.put(Produit.COLUMN_DESCRIPTION, p.getDescription());
        values.put(Produit.COLUMN_CODEFOUR, p.getCodeFournisseur());
        values.put(Produit.COLUMN_ACTIF, p.isActif()?1:0);
        values.put(Produit.COLUMN_IMAGE, p.getImage());

        // insert row
        long id = db.insert(Produit.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public int updateProduit(Produit p){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Produit.COLUMN_CODE, p.getCodeProduit());
        values.put(Produit.COLUMN_CATEGORIE, p.getCat().getNomCategorie());
        values.put(Produit.COLUMN_PRIX, p.getPrix());
        values.put(Produit.COLUMN_QUANTITE, p.getQuantite());
        values.put(Produit.COLUMN_NOM, p.getNom());
        values.put(Produit.COLUMN_DESCRIPTION, p.getDescription());
        values.put(Produit.COLUMN_CODEFOUR, p.getCodeFournisseur());
        values.put(Produit.COLUMN_ACTIF, p.isActif()?1:0);
        values.put(Produit.COLUMN_IMAGE, p.getImage());

        // insert row
        int id = db.update(Produit.TABLE_NAME, values, Produit.COLUMN_CODE + " = " + p.getCodeProduit(), null);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public int deleteProduit(int code){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Produit.TABLE_NAME, Produit.COLUMN_CODE + " = " + code, null);
    }

    public int deleteAllProduits(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Produit.TABLE_NAME, null, null);
    }

    public Produit getProduit(int code){
        // get readable database as we are not inserting anything
        String selectQuery = "SELECT  * FROM " + Produit.TABLE_NAME + " WHERE " + Produit.COLUMN_CODE + " = " + code;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Produit p = null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                p = new Produit();
                p.setCodeProduit(cursor.getInt(0));
                p.setCat(new Categorie(0, cursor.getString(1)));
                p.setCategorie(cursor.getString(1));
                p.setPrix(cursor.getInt(2));
                p.setQuantite(cursor.getInt(3));
                p.setNom(cursor.getString(4));
                p.setDescription(cursor.getString(5));
                p.setCodeFournisseur(cursor.getString(6));
                p.setDate(cursor.getString(7));
                p.setActif(cursor.getInt(8)==1);
                p.setImage(cursor.getString(9));
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return p;
    }



    public int insertCommande(Commande c) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Commande.COLUMN_ID, c.getIdCommande());
        values.put(Commande.COLUMN_MONTANT, c.getMontant());
        values.put(Commande.COLUMN_NOMCLIENT, c.getNomClient());
        values.put(Commande.COLUMN_TELEPHONECLIENT, c.getTelephoneclient());
        values.put(Commande.COLUMN_ADDRESSECLIENT, c.getAddresseClient());
        values.put(Commande.COLUMN_COMMENTAIRE, c.getCommentaire());
        values.put(Commande.COLUMN_LIVRE, c.isLivre());

        // insert row
        long id = db.insert(Commande.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public Commande getCommande(int id) {
        // get readable database as we are not inserting anything
        String selectQuery = "SELECT  * FROM " + Commande.TABLE_NAME + " WHERE IDCOMMANDE = " +id;
        Commande c=null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                c = new Commande(cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_DATECOMMANDE)),
                        cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_MONTANT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_NOMCLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_TELEPHONECLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_ADDRESSECLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_COMMENTAIRE)),
                        cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_LIVRE)));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return c;
    }

    public List<Commande> getAllCommande() {
        List<Commande> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Commande.TABLE_NAME + " WHERE IDCOMMANDE > 0 ORDER BY " +
                Commande.COLUMN_DATECOMMANDE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Commande c = new Commande(cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_DATECOMMANDE)),
                        cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_MONTANT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_NOMCLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_TELEPHONECLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_ADDRESSECLIENT)),
                        cursor.getString(cursor.getColumnIndex(Commande.COLUMN_COMMENTAIRE)),
                        cursor.getInt(cursor.getColumnIndex(Commande.COLUMN_LIVRE)));
                notes.add(c);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int deleteCommande(int idc){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Commande.TABLE_NAME, Commande.COLUMN_ID + " = " + idc, null);
    }

    public int updateCommande(Commande c){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Commande.COLUMN_ID, c.getIdCommande());
        values.put(Commande.COLUMN_MONTANT, c.getMontant());
        values.put(Commande.COLUMN_NOMCLIENT, c.getNomClient());
        values.put(Commande.COLUMN_TELEPHONECLIENT, c.getTelephoneclient());
        values.put(Commande.COLUMN_ADDRESSECLIENT, c.getAddresseClient());
        values.put(Commande.COLUMN_COMMENTAIRE, c.getCommentaire());
        values.put(Commande.COLUMN_LIVRE, c.isLivre());

        // insert row
        int id = db.update(Commande.TABLE_NAME, values, Commande.COLUMN_ID + " = " + c.getIdCommande(), null);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public int getCommandeCount() {
        String countQuery = "SELECT  * FROM " + Commande.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getElementCommandeCount(int id) {
        String countQuery = "SELECT  * FROM " + ListeCommande.TABLE_NAME + " WHERE "+ListeCommande.COLUMN_IDCOMMANDE + "="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }



    public int insertElementCommande(ListeCommande listeCommande) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(ListeCommande.COLUMN_ID, listeCommande.getIdLCommande());
        values.put(ListeCommande.COLUMN_IDCOMMANDE, listeCommande.getIdCommande());
        values.put(ListeCommande.COLUMN_CODEPRODUIT, listeCommande.getCodeProduit());
        values.put(ListeCommande.COLUMN_PHOTO, "");
        values.put(ListeCommande.COLUMN_PRIXU, listeCommande.getPrixU());
        values.put(ListeCommande.COLUMN_QUANTITE, listeCommande.getQuantite());

        // insert row
        long id = db.insert(ListeCommande.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public int updateElementCommande(ListeCommande listeCommande){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(ListeCommande.COLUMN_ID, listeCommande.getIdLCommande());
        values.put(ListeCommande.COLUMN_IDCOMMANDE, listeCommande.getIdCommande());
        values.put(ListeCommande.COLUMN_CODEPRODUIT, listeCommande.getCodeProduit());
        values.put(ListeCommande.COLUMN_PHOTO, "");
        values.put(ListeCommande.COLUMN_PRIXU, listeCommande.getPrixU());
        values.put(ListeCommande.COLUMN_QUANTITE, listeCommande.getQuantite());

        // insert row
        //long id = db.insert(ListeCommande.TABLE_NAME, null, values);
        int id = db.update(ListeCommande.TABLE_NAME, values, ListeCommande.COLUMN_ID + " = " + listeCommande.getIdLCommande(), null);
        // close db connection
        db.close();
        // return newly inserted row id
        return Integer.parseInt(id+"");
    }

    public int deleteElementCommande(int idlc, int codeProduit){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ListeCommande.TABLE_NAME, ListeCommande.COLUMN_ID + " = " + idlc + " AND " + ListeCommande.COLUMN_IDCOMMANDE + " = " + codeProduit, null);
    }

    public int deleteAllElementsCommande(int idlc){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ListeCommande.TABLE_NAME, ListeCommande.COLUMN_ID + " = " + idlc, null);
    }

    public List<ListeCommande> getAllProduitOfCommande(int idc) {
        List<ListeCommande> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + ListeCommande.TABLE_NAME + " WHERE IDCOMMANDE = " + idc;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListeCommande c = new ListeCommande(idc,
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_CODEPRODUIT)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_QUANTITE)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_PRIXU)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_PHOTO)));
                notes.add(c);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return notes;
    }

    public ListeCommande getLigneProduit(int idc, int codePro) {
        ListeCommande ligneProduit = null;
        String selectQuery = "SELECT  * FROM " + ListeCommande.TABLE_NAME + " WHERE IDCOMMANDE = " + idc + " AND CODEPRODUIT = " + codePro;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ligneProduit = new ListeCommande(idc,
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_CODEPRODUIT)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_QUANTITE)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_PRIXU)),
                        cursor.getInt(cursor.getColumnIndex(ListeCommande.COLUMN_PHOTO)));
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return ligneProduit;
    }



    public void clearBasket(){
        deleteAllElementsCommande(0);
        deleteCommande(0);
    }

}
