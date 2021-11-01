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

        int optionSelected;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("\nAvailable options: \n" +
                    "1. Add new process;\n" +
                    "2. List all running processes;\n" +
                    "3. Kill process/es;\n" +
                    "(Press 0 to exit)\n" +
                    "Enter option: ");
            optionSelected = scanner.nextInt();

            switch (optionSelected) {
                case 1 -> addNewProcess(service, scanner);
                case 2 -> showProcesses(taskManager, scanner);
                case 3 -> killProcesses(taskManager, service, scanner);
                case 0 -> {
                    System.out.println("Closing Task Manager");
                    taskManager.getQueue().clear();
                }
                default -> System.out.println("Not a valid option");
            }
        } while(optionSelected != 0);
    }

    private static void addNewProcess(TaskManagerService service, Scanner scanner) {
        System.out.print("Select add process approach: \n" +
                "1. FIFO;\n" +
                "2. PRIORITY;\n" +
                "3. DEFAULT;\n");
        int addStrategy = scanner.nextInt();

        System.out.print("Select process priority: \n" +
                "1. LOW;\n" +
                "2. MEDIUM;\n" +
                "3. HIGH;\n");
        int optionSelected = scanner.nextInt();

        Process process = null;
        switch (optionSelected) {
            case 1 -> process = ProcessService.createProcess(Priority.LOW);
            case 2 -> process = ProcessService.createProcess(Priority.MEDIUM);
            case 3 -> process = ProcessService.createProcess(Priority.HIGH);
            default -> System.out.println("Not a valid option");
        }
        service.addProcess(process, addStrategy);
    }

    private static void showProcesses(TaskManager taskManager, Scanner scanner) {
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
