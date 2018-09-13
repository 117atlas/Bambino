package com.appsonetimes.bambino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private static final int EDIT_CODE = 419;
    private Toolbar toolbar;
    private TextView name, address, mobile;
    private ImageButton editName, editAddress, editMobile;

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        appPreferences = AppPreferences.getPreferences(this);
        if (appPreferences==null) finish();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parametres Utilisateur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        editName = findViewById(R.id.edit_name);
        editAddress = findViewById(R.id.edit_address);
        editMobile = findViewById(R.id.edit_mobile);

        View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.edit_name:{
                        Intent intent = new Intent(ProfileActivity.this, EditSettingActivity.class);
                        intent.putExtra("WHAT", "Votre nom");
                        intent.putExtra("OLD_VALUE", appPreferences.getUserName());
                        startActivityForResult(intent, EDIT_CODE);
                    } break;
                    case R.id.edit_address:{
                        Intent intent = new Intent(ProfileActivity.this, EditSettingActivity.class);
                        intent.putExtra("WHAT", "Votre adresse");
                        intent.putExtra("OLD_VALUE", appPreferences.getUserAddress());
                        startActivityForResult(intent, EDIT_CODE);
                    } break;
                    case R.id.edit_mobile:{
                        Intent intent = new Intent(ProfileActivity.this, EditSettingActivity.class);
                        intent.putExtra("WHAT", "Votre contact");
                        intent.putExtra("OLD_VALUE", appPreferences.getUserMobile());
                        startActivityForResult(intent, EDIT_CODE);
                    } break;
                }
            }
        };

        editName.setOnClickListener(editListener);
        editAddress.setOnClickListener(editListener);
        editMobile.setOnClickListener(editListener);

        bind();
    }

    private void bind(){
        name.setText(appPreferences.getUserName());
        address.setText(appPreferences.getUserAddress());
        mobile.setText(appPreferences.getUserMobile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==EDIT_CODE && resultCode==RESULT_OK){
            String what = data.getStringExtra("WHAT");
            System.out.println(data.getStringExtra("VALUE") + what);
            if (what.contains("nom")){
                AppPreferences.SetUserName(this, data.getStringExtra("VALUE"));
            }
            else if (what.contains("adresse")){
                AppPreferences.SetUserAddress(this, data.getStringExtra("VALUE"));
            }
            else{
                AppPreferences.SetUserMobile(this, data.getStringExtra("VALUE"));
            }
            appPreferences = AppPreferences.getPreferences(this);
            bind();
        }
    }

}
