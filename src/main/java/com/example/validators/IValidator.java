package com.example.validators;

import com.example.contracts.Contract;

public interface IValidator {

    ValidationMessage validation(Contract contract);
}
