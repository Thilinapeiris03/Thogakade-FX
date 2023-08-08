package Thogakade.controller;

import Thogakade.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class OrderFormController implements Initializable {
    public Button btnNewCustomer;
    public TextField txtOrderDate;
    public ComboBox combCustomerId;
    public ComboBox combItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public Button btnAdd;
    public Button btnRemove;
    public Button btnPlaceOrder;
    public Label lblTotal;
    public Label txtCustomerName;
    public Label lblOrderId;

    private void loadAllCustomerIds() {
        try {
            for (String tempId : CustomerController.getAllCustomerIds()) {
                System.out.println(tempId);
                //combCustomerId.addItem(tempId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





    public void txtOrderDateOnAction(ActionEvent actionEvent) {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void combCustomerIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = combCustomerId.getSelectionModel().getSelectedItem().toString();
        txtCustomerName.setText(CustomerController.searchCustomer(customerId).getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createOrderId();
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrderId() throws SQLException, ClassNotFoundException {
        String id=OrderController.getOrderId();
        if(id!= null){
            id = id.split("[A-Z]")[1]; // D001==> 001
            id = String.format("D%03d", (Integer.parseInt(id)+1));
            lblOrderId.setText(id);
        }

    }
}
