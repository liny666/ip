package Goose;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GooseException;

    public boolean isExit() {
        return false;
    }
}
