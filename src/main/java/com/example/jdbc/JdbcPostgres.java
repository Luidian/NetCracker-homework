package com.example.jdbc;

import com.example.contracts.Contract;
import com.example.contracts.DTVContract;
import com.example.contracts.MCContract;
import com.example.contracts.WIContract;
import com.example.human.Human;
import com.example.repository.Repository;

import java.sql.*;
import java.time.LocalDate;

/**
 * @author Alexanrd Spaskin
 */
public class JdbcPostgres {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "user";
    private final String password = "pass";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public boolean saveRepository(Contract[] contracts){
        String insertDTVContract = "insert into " +
                "DTVCONTRACT(id_dtvcontract, start_date, end_date, contract_number, id_human, channel_package)" +
                " values(?, ?, ?, ?, ?, ?)";
        String insertMCContract = "insert into " +
                "MCCONTRACT(id_mccontract, start_date, end_date, contract_number, id_human, minutes, sms, traffic)" +
                " values(?, ?, ?, ?, ?, ?, ?, ?)";
        String insertWIContract = "insert into " +
                "WICONTRACT(id_wicontract, start_date, end_date, contract_number, id_human, speed)" +
                " values(?, ?, ?, ?, ?, ?)";

        clearDB();
        Connection connection = connection();
        PreparedStatement preparedStatement = null;

        for (Contract contract : contracts){
            if (contract.getClass().getName().equals("com.example.contracts.DTVContract")) {
                try {
                    preparedStatement = connection.prepareStatement(insertDTVContract);
                    preparedStatement.setInt(1, contract.getId());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(contract.getStartDate().atStartOfDay()));
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(contract.getEndDate().atStartOfDay()));
                    preparedStatement.setInt(4, contract.getContractNumber());
                    preparedStatement.setInt(5, contract.getContractOwner().getId());
                    addHuman(contract.getContractOwner(), connection);
                    preparedStatement.setString(6, ((DTVContract) contract).getChannelPackage());

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (contract.getClass().getName().equals("com.example.contracts.MCContract")) {
                try {
                    preparedStatement = connection.prepareStatement(insertMCContract);
                    preparedStatement.setInt(1, contract.getId());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(contract.getStartDate().atStartOfDay()));
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(contract.getEndDate().atStartOfDay()));
                    preparedStatement.setInt(4, contract.getContractNumber());
                    preparedStatement.setInt(5, contract.getContractOwner().getId());
                    addHuman(contract.getContractOwner(), connection);
                    preparedStatement.setInt(6, ((MCContract) contract).getMinutes());
                    preparedStatement.setInt(7, ((MCContract) contract).getSms());
                    preparedStatement.setInt(8, ((MCContract) contract).getTraffic());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (contract.getClass().getName().equals("com.example.contracts.WIContract")) {
                try {
                    preparedStatement = connection.prepareStatement(insertWIContract);
                    preparedStatement.setInt(1, contract.getId());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(contract.getStartDate().atStartOfDay()));
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(contract.getEndDate().atStartOfDay()));
                    preparedStatement.setInt(4, contract.getContractNumber());
                    preparedStatement.setInt(5, contract.getContractOwner().getId());
                    addHuman(contract.getContractOwner(), connection);
                    preparedStatement.setInt(6, ((WIContract) contract).getSpeed());

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Contract[] recoveryRepository(){
        Repository repository = new Repository();
        String selectDTVContract = "select * from DTVCONTRACT";
        String selectMCContract = "select * from MCCONTRACT";
        String selectWIContract = "select * from WICONTRACT";

        Connection connection = connection();
        Statement statement = null;

        try {
            //DTVCONTRACT
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectDTVContract);
            while (resultSet.next()){
                int id = resultSet.getInt("id_dtvcontract");
                LocalDate startDate = resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate();
                LocalDate endDate = resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate();
                int contractNumber = resultSet.getInt("contract_number");
                Human contractOwner = getHuman(resultSet.getInt("id_human"), connection);
                String channelPackage = resultSet.getString("channel_package");
                repository.add(new DTVContract(id, startDate, endDate, contractNumber, contractOwner, channelPackage));
            }

            //MCCONTRACT
            resultSet = statement.executeQuery(selectMCContract);
            while (resultSet.next()){
                int id = resultSet.getInt("id_mccontract");
                LocalDate startDate = resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate();
                LocalDate endDate = resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate();
                int contractNumber = resultSet.getInt("contract_number");
                Human contractOwner = getHuman(resultSet.getInt("id_human"), connection);
                int minutes = resultSet.getInt("minutes");
                int sms = resultSet.getInt("sms");
                int traffic = resultSet.getInt("traffic");
                repository.add(new MCContract(id, startDate, endDate, contractNumber, contractOwner, minutes, sms, traffic));
            }

            //WICONTRACT
            resultSet = statement.executeQuery(selectWIContract);
            while (resultSet.next()){
                int id = resultSet.getInt("id_wicontract");
                LocalDate startDate = resultSet.getTimestamp("start_date").toLocalDateTime().toLocalDate();
                LocalDate endDate = resultSet.getTimestamp("end_date").toLocalDateTime().toLocalDate();
                int contractNumber = resultSet.getInt("contract_number");
                Human contractOwner = getHuman(resultSet.getInt("id_human"), connection);
                int speed = resultSet.getInt("speed");
                repository.add(new WIContract(id, startDate, endDate, contractNumber, contractOwner, speed));
            }

            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return repository.getContracts();
    }

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection().createStatement();
        int result = statement.executeUpdate(query);

        return result;
    }

    private void createTable(){
        String createHumanTable = "create table if not exists HUMAN (" +
                "id_human serial primary key, " +
                "first_name varchar(255), " +
                "name varchar(255), " +
                "middle_name varchar(255), " +
                "date_of_birthday timestamp, " +
                "gender varchar(255), " +
                "passport_series integer, " +
                "passport_id integer " +
                ");";

        String contractTable = "start_date timestamp, " +
                "end_date timestamp, " +
                "contract_number integer, " +
                "id_human serial, ";

        String createDTVContract = "create table if not exists DTVCONTRACT (" +
                "id_dtvcontract serial primary key, " +
                contractTable +
                "channel_package varchar(255) " +
                ");";

        String createMCContract = "create table if not exists MCCONTRACT (" +
                "id_mccontract serial primary key, " +
                contractTable +
                "minutes integer, " +
                "sms integer, " +
                "traffic integer " +
                ");";

        String createWIContract = "create table if not exists WICONTRACT (" +
                "id_wicontract serial primary key, " +
                contractTable +
                "speed integer" +
                ");";

        try {
            executeUpdate(createHumanTable);
            executeUpdate(createDTVContract);
            executeUpdate(createMCContract);
            executeUpdate(createWIContract);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addHuman(Human human, Connection connection) {
        String insertHuman = "insert into HUMAN values(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertHuman);
            preparedStatement.setInt(1, human.getId());
            preparedStatement.setString(2, human.getFirstName());
            preparedStatement.setString(3, human.getName());
            preparedStatement.setString(4, human.getMiddleName());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(human.getDateOfBirthday().atStartOfDay()));
            preparedStatement.setString(6, human.getGender());
            preparedStatement.setInt(7, human.getPassportSeries());
            preparedStatement.setInt(8, human.getPassportID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Human getHuman(int id_human, Connection connection) {
        String selectHuman = "select * from Human where id_human = " +
                String.valueOf(id_human) +
                ";";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectHuman);
            while (resultSet.next()){
                int id = resultSet.getInt("id_human");
                String firstName = resultSet.getString("first_name");
                String name = resultSet.getString("name");
                String middleName = resultSet.getString("middle_name");
                LocalDate dateOfBirthday = resultSet.getTimestamp("date_of_birthday").toLocalDateTime().toLocalDate();
                String gender = resultSet.getString("gender");
                int passportSeries = resultSet.getInt("passport_series");
                int passportID = resultSet.getInt("passport_id");

                return new Human(id, firstName, name, middleName, gender, dateOfBirthday, passportSeries, passportID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearDB(){
        String drop = "DROP SCHEMA public CASCADE;";
        String create = "CREATE SCHEMA public;";

        try {
            executeUpdate(drop);
            executeUpdate(create);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
