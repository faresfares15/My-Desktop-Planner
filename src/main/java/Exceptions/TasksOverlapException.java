package Exceptions;

public class TasksOverlapException extends Exception{
    private String message;
    public TasksOverlapException(){
        this.message = "Tasks overlap";
    }
    public TasksOverlapException(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
