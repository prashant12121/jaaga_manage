package com.freeman.jaaga;

import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by freeman on 18/2/17.
 */

public class detailsAdapter extends BaseAdapter {

    private Context mycontext;
    private LayoutInflater inflater ;
    private ArrayList<JSONObject> jsonarrays;

    public detailsAdapter(Context context ,ArrayList<JSONObject> namesarr){

        mycontext = context;
        jsonarrays = namesarr;
        inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        return jsonarrays.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonarrays.get(position);
    }

    @Override
    public long getItemId(int position) {
        jsonarrays.get(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.detaillistrow , parent , false);
        TextView tv1 = (TextView) rowView.findViewById(R.id.firstname);
        TextView tv2 = (TextView) rowView.findViewById(R.id.secondname);
        ImageView img1 = (ImageView) rowView.findViewById(R.id.item_image);
        JSONObject myjsonobjectatposition =  jsonarrays.get(position);
        try {
            tv1.setText(myjsonobjectatposition.getString("first_name"));
            tv2.setText(myjsonobjectatposition.getString("last_name"));
            String url = myjsonobjectatposition.getString("avatar");
            Glide.with(mycontext).load(url).into(img1);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}
