package org.grakovne.qook.exceptions;

public class GameException extends RuntimeException {
    public GameException(GameExceptionCodes exceptionCode){
        super(exceptionCode.toString());
    }
}
