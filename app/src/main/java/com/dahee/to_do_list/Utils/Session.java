package com.dahee.to_do_list.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    public SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin,String email){
        editor.putBoolean("loggedInmode",logggedin);
        editor.putString("EMAIL",email);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
