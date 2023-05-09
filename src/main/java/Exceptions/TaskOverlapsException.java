package Exceptions;

public class TaskOverlapsException extends Exception{
    private String message;
    public TaskOverlapsException(){}
    public TaskOverlapsException(String message){
        super();
        this.message = message;
    }
    @Override
    public String getMessage() {
        if(this.message == null) return "Another task is already scheduled at this duration";
        return this.message;
    }
}
