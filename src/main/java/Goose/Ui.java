package Goose;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String DIVIDER_LINE =
            "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println(DIVIDER_LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from file. Starting with empty list.");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showMatchingTasks(ArrayList<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i));
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
