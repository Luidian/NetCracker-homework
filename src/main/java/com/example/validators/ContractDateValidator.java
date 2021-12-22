package com.example.validators;

import com.example.contracts.Contract;

public class ContractDateValidator implements IValidator{
    ValidationMessage message = new ValidationMessage("Ok", "", "");

    @Override
    public ValidationMessage validation(Contract contract) {
        if (!contract.getStartDate().isBefore(contract.getEndDate())){
            ValidationMessage messageError = new ValidationMessage();
            messageError.setStatus("Error");
            messageError.setMessage("Contract start date is less than end date");
            messageError.setInValidField("getEndDate");
            return messageError;
        }
        return message;
    }
}
