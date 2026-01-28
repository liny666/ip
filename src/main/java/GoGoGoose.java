import java.util.Scanner;

public class GoGoGoose {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println(LINE);
        System.out.println("Hello! I'm GoGoGoose");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        while (true) {
            String input = in.nextLine();

            System.out.println(LINE);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            System.out.println(input);
            System.out.println(LINE);
        }

        in.close();
    }
}
