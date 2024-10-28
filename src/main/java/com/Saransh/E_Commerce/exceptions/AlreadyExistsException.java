package com.Saransh.E_Commerce.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message)
    {
        super(message);
    }
}
