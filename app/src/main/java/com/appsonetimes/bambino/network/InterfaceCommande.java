package com.appsonetimes.bambino.network;


import com.appsonetimes.bambino.model.Commande;
import com.appsonetimes.bambino.model.ListeCommande;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceCommande {

    @GET("listeCommandes.php")
    Call<ResponseCommandes> listeCommande();

    @POST("ajouterCommande.php")
    Call<BaseResponse> ajouterCommande(@Body AjouterCommandeBody body);

    class ResponseCommandes extends BaseResponse{
        @SerializedName("result") @Expose
        protected List<Commande> commandes;

        public List<Commande> getCommandes() {
            return commandes;
        }

        public void setCommandes(List<Commande> commandes) {
            this.commandes = commandes;
        }
    }

    class AjouterCommandeBody{
        @SerializedName("commande") @Expose private Commande commande;
        @SerializedName("lignescommande") @Expose private List<ListeCommande> lignesCommandes;

        public Commande getCommande() {
            return commande;
        }

        public void setCommande(Commande commande) {
            this.commande = commande;
        }

        public List<ListeCommande> getLignesCommandes() {
            return lignesCommandes;
        }

        public void setLignesCommandes(List<ListeCommande> lignesCommandes) {
            this.lignesCommandes = lignesCommandes;
        }
    }
}
