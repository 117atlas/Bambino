package com.appsonetimes.bambino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Commande implements Serializable{

    public static final String TABLE_NAME = "commande";

    public static final String COLUMN_ID = "IDCOMMANDE";
    public static final String COLUMN_DATECOMMANDE = "DATECOMMANDE";
    public static final String COLUMN_MONTANT = "MONTANT";
    public static final String COLUMN_NOMCLIENT = "NOMCLIENT";
    public static final String COLUMN_TELEPHONECLIENT = "TELEPHONECLIENT";
    public static final String COLUMN_ADDRESSECLIENT = "ADDRESSECLIENT";
    public static final String COLUMN_COMMENTAIRE = "COMMENTAIRE";
    public static final String COLUMN_LIVRE = "LIVRE";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `commande` (" +
                    "  `IDCOMMANDE`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "  `DATECOMMANDE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "  `MONTANT` INTEGER NOT NULL, " +
                    "  `NOMCLIENT` varchar(50) NOT NULL, " +
                    "  `TELEPHONECLIENT` varchar(20) NOT NULL, " +
                    "  `ADDRESSECLIENT` text NOT NULL, " +
                    "  `COMMENTAIRE` text, " +
                    "  `LIVRE` INTEGER  DEFAULT 0" +
                    ")";

    @SerializedName("IDCOMMANDE") @Expose private int IdCommande;
    @SerializedName("DATE") @Expose private String date;
    @SerializedName("MONTANT") @Expose private int montant;
    @SerializedName("NOMCLIENT") @Expose private String nomClient;
    @SerializedName("TELEPHONECLIENT") @Expose private String telephoneclient;
    @SerializedName("ADRESSECLIENT") @Expose private String addresseClient;
    @SerializedName("COMMENTAIRE") @Expose private String commentaire;
    @SerializedName("LIVRE") @Expose private int livre;

    @SerializedName("LIGNESCOMMANDE") @Expose private List<ListeCommande> produits;

    public List<ListeCommande> getProduits() {
        return produits;
    }

    public void setProduits(List<ListeCommande> produits) {
        this.produits = produits;
    }

    public Commande() {
    }

    public Commande(int idCommande, String date, int montant, String nomClient, String telephoneclient,
                    String addresseClient, String commentaire, int livre) {
        IdCommande = idCommande;
        this.date = date;
        this.montant = montant;
        this.nomClient = nomClient;
        this.telephoneclient = telephoneclient;
        this.addresseClient = addresseClient;
        this.commentaire = commentaire;
        this.livre = livre;
    }

    public Commande(int montant, String nomClient, String telephoneclient,
                    String addresseClient, String commentaire, int livre) {
        this.montant = montant;
        this.nomClient = nomClient;
        this.telephoneclient = telephoneclient;
        this.addresseClient = addresseClient;
        this.commentaire = commentaire;
        this.livre = livre;
    }

    public int getIdCommande() {
        return IdCommande;
    }

    public void setIdCommande(int idCommande) {
        IdCommande = idCommande;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTelephoneclient() {
        return telephoneclient;
    }

    public void setTelephoneclient(String telephoneclient) {
        this.telephoneclient = telephoneclient;
    }

    public String getAddresseClient() {
        return addresseClient;
    }

    public void setAddresseClient(String addresseClient) {
        this.addresseClient = addresseClient;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int isLivre() {
        return livre;
    }

    public void setLivre(int livre) {
        this.livre = livre;
    }
}
