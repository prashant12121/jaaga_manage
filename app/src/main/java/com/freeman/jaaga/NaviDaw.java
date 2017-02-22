package com.freeman.jaaga;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NaviDaw extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<ResponseBody> {

    LinearLayout l;
    dummyapi myservice;
    ArrayList<JSONObject> myarr = new ArrayList<>();
    private ListView listv ;
    detailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navi_daw);
        Bundle bundle = getIntent().getExtras();

        //Intent k = this.getIntent();
        String imagame = bundle.getString("personPhoto");
        String personEmail = bundle.getString("personEmail");
        String persongivenname = bundle.getString("personGivenName");
        Uri url = Uri.parse(imagame);
        //image.setImageURI(url);// Glide.with(getApplicationContext()).load(url).into(image);

        //listview/adapter of userdetatils from
        listv = (ListView)findViewById(R.id.mylistview);
        adapter = new detailsAdapter(this , myarr);
        listv.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //sample data using api call
        int pageno =1;
        myservice = Helper.getRetrofit();
        Call<ResponseBody> request = myservice.userdata(pageno);// total api (base url + resturl ) + other info after retrofit processd in previous line
        request.enqueue(this);  // execute request // the overriing methos oncall resonse are exected

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView1);
        TextView tv1 = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_title);
        TextView tv2 = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView) ;
        tv1.setText(personEmail);
        tv2.setText(persongivenname);

        Glide.with(this)
                .load(url)
                .into(image);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi_daw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String josn = null;
        try {
            josn = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            System.out.println(josn);
            JSONObject jsonObject;
            jsonObject= new JSONObject(josn);       // json parser converting string into json
            JSONArray userarr = jsonObject.getJSONArray("data");
            ArrayList<String> names = null;
            ArrayList<String> url = null;

            for(int i=0 ; i<userarr.length() ;  i++) {
                myarr.add(userarr.getJSONObject(i));
            }
            adapter.notifyDataSetChanged();

            int pageno =  Integer.parseInt(jsonObject.getString("page"));
            int totalpages =  Integer.parseInt(jsonObject.getString("total_pages"));
            if(pageno < totalpages) {
                pageno = pageno+1;
                Call<ResponseBody> request = myservice.userdata(pageno);
                request.enqueue(this);
            }
            Log.i("page", String.valueOf(pageno-1));
            Log.i("response",response.raw().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.i("faliure",t.getCause().toString());
    }
}
