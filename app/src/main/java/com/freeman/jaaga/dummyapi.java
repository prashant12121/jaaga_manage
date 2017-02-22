package com.freeman.jaaga;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by freeman on 15/2/17.
 */

public interface dummyapi {         //

        @GET("users")
        Call<ResponseBody> userdata(@Query("page") int pageno); //

}
