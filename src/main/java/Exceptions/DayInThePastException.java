package Exceptions;

public class DayInThePastException extends Exception{
    @Override
    public String getMessage() {
        return "You can't set a task in the past";
    }
}
