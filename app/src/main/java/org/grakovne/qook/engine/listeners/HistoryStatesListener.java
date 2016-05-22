package org.grakovne.qook.engine.listeners;

import java.util.EventListener;

public interface HistoryStatesListener extends EventListener {
    void historyIsEmpty();
    void historyIsContainsSomething();
}
