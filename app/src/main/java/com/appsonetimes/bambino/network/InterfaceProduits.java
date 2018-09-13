package com.appsonetimes.bambino.network;

import com.appsonetimes.bambino.model.Produit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceProduits {
    @GET("listeProduits.php")
    Call<ResponseProduits> listeProduits();

    class ResponseProduits extends BaseResponse{
        @SerializedName("result") @Expose
        protected List<Produit> produits;

        public List<Produit> getProduits() {
            return produits;
        }

        public void setProduits(List<Produit> produits) {
            this.produits = produits;
        }
    }
}
