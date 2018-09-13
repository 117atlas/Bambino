package com.appsonetimes.bambino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class ListeCommande implements Serializable{

    public static final String TABLE_NAME = "listeCommande";

    public static final String COLUMN_ID = "IDLCOMMANDE";
    public static final String COLUMN_IDCOMMANDE = "IDCOMMANDE";
    public static final String COLUMN_CODEPRODUIT = "CODEPRODUIT";
    public static final String COLUMN_QUANTITE = "QUANTITE";
    public static final String COLUMN_PRIXU = "PRIXUNITAIRE";
    public static final String COLUMN_PHOTO = "PHOTO";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `listeCommande` (" +
                    "  `IDLCOMMANDE` INTEGER NOT NULL," +
                    "  `IDCOMMANDE` INTEGER unsigned NOT NULL," +
                    "  `CODEPRODUIT` INTEGER unsigned NOT NULL," +
                    "  `QUANTITE` INTEGER unsigned NOT NULL," +
                    "  `PRIXUNITAIRE` INTEGER unsigned NOT NULL," +
                    "  `PHOTO` INTEGER unsigned NOT NULL" +
                    ")";

    @SerializedName("IDLCOMMANDE") @Expose private int IdLCommande;
    @SerializedName("IDCOMMANDE") @Expose private int idCommande;
    @SerializedName("CODEPRODUIT") @Expose private int codeProduit;
    @SerializedName("QUANTITE") @Expose private int quantite;
    @SerializedName("PRIXU") @Expose private int prixU;
    @SerializedName("PHOTO") @Expose private int photo;

    public ListeCommande() {
    }


    public ListeCommande(int idLCommande, int idCommande, int codeProduit, int quantite, int prixU, int idf) {
        IdLCommande = idLCommande;
        this.idCommande = idCommande;
        this.codeProduit = codeProduit;
        this.quantite = quantite;
        this.prixU = prixU;
        this.photo=idf;
    }

    public ListeCommande(int idCommande, int codeProduit, int quantite, int prixU, int tof) {
        this.idCommande = idCommande;
        this.codeProduit = codeProduit;
        this.quantite = quantite;
        this.prixU = prixU;
        this.photo=tof;
    }

    public int getPrixU() {
        return prixU;
    }

    public void setPrixU(int prixU) {
        this.prixU = prixU;
    }

    public int getIdLCommande() {
        return IdLCommande;
    }

    public void setIdLCommande(int idLCommande) {
        IdLCommande = idLCommande;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(int codeProduit) {
        this.codeProduit = codeProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListeCommande that = (ListeCommande) o;
        return IdLCommande == that.IdLCommande &&
                idCommande == that.idCommande &&
                codeProduit == that.codeProduit;
    }

}
