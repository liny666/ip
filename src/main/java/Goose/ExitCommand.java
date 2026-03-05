package Goose;

/**
 * Represents the command to exit the application.
 */
public class ExitCommand extends Command {

    /** Prints the goodbye message to the user. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns {@code true} to signal the application to stop the command loop.
     *
     * @return {@code true} always.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
