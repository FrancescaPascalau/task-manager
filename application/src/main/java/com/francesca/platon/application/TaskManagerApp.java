package com.francesca.platon.application;

import com.francesca.platon.application.exceptions.ProcessNotFoundException;
import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.Process;
import com.francesca.platon.domain.model.TaskManager;
import com.francesca.platon.domain.service.ProcessService;
import com.francesca.platon.domain.service.TaskManagerService;
import org.apache.commons.lang3.EnumUtils;

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

    public static void addNewProcess(TaskManagerService service, Scanner scanner) {
        System.out.print("Select add process approach: \n" +
                "1. FIFO;\n" +
                "2. PRIORITY;\n" +
                "3. DEFAULT;\n");
        int addStrategy = scanner.nextInt();

        if (addStrategy <= 0 || addStrategy > 3)
            System.out.println("Not a valid option");
        else
            addProcess(service, scanner, addStrategy);
    }

    private static void addProcess(TaskManagerService service,
                                   Scanner scanner,
                                   int addStrategy) {
        System.out.print("Select process priority: \n" +
                "1. LOW;\n" +
                "2. MEDIUM;\n" +
                "3. HIGH;\n");
        int optionSelected = scanner.nextInt();

        if (addStrategy <= 0 || addStrategy > 3)
            System.out.println("Not a valid option");
        else {
            Process process = null;
            switch (optionSelected) {
                case 1 -> process = ProcessService.createProcess(Priority.LOW);
                case 2 -> process = ProcessService.createProcess(Priority.MEDIUM);
                case 3 -> process = ProcessService.createProcess(Priority.HIGH);
                default -> System.out.println("Not a valid option");
            }
            service.addProcess(process, addStrategy);
        }
    }

    public static void showProcesses(TaskManager taskManager, Scanner scanner) {
        System.out.print("\nSelect list running processes filter: \n" +
                "1. By time;\n" +
                "2. By priority;\n" +
                "3. By Pid;\n");
        int showStrategy = scanner.nextInt();

        if (showStrategy <= 0 || showStrategy > 3)
            System.out.println("Not a valid option");
        else {
            List<Process> processes;
            switch (showStrategy) {
                case 2 -> processes = taskManager.getProcessesByPriority();
                case 3 -> processes = taskManager.getProcessesByPid();
                default -> processes = taskManager.getProcesses();
            }

            if (processes.isEmpty())
                System.out.println("There are no running processes to show!");
            else {
                for (Process process : processes)
                    System.out.println(process);
            }
        }
    }

    public static void killProcesses(TaskManager taskManager,
                                      TaskManagerService service,
                                      Scanner scanner) {
        System.out.print("\nSelect option: \n" +
                "1. Kill one specific process;\n" +
                "2. Kill all processes with specific priority;\n" +
                "3. Kill all running processes;\n");
        int killStrategy = scanner.nextInt();

        if (killStrategy <= 0 || killStrategy > 3)
            System.out.println("Not a valid option");
        else {
            var processes = taskManager.getProcesses();
            if (processes.isEmpty())
                System.out.println("There are no running processes to kill!");
            else {
                switch (killStrategy) {
                    case 1 -> killProcessByPid(service, scanner, processes);
                    case 2 -> killProcessesByPriority(service, scanner, processes);
                    case 3 -> service.removeAllProcesses();
                    default -> System.out.println("Not a valid option");
                }
            }
        }
    }

    private static void killProcessByPid(TaskManagerService service,
                                         Scanner scanner,
                                         List<Process> processes) {
        System.out.println("\nIntroduce process PID: \nPID:");
        int pid = scanner.nextInt();

        processes.stream()
                .filter(it -> it.getPid() == pid)
                .findFirst()
                .ifPresentOrElse(service::removeProcess, ProcessNotFoundException::new);
    }

    private static void killProcessesByPriority(TaskManagerService service,
                                                Scanner scanner,
                                                List<Process> processes) {
        System.out.println("\nIntroduce priority: ");
        String priority = scanner.next().toUpperCase(Locale.ROOT);

        if (EnumUtils.isValidEnum(Priority.class, priority)) {
            var processesToKill = processes.stream()
                    .filter(it -> it.getPriority().name().equals(priority))
                    .collect(Collectors.toList());

            if (processesToKill.isEmpty()) {
                System.out.println("There are no running processes with " + priority + " priority");
            } else {
                for (Process process : processesToKill)
                    service.removeProcess(process);
            }
        } else
            System.out.println(priority + " is not a valid priority\n");
    }
}
