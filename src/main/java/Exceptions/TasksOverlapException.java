package Exceptions;

public class TasksOverlapException extends Exception{
    public TasksOverlapException(){}
    public TasksOverlapException(String message){
        super(message);
    }

}
