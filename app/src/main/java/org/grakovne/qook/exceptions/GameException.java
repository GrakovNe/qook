package org.grakovne.qook.exceptions;

import org.grakovne.qook.enums.GameExceptionCodes;

public class GameException extends RuntimeException {
    public GameException(GameExceptionCodes exceptionCode){
        super(exceptionCode.toString());
    }
}
