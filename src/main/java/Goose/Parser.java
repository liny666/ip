package Goose;

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

    public static Command parse(String userInput) throws GooseException {
        if (userInput.equals(COMMAND_BYE)) {
            return new ExitCommand();
        } else if (userInput.equals(COMMAND_LIST)) {
            return new ListCommand();
        } else if (userInput.startsWith(COMMAND_UNMARK)) {
            return parseUnmarkCommand(userInput);
        } else if (userInput.startsWith(COMMAND_MARK)) {
            return parseMarkCommand(userInput);
        } else if (userInput.startsWith(COMMAND_TODO)) {
            return parseTodoCommand(userInput);
        } else if (userInput.startsWith(COMMAND_DEADLINE)) {
            return parseDeadlineCommand(userInput);
        } else if (userInput.startsWith(COMMAND_EVENT)) {
            return parseEventCommand(userInput);
        } else if (userInput.startsWith(COMMAND_DELETE)) {
            return parseDeleteCommand(userInput);
        } else if (userInput.startsWith(COMMAND_FIND)) {
            return parseFindCommand(userInput);
        } else {
            throw new GooseException("Quack!! Type something that I can understand.");
        }
    }

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

    private static MarkCommand parseMarkCommand(String userInput) throws GooseException {
        return new MarkCommand(parseIndex(userInput, COMMAND_MARK));
    }

    private static UnmarkCommand parseUnmarkCommand(String userInput) throws GooseException {
        return new UnmarkCommand(parseIndex(userInput, COMMAND_UNMARK));
    }

    private static TodoCommand parseTodoCommand(String userInput) throws GooseException {
        String description = userInput.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new GooseException("Quack!! todo cannot be empty and needs a description.");
        }
        return new TodoCommand(description);
    }

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

    private static DeleteCommand parseDeleteCommand(String userInput) throws GooseException {
        return new DeleteCommand(parseIndex(userInput, COMMAND_DELETE));
    }

    private static FindCommand parseFindCommand(String userInput) throws GooseException {
        String keyword = userInput.substring(COMMAND_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new GooseException("Quack!! Please provide a keyword to search.");
        }
        return new FindCommand(keyword);
    }
}
