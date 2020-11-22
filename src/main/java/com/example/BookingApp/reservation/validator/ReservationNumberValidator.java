package com.example.BookingApp.reservation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservationNumberValidator implements ConstraintValidator<ReservationNumber, Integer> {
    @Override
    public boolean isValid(Integer reservationNumber, ConstraintValidatorContext constraintValidatorContext) {
        return reservationNumber.toString().contains("00") && reservationNumber.toString().endsWith("12");
    }
}
