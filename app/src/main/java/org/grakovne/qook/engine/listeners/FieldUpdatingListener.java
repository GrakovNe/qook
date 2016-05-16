package org.grakovne.qook.engine.listeners;

import java.util.EventListener;

public interface FieldUpdatingListener extends EventListener {
    void startUpdate();
    void finishUpdate();
}
