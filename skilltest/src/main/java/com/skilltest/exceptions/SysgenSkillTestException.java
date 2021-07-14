package com.skilltest.exceptions;

import lombok.Getter;

@Getter
public class SysgenSkillTestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    /**
     * @param errorCode
     * @param errorMessage
     */
    public SysgenSkillTestException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
