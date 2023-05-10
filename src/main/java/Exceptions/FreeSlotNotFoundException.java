package Exceptions;

public class FreeSlotNotFoundException extends Exception{
    private String message;
    public FreeSlotNotFoundException(String message){
        this.message = message;
    }
    public FreeSlotNotFoundException(){
        this.message = "Free slot not found";
    }
    public String getMessage(){
        return this.message;
    }
}
