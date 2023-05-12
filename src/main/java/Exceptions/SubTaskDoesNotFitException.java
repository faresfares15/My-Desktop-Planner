package Exceptions;

public class SubTaskDoesNotFitException extends Exception{
    private String message;
    public SubTaskDoesNotFitException(){
        this.message = "Subtask does not fit in the free slot";
    }
    public SubTaskDoesNotFitException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
