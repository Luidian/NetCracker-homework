package com.example.validators;

import com.example.contracts.Contract;

public class ContractDateValidator implements IValidator{
    ValidationMessage message = new ValidationMessage("Ok", "", "");

    @Override
    public ValidationMessage validation(Contract contract) {
        if (!contract.getStartDate().isBefore(contract.getEndDate())){
            message.setStatus("Error");
            message.setMessage("Contract start date is less than end date");
            message.setInValidField("getEndDate");
        }
        return message;
    }
}
