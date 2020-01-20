package com.homeBudget.exception;

/**
 * Created by shehab.tarek on 8/21/2019.
 */
public class OrderConstraintViolationException extends Exception {
    public OrderConstraintViolationException(String message)
    {
        super(message);
    }
}
