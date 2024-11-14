package com.neoflex.calculator.exeption;

public class ScoringExeption extends RuntimeException {
    public ScoringExeption() {
        super();
    }

    public ScoringExeption(String message) {
        super(message);
    }
}