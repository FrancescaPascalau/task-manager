package com.francesca.platon.domain;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;

public class ProcessFactory {

    public static Process create(Priority priority) {
        return switch (priority) {
            case HIGH -> new Process(Priority.HIGH);
            case MEDIUM -> new Process(Priority.MEDIUM);
            default -> new Process(Priority.LOW);
        };
    }
}
