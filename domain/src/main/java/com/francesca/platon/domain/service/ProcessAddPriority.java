package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;

import java.util.concurrent.ArrayBlockingQueue;

public class ProcessAddPriority implements ProcessAddStrategy {

    @Override
    public void addProcess(ArrayBlockingQueue<Process> queue, Process process) {
        if (queue.remainingCapacity() == 0) {
            System.out.println("Queue is full \nEvaluating process priority...\n");
            handleProcessByPriority(queue, process, process.getPriority());
        }

        if (queue.remainingCapacity() != 0) {
            queue.add(process);
            process.run();
        }
    }

    private void handleProcessByPriority(ArrayBlockingQueue<Process> queue,
                                         Process process,
                                         Priority priority) {
        switch (priority) {
            case LOW -> process.skip();
            case MEDIUM -> getOlderProcessByPriority(queue, Priority.LOW);
            case HIGH -> queue.stream()
                    .filter(it -> it.getPriority().equals(Priority.LOW))
                    .findFirst()
                    .ifPresentOrElse(olderProcess -> {
                        var removed = queue.removeIf(it -> it.getPid() == olderProcess.getPid());
                        isProcessRemoved(olderProcess, removed);
                        }, () -> getOlderProcessByPriority(queue, Priority.MEDIUM)
                    );
        }
    }

    private void getOlderProcessByPriority(ArrayBlockingQueue<Process> queue, Priority priority) {
        queue.stream()
                .filter(it -> it.getPriority().equals(priority))
                .findFirst()
                .ifPresent(process -> {
                        var removed = queue.removeIf(it -> it.getPid() == process.getPid());
                        isProcessRemoved(process, removed);
                });
    }

    private void isProcessRemoved(Process process, boolean removed) {
        if (removed)
            System.out.println("Process with lower priority removed (PID = " + process.getPid() + ")");
    }
}
