package Goose;

/**
 * Represents an exception specific to the GoGoGoose application.
 * Used to signal user-facing errors such as invalid commands or missing input.
 */
public class GooseException extends Exception {

    /**
     * Creates a new GooseException with the given error message.
     *
     * @param message The error message to display to the user.
     */
    public GooseException(String message) {
        super(message);
    }
}
