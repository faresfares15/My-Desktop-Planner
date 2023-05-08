package Exceptions;

public class UserNameNotProvidedException extends Exception{
    @Override
    public String getMessage() {
        return "User name not provided";
    }
}
