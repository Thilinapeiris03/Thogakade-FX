package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.Customer;
import Thogakade.model.Item;
import Thogakade.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemController implements ItemServiceController {

    public static boolean updateStock(ArrayList<OrderDetails> list) throws SQLException, ClassNotFoundException {
        for (OrderDetails orderDetail : list){
            if(!updateStock(orderDetail)){
                return false;
            }
        }
        return true;
    }
    public static boolean updateStock(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Update Item set qtyOnHand=qtyOnHand-? where code=?");
        preparedStatement.setObject(1,orderDetails.getQty());
        preparedStatement.setObject(2,orderDetails.getItemCode());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean addItem(Item item) throws SQLException, ClassNotFoundException {
        String SQL="Insert into Item values (?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ptsm = connection.prepareStatement(SQL);

        ptsm.setObject(1,item.getItemCode());
        ptsm.setObject(2,item.getDescription());
        ptsm.setObject(3,item.getUnitPrice());
        ptsm.setObject(4,item.getQtyOnHand());

        return ptsm.executeUpdate()>0;

    }

    @Override
    public Customer searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean updateItem(Item item) throws SQLException, ClassNotFoundException {
        String SQL="Update Item set description=? , unitPrice=? , qtyOnHand=? where code=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1,item.getDescription());
        preparedStatement.setObject(2,item.getUnitPrice());
        preparedStatement.setObject(3,item.getQtyOnHand());
        preparedStatement.setObject(4,item.getItemCode());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
