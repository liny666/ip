import java.util.Scanner;

public class GoGoGoose {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";


    private static final String DIVIDER_LINE =
            "____________________________________________________________";

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(DIVIDER_LINE);
    }

    private static int addTask(Task[] tasks, int taskCount, Task task) {
        tasks[taskCount] = task;
        taskCount++;
        printTaskAdded(task, taskCount);
        return taskCount;
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

    private static void handleListCommand(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        System.out.println(DIVIDER_LINE);
    }

    private static void handleMarkCommand(String userInput, Task[] tasks) {
        int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
        tasks[index].markAsDone();

        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[index]);
        System.out.println(DIVIDER_LINE);
    }

    private static int handleTodoCommand(String userInput, Task[] tasks, int taskCount) {
        String description = userInput.substring(COMMAND_TODO.length());
        return addTask(tasks, taskCount, new Todo(description));

    }

    private static void handleUnmarkCommand(String userInput, Task[] tasks) {
        int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
        tasks[index].unmarkAsDone();

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[index]);
        System.out.println(DIVIDER_LINE);
    }

    private static int handleDeadlineCommand(String userInput, Task[] tasks, int taskCount) {
        Deadline task = parseDeadline(userInput);
        return addTask(tasks, taskCount, task);
    }

    private static int handleEventCommand(String userInput, Task[] tasks, int taskCount) {
        String[] parts = userInput
                .substring(COMMAND_EVENT.length())
                .split(" /from | /to ");

        Event task = new Event(parts[0], parts[1], parts[2]);
        return addTask(tasks, taskCount, task);
    }

    private static int handleCommand(String userInput, Task[] tasks, int taskCount) {
        if (userInput.equals(COMMAND_LIST)) {
            handleListCommand(tasks, taskCount);
            return taskCount;
        } else if (userInput.startsWith(COMMAND_MARK)) {
            handleMarkCommand(userInput, tasks);
            return taskCount;
        } else if (userInput.startsWith(COMMAND_UNMARK)) {
            handleUnmarkCommand(userInput, tasks);
            return taskCount;
        } else if (userInput.startsWith(COMMAND_TODO)) {
            return handleTodoCommand(userInput, tasks, taskCount);
        } else if (userInput.startsWith(COMMAND_DEADLINE)) {
            return handleDeadlineCommand(userInput, tasks, taskCount);
        } else if (userInput.startsWith(COMMAND_EVENT)) {
            return handleEventCommand(userInput, tasks, taskCount);
        }
        return taskCount;
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        printWelcomeMessage();

        while (true) {
            String userInput = inputScanner.nextLine().trim();
            System.out.println(DIVIDER_LINE);

            if (userInput.equals(COMMAND_BYE)) {
                printGoodbyeMessage();
                break;
            }

            taskCount = handleCommand(userInput, tasks, taskCount);

        }
        inputScanner.close();
    }
}
