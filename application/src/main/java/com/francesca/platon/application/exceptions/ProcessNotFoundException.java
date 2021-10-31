package com.francesca.platon.application.exceptions;

public class ProcessNotFoundException extends RuntimeException {

    public ProcessNotFoundException() {
        super("Could not find running process");
    }
}
