package Goose;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

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
