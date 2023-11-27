package com.zangesterra.burgerQueen.Validator;

import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!user.getEmail().contains("@") && !user.getEmail().isEmpty()){
            errors.rejectValue("email", "Valid.userForm.email");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if ((user.getPassword().length() < 8 || user.getPassword().length() > 32) && !user.getPassword().isEmpty()){
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
