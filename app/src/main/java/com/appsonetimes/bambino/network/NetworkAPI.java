package com.appsonetimes.bambino.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkAPI {

    public static String SERVER_URL = "http://mbogni.boutique-bambino.com/apis/";
    public static String IMAGE_BASE_URL = "http://mbogni.boutique-bambino.com/eshop/productImages/";

    private static Retrofit retrofit = null;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static void setServerUrl(String server){
        SERVER_URL = "http://"+server+"/";
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
