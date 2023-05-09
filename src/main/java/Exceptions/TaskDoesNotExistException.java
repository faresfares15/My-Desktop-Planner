package Exceptions;

public class TaskDoesNotExistException extends Exception {
    public TaskDoesNotExistException() {
        super("Task does not exist");
    }
}
