package com.freeman.jaaga;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by freeman on 15/2/17.
 */

//

public class Helper  {

    static dummyapi service;

    public static dummyapi getRetrofit(){                             // hanle request response in bckground
        if(service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://reqres.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())    //
                    .build();

            dummyapi service = retrofit.create(dummyapi.class); //connecting url of  retrofit class with dummy class annotated method link
            return service;
        }
        return  service;
    }

}
