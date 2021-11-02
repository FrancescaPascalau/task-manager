package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;
import com.francesca.platon.domain.model.TaskManager;

import java.util.concurrent.ArrayBlockingQueue;

public class TaskManagerService {

    private final TaskManager taskManager;

    public TaskManagerService(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void addProcess(Process process, int strategy) {
        ArrayBlockingQueue<Process> queue = taskManager.getQueue();

        try {
            add(process, queue, strategy);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void add(Process process,
                     ArrayBlockingQueue<Process> queue,
                     int strategy) {
        switch (strategy) {
            case 1 -> new ProcessAddFifo().addProcess(queue, process);
            case 2 -> new ProcessAddPriority().addProcess(queue, process);
            default -> new ProcessAddDefault().addProcess(queue, process);
        }
    }

    public void removeProcess(Process process) {
        ArrayBlockingQueue<Process> queue = taskManager.getQueue();

        try {
            process.kill();
            queue.removeIf(it -> it.getPid() == process.getPid());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        System.out.println("Running processes: " + queue);
    }

    public void removeAllProcesses() {
        ArrayBlockingQueue<Process> queue = taskManager.getQueue();

        for (Process process : queue)
            process.kill();

        try {
            queue.clear();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("Task Manager has no running processes at the moment: " + queue);
    }
}
