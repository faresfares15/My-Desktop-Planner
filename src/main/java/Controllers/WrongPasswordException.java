package Controllers;

public class WrongPasswordException extends Exception{
    @Override
    public String getMessage() {
        return "Wrong password !";
    }
}
