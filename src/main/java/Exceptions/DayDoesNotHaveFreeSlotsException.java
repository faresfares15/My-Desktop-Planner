package Exceptions;

public class DayDoesNotHaveFreeSlotsException extends Exception{
    @Override
    public String getMessage() {
        return "Day does not have free slots !";
    }
}
