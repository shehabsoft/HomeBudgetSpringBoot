package com.homeBudget.exception;

/**
 * Created by shehab.tarek on 8/21/2019.
 */
public class ProductConstraintViolationException extends Exception {
    public ProductConstraintViolationException(String message)
    {
        super(message);
    }
}
