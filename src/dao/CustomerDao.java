package dao;

import core.DbHelper;
import entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {
    private Connection connection;

    public CustomerDao() {
        this.connection = DbHelper.getInstance();
    }

    public ArrayList<Customer> query(String query){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                customers.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public boolean delete(int id){
        String query = "DELETE FROM customer WHERE customer_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Customer customer){
        String query = "UPDATE customer SET " +
                "customer_name = ? , " +
                "customer_type = ? , " +
                "customer_phoneNumber = ? , " +
                "customer_mail = ? , " +
                "customer_adress = ?" +
                "WHERE customer_id = ? ";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,customer.getCustomerName());
            preparedStatement.setString(2,customer.getType().toString());
            preparedStatement.setString(3,customer.getCustomerPhone());
            preparedStatement.setString(4,customer.getCustomerMail());
            preparedStatement.setString(5,customer.getCustomerAdress());
            preparedStatement.setInt(6, customer.getCustomerId());
            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Customer customer){
        String query = "INSERT INTO customer " +
                "(customer_name, customer_type, customer_phoneNumber, customer_mail, customer_adress)" +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,customer.getCustomerName());
            preparedStatement.setString(2,customer.getType().toString());   //ENUM
            preparedStatement.setString(3,customer.getCustomerPhone());
            preparedStatement.setString(4,customer.getCustomerMail());
            preparedStatement.setString(5,customer.getCustomerAdress());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get customer by ID
    public Customer getById(int id){
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE customer_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                customer = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    //fetch all users
    public ArrayList<Customer> findAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM customer");
            while (resultSet.next()){
                customers.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public Customer match(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setCustomerName(resultSet.getString("customer_name"));
        customer.setCustomerMail(resultSet.getString("customer_mail"));
        customer.setCustomerPhone(resultSet.getString("customer_phoneNumber"));
        customer.setCustomerAdress(resultSet.getString("customer_adress"));
        customer.setType(Customer.TYPE.valueOf(resultSet.getString("customer_type")));
        return customer;
    }
}
