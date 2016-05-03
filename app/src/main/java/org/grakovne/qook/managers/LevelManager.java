package org.grakovne.qook.managers;

import android.content.Context;
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

public class LevelManager {
    private static final String LEVELS_FOLDER = "levels";
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

    private static Context context;
    private static SharedSettingsManager sharedSettingsManager;
    private static LevelManager instance;

    private LevelManager() {
        //TODO: remove it when release
        sharedSettingsManager.setCurrentLevel(1);
    }

    public static LevelManager build(Context currentContext) {
        context = currentContext;
        sharedSettingsManager = new SharedSettingsManager(context);

        if (instance == null) {
            instance = new LevelManager();
        }
        return instance;
    }

    public Level getLevel(int levelNumber) throws IOException {
        Scanner scanner = openLevel(levelNumber);

        int levelWidth = scanner.nextInt();
        int levelHeight = scanner.nextInt();

        Item levelMatrix[][] = new Item[levelHeight][levelWidth];

        for (int i = 0; i < levelHeight; i++) {
            for (int j = 0; j < levelWidth; j++) {
                levelMatrix[i][j] = convertLegendToItem(scanner.nextInt());
            }
        }

        Level level = new Level(levelMatrix);
        sharedSettingsManager.setCurrentLevel(levelNumber);
        return level;
    }

    public Level getCurrentLevel() throws IOException {
        return getLevel(getCurrentLevelNumber());
    }

    private Item convertLegendToItem(int itemLegend) {
        switch (itemLegend) {
            case EMPTY_CELL:
                return null;

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

    public int getLevelsNumber() throws IOException {
        AssetManager assetManager = context.getAssets();
        return assetManager.list(LEVELS_FOLDER).length;
    }

    public int getCurrentLevelNumber() {
        return sharedSettingsManager.getCurrentLevel();
    }

    public void finishLevel() {
        sharedSettingsManager.setCurrentLevel(
                sharedSettingsManager.getCurrentLevel() + 1
        );
    }

    private Scanner openLevel(int levelNumber) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(
                LEVELS_FOLDER +
                        "/" +
                        String.valueOf(levelNumber) +
                        LEVEL_FILE_EXTENSION);

        BufferedReader bufferedReader =
                new BufferedReader
                        (new InputStreamReader(inputStream));

        return new Scanner(bufferedReader);
    }

    public Level resetLevel() throws IOException {
        return getCurrentLevel();
    }
}
