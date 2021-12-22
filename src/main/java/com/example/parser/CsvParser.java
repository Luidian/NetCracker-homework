package com.example.parser;

import com.example.human.Human;
import com.example.repository.Repository;
import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import com.example.validators.ContractDateValidator;
import com.example.validators.HumanAgeValidator;
import com.example.validators.IValidator;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvParser
 * @author  Alexanrd Spaskin
 */
public class CsvParser {
    /**
     * Method parses csv file
     * @param path path to csv file
     * @param repository the storage to which the new data will be added
     * @throws CsvParseException
     */
    public void parse(final String path, Repository repository) throws CsvParseException{
        ArrayList<IValidator> validators = new ArrayList<>();
        validators.add(new ContractDateValidator());
        validators.add(new HumanAgeValidator());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d");
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).build();
            String[] values = null;
            int lineNum = 0;
            try {
                while ((values = csvReader.readNext()) != null) {
                    lineNum++;
                    Human h = human(values[3], values[4], values[5], values[6], values[7], values[8], values[9], formatter);
                    switch (values[10]){
                        case ("mobile"):
                            MCContract mcContract = mobile(values[0], values[1], values[2], h, values[11], values[12], values[13], formatter);
                            boolean validMcContract = validators.stream().map(v -> v.validation(mcContract)).anyMatch(vm -> vm.getStatus() == "Ok");
                            if(validMcContract) {
                                repository.add(mcContract);
                            }
                            break;
                        case ("internet"):
                            WIContract wiContract = internet(values[0], values[1], values[2], h, values[11], formatter);
                            boolean validWiContract = validators.stream().map(v -> v.validation(wiContract)).anyMatch(vm -> vm.getStatus() == "Ok");
                            if(validWiContract) {
                                repository.add(wiContract);
                            }
                            break;
                        case ("tv"):
                            DTVContract dtvContract = tv(values[0], values[1], values[2], h, values[11], formatter);
                            boolean validDtvContract= validators.stream().map(v -> v.validation(dtvContract)).anyMatch(vm -> vm.getStatus() == "Ok");
                            if(validDtvContract) {
                                repository.add(dtvContract);
                            }
                            break;
                        default:
                            throw new CsvParseException("Data entry error: row " + String.valueOf(lineNum));
                    }

                }
            } catch (CsvValidationException | IOException e) {
                throw new CsvParseException(e);
            }

        } catch (FileNotFoundException e) {
            throw new CsvParseException(e);
        }finally {
            if(csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    throw new CsvParseException(e);
                }
            }
        }
    }

    /**
     * DTVContract add method
     * @param startDate start date of the contract
     * @param endDate end date of the contract
     * @param contractNumber Number of contract
     * @param contractOwner Owner of contract
     * @param channelPackage Channel package
     * @param formatter format of date
     * @return object of class DTVContract
     * @throws CsvParseException
     */
    private DTVContract tv(String startDate, String endDate, String contractNumber, Human contractOwner, String channelPackage, DateTimeFormatter formatter) throws CsvParseException {
        try {
            LocalDate sDate = LocalDate.parse(startDate, formatter);
            LocalDate eDate = LocalDate.parse(endDate, formatter);
            int numberInt = Integer.parseInt(contractNumber);
            return new DTVContract(0, sDate, eDate, numberInt, contractOwner, channelPackage);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new CsvParseException(e);
        }
    }

    /**
     * WIContract add method
     * @param startDate start date of the contract
     * @param endDate end date of the contract
     * @param contractNumber Number of contract
     * @param contractOwner Owner of contract
     * @param speed speed of internet
     * @param formatter format of date
     * @return object of class WIContract
     * @throws CsvParseException
     */
    private WIContract internet(String startDate, String endDate, String contractNumber, Human contractOwner, String speed, DateTimeFormatter formatter) throws CsvParseException {
        try {
            LocalDate sDate = LocalDate.parse(startDate, formatter);
            LocalDate eDate = LocalDate.parse(endDate, formatter);
            int numberInt = Integer.parseInt(contractNumber);
            int speedInt = Integer.parseInt(speed);
            return new WIContract(0, sDate, eDate, numberInt, contractOwner, speedInt);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new CsvParseException(e);
        }
    }

    /**
     * MCContract add method
     * @param startDate start date of the contract
     * @param endDate end date of the contract
     * @param contractNumber Number of contract
     * @param contractOwner Owner of contract
     * @param minutes minutes
     * @param sms sms
     * @param traffic traffic
     * @param formatter format of date
     * @return object of class MCContract
     * @throws CsvParseException
     */
    private MCContract mobile(String startDate, String endDate, String contractNumber, Human contractOwner, String minutes, String sms, String traffic, DateTimeFormatter formatter) throws CsvParseException {
        try {
            LocalDate sDate = LocalDate.parse(startDate, formatter);
            LocalDate eDate = LocalDate.parse(endDate, formatter);
            int numberInt = Integer.parseInt(contractNumber);
            int minInt = Integer.parseInt(minutes);
            int smsInt = Integer.parseInt(sms);
            int trafficInt = Integer.parseInt(traffic);
            return new MCContract(0, sDate, eDate, numberInt, contractOwner, minInt, smsInt, trafficInt);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new CsvParseException(e);
        }
    }

    /**
     * Method creates an object of the Human class
     * @param firstName first name of contract owner
     * @param name name of contract owner
     * @param middleName middle name of contract owner
     * @param gender gender of contract owner
     * @param dateOfBirthday date of birthday of contract owner
     * @param passportSeries passport series of contract owner
     * @param passportID passport id of contract owner
     * @param formatter format of date
     * @return object of class Human
     * @throws CsvParseException
     */
    private Human human(String firstName, String name, String middleName, String gender, String dateOfBirthday, String passportSeries, String passportID, DateTimeFormatter formatter) throws CsvParseException {
        try {
            int passportS = Integer.parseInt(passportSeries);
            int passportI = Integer.parseInt(passportID);
            LocalDate date = LocalDate.parse(dateOfBirthday, formatter);
            return new Human(0, firstName, name, middleName, gender, date, passportS, passportI);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new CsvParseException(e);
        }
    }
}
