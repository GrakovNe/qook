package org.grakovne.reqook.managers;

import org.grakovne.reqook.engine.Level;
import org.grakovne.reqook.engine.listeners.HistoryStatesListener;
import org.grakovne.reqook.entity.Item;

import java.util.Stack;

public class HistoryManager {
    private Stack<Level> history;
    private static HistoryManager instance;
    private static HistoryStatesListener listener;

    private HistoryManager() {
        history = new Stack<>();
    }

    public static HistoryManager build(HistoryStatesListener outListener) {
        if (instance == null) {
            instance = new HistoryManager();
        }
        listener = outListener;

        if (listener != null) {
            listener.historyIsEmpty();
        }

        return instance;
    }

    public void saveToHistory(Level level) {
        Item[][] position = new Item[level.getField().length][level.getField()[0].length];

        for (int i = 0; i < level.getField().length; i++) {
            System.arraycopy(level.getField()[i], 0, position[i], 0, level.getField()[0].length);
        }

        Level newLevel = new Level(position);

        history.push(newLevel);

        if (listener != null) {
            listener.historyIsContainsSomething();
        }
    }

    public void clearHistory(){
        history.clear();

        if (listener != null){
            listener.historyIsEmpty();
        }
    }

    public Level getFromHistory() {

        Level level = new Level(history.pop());

        if (history.isEmpty()){
            if (listener != null) {
                listener.historyIsEmpty();
            }
        }

        return level;
    }
}
