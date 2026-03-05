package Goose;

/**
 * Represents the command to display all tasks in the task list.
 */
public class ListCommand extends Command {

    /** Prints all tasks currently in the task list. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
