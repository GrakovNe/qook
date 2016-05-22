package org.grakovne.qook.engine;

import org.grakovne.qook.dimensionality.Coordinates;
import org.grakovne.qook.engine.listeners.FieldUpdatingListener;
import org.grakovne.qook.engine.listeners.LevelCompleteListener;
import org.grakovne.qook.entity.Ball;
import org.grakovne.qook.entity.Hole;
import org.grakovne.qook.entity.Item;
import org.grakovne.qook.enums.Direction;

import java.io.Serializable;

public class Field implements Serializable {
    private Level level;
    private int ballsCount;

    private static final int SLEEP_LATENCY = 35;
    private boolean isAnimation = true;

    transient private LevelCompleteListener completeListener;
    transient private FieldUpdatingListener updatingListener;

    public void loadFromHistory(Level level){
        this.level = level;
        this.ballsCount = level.getBallsCount();
    }

    public Field(Level level, boolean isAnimation) {
        this.level = level;
        this.ballsCount = level.getBallsCount();
        this.isAnimation = isAnimation;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void setIsAnimation(boolean state){
        isAnimation = state;
    }

    public Item[][] getField() {
        return level.getField();
    }

    public void setCompleteListener(LevelCompleteListener completeListener){
        this.completeListener = completeListener;
    }

    public void setUpdatingListener(FieldUpdatingListener updatingListener) {
        this.updatingListener = updatingListener;
    }

    private Coordinates moveItem(Coordinates coordinates, Direction direction) {

        int horizontal = coordinates.getHorizontal();
        int vertical = coordinates.getVertical();

        if (direction.equals(Direction.NOWHERE) || level.getField()[vertical][horizontal] == null) {
            return null;
        }

        Class clazz = level.getField()[vertical][horizontal].getClass();
        if (!clazz.equals(Ball.class)) {
            return null;
        }

        switch (direction) {
            case RIGHT:
                return moveRight(horizontal, vertical);

            case LEFT:
                return moveLeft(horizontal, vertical);

            case UP:
                return moveUp(horizontal, vertical);

            case DOWN:
                return moveDown(horizontal, vertical);
        }

        return null;
    }

    private void catchBall() {
        ballsCount--;
    }

    public void makeTurn(final Coordinates coordinates, final Direction direction) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (updatingListener != null){
                    updatingListener.startUpdate();
                }

                Coordinates newCoordinates = moveItem(coordinates, direction);
                if (newCoordinates != null) {
                    acceptHole(newCoordinates, direction);
                }

                if (updatingListener != null){
                    updatingListener.finishUpdate();
                }

                if (completeListener != null && checkWin()) {
                    completeListener.levelComplete();
                }
            }
        };

        new Thread(runnable).start();


    }

    private boolean acceptHole(Coordinates coordinates, Direction direction) {
        boolean isAccepted = false;
        switch (direction) {
            case UP:
                isAccepted = acceptUp(coordinates);
                break;

            case DOWN:
                isAccepted = acceptDown(coordinates);
                break;

            case RIGHT:
                isAccepted = acceptRight(coordinates);
                break;

            case LEFT:
                isAccepted = acceptLeft(coordinates);
                break;
        }

        if (!isAccepted) {
            return false;
        }

        catchBall();

        return checkWin();

    }

    private boolean acceptUp(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical - 1][horizontal];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;

        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return true;
    }

    private boolean acceptDown(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item downItem = level.getField()[vertical + 1][horizontal];
            Item item = level.getField()[vertical][horizontal];

            if (downItem == null || !downItem.getClass().equals(Hole.class) || !(downItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;

        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return true;
    }

    private boolean acceptRight(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical][horizontal + 1];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;

        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return true;
    }

    private boolean acceptLeft(Coordinates coordinates) {
        try {
            int horizontal = coordinates.getHorizontal();
            int vertical = coordinates.getVertical();

            Item upItem = level.getField()[vertical][horizontal - 1];
            Item item = level.getField()[vertical][horizontal];

            if (upItem == null || !upItem.getClass().equals(Hole.class) || !(upItem.getColor().equals(item.getColor()))) {
                return false;
            }

            level.getField()[vertical][horizontal] = null;

        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return true;
    }

    private Coordinates moveRight(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord][xCoord + 1] == null) {
                synchronized (level.getField()) {
                    level.getField()[yCoord][xCoord + 1] = level.getField()[yCoord][xCoord];
                    level.getField()[yCoord][xCoord++] = null;
                }

                if (isAnimation) {
                    Thread.sleep(SLEEP_LATENCY);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveLeft(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord][xCoord - 1] == null) {
                synchronized (level.getField()) {
                    level.getField()[yCoord][xCoord - 1] = level.getField()[yCoord][xCoord];
                    level.getField()[yCoord][xCoord--] = null;
                }

                if (isAnimation) {
                    Thread.sleep(SLEEP_LATENCY);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveUp(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord - 1][xCoord] == null) {

                synchronized (level.getField()) {
                    level.getField()[yCoord - 1][xCoord] = level.getField()[yCoord][xCoord];
                    level.getField()[yCoord--][xCoord] = null;
                }

                if (isAnimation) {
                    Thread.sleep(SLEEP_LATENCY);
                }

            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Coordinates(xCoord, yCoord);
    }

    private Coordinates moveDown(int xCoord, int yCoord) {
        try {
            while (level.getField()[yCoord + 1][xCoord] == null) {
                synchronized (level.getField()) {
                    level.getField()[yCoord + 1][xCoord] = level.getField()[yCoord][xCoord];
                    level.getField()[yCoord++][xCoord] = null;
                }

                if (isAnimation) {
                    Thread.sleep(SLEEP_LATENCY);
                }

            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Coordinates(xCoord, yCoord);
    }

    private boolean checkWin() {
        return ballsCount == 0;
    }
}
