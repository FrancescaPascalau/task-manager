package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;

import java.util.concurrent.ArrayBlockingQueue;

public class ProcessAddFifo implements ProcessAddStrategy {

    @Override
    public void addProcess(ArrayBlockingQueue<Process> queue, Process process) {
        if (queue.remainingCapacity() == 0) {
            System.out.println("Queue is full \nRemoving oldest process to make space for new ones...\n");
            queue.poll();
        }
            queue.add(process);
            process.run();
    }
}
