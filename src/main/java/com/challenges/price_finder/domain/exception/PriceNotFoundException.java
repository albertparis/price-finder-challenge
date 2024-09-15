package com.challenges.price_finder.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String msg) {
        super(msg);
    }
}
