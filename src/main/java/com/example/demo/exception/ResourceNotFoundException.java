package com.example.demo.exception;

/**
 * Exception thrown when a requested resource is not found in the system.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new ResourceNotFoundException with a custom message.
     *
     * @param message detailed error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
