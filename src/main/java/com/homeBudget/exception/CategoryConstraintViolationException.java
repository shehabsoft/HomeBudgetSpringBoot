package com.homeBudget.exception;

/**
 * Created by shehab.tarek on 8/21/2019.
 */
public class CategoryConstraintViolationException extends Exception {
    public CategoryConstraintViolationException(String message)
    {
        super(message);
    }
}
