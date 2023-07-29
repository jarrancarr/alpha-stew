package com.mycompany.app;

public class PuzzleFormatException extends Exception {

    public PuzzleFormatException(Exception e) {
        super("Input file could not be parsed into a puzzle.", e);
    }

}
