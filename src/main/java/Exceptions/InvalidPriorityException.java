package Exceptions;

public class InvalidPriorityException extends Exception{
    private String message;
    public InvalidPriorityException(){}
    public InvalidPriorityException(String message){
        super();
        this.message = message;
    }
    @Override
    public String getMessage() {
        if(this.message == null) return "Invalid priority";
        return this.message;
    }
}
