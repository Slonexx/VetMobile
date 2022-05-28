package com.example.vetmobile;

import android.content.SharedPreferences;

public class SaveDateSharedPreferences  {

    public int setSPrefUserId(SharedPreferences sPref, int UserId){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("USER_ID", UserId);
        ed.commit();
        return UserId;
    }

    public int getSPrefUserId(SharedPreferences sPref, int UserId){
        UserId = sPref.getInt("USER_ID", UserId);
        return UserId;
    }

    public String setSPrefToken(SharedPreferences sPref, String setToken){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("TOKEN", setToken);
        ed.commit();
        return setToken;
    }

    public String getSPrefToken(SharedPreferences sPref, String getToken){
        getToken = sPref.getString("TOKEN", "");
        return getToken;
    }

}
