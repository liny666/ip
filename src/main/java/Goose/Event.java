package Goose;

/**
 * Represents a task that occurs over a time range, with a start and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new Event task with the given description and time range.
     *
     * @param description The description of the event.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of this event.
     *
     * @return The start time string.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time of this event.
     *
     * @return The end time string.
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
