package com.appsonetimes.bambino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Produit implements Serializable{

    public static final String TABLE_NAME = "produit";

    public static final String COLUMN_CODE = "CODE";
    public static final String COLUMN_CATEGORIE = "CATEGORIE";
    public static final String COLUMN_PRIX = "PRIX";
    public static final String COLUMN_QUANTITE = "QTE";
    public static final String COLUMN_NOM = "NOM";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_CODEFOUR = "CODEFOUR";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_ACTIF = "ACTIF";
    public static final String COLUMN_IMAGE = "IMAGE";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `produit` (" +
                    "  `" + COLUMN_CODE + "` INTEGER NOT NULL PRIMARY KEY, " +
                    "  `" + COLUMN_CATEGORIE + "` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "  `" + COLUMN_PRIX + "` INTEGER NOT NULL, " +
                    "  `" + COLUMN_QUANTITE + "` INTEGER NOT NULL, " +
                    "  `" + COLUMN_NOM + "` varchar(20) NOT NULL, " +
                    "  `" + COLUMN_DESCRIPTION + "` text, " +
                    "  `" + COLUMN_CODEFOUR + "` text, " +
                    "  `" + COLUMN_DATE + "` text, " +
                    "  `" + COLUMN_ACTIF + "` INTEGER, " +
                    "  `" + COLUMN_IMAGE + "` text" +
                    ")";

    @SerializedName("codePro") @Expose private int codeProduit;
    @SerializedName("categorie") @Expose private Categorie cat;
    @SerializedName("prix") @Expose private int prix;
    @SerializedName("qte") @Expose private int quantite;
    @SerializedName("nomPro") @Expose private String nom;
    @SerializedName("description") @Expose private String description;
    @SerializedName("codeFour") @Expose private String codeFournisseur;
    @SerializedName("date") @Expose private String date;
    @SerializedName("actif") @Expose private boolean actif = true;
    @SerializedName("image") @Expose private String image;

    private int photo1;
    private int photo2;
    private int photo3;
    private int photo4;
    private int photo5;

    @SerializedName("categorie2") private String categorie;

    private List<String> photos;

    public Categorie getCat() {
        return cat;
    }

    public void setCat(Categorie cat) {
        this.cat = cat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Produit() {
    }

    public Produit(int codeProduit, String categorie, int prix, String nom, int photo1, int photo2, int photo3, int photo4, int photo5) {
        this.codeProduit = codeProduit;
        this.categorie = categorie;
        this.prix = prix;
        this.quantite = quantite;
        this.nom = nom;
        this.description = description;
        this.codeFournisseur = codeFournisseur;
        this.date = date;
        this.actif = actif;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.photo5 = photo5;
    }


    public String getCategorie() {
        return categorie;
    }

    /*public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public void setPhoto5(String photo5) {
        this.photo5 = photo5;
    }*/

    public int getPhoto1() {
        return photo1;
    }

    public void setPhoto1(int photo1) {
        this.photo1 = photo1;
    }

    public int getPhoto2() {
        return photo2;
    }

    public void setPhoto2(int photo2) {
        this.photo2 = photo2;
    }

    public int getPhoto3() {
        return photo3;
    }

    public void setPhoto3(int photo3) {
        this.photo3 = photo3;
    }

    public int getPhoto4() {
        return photo4;
    }

    public void setPhoto4(int photo4) {
        this.photo4 = photo4;
    }

    public int getPhoto5() {
        return photo5;
    }

    public void setPhoto5(int photo5) {
        this.photo5 = photo5;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
