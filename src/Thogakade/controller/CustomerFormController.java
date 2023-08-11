package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    public TableColumn colId;
    public TableColumn colSalary;
    public TableColumn colAddress;
    public TableColumn colName;
    public TextField txtId;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtSalary;
    public TableView tblCustomer;



    public void btnAddOnACtion(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSalary.getText()));
        boolean isAdded = new CustomerController().addCustomer(customer);
        if (isAdded) {
            System.out.println("Added Success");
            new Alert(Alert.AlertType.CONFIRMATION,"Added Success").show();
            loadTable();
        } else {
            System.out.println("Failed");
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSalary.getText()));
        boolean isUpdate = new CustomerController().updateCustomer(customer);
        if (isUpdate) {
            System.out.println("Update Success");
            new Alert(Alert.AlertType.CONFIRMATION,"Update Success").show();
            loadTable();
        } else {
            System.out.println("Failed");
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isDeleted = new CustomerController().deleteCustomer(txtId.getText());
        if(isDeleted){
            System.out.println("Delete Success");
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Success").show();
            loadTable();
            txtId.setText(null);
            txtName.setText(null);
            txtAddress.setText(null);
            txtSalary.setText(null);
        }else{
            new Alert(Alert.AlertType.ERROR,"Delete Failed").show();
            System.out.println("Failed");
        }
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable,
                                                                        oldValue,
                                                                        newValue) -> {
            if(newValue!=null) {
                setTableValuesToTxt((Customer) newValue);
            }
        });
    }

    private void setTableValuesToTxt(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }

    public void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        String SQL="Select * from Customer";
        ObservableList<Customer> list = FXCollections.observableArrayList();
        Connection connection= null;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3), resultSet.getDouble(4));
                list.add(customer);
            }
            tblCustomer.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> getAllCustomerIds() throws ClassNotFoundException, SQLException{

        ResultSet rst  = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT id FROM Customer")
                .executeQuery();
        ArrayList<String> idSet= new ArrayList<>();
        System.out.println();
        while (rst.next()) {
            idSet.add(rst.getString(1));
        }
        return idSet;
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=txtId.getText();
        Customer customer= new CustomerController().searchCustomer(id);
        if(customer!=null){
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary()+"");

        }else{
            new Alert(Alert.AlertType.ERROR,"Customer not Found").show();
        }
    }
    public static Customer OrderSearchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DBConnection.getInstance().getConnection().prepareStatement("Select * From Customer where id=?");
        stm.setObject(1,customerId);
        ResultSet rst=  stm.executeQuery();
        if(rst.next()){
            Customer customer=new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
            return customer;
        }
        return null;
    }

}
