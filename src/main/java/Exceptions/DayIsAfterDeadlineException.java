package Exceptions;

public class DayIsAfterDeadlineException extends Exception{
    @Override
    public String getMessage() {
        return "This day is after the deadline of the task";
    }
}
