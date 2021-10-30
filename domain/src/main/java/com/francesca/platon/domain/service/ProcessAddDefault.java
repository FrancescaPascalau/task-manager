package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;

import java.util.concurrent.ArrayBlockingQueue;

public class ProcessAddDefault implements ProcessAddStrategy {

    @Override
    public void addProcess(ArrayBlockingQueue<Process> queue, Process process) {
        if (queue.remainingCapacity() != 0) {
            queue.add(process);
            process.run();
        } else {
            process.remove();
            System.out.println("\nTask Manager is currently full.\n");
        }
    }
}
