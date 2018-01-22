package ru.javawebinar.topjava.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import org.apache.commons.validator.routines.EmailValidator;

@Component
public class UserFormValidator implements Validator {


    private final UserService service;

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    public UserFormValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo user = (UserTo) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.notEmptyName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.notEmptyEmail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.notEmptyPassword");

        if (!emailValidator.isValid(user.getEmail())) {
            errors.rejectValue("email", "validation.badEmail");
        } else {
            if (!AuthorizedUser.get().getUserTo().getEmail().equals(user.getEmail()) && service.getByEmail(user.getEmail()) != null) {
                errors.rejectValue("email", "validation.duplicatedEmail");
            }
        }
    }
}