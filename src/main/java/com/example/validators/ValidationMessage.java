package com.example.validators;

/**
 * Validation message
 * @author  Alexanrd Spaskin
 */
public class ValidationMessage {

    private String status;

    private String message;

    private String inValidField;

    public ValidationMessage(){}

    public ValidationMessage(String status, String message, String inValidField){
        this.status = status;
        this.message = message;
        this.inValidField = inValidField;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInValidField() {
        return inValidField;
    }

    public void setInValidField(String inValidField) {
        this.inValidField = inValidField;
    }

    @Override
    public String toString() {
        return "ContractValidator{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", invalidField='" + inValidField + '\'' +
                '}';
    }
}
