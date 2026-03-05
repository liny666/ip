package Goose;

/**
 * Represents the command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private int index;

    /**
     * Creates an UnmarkCommand targeting the task at the given zero-based index.
     *
     * @param index Zero-based index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the target task as not done, saves the updated list, and prints a confirmation.
     *
     * @throws GooseException If the index is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GooseException {
        if (index < 0 || index >= tasks.size()) {
            throw new GooseException("Quack!! Task number invalid or does not exist.");
        }
        Task task = tasks.get(index);
        task.unmarkAsDone();
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(task);
    }
}
