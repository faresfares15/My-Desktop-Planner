package Exceptions;

public class UserNameNotProvidedException extends Exception{
    @Override
    public String getMessage() {
        return "Username not provided";
    }
}
