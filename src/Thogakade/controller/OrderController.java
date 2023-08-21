package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.Order;

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

    public static boolean placeOrder(Order order) {

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into Orders value(?,?,?)");
            preparedStatement.setObject(1, order.getId());
            preparedStatement.setObject(2, order.getDate());
            preparedStatement.setObject(3, order.getCustomerId());
            boolean orderIsAdded = preparedStatement.executeUpdate() > 0;

            if (orderIsAdded) {
                boolean orderDetailAdded = OrderDetailController.addOrderDetail(order.getList());
                if (orderDetailAdded) {
                    boolean itemUpdate = ItemController.updateStock(order.getList());
                    if (itemUpdate) {
                        return true;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

}
