package org.grakovne.reqook.engine.listeners;

import java.util.EventListener;

public interface FieldUpdatingListener extends EventListener {
    void startUpdate();
    void finishUpdate();
}
