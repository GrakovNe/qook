package org.grakovne.qook.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedSettingsManager {
    private static final String APP_PREFS = "qook_Prefs";
    public static final String CURRENT_LEVEL = "current_level";

    private Context context;
    SharedPreferences sharedPreferences;

    public SharedSettingsManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public void setCurrentLevel(int currentLevel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_LEVEL, currentLevel);
        editor.apply();
    }

    public int getCurrentLevel(){
        return sharedPreferences.getInt(CURRENT_LEVEL, 1);
    }
}
