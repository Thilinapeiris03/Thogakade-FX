package Thogakade.controller;

import Thogakade.model.Customer;

import java.sql.SQLException;

public interface CustomerServiceController {
    boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    Customer searchCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
}
