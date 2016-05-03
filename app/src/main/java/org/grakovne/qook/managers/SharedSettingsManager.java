package org.grakovne.qook.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedSettingsManager {
    private static final String APP_PREFS = "qook_prefs";
    public static final String LAST_LEVEL = "current_level";
    public static final String MAX_LEVEL = "max_level";
    public static final String WAS_RAN_BEFORE = "was_ran_before";

    SharedPreferences sharedPreferences;

    public SharedSettingsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        setCurrentLevel(getMaxLevel());
    }

    public int getMaxLevel(){
        return sharedPreferences.getInt(MAX_LEVEL, 1);
    }

    public void setRanBefore(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WAS_RAN_BEFORE, true);
        editor.apply();
    }

    public boolean isRanBefore(){
        return sharedPreferences.getBoolean(WAS_RAN_BEFORE, false);
    }

    private void setMaxLevel(int maxLevel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAX_LEVEL, maxLevel);
        editor.apply();
    }

    public void setCurrentLevel(int currentLevel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_LEVEL, currentLevel);

        editor.apply();

        if (getMaxLevel() < currentLevel){
            setMaxLevel(currentLevel);
        }
    }

    public int getCurrentLevel(){
        return sharedPreferences.getInt(LAST_LEVEL, 1);
    }
}
