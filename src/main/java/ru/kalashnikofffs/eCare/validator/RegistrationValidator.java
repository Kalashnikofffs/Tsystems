package ru.kalashnikofffs.eCare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.service.ClientService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class RegistrationValidator implements Validator {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (client.getName().length() > 60) {
            errors.rejectValue("name", "Size.client.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Required");
        if (client.getName().length() > 60) {
            errors.rejectValue("surname", "Size.client.surname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "Required");
//        if(client.getBirthDate()) {
//            errors.rejectValue("birthDate", "Format.client.birthDate");
//        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "Required");
        if (client.getPassport() != null && client.getPassport().toString().length() != 10) {
            errors.rejectValue("passport", "Format.client.passport");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if (client.getAddress().length() > 250) {
            errors.rejectValue("surname", "Size.client.address");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if (client.getEmail().length() > 60) {
            errors.rejectValue("email", "Size.client.email");
        }

        if (client.getEmail().length() > 0)
            if(clientService.existEmail(client.getEmail())) {
            errors.rejectValue("email", "Duplicate.client.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (client.getPassword().length() > 0 && (client.getPassword().length() < 8 || client.getPassword().length() > 32)) {
            errors.rejectValue("password", "Size.client.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Required");
        if (!client.getConfirmPassword().equals(client.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.client.password");
        }

    }
}
