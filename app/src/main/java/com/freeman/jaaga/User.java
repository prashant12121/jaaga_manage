package com.freeman.jaaga;

/**
 * Created by freeman on 23/2/17.
 */

public class User {
   public String PersonPhotoUri;
   public String EmailId;
   public String PersonName;
   public String PersonUserName;
   public String PersonId;

    User(){

    }

    User(String PersonPhotoUri, String EmailId , String PersonName ,String PersonUserName,String PersonId){
        this.PersonPhotoUri = PersonPhotoUri;
        this.EmailId = EmailId;
        this.PersonName = PersonName;
        this.PersonUserName = PersonUserName;
        this.PersonId = PersonId;
    }
}
