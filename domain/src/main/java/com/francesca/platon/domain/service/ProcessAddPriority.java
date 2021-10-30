package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;

import java.util.concurrent.ArrayBlockingQueue;

public class ProcessAddPriority implements ProcessAddStrategy {

    @Override
    public void addProcess(ArrayBlockingQueue<Process> queue, Process process) {
        if (queue.remainingCapacity() == 0) {
            System.out.println("Queue is full \nEvaluating process priority...\n");
            checkProcessPriority(queue, process, process.getPriority());
        }

        if (queue.remainingCapacity() != 0) {
            queue.add(process);
            process.run();
        }
    }

    private void checkProcessPriority(ArrayBlockingQueue<Process> queue,
                                      Process process,
                                      Priority priority) {
        switch (priority) {
            case LOW -> process.skip();
            case MEDIUM -> getOldestProcessByPriority(queue, Priority.LOW);
            case HIGH -> queue.stream()
                    .filter(it -> it.getPriority().equals(Priority.LOW))
                    .findFirst()
                    .ifPresentOrElse(oldestProcess ->
                            queue.removeIf(it -> it.getPid() == oldestProcess.getPid()),
                            () -> getOldestProcessByPriority(queue, Priority.MEDIUM)
                    );
        }
    }

    private void getOldestProcessByPriority(ArrayBlockingQueue<Process> queue, Priority priority) {
        queue.stream()
                .filter(it -> it.getPriority().equals(priority))
                .findFirst()
                .ifPresent(lowPriorityProcess ->
                        queue.removeIf(it -> it.getPid() == lowPriorityProcess.getPid())
                );
    }
}
