package org.grakovne.qook;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import org.grakovne.qook.entity.Level;
import org.grakovne.qook.entity.elements.Ball;
import org.grakovne.qook.entity.elements.Block;
import org.grakovne.qook.entity.elements.Hole;
import org.grakovne.qook.entity.elements.Item;
import org.grakovne.qook.enums.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;

public class LevelManager {
    private static final String LEVELS_FOLDER = "levels/";
    private static final String LEVEL_FILE_EXTENSION = ".lev";

    private static final int EMPTY_CELL = 0;
    private static final int BLOCK_CELL = 1;
    private static final int GREEN_BALL_CELL = 2;
    private static final int RED_BALL_CELL = 3;
    private static final int BLUE_BALL_CELL = 4;
    private static final int YELLOW_BALL_CELL = 5;
    private static final int PURPLE_BALL_CELL = 6;
    private static final int CYAN_BALL_CELL = 7;

    private static final int GREEN_HOLE_CELL = 22;
    private static final int RED_HOLE_CELL = 33;
    private static final int BLUE_HOLE_CELL = 44;
    private static final int YELLOW_HOLE_CELL = 55;
    private static final int PURPLE_HOLE_CELL = 66;
    private static final int CYAN_HOLE_CELL = 77;

    private static Activity activity;
    private static LevelManager instance;

    private int currentLevel = 1;
    private Stack<Level> snapshot = new Stack<>();

    private LevelManager() {
    }

    public LevelManager setCurrentLevel(int currentLevel) {
        if (currentLevel <= 0) {
            throw new RuntimeException("Level can't be negative!");
        }

        this.currentLevel = currentLevel;
        return this;
    }

    public static LevelManager build(Activity currentActivity) {
        activity = currentActivity;

        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    public void makeSnapshot(Level level) {
        snapshot.push(level);
    }

    public Level parseLevelFromFile() throws IOException {
        Scanner scanner = openLevel();

        int levelWidth = scanner.nextInt();
        int levelHeight = scanner.nextInt();

        Item levelMatrix[][] = new Item[levelHeight][levelWidth];

        for (int i = 0; i < levelHeight; i++){
            for (int j = 0; j < levelWidth; j++){
                levelMatrix[i][j] = convertLegendToItem(scanner.nextInt());
            }
        }

        Level level = new Level(levelMatrix);

        return level;
    }

    private Item convertLegendToItem(int itemLegend) {
        switch (itemLegend){
            case BLOCK_CELL:
                return new Block();

            case GREEN_BALL_CELL:
                return new Ball(Color.GREEN);

            case RED_BALL_CELL:
                return new Ball(Color.RED);

            case BLUE_BALL_CELL:
                return new Ball(Color.BLUE);

            case YELLOW_BALL_CELL:
                return new Ball(Color.YELLOW);

            case PURPLE_BALL_CELL:
                return new Ball(Color.PURPLE);

            case CYAN_BALL_CELL:
                return new Ball(Color.CYAN);

            case GREEN_HOLE_CELL:
                return new Hole(Color.GREEN);

            case RED_HOLE_CELL:
                return new Hole(Color.RED);

            case BLUE_HOLE_CELL:
                return new Hole(Color.BLUE);

            case YELLOW_HOLE_CELL:
                return new Hole(Color.YELLOW);

            case PURPLE_HOLE_CELL:
                return new Hole(Color.PURPLE);

            case CYAN_HOLE_CELL:
                return new Hole(Color.CYAN);
        }


        return null;
    }

    private Scanner openLevel() throws IOException {
        AssetManager assetManager = activity.getAssets();
        InputStream inputStream = assetManager.open(LEVELS_FOLDER + "05" + LEVEL_FILE_EXTENSION);

        BufferedReader bufferedReader =
                new BufferedReader
                        (new InputStreamReader(inputStream));

        return new Scanner(bufferedReader);
    }

    public Level getLastSnapshot() {
        return snapshot.pop();
    }
}
