package Exceptions;

public class NoDecompositionProvidedException extends Exception{
    public NoDecompositionProvidedException() {
        super("No decomposition provided");
    }
    public NoDecompositionProvidedException(String message) {
        super(message);
    }
}
