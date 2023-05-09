package Exceptions;

public class SimpleTaskDoesNotFitException extends Exception{
    private String message;
    public SimpleTaskDoesNotFitException(){}
    public SimpleTaskDoesNotFitException(String message){
        super();
        this.message = message;
    }
    @Override
    public String getMessage() {
        if(this.message == null) return "Simple task does not fit in the day";
        return this.message;
    }
}
