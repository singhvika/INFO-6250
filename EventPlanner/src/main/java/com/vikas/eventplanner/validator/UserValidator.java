package com.vikas.eventplanner.validator;

import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.vikas.eventplanner.pojo.User;



public class UserValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> type) {
        return type.isAssignableFrom(User.class);
    }

	@Override
    public void validate(Object o, Errors errors) {
	
	}
	

	
	
}
