package Exceptions;

public class EmptyRequiredFieldException extends Exception{
    private String message;
    public EmptyRequiredFieldException(){
        this.message = "Empty field is required";
    }
    public EmptyRequiredFieldException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
