package com.evgenysav.kameleoon.util;

public class QuoteUpdateException extends RuntimeException{
    public QuoteUpdateException(long id){
        super("Error while updating quote with id = " + id + ". Quote with this id wasn't found");
    }
}
