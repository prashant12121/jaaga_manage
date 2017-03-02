package com.freeman.jaaga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 12;
    TextView tv1;
    Spinner spinner;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    SharedPreferences sp;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences prefs = getSharedPreferences("myprefs",MODE_PRIVATE);
        int IsLogin = prefs.getInt("Islogin",MODE_PRIVATE);
        System.out.println(IsLogin);
        String personName = prefs.getString("personName",null);
        String personEmail = prefs.getString("personEmail",null);
        String personPhoto = prefs.getString("image",null);
        if(IsLogin==1){
            Intent j = new Intent(this , databaseuserlist.class);
            j.putExtra("personPhoto",personPhoto);
            j.putExtra("personEmail",personEmail);
            j.putExtra("personGivenName",personName);
            startActivity(j);
            finish();
        }

        tv1 = (TextView) findViewById(R.id.tv1);     //font
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/heyfont.ttf"); //font
        tv1.setTypeface(face);   //font
        spinner = (Spinner) findViewById(R.id.spinner); // type1,type 2
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);     // no such fnc
    }

    public void writeNewUser( String PersonPhotoUri,String EmailId,String PersonName,String PersonUserName,String PersonId){
    User user = new User(PersonPhotoUri,EmailId,PersonName,PersonUserName,PersonId);
        String key = mDatabase.child("user").push().getKey();
        mDatabase.child("user").child(key).setValue(user);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        // Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            String personPhoto = acct.getPhotoUrl().toString();
            writeNewUser( personPhoto, personEmail, personName, personGivenName,personId);

            int Islogin = 1;
            sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("Islogin",Islogin);
            edit.putString("personid", personId);
            edit.putString("personName",personName);
            edit.putString("personGivenName",personGivenName);
            edit.putString("personFamilyName",personFamilyName);
            edit.putString("personEmail",personEmail);
            edit.putString("image", String.valueOf(personPhoto));
            edit.apply();


            Intent j = new Intent(this , databaseuserlist.class);
            j.putExtra("personPhoto",personPhoto);
            j.putExtra("personEmail",personEmail);
            j.putExtra("personGivenName",personGivenName);
            startActivity(j);



            /*
            Intent i = new Intent(this,m_activity.class);
            i.putExtra("personid",personId);
            i.putExtra("personName",personName);
            i.putExtra("personGivenName",personGivenName);
            i.putExtra("personFamilyName",personFamilyName);
            i.putExtra("personEmail",personEmail);
            */
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "sign in successful using the valid email address " + acct.getDisplayName() , Toast.LENGTH_SHORT).show();
            // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            finish();
        }
        else {
            Toast.makeText(this , "sign in using the valid account",Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //this overrie what
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {                   //result code
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "could not connect", Toast.LENGTH_SHORT).show();
    }
}