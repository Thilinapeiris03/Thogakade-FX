package Thogakade.controller;

import Thogakade.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {
    static String getOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("Select id from Orders order by id DESC LIMIT 1");
        ResultSet rst= stm.executeQuery();
        return rst.next() ? rst.getString("id") :null;
    }

}
