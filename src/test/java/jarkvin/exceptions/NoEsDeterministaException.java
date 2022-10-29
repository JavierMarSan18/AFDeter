package jarkvin.exceptions;

public class NoEsDeterministaException extends RuntimeException{
    public NoEsDeterministaException() {
    }

    public NoEsDeterministaException(String message) {
        super(message);
    }
}
