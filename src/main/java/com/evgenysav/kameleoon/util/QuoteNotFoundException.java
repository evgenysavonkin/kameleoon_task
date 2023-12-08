package com.evgenysav.kameleoon.util;

public class QuoteNotFoundException extends RuntimeException{

    public QuoteNotFoundException(long id){
        super("Quote with id = " + id + " not found");
    }
}
