package com.homeBudget.exception;

/**
 * Created by shehab.tarek on 8/21/2019.
 */
public class CurrencyConstraintViolationException extends Exception {
    public CurrencyConstraintViolationException(String message)
    {
        super(message);
    }
}
