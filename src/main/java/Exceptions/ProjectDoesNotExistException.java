package Exceptions;

public class ProjectDoesNotExistException extends Exception{
    @Override
    public String getMessage() {
        return "Project doesn't exist !";
    }
}
