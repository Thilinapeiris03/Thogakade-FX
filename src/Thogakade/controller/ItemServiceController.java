package Thogakade.controller;

import Thogakade.model.Customer;
import Thogakade.model.Item;

import java.sql.SQLException;

public interface ItemServiceController {
    boolean addItem(Item item) throws SQLException, ClassNotFoundException;
    Customer searchItem(String id) throws SQLException, ClassNotFoundException;
    boolean updateItem(Item item) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
}
