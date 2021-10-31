package com.francesca.platon.domain.model;

import java.util.concurrent.ArrayBlockingQueue;

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

    public void listAll() {
        for (Process process : this.queue) {
            System.out.println(process);
        }
    }
}
