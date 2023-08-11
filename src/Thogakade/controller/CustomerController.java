package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.Customer;

import java.sql.*;

public class CustomerController implements CustomerServiceController{

    @Override
    public boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String SQL="Insert into Customer values(?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setObject(1,customer.getId());
        preparedStatement.setObject(2,customer.getName());
        preparedStatement.setObject(3,customer.getAddress());
        preparedStatement.setObject(4,customer.getSalary());

        return preparedStatement.executeUpdate()>0;

    }

    @Override
    public Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
        String SQL= "Select * From Customer Where id='"+id+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        if (resultSet.next()){
            return new Customer(id,resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
        }else{
            return null;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {

        String SQL="Update Customer set name=?, address=?, salary=? where id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setObject(1,customer.getName());
        preparedStatement.setObject(2,customer.getAddress());
        preparedStatement.setObject(3,customer.getSalary());
        preparedStatement.setObject(4,customer.getId());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String SQL="Delete From Customer where id='"+id+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        return statement.executeUpdate(SQL)>0;
    }
}
