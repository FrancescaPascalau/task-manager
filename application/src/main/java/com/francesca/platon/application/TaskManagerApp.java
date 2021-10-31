package com.francesca.platon.application;

import com.francesca.platon.domain.model.Priority;
import com.francesca.platon.domain.model.TaskManager;
import com.francesca.platon.domain.service.ProcessService;
import com.francesca.platon.domain.service.TaskManagerService;

import java.util.Scanner;

public class TaskManagerApp {

    public static void main(String[] args) {
        // First set here Task Manager's capacity to handle processes
        TaskManager taskManager = new TaskManager(3);
        TaskManagerService service = new TaskManagerService(taskManager);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Available options: \n" +
                "1. Add new process;\n" +
                "2. List all running processes;\n" +
                "3. Kill process;\n" +
                "Enter option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1 -> {
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
            case 2 -> {
                var process1 = ProcessService.createProcess(Priority.HIGH);
                service.addProcess(process1, 1);

                var process2 = ProcessService.createProcess(Priority.MEDIUM);
                service.addProcess(process2, 1);

                var process3 = ProcessService.createProcess(Priority.MEDIUM);
                service.addProcess(process3, 1);

                var process4 = ProcessService.createProcess(Priority.HIGH);
                service.addProcess(process4, 1);

                taskManager.listAll();
            }
            case 3 -> {
                //TODO - Implement kill() functionality
            }
            default -> System.out.println("Try again");
        }
    }
}
