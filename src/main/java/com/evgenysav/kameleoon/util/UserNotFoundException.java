package com.evgenysav.kameleoon.util;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("User with id = " + id + " wasn't found");
    }
}
