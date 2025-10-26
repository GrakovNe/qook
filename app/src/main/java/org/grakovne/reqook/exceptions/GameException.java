package org.grakovne.reqook.exceptions;

import org.grakovne.reqook.enums.GameExceptionCodes;

public class GameException extends RuntimeException {
    public GameException(GameExceptionCodes exceptionCode) {
        super(exceptionCode.toString());
    }
}
