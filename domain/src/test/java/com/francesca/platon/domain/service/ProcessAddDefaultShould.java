package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ArrayBlockingQueue;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessAddDefaultShould {

    private static final int CAPACITY = 5;

    @Mock
    private final ArrayBlockingQueue<Process> queue = new ArrayBlockingQueue<>(CAPACITY);

    @InjectMocks
    private ProcessAddDefault processAddDefault;

    @BeforeEach
    public void init() {
        queue.clear();
    }

    @Test
    void add_a_process_on_the_queue_if_there_is_remaining_space() {
        var process = mock(Process.class);
        when(queue.remainingCapacity()).thenReturn(1);

        processAddDefault.addProcess(queue, process);

        verify(queue).add(process);
        verify(process).run();
    }

    @Test
    void do_not_add_a_process_on_the_queue_if_there_is_no_space() {
        var process = mock(Process.class);
        when(queue.remainingCapacity()).thenReturn(0);

        processAddDefault.addProcess(queue, process);

        verify(queue, never()).add(process);
        verify(process).remove();
    }
}