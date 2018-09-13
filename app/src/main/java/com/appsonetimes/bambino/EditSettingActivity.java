package com.appsonetimes.bambino;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class EditSettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout editTil;
    private EditText edit;
    private AppCompatButton back, validate;

    private String what = "";
    private String oldValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);

        what = getIntent().getStringExtra("WHAT");
        oldValue = getIntent().getStringExtra("OLD_VALUE");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modifier " + what);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editTil = findViewById(R.id.edit_til);
        edit = findViewById(R.id.edit);
        back = findViewById(R.id.back);
        validate = findViewById(R.id.validate);

        editTil.setHint(what);
        edit.setText(oldValue);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString().equals(oldValue)){
                    setResult(RESULT_CANCELED);
                    finish();
                }
                else{
                    Intent data = new Intent();
                    data.putExtra("WHAT", what);
                    data.putExtra("VALUE", edit.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });


        if (oldValue.length()==0) validate.setEnabled(false);
        else validate.setEnabled(true);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()==0) validate.setEnabled(false);
                else validate.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
