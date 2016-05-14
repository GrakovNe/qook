package org.grakovne.qook.managers;

import org.grakovne.qook.entity.Field;
import org.grakovne.qook.enums.GameExceptionCodes;
import org.grakovne.qook.exceptions.GameException;

import java.util.Stack;

public class SnapshotManager {
    private Stack<Field> fieldStack;

    public SnapshotManager(){
        fieldStack = new Stack<>();
    }

    public void clearHistory(){
        fieldStack.clear();
    }

    public void saveField(Field field){

        if (field == null){
            return;
        }

        fieldStack.push(field);
    }

    public Field getFromHistory(){
        if (fieldStack.isEmpty()){
            throw new GameException(GameExceptionCodes.HISTORY_IS_EMPTY);
        }

        return fieldStack.pop();
    }
}
