package Exceptions;

public class TaskCanNotBeDecomposedException extends Exception{
    @Override
    public String getMessage() {
        return "Task can not be decomposed";
    }
}
