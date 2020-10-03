package com.github.reducktion.socrates.extractor.exceptions;

/**
 * Exception that is thrown when trying to extract citizen information from an invalid National Identification Number.
 */
public class InvalidIdException extends Exception {

    public InvalidIdException() {
        super("The provided id is invalid.");
    }
}
