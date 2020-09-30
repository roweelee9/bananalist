package ui;

import model.*;
import persistence.Reader;
import persistence.Writer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// BananaList application (Console Version)
public class BananaListAppConsoleVersion {
    private static final String TASKS_FILE = "./data/tasks.json";
    private static final String POINTS_FILE = "./data/points.json";
    private ToDoList toDoList;
    private Scanner input;
    private Reader reader;
    private Writer writer;

    // EFFECTS: runs the BananaList app
    public BananaListAppConsoleVersion() {
        runBananaList();
    }

    // structure referenced from the CPSC210 TellerApp class

    // MODIFIES: this
    // EFFECTS: processes input
    private void runBananaList() {
        reader = new Reader(TASKS_FILE, POINTS_FILE);
        writer = new Writer(TASKS_FILE, POINTS_FILE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        boolean keepRunning = true;
        String command;

        loadToDoList();

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quit();
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("shutting down ...");
    }

    // MODIFIES: this
    // EFFECTS: loads to-do list and points from TASKS_FILE and POINTS_FILE, if it exists or is not empty,
    // otherwise creates an empty to-do list with zero BananaPoints
    private void loadToDoList() {
        try {
            ArrayList<Task> tasks = reader.parseTasks();
            int bananaPoints = reader.parseBananaPoints();
            toDoList = new ToDoList(tasks, bananaPoints);
            System.out.println("Loaded previous saved BananaList!");
        } catch (Exception e) {
            toDoList = new ToDoList(new ArrayList<Task>(), 0);
        }
    }

    // EFFECTS: saves state of to-do list to TASKS_FILE and POINTS_FILE
    private void quit() {
        boolean inProcessOfQuitting = true;

        while (inProcessOfQuitting) {
            System.out.println("save this BananaList? y/n");
            if (input.next().toLowerCase().equals("y")) {
                try {
                    writer.save(toDoList.getTasks(), toDoList.getBananaPoints());
                    System.out.println("BananaList saved :)");
                    inProcessOfQuitting = false;
                } catch (IOException e) {
                    System.out.println("file not found :(");
                }

            } else if (input.next().toLowerCase().equals("n")) {
                inProcessOfQuitting = false;
            } else {
                System.out.println("Invalid command, try again");
            }
        }
    }

    // EFFECTS: displays ToDoList and menu of options
    private void displayMenu() {
        System.out.println("\n-------[BANANALIST v1.0]-------");
        System.out.println("BananaPoints: " + toDoList.getBananaPoints());
        displayList(toDoList.getTasks());
        System.out.println("\ncompleted tasks: " + toDoList.countCompleteTasks());
        System.out.println("urgent tasks: " + toDoList.countUrgentTasks());
        System.out.println("-------------------------------");
        displayOptions();
    }

    // EFFECTS: displays contents of list of tasks
    private void displayList(ArrayList<Task> list) {
        System.out.println("\nTo-Do:");
        int counter = 1;

        for (Task task : list) {
            if (task.isComplete()) {
                if (task.isUrgent()) {
                    System.out.println(counter + " -> " + task.getTask() + "[COMPLETE][URGENT]");
                } else {
                    System.out.println(counter + " -> " + task.getTask() + "[COMPLETE]");
                }
            } else {
                if (task.isUrgent()) {
                    System.out.println(counter + " -> " + task.getTask() + "[URGENT]");
                } else {
                    System.out.println(counter + " -> " + task.getTask());
                }
            }
            counter++;
        }
    }

    // EFFECTS: displays options to user
    private void displayOptions() {
        System.out.println("choose a command:");
        System.out.println("\tc -> show completed tasks");
        System.out.println("\tu -> show urgent tasks");
        System.out.println("\ta -> add a task");
        System.out.println("\td -> delete a task");
        System.out.println("\tmc -> mark a task as complete/incomplete");
        System.out.println("\tmu -> mark a task as urgent/not urgent");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("c")) {
            displayList(toDoList.getCompletedTasks());
        } else if (command.equals("u")) {
            displayList(toDoList.getUrgentTasks());
        } else if (command.equals("a")) {
            addTask();
        } else if (command.equals("d")) {
            deleteTask();
        } else if (command.equals("mc")) {
            toggleComplete();
        } else if (command.equals("mu")) {
            toggleUrgent();
        } else {
            System.out.println("Invalid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the to-do list
    private void addTask() {
        String taskToBeAdded;
        boolean selectingUrgent = true;
        System.out.println("enter your task:");
        taskToBeAdded = input.next();

        while (selectingUrgent) {
            System.out.println("is it urgent? y/n");
            if (input.next().toLowerCase().equals("y")) {
                toDoList.addTask(new Task(taskToBeAdded, false, true));
                selectingUrgent = false;
            } else if (input.next().toLowerCase().equals("n")) {
                toDoList.addTask(new Task(taskToBeAdded, false, false));
                selectingUrgent = false;
            } else {
                System.out.println("Invalid command, try again");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a task from the to-do list
    private void deleteTask() {
        if (toDoList.getTasks().isEmpty()) {
            System.out.println("your to-do list is empty; there are no tasks.json to delete!");
        } else {
            int index = selectTask() - 1;
            toDoList.deleteTask(index);
            System.out.println("task " + index + "deleted");
        }

    }

    // MODIFIES: this
    // EFFECTS: toggles a selected task as complete on to-do list
    private void toggleComplete() {
        int index = selectTask() - 1;
        toDoList.toggleTaskCompleteAtIndex(index);
    }

    private void toggleUrgent() {
        int index = selectTask() - 1;
        toDoList.toggleTaskUrgentAtIndex(index);
    }

    // EFFECTS: returns the number of valid task selected
    private int selectTask() {
        boolean selecting = true;
        int output = 0;
        while (selecting) {
            System.out.println("enter the task number: (1 - " + toDoList.getTasks().size() + ")");
            if (input.hasNextInt()) {
                int inputInt = input.nextInt();
                if (inputInt <= toDoList.getTasks().size() && 0 < inputInt) {
                    output = inputInt;
                    selecting = false;
                } else {
                    System.out.println("there is no task with this number!");
                }
            } else {
                System.out.println("that is not a number!");
            }
        }
        return output;
    }
}

