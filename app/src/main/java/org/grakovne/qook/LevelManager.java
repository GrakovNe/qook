package org.grakovne.qook;

import org.grakovne.qook.entity.Level;

import java.util.Stack;

public class LevelManager {
    private static LevelManager instance;

    private int currentLevel = 1;
    private Stack<Level> snapshot = new Stack<>();

    private LevelManager(){
    }

    public void setCurrentLevel(int currentLevel){
        if (currentLevel <= 0){
            throw new RuntimeException("Level can't be negative!");
        }

        this.currentLevel = currentLevel;
    }

    public static LevelManager build(){
        if (instance == null){
            instance = new LevelManager();
        }
        return instance;
    };

    public void makeSnapshot(Level level){
        snapshot.push(level);
    }

    public Level getLastSnapshot(){
        return snapshot.pop();
    }
}
