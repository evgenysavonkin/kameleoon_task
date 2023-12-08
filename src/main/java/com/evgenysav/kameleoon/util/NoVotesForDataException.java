package com.evgenysav.kameleoon.util;

public class NoVotesForDataException extends RuntimeException{
    public NoVotesForDataException(long quoteId){
        super("No votes for the quote with id: " + quoteId);
    }
}
