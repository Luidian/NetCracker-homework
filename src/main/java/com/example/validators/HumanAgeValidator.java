package com.example.validators;

import com.example.contracts.Contract;

public class HumanAgeValidator implements IValidator{
    ValidationMessage message = new ValidationMessage("Ok", "", "");

    @Override
    public ValidationMessage validation(Contract contract) {
        if (contract.getContractOwner().calculateAge() < 18){
            message.setStatus("Red risk");
            message.setMessage("The contract holder is less than 18 years old ");
            message.setInValidField("dateOfBirthday");
        }
        return message;
    }

}
