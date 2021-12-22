package com.example.validators;

import com.example.contracts.Contract;

public class HumanAgeValidator implements IValidator{
    ValidationMessage message = new ValidationMessage("Ok", "", "");

    @Override
    public ValidationMessage validation(Contract contract) {
        if (contract.getContractOwner().calculateAge() < 18){
            ValidationMessage messageError = new ValidationMessage();
            messageError.setStatus("Red risk");
            messageError.setMessage("The contract holder is less than 18 years old ");
            messageError.setInValidField("dateOfBirthday");
            return messageError;
        }
        return message;
    }
}
