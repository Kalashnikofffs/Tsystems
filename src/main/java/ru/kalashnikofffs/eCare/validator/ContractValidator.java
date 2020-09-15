package ru.kalashnikofffs.eCare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kalashnikofffs.eCare.model.Contract;
import ru.kalashnikofffs.eCare.service.ContractService;

@Component
public class ContractValidator implements Validator {

    private ContractService contractService;

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Contract.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contract contract = (Contract) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "Required");
        if (contract.getNumber() != null)
            if(contractService.isExistNumber(contract.getNumber())) {
                errors.rejectValue("number", "Duplicate.contract.number");
            }
    }
}
