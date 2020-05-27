package com.paytel.task.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException(Long id) {
        super("Log not found id: " + id);
    }

    public LogNotFoundException() {
        super("Not found");
    }
}