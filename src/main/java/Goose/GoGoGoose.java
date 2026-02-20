package Goose;

import java.util.Scanner;
import java.util.ArrayList;

public class GoGoGoose {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";


    private static final String DIVIDER_LINE =
            "____________________________________________________________";

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(DIVIDER_LINE);
    }

    private static int addTask(ArrayList<Task> tasks, Task task) {
        tasks.add(task);
        printTaskAdded(task, tasks.size());
        return tasks.size();
    }

    private static Deadline parseDeadline(String userInput) {
        String[] parts = userInput
                .substring(COMMAND_DEADLINE.length())
                .split(" /by ", 2);

        return new Deadline(parts[0], parts[1]);
    }

    private static void printWelcomeMessage() {
        System.out.println(DIVIDER_LINE);
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER_LINE);
    }

    private static void printGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER_LINE);
    }

    private static void handleListCommand(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(DIVIDER_LINE);
    }

    private static void handleMarkCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2) {
            throw new GooseException("Quack!! Task number missing. Example: mark 2");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new GooseException("Quack!! Task number invalid or does not exist.");
            }

            tasks.get(index).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(index));
            System.out.println(DIVIDER_LINE);

        } catch (NumberFormatException e) {
            throw new GooseException("Quack!! Please provide a valid number.");
        }
    }

    private static int handleTodoCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new GooseException("Quack!! todo cannot be empty and needs a description.");
        }
        return addTask(tasks, new Todo(description));
    }

    private static void handleUnmarkCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2) {
            throw new GooseException("Quack!! Task number missing.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new GooseException("Quack!! Task number invalid or does not exist.");
            }

            tasks.get(index).unmarkAsDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(index));
            System.out.println(DIVIDER_LINE);

        } catch (NumberFormatException e) {
            throw new GooseException("Quack!! Please provide a valid number.");
        }
    }

    private static int handleDeadlineCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String remaining = userInput.substring(COMMAND_DEADLINE.length()).trim();

        if (remaining.isEmpty()) {
            throw new GooseException("Quack!! Deadline description and /by date cannot be empty.");
        }

        String[] parts = remaining.split("/by", 2);

        String description = parts[0].trim();
        String by = (parts.length > 1) ? parts[1].trim() : "";

        if (description.isEmpty() && by.isEmpty()) {
            throw new GooseException("Quack!! Deadline description and /by date cannot be empty.");
        }
        else if (description.isEmpty()) {
            throw new GooseException("Quack!! Deadline description cannot be empty.");
        }
        else if (by.isEmpty()) {
            throw new GooseException("Quack!! Deadline /by date cannot be empty.");
        }

        Deadline task = new Deadline(description, by);
        return addTask(tasks, task);
    }

    private static int handleEventCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String remaining = userInput.substring(COMMAND_EVENT.length()).trim();
        if (remaining.isEmpty() || remaining.startsWith("/from") || remaining.startsWith("/to")) {
            throw new GooseException("Quack!! Event description cannot be empty.");
        }

        String[] firstSplit = remaining.split(" /from ", 2);
        String description = firstSplit[0].trim();

        if (description.isEmpty()) {
            throw new GooseException("Quack!! Event description cannot be empty.");
        }

        if (firstSplit.length < 2 || firstSplit[1].trim().isEmpty()) {
            throw new GooseException("Quack!! Event start time after /from cannot be empty.");
        }

        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        String from = secondSplit[0].trim();

        if (from.isEmpty()) {
            throw new GooseException("Quack!! Event start time after /from cannot be empty.");
        }

        if (secondSplit.length < 2 || secondSplit[1].trim().isEmpty()) {
            throw new GooseException("Quack!! Event end time after /to cannot be empty.");
        }

        String to = secondSplit[1].trim();

        Event task = new Event(description, from, to);
        return addTask(tasks, task);
    }

    private static void handleDeleteCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2) {
            throw new GooseException("Quack!! Task number missing. Example: delete 2");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new GooseException("Quack!! Task number invalid or does not exist.");
            }

            Task removed = tasks.remove(index);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removed);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(DIVIDER_LINE);

        } catch (NumberFormatException e) {
            throw new GooseException("Quack!! Please provide a valid number.");
        }
    }

    private static int handleCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        if (userInput.equals(COMMAND_LIST)) {
            handleListCommand(tasks);
            return tasks.size();
        } else if (userInput.startsWith(COMMAND_MARK)) {
            handleMarkCommand(userInput, tasks);
            return tasks.size();
        } else if (userInput.startsWith(COMMAND_UNMARK)) {
            handleUnmarkCommand(userInput, tasks);
            return tasks.size();
        } else if (userInput.startsWith(COMMAND_TODO)) {
            return handleTodoCommand(userInput, tasks);
        } else if (userInput.startsWith(COMMAND_DEADLINE)) {
            return handleDeadlineCommand(userInput, tasks);
        } else if (userInput.startsWith(COMMAND_EVENT)) {
            return handleEventCommand(userInput, tasks);
        }else if (userInput.startsWith(COMMAND_DELETE)) {
                handleDeleteCommand(userInput, tasks);
                return tasks.size();
        }

        throw new GooseException("Quack!! Type something that I can understand.");
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<>();

        printWelcomeMessage();

        while (true) {
            String userInput = inputScanner.nextLine().trim();
            System.out.println(DIVIDER_LINE);

            if (userInput.equals(COMMAND_BYE)) {
                printGoodbyeMessage();
                break;
            }

            try {
                handleCommand(userInput, tasks);
            } catch (GooseException e) {
                System.out.println(e.getMessage());
                System.out.println(DIVIDER_LINE);
            } catch (Exception e) {
                System.out.println("Quack!! An unexpected error occurred. Check your input.");
                System.out.println(DIVIDER_LINE);
            }
        }
        inputScanner.close();
    }
}
