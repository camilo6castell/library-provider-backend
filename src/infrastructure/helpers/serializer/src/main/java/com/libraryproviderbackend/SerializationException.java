package com.libraryproviderbackend;

/**
 * Unchecked exception thrown when event serialization or deserialization fails.
 */
public class SerializationException extends RuntimeException {

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
