package com.freeman.jaaga;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by freeman on 1/3/17.
 */



public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.dataholder>{

    private ArrayList<User> userarray;

    public RecyclerAdapter(ArrayList<User> Userarray) {
        this.userarray = Userarray;
    }

    @Override
    public RecyclerAdapter.dataholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detaillistrow,parent,false);
        return new dataholder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.dataholder holder, int position) {

        holder.binddata(userarray.get(position));

    }

    @Override
    public int getItemCount() {
        return userarray.size();
    }

    //Made the class extend RecyclerView.ViewHolder, allowing it to be used as a ViewHolder for the adapter.
    public static class dataholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Added a list of references to the lifecycle of the object to allow the ViewHolder to hang on to your ImageView and TextView, so it doesn’t have to repeatedly query the same information.

        private TextView name;
        private TextView email;
        private ImageView imgview;

        public void binddata(User user){

            name.setText(user.PersonName);
            email.setText(user.EmailId);
            Glide.with(imgview.getContext()).load(user.PersonPhotoUri).into(imgview);
        }


        //Set up a constructor to handle grabbing references to various subviews of the photo layout.
        public dataholder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.firstname);
            email = (TextView) itemView.findViewById(R.id.secondname);
            imgview = (ImageView) itemView.findViewById(R.id.item_image);

        }

        @Override
        public void onClick(View v) {
            //Intent myintent = new Intent(this,chatclass);
        }
    }

}
