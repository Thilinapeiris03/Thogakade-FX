package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailController {
    public static boolean addOrderDetail(ArrayList<OrderDetails> list) throws SQLException, ClassNotFoundException {
        for (OrderDetails orderDetails:list) {
            boolean isAdded=addOrderDetail((orderDetails));
            if(!isAdded){
                return false;
            }
        }
    return true;
    }

    public static boolean addOrderDetail(OrderDetails orderDetail) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into OrderDetail values(?,?,?,?)");
        System.out.println(orderDetail);
        preparedStatement.setObject(1,orderDetail.getOrderId());
        preparedStatement.setObject(2,orderDetail.getItemCode());
        preparedStatement.setObject(3,orderDetail.getQty());
        preparedStatement.setObject(4,orderDetail.getUnitPrice());
        return preparedStatement.executeUpdate()>0;
    }
}
