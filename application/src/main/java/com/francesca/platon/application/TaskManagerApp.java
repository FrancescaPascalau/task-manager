package com.francesca.platon.application;

import com.francesca.platon.application.exceptions.ProcessNotFoundException;
import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;
import com.francesca.platon.domain.model.TaskManager;
import com.francesca.platon.domain.service.ProcessService;
import com.francesca.platon.domain.service.TaskManagerService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskManagerApp {

    public static void main(String[] args) {
        // First set here Task Manager's capacity to handle processes
        TaskManager taskManager = new TaskManager(3);
        TaskManagerService service = new TaskManagerService(taskManager);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Available options: \n" +
                "1. Add new process;\n" +
                "2. List all running processes;\n" +
                "3. Kill process/es;\n" +
                "Enter option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> addNewProcess(service, scanner);
            case 2 -> showProcesses(taskManager, service, scanner);
            case 3 -> killProcesses(taskManager, service, scanner);
            default -> System.out.println("Try again");
        }
    }

    private static void addNewProcess(TaskManagerService service, Scanner scanner) {
        System.out.print("Select add process approach: \n" +
                "1. FIFO;\n" +
                "2. PRIORITY;\n" +
                "3. DEFAULT;\n");
        int addStrategy = scanner.nextInt();

        var process1 = ProcessService.createProcess(Priority.HIGH);
        service.addProcess(process1, addStrategy);

        var process2 = ProcessService.createProcess(Priority.MEDIUM);
        service.addProcess(process2, addStrategy);

        var process3 = ProcessService.createProcess(Priority.MEDIUM);
        service.addProcess(process3, addStrategy);

        var process4 = ProcessService.createProcess(Priority.HIGH);
        service.addProcess(process4, addStrategy);
    }

    private static void showProcesses(TaskManager taskManager,
                                      TaskManagerService service,
                                      Scanner scanner) {
        var process1 = ProcessService.createProcess(Priority.HIGH);
        service.addProcess(process1, 1);

        var process2 = ProcessService.createProcess(Priority.MEDIUM);
        service.addProcess(process2, 1);

        var process3 = ProcessService.createProcess(Priority.LOW);
        service.addProcess(process3, 1);

        System.out.print("\nSelect list running processes filter: \n" +
                "1. By time;\n" +
                "2. By priority;\n" +
                "3. By Pid;\n");
        int showStrategy = scanner.nextInt();

        List<Process> runningProcesses;
        switch (showStrategy) {
            case 2 -> runningProcesses = taskManager.getProcessesByPriority();
            case 3 -> runningProcesses = taskManager.getProcessesByPid();
            default -> runningProcesses = taskManager.getProcesses();
        }

        for (Process process : runningProcesses)
            System.out.println(process);
    }

    private static void killProcesses(TaskManager taskManager,
                                      TaskManagerService service,
                                      Scanner scanner) {
        var process1 = ProcessService.createProcess(Priority.MEDIUM);
        service.addProcess(process1, 1);

        var process2 = ProcessService.createProcess(Priority.LOW);
        service.addProcess(process2, 1);

        var process3 = ProcessService.createProcess(Priority.LOW);
        service.addProcess(process3, 1);

        System.out.print("\nSelect option: \n" +
                "1. Kill one specific process;\n" +
                "2. Kill all processes with specific priority;\n" +
                "3. Kill all running processes;\n");
        int killStrategy = scanner.nextInt();

        var processes = taskManager.getProcesses();
        switch (killStrategy) {
            case 1 -> {
                System.out.println("\nIntroduce process PID: \nPID:");
                int pid = scanner.nextInt();

                processes.stream()
                        .filter(it -> it.getPid() == pid)
                        .findFirst()
                        .ifPresentOrElse(service::removeProcess, ProcessNotFoundException::new);
            }
            case 2 -> {
                System.out.println("\nIntroduce priority: \nPriority = ");
                String priority = scanner.next().toUpperCase(Locale.ROOT);

               var processesToKill = processes.stream()
                        .filter(it -> it.getPriority().name().equals(priority))
                        .collect(Collectors.toList());

               for (Process process : processesToKill)
                   service.removeProcess(process);
            }
            default -> service.removeAllProcesses();
        }
    }
}
