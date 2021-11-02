package com.francesca.platon.domain.service;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessAddPriorityShould {

    private static final int CAPACITY = 2;

    private final ArrayBlockingQueue<Process> queue = new ArrayBlockingQueue<>(CAPACITY);

    @InjectMocks
    private ProcessAddPriority processAddPriority;

    @BeforeEach
    public void init() {
        queue.clear();
    }

    @Test
    void add_a_process_on_the_queue_if_there_is_remaining_space() {
        var process = mock(Process.class);

        processAddPriority.addProcess(queue, process);

        verify(process).run();
        assertThat(queue.size()).isEqualTo(1);
    }

    @Test
    void do_not_add_a_process_on_the_queue_if_there_is_no_space_and_no_lower_priority() {
        var process = new Process(Priority.LOW);

        queue.add(new Process(Priority.MEDIUM));
        queue.add(new Process(Priority.HIGH));

        processAddPriority.addProcess(queue, process);

        assertThat(queue.size()).isEqualTo(CAPACITY);
    }

    @ParameterizedTest
    @EnumSource(
            value = Priority.class,
            names = "LOW",
            mode = EnumSource.Mode.EXCLUDE
    )
    void remove_LOW_priority_process_if_the_queue_is_full(Priority priority) {
        var process1 = new Process(Priority.LOW);
        var process2 = new Process(Priority.MEDIUM);
        var process3 = new Process(priority);

        queue.add(process1);
        queue.add(process2);

        processAddPriority.addProcess(queue, process3);

        assertThat(queue.size()).isEqualTo(2);
        assertThat(queue.containsAll(List.of(process2, process3))).isTrue();
    }

    @Test
    void remove_MEDIUM_priority_process_if_the_queue_is_full() {
        var process1 = new Process(Priority.HIGH);
        var process2 = new Process(Priority.MEDIUM);
        var process3 = new Process(Priority.HIGH);

        queue.add(process1);
        queue.add(process2);

        processAddPriority.addProcess(queue, process3);

        assertThat(queue.size()).isEqualTo(2);
        assertThat(queue.containsAll(List.of(process1, process3))).isTrue();
    }

    @ParameterizedTest
    @EnumSource(
            value = Priority.class
    )
    void do_not_remove_any_process_if_all_have_same_priority(Priority priority) {
        var process1 = new Process(priority);
        var process2 = new Process(priority);
        var process3 = new Process(priority);

        queue.add(process1);
        queue.add(process2);

        processAddPriority.addProcess(queue, process3);

        assertThat(queue.size()).isEqualTo(2);
        assertThat(queue.containsAll(List.of(process1, process2))).isTrue();
    }
}