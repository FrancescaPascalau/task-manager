package com.francesca.platon.domain.service;

import com.francesca.platon.domain.ProcessFactory;
import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;

public class ProcessService {

    private final ProcessFactory processFactory;

    public ProcessService(ProcessFactory processFactory) {
        this.processFactory = processFactory;
    }

    public static Process createProcess(Priority priority){
        Process process = ProcessFactory.create(priority);
        System.out.println(process);
        return process;
    }
}
