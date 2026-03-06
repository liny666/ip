package Goose;

/**
 * Parses raw user input strings into executable {@link Command} objects.
 */
public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";

    /**
     * Parses the given user input and returns the corresponding Command.
     *
     * @param userInput The raw input string from the user.
     * @return The Command corresponding to the input.
     * @throws GooseException If the input does not match any known command.
     */
    public static Command parse(String userInput) throws GooseException {
        if (userInput.equals(COMMAND_BYE)) {
            return new ExitCommand();
        } else if (userInput.equals(COMMAND_LIST)) {
            return new ListCommand();
        } else if (userInput.startsWith(COMMAND_UNMARK + " ")) {
            return parseUnmarkCommand(userInput);
        } else if (userInput.startsWith(COMMAND_MARK + " ")) {
            return parseMarkCommand(userInput);
        } else if (userInput.startsWith(COMMAND_TODO + " ")) {
            return parseTodoCommand(userInput);
        } else if (userInput.startsWith(COMMAND_DEADLINE + " ")) {
            return parseDeadlineCommand(userInput);
        } else if (userInput.startsWith(COMMAND_EVENT + " ")) {
            return parseEventCommand(userInput);
        } else if (userInput.startsWith(COMMAND_DELETE + " ")) {
            return parseDeleteCommand(userInput);
        } else if (userInput.startsWith(COMMAND_FIND + " ")) {
            return parseFindCommand(userInput);
        } else {
            throw new GooseException("Quack!! Type something that I can understand.");
        }
    }

    /**
     * Parses a 1-based task number from the input and returns it as a 0-based index.
     *
     * @param userInput   The full user input string.
     * @param commandName The command keyword, used in the error message.
     * @return Zero-based index of the referenced task.
     * @throws GooseException If no number is provided or the number is not valid.
     */
    private static int parseIndex(String userInput, String commandName) throws GooseException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2) {
            throw new GooseException("Quack!! Task number missing. Example: " + commandName + " 2");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new GooseException("Quack!! Please provide a valid number.");
        }
    }

    /**
     * Parses a {@code mark} command and returns a {@link MarkCommand}.
     *
     * @param userInput The full user input string.
     * @return A MarkCommand with the parsed task index.
     * @throws GooseException If the task number is missing or invalid.
     */
    private static MarkCommand parseMarkCommand(String userInput) throws GooseException {
        return new MarkCommand(parseIndex(userInput, COMMAND_MARK));
    }

    /**
     * Parses an {@code unmark} command and returns an {@link UnmarkCommand}.
     *
     * @param userInput The full user input string.
     * @return An UnmarkCommand with the parsed task index.
     * @throws GooseException If the task number is missing or invalid.
     */
    private static UnmarkCommand parseUnmarkCommand(String userInput) throws GooseException {
        return new UnmarkCommand(parseIndex(userInput, COMMAND_UNMARK));
    }

    /**
     * Parses a {@code todo} command and returns a {@link TodoCommand}.
     *
     * @param userInput The full user input string.
     * @return A TodoCommand with the parsed description.
     * @throws GooseException If the description is empty.
     */
    private static TodoCommand parseTodoCommand(String userInput) throws GooseException {
        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new GooseException("Quack!! todo cannot be empty and needs a description.");
        }
        return new TodoCommand(description);
    }

    /**
     * Parses a {@code deadline} command and returns a {@link DeadlineCommand}.
     * Expected format: {@code deadline <description> /by <date>}
     *
     * @param userInput The full user input string.
     * @return A DeadlineCommand with the parsed description and due date.
     * @throws GooseException If the description or due date is missing.
     */
    private static DeadlineCommand parseDeadlineCommand(String userInput) throws GooseException {
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
        return new DeadlineCommand(description, by);
    }

    /**
     * Parses an {@code event} command and returns an {@link EventCommand}.
     * Expected format: {@code event <description> /from <start> /to <end>}
     *
     * @param userInput The full user input string.
     * @return An EventCommand with the parsed description, start time, and end time.
     * @throws GooseException If the description, start time, or end time is missing.
     */
    private static EventCommand parseEventCommand(String userInput) throws GooseException {
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
        return new EventCommand(description, from, to);
    }

    /**
     * Parses a {@code delete} command and returns a {@link DeleteCommand}.
     *
     * @param userInput The full user input string.
     * @return A DeleteCommand with the parsed task index.
     * @throws GooseException If the task number is missing or invalid.
     */
    private static DeleteCommand parseDeleteCommand(String userInput) throws GooseException {
        return new DeleteCommand(parseIndex(userInput, COMMAND_DELETE));
    }

    /**
     * Parses a {@code find} command and returns a {@link FindCommand}.
     *
     * @param userInput The full user input string.
     * @return A FindCommand with the parsed search keyword.
     * @throws GooseException If no keyword is provided.
     */
    private static FindCommand parseFindCommand(String userInput) throws GooseException {
        String keyword = userInput.substring(COMMAND_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new GooseException("Quack!! Please provide a keyword to search.");
        }
        return new FindCommand(keyword);
    }
}
