package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;

import java.util.concurrent.ArrayBlockingQueue;

public interface ProcessAddStrategy {

    void addProcess(ArrayBlockingQueue<Process> queue, Process process);
}
