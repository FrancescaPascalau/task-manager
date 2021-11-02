package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Process;
import com.francesca.platon.domain.model.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskManagerServiceShould {

    private static final int GIVEN_CAPACITY = 2;
    private static final int FIFO_STRATEGY = 1;
    private static final int PRIORITY_STRATEGY = 2;
    private static final int DEFAULT_STRATEGY = 3;

    private final ArrayBlockingQueue<Process> queue = new ArrayBlockingQueue<>(GIVEN_CAPACITY);

    @Mock
    private TaskManager taskManager;

    @InjectMocks
    private TaskManagerService service;

    @BeforeEach
    public void init() {
        queue.clear();
    }

    @Test
    void add_a_process_to_the_queue_with_default_strategy(){
        var process = mock(Process.class);
        when(taskManager.getQueue()).thenReturn(queue);

        service.addProcess(process, DEFAULT_STRATEGY);

        verify(process).run();
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void add_a_process_to_the_queue_with_fifo_strategy(){
        var process = mock(Process.class);
        when(taskManager.getQueue()).thenReturn(queue);

        service.addProcess(process, FIFO_STRATEGY);

        verify(process).run();
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void add_a_process_to_the_queue_with_priority_strategy(){
        var process = mock(Process.class);
        when(taskManager.getQueue()).thenReturn(queue);

        service.addProcess(process, PRIORITY_STRATEGY);

        verify(process).run();
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void remove_a_process_from_the_queue(){
        var process = mock(Process.class);
        when(taskManager.getQueue()).thenReturn(queue);

        service.removeProcess(process);

        verify(process).kill();
        assertThat(queue.size()).isZero();
    }

    @Test
    void remove_all_processes_from_the_queue(){
        var process1 = mock(Process.class);
        var process2 = mock(Process.class);
        when(taskManager.getQueue()).thenReturn(queue);

        service.addProcess(process1, DEFAULT_STRATEGY);
        service.addProcess(process2, DEFAULT_STRATEGY);

        service.removeAllProcesses();

        verify(process1).kill();
        verify(process2).kill();
        assertThat(queue.size()).isZero();
    }
}
