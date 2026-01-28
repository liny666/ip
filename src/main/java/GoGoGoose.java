import java.util.Scanner;

public class GoGoGoose {
    private static final String DIVIDER_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        String[] tasks = new String[100];
        int taskCount = 0;

        System.out.println(DIVIDER_LINE);
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER_LINE);

        while (true) {
            String input = inputScanner.nextLine();

            System.out.println(DIVIDER_LINE);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(DIVIDER_LINE);
                break;
            }

            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(DIVIDER_LINE);
                continue;
            }

            tasks[taskCount] = input;
            taskCount++;

            System.out.println("added: " + input);
            System.out.println(DIVIDER_LINE);
        }

        inputScanner.close();
    }
}
