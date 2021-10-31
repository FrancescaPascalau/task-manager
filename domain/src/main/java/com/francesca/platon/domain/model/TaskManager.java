package com.francesca.platon.domain.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class TaskManager {

    private final ArrayBlockingQueue<Process> queue;
    private final int capacity;

    public TaskManager(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.capacity = capacity;
    }

    public ArrayBlockingQueue<Process> getQueue() {
        return queue;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Process> getProcesses() {
        return getProcessesList();
    }

    public List<Process> getProcessesByPriority() {
        return getProcessesList()
                .stream()
                .sorted(Comparator.comparing(Process::getPriority))
                .collect(Collectors.toList());
    }

    public List<Process> getProcessesByPid() {
        return getProcessesList()
                .stream()
                .sorted(Comparator.comparing(Process::getPid))
                .collect(Collectors.toList());
    }

    private List<Process> getProcessesList() {
        return Arrays.stream(queue.toArray())
                .map(Process.class::cast)
                .collect(Collectors.toList());
    }
}
