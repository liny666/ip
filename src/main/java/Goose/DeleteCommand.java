package Goose;

/**
 * Represents the command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a DeleteCommand targeting the task at the given zero-based index.
     *
     * @param index Zero-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the target task from the list, saves the updated list, and prints a confirmation.
     *
     * @throws GooseException If the index is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GooseException {
        if (index < 0 || index >= tasks.size()) {
            throw new GooseException("Quack!! Task number invalid or does not exist.");
        }
        Task task = tasks.delete(index);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(task, tasks.size());
    }
}
