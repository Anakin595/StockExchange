package com.example.demo.validate

import com.example.demo.models.User
import com.example.demo.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserValidator implements Validator {
    
    @Autowired
    private UserService userService
    
    @Override
    boolean supports(Class<?> clazz) {
        User.class == clazz
    }

    @Override
    void validate(@Nullable Object target, Errors errors) {
        def user = target as User

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
        if (user.username.length() < 6 || user.username.length() > 32) {
            errors.rejectValue("username", "Size.userForm.username")
        }
        if (userService.findByUsername(user.username) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username")
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
        if (user.password.length() < 8 || user.password.length() > 32) {
            errors.rejectValue("password", "Size.userForm.password")
        }

        if (user.passwordConfirm != user.password) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }
    }
}
