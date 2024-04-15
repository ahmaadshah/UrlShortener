package com.HosseiniAhmad.URLShorterner.dto;

public class Constants {
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
    public static final String BAD_PASSWORD_MESSAGE = "Password must include digits, lowercase letter, capital letter, special symbols and consist of at least 8 characters";
    public static final String BAD_PASSWORD_CONFIRMATION_MESSAGE = "Password and password confirmation don't match";
    public static final String BAD_EMAIL_MESSAGE = "Email is incorrect";
    public static final String BAD_USERNAME_MESSAGE = "Name length could be from 1 to 100 symbols";
    public static final String USERNAME_IS_BUSY_MESSAGE = "Username is busy. Use another one";
    public static final String EMAIL_IS_BUSY_MESSAGE = "Email is busy. Use another one";
    public static final String NECESSARY_FIELD_ABSENT_MESSAGE = "At least one field must be changed";
}
