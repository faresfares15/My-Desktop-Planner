package Exceptions;

public class DayDoesNotHaveTasksException extends Exception{
    @Override
    public String getMessage() {
        return "Day does not have tasks";
    }
}
