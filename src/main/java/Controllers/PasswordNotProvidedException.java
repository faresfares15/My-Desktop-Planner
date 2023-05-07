package Controllers;

public class PasswordNotProvidedException extends Exception{
    @Override
    public String getMessage() {
        return "Password not provided";
    }
}
