package com.francesca.platon.domain.model;

import java.time.LocalDateTime;
import java.util.Random;

public class Process {

    private static final int BOUND = 10000;

    private final int pid;
    private final Priority priority;
    private final LocalDateTime createdAt;

    public Process(Priority priority) {
        this.pid = new Random().nextInt(BOUND);
        this.priority = priority;
        this.createdAt = LocalDateTime.now();
    }

    public void run(){
        System.out.println("Process " + this.pid + " is running...");
    }

    public void remove() {
        System.out.println("Process " + this.pid + " could not be started. Removing it from the poll.");
    }

    public void skip() {
        System.out.println("Skipping process " + this.pid + "\n");
    }

    public void kill(Integer pid) {
        System.out.println("\nProcess " + pid + " stopped\n");
    }

    public int getPid() {
        return pid;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Process: " +
                "pid = '" + pid + '\'' +
                ", priority = " + priority +
                ", createdAt = " + createdAt +
                ';';
    }
}
