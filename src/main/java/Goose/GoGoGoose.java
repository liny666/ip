package Goose;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;


public class GoGoGoose {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String FILE_PATH = "./data/goose.txt";
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
        saveTasksToFile(tasks);
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

    private static int parseTaskIndex(String userInput, ArrayList<Task> tasks, String commandName) throws GooseException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2) {
            throw new GooseException("Quack!! Task number missing. Example: " + commandName + " 2");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new GooseException("Quack!! Task number invalid or does not exist.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new GooseException("Quack!! Please provide a valid number.");
        }
    }

    private static void handleMarkCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        int index = parseTaskIndex(userInput, tasks, COMMAND_MARK);  // parse + validate
        Task task = tasks.get(index);
        task.markAsDone();
        saveTasksToFile(tasks);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(DIVIDER_LINE);
    }

    private static void handleUnmarkCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        int index = parseTaskIndex(userInput, tasks, COMMAND_UNMARK); // parse + validate
        Task task = tasks.get(index);
        task.unmarkAsDone(); // unmark task
        saveTasksToFile(tasks);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(DIVIDER_LINE);
    }

    private static int handleTodoCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new GooseException("Quack!! todo cannot be empty and needs a description.");
        }
        return addTask(tasks, new Todo(description));
    }

    private static String[] parseDeadlineInput(String userInput) throws GooseException {
        String remaining = userInput.substring(COMMAND_DEADLINE.length()).trim();

        if (remaining.isEmpty()) {
            throw new GooseException("Quack!! Deadline description and /by date cannot be empty.");
        }

        String[] parts = remaining.split("/by", 2);
        String description = parts[0].trim();
        String by = (parts.length > 1) ? parts[1].trim() : "";

        if (description.isEmpty() && by.isEmpty()) {
            throw new GooseException("Quack!! Deadline description and /by date cannot be empty.");
        } else if (description.isEmpty()) {
            throw new GooseException("Quack!! Deadline description cannot be empty.");
        } else if (by.isEmpty()) {
            throw new GooseException("Quack!! Deadline /by date cannot be empty.");
        }

        return new String[]{description, by};
    }

    private static int handleDeadlineCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String[] deadlineData = parseDeadlineInput(userInput);  // parse + validate
        Deadline deadlineTask = new Deadline(deadlineData[0], deadlineData[1]); // create object
        return addTask(tasks, deadlineTask); // add task
    }

    private static String[] parseEventInput(String userInput) throws GooseException {
        String remaining = userInput.substring(COMMAND_EVENT.length()).trim();

        if (remaining.isEmpty() || remaining.startsWith("/from") || remaining.startsWith("/to")) {
            throw new GooseException("Quack!! Event description cannot be empty.");
        }

        String[] firstSplit = remaining.split(" /from ", 2);
        String description = firstSplit[0].trim();

        if (description.isEmpty()) throw new GooseException("Quack!! Event description cannot be empty.");

        if (firstSplit.length < 2 || firstSplit[1].trim().isEmpty()) {
            throw new GooseException("Quack!! Event start time after /from cannot be empty.");
        }

        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        String from = secondSplit[0].trim();
        if (from.isEmpty()) throw new GooseException("Quack!! Event start time after /from cannot be empty.");

        if (secondSplit.length < 2 || secondSplit[1].trim().isEmpty()) {
            throw new GooseException("Quack!! Event end time after /to cannot be empty.");
        }

        String to = secondSplit[1].trim();
        return new String[]{description, from, to};
    }

    private static Event createEvent(String[] eventData) {
        return new Event(eventData[0], eventData[1], eventData[2]);
    }

    private static int handleEventCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        String[] eventData = parseEventInput(userInput);
        Event eventTask = createEvent(eventData);
        return addTask(tasks, eventTask);
    }

    private static void handleDeleteCommand(String userInput, ArrayList<Task> tasks) throws GooseException {
        int index = parseTaskIndex(userInput, tasks, COMMAND_DELETE); // parse + validate
        Task removedTask = tasks.remove(index); // remove task
        saveTasksToFile(tasks);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(DIVIDER_LINE);
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

    private static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File taskFile = new File(FILE_PATH);
            File taskFolder = taskFile.getParentFile();
            if (!taskFolder.exists()) taskFolder.mkdirs();
            if (!taskFile.exists()) taskFile.createNewFile();

            Scanner fileScanner = new Scanner(taskFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.markAsDone();
                    tasks.add(todo);
                    break;
                case "D":
                    String by = parts.length > 3 ? parts[3] : "";
                    Deadline deadline = new Deadline(description, by);
                    if (isDone) deadline.markAsDone();
                    tasks.add(deadline);
                    break;
                case "E":
                    String fromTo = parts.length > 3 ? parts[3] : "";
                    String[] fromToSplit = fromTo.split(" to ");
                    String from = fromToSplit.length > 0 ? fromToSplit[0] : "";
                    String to = fromToSplit.length > 1 ? fromToSplit[1] : "";
                    Event event = new Event(description, from, to);
                    if (isDone) event.markAsDone();
                    tasks.add(event);
                    break;
                default:
                    System.out.println("Warning: Unknown task type in file: " + line);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Warning: Could not load tasks from file. Starting with empty list.");
        }
        return tasks;
    }

    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File taskFile = new File(FILE_PATH);
            File taskFolder = taskFile.getParentFile();
            if (!taskFolder.exists()) taskFolder.mkdirs();

            PrintWriter writer = new PrintWriter(taskFile);
            for (Task t : tasks) {
                String line = "";
                if (t instanceof Todo) {
                    line = "T | " + (t.getStatusIcon().equals("X") ? "1" : "0") + " | " + ((Todo) t).getDescription();
                } else if (t instanceof Deadline) {
                    line = "D | " + (t.getStatusIcon().equals("X") ? "1" : "0") + " | "
                            + ((Deadline) t).getDescription() + " | " + ((Deadline) t).getBy();
                } else if (t instanceof Event) {
                    line = "E | " + (t.getStatusIcon().equals("X") ? "1" : "0") + " | "
                            + ((Event) t).getDescription() + " | " + ((Event) t).getFrom() + " to " + ((Event) t).getTo();
                }
                writer.println(line);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Warning: Could not save tasks to file.");
        }
    }


    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasksFromFile();

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
