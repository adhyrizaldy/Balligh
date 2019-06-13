package com.exomatik.balligh.balligh.Featured;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreference {
    private String KEY_NAME = "name";
    private String KEY_EMAIL = "email";
    private String KEY_PHONE = "phone";
    private String KEY_UID = "uid";
    private String KEY_FOTO = "foto";
    private String KEY_JENIS = "jenisAkun";

    private SharedPreferences preferences;

    public UserPreference(Context paramContext) {
        this.preferences = paramContext.getSharedPreferences("UserPref", 0);
    }

    public String getKEY_NAME() {
        return this.preferences.getString(this.KEY_NAME, null);
    }
    public String getKEY_EMAIL() {
        return this.preferences.getString(this.KEY_EMAIL, null);
    }
    public String getKEY_PHONE() {
        return this.preferences.getString(this.KEY_PHONE, null);
    }
    public String getKEY_UID() {
        return this.preferences.getString(this.KEY_UID, null);
    }
    public String getKEY_FOTO() {
        return this.preferences.getString(this.KEY_FOTO, null);
    }
    public String getKEY_JENIS() {return this.preferences.getString(this.KEY_JENIS, null);}

    public void setKEY_NAME(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_NAME, paramString);
        localEditor.apply();
    }
    public void setKEY_EMAIL(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_EMAIL, paramString);
        localEditor.apply();
    }
    public void setKEY_PHONE(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_PHONE, paramString);
        localEditor.apply();
    }
    public void setKEY_UID(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_UID, paramString);
        localEditor.apply();
    }
    public void setKEY_FOTO(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_FOTO, paramString);
        localEditor.apply();
    }
    public void setKEY_JENIS(String paramString) {
        Editor localEditor = this.preferences.edit();
        localEditor.putString(this.KEY_JENIS, paramString);
        localEditor.apply();
    }
}
