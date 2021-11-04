package com.francesca.platon.application;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.TaskManager;
import com.francesca.platon.domain.service.TaskManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskManagerAppShould {

    private static final int CAPACITY = 5;

    private final TaskManager taskManager = new TaskManager(CAPACITY);
    private final TaskManagerService service = new TaskManagerService(taskManager);

    // Add new process flow
    @Test
    void add_processes_up_to_max_capacity() {
        String userInput1 = "3 1";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner2);
        Scanner scanner3 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner3);
        Scanner scanner4 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner4);
        Scanner scanner5 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner5);
        Scanner scanner6 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner6);

        assertThat(taskManager.getQueue().size()).isEqualTo(CAPACITY);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-12", "0", "4", "3297"})
    void do_not_add_process_if_strategy_not_valid(String option) {
        Scanner scanner = getScanner(option);

        TaskManagerApp.addNewProcess(service, scanner);

        assertThat(taskManager.getQueue().size()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 -1", "1 0", "1 5"})
    void do_not_add_process_if_process_priority_option_not_valid(String option) {
        Scanner scanner = getScanner(option);

        TaskManagerApp.addNewProcess(service, scanner);

        assertThat(taskManager.getQueue().size()).isEqualTo(0);
    }

    @Test
    void add_a_new_LOW_process_on_the_queue_with_default_strategy() {
        String userInput = "3 1";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.LOW);
    }

    @Test
    void add_a_new_MEDIUM_process_on_the_queue_with_default_strategy() {
        String userInput = "3 2";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.MEDIUM);
    }

    @Test
    void add_a_new_HIGH_process_on_the_queue_with_default_strategy() {
        String userInput = "3 3";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.HIGH);
    }

    @Test
    void add_a_new_LOW_process_on_the_queue_with_FIFO_strategy() {
        String userInput = "1 1";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.LOW);
    }

    @Test
    void add_a_new_MEDIUM_process_on_the_queue_with_FIFO_strategy() {
        String userInput = "1 2";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.MEDIUM);
    }

    @Test
    void add_a_new_HIGH_process_on_the_queue_with_FIFO_strategy() {
        String userInput = "1 3";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.HIGH);
    }

    @Test
    void add_a_new_LOW_process_on_the_queue_with_priority_strategy() {
        String userInput = "2 1";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.LOW);
    }

    @Test
    void add_a_new_MEDIUM_process_on_the_queue_with_priority_strategy() {
        String userInput = "2 2";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.MEDIUM);
    }

    @Test
    void add_a_new_HIGH_process_on_the_queue_with_priority_strategy() {
        String userInput = "2 3";
        Scanner scanner = getScanner(userInput);

        TaskManagerApp.addNewProcess(service, scanner);

        System.out.println(taskManager.getQueue());

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.HIGH);
    }

    // List running processes flow
    @Test
    void show_running_processes_by_time() {
        String userInput1 = "3 1";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner2);
        Scanner scanner3 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner3);

        String userInput2 = "1";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.showProcesses(taskManager, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(3);
    }

    @Test
    void show_running_processes_by_priority() {
        String userInput1 = "3 3";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner("3 1");
        TaskManagerApp.addNewProcess(service, scanner2);
        Scanner scanner3 = getScanner("3 2");
        TaskManagerApp.addNewProcess(service, scanner3);

        String userInput2 = "2";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.showProcesses(taskManager, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(3);
        assertThat(Objects.requireNonNull(taskManager.getQueue().poll()).getPriority())
                .isEqualTo(Priority.HIGH);
    }

    @Test
    void show_running_processes_by_pid() {
        String userInput1 = "3 2";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner2);
        Scanner scanner3 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner3);

        String userInput2 = "3";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.showProcesses(taskManager, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(3);
    }

    // Kill process flow
    @Test
    void do_not_kill_running_process_if_option_not_valid() {
        String userInput1 = "3 2";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner2);

        String userInput2 = "9737";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.killProcesses(taskManager, service, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(2);
    }

    @Test
    void kill_running_process_by_pid() {
        String userInput1 = "3 3";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner("3 3");
        TaskManagerApp.addNewProcess(service, scanner2);

        var pidToKill = Objects.requireNonNull(taskManager.getQueue().poll()).getPid();

        String userInput2 = "1 " + pidToKill;
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.killProcesses(taskManager, service, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
    }

    @Test
    void kill_running_process_by_priority() {
        String userInput1 = "3 2";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner("3 3");
        TaskManagerApp.addNewProcess(service, scanner2);

        String userInput2 = "2 MEDIUM";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.killProcesses(taskManager, service, scanner4);

        assertThat(taskManager.getQueue().size()).isEqualTo(1);
    }

    @Test
    void kill_all_running_process() {
        String userInput1 = "3 2";
        Scanner scanner1 = getScanner(userInput1);

        TaskManagerApp.addNewProcess(service, scanner1);
        Scanner scanner2 = getScanner(userInput1);
        TaskManagerApp.addNewProcess(service, scanner2);

        String userInput2 = "3";
        Scanner scanner4 = getScanner(userInput2);

        TaskManagerApp.killProcesses(taskManager, service, scanner4);

        assertThat(taskManager.getQueue().size()).isZero();
    }

    private Scanner getScanner(String userInput) {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        return scanner;
    }
}
