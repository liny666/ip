package Goose;

/**
 * Represents an executable command issued by the user.
 * Each concrete subclass encapsulates the logic for one type of command.
 */
public abstract class Command {

    /**
     * Executes this command using the provided task list, UI, and storage.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The UI handler for printing output.
     * @param storage The storage handler for persisting changes.
     * @throws GooseException If the command cannot be executed due to invalid state.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GooseException;

    /**
     * Returns whether this command signals the application to exit.
     * Returns {@code false} by default; overridden by {@link ExitCommand}.
     *
     * @return {@code true} if the app should exit after this command, {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
