package org.grakovne.reqook.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedSettingsManager {
    private static final String LAST_LEVEL = "current_level";
    private static final String MAX_LEVEL = "max_level";
    private static final String WAS_RAN_BEFORE = "was_ran_before";
    private static final String IS_ANIMATION_NEED = "is_animation_need";
    private static final String IS_UNDO_PURCHASED = "is_undo_purchased";
    private static final String APP_PREFS = "qook_prefs";
    private static Context context;
    private static SharedSettingsManager instance;
    SharedPreferences sharedPreferences;

    private SharedSettingsManager() {
        sharedPreferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public static SharedSettingsManager build(Context currentContext) {
        context = currentContext;

        if (instance == null) {
            instance = new SharedSettingsManager();
        }
        return instance;
    }

    public boolean isAnimationNeed() {
        return sharedPreferences.getBoolean(IS_ANIMATION_NEED, true);
    }

    public boolean isUndoPurchased() {
        return sharedPreferences.getBoolean(IS_UNDO_PURCHASED, false);
    }

    public void dropUndo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_UNDO_PURCHASED, false);
        editor.apply();
    }

    public void makeUndoPurchased() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_UNDO_PURCHASED, true);
        editor.apply();
    }

    public void setIsAnimationNeed(boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_ANIMATION_NEED, state);
        editor.apply();
    }

    public int getMaxLevel() {
        return sharedPreferences.getInt(MAX_LEVEL, 1);
    }

    public void setMaxLevel(int maxLevel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAX_LEVEL, maxLevel);
        editor.apply();
    }

    public void setRanBefore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WAS_RAN_BEFORE, true);
        editor.apply();
    }

    public boolean isRanBefore() {
        return sharedPreferences.getBoolean(WAS_RAN_BEFORE, false);
    }

    public int getCurrentLevel() {
        return sharedPreferences.getInt(LAST_LEVEL, 1);
    }

    public void setCurrentLevel(int currentLevel) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_LEVEL, currentLevel);

        editor.apply();

        if (getMaxLevel() < currentLevel) {
            setMaxLevel(currentLevel);
        }
    }
}
