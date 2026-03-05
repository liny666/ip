package Goose;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all interactions with the user, including reading input and printing output.
 */
public class Ui {
    private static final String DIVIDER_LINE =
            "____________________________________________________________";
    private Scanner scanner;

    /** Creates a new Ui instance and initialises the input scanner. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Prints the divider line. */
    public void showLine() {
        System.out.println(DIVIDER_LINE);
    }

    /** Prints the welcome message when the application starts. */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        showLine();
    }

    /** Prints the goodbye message when the user exits. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Prints a warning that tasks could not be loaded from file. */
    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from file. Starting with empty list.");
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The trimmed input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints a confirmation message after a task is added.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints all tasks currently in the list.
     *
     * @param tasks The TaskList to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints a confirmation message after a task is marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Prints a confirmation message after a task is marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Prints a confirmation message after a task is deleted.
     *
     * @param task      The task that was removed.
     * @param taskCount The total number of tasks remaining after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints all tasks that matched a find keyword search.
     *
     * @param matchingTasks The list of tasks matching the search keyword.
     */
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

    /** Closes the input scanner. */
    public void close() {
        scanner.close();
    }
}
