package com.freeman.jaaga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class m_activity extends AppCompatActivity implements Callback<JSONObject> {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        /*Bundle b = new Bundle();
        setContentView(R.layout.activity_m_activity);
        b = getIntent().getExtras();
        String id = b.getString("personid");
        String name = b.getString("personName");
        String givename = b.getString("personGivenName");
        String familyname = b.getString("personFamilyName");
        String email = b.getString("personEmail");*/





    }

}
