import java.util.Scanner;

public class GoGoGoose {
    private static final String DIVIDER_LINE = "____________________________________________________________";

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
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskIndex]);
                }
                System.out.println(DIVIDER_LINE);
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(userInput.split(" ")[1]) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].unmarkAsDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskIndex]);
                }
                System.out.println(DIVIDER_LINE);
                continue;
            }

            tasks[taskCount] = new Task(userInput);
            taskCount++;

            System.out.println("added: " + userInput);
            System.out.println(DIVIDER_LINE);
        }

        inputScanner.close();
    }
}
