import java.util.Scanner;

public class GoGoGoose {
    private static final String DIVIDER_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(DIVIDER_LINE);
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER_LINE);

        while (true) {
            String userInput = inputScanner.nextLine().trim();
            System.out.println(DIVIDER_LINE);

            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER_LINE);
                break;
            }

            if (userInput.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5);
                tasks[taskCount++] = new Todo(description);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                String description = parts[0];
                String by = parts[1];

                tasks[taskCount++] = new Deadline(description, by);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];

                tasks[taskCount++] = new Event(description, from, to);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(DIVIDER_LINE);
                continue;
            }
        }

        inputScanner.close();
    }
}
