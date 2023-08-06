package Thogakade.controller;

import Thogakade.db.DBConnection;
import Thogakade.model.Customer;
import Thogakade.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
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
        try{
            String id=txtId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            double salary=Double.parseDouble(txtSalary.getText());

            //System.out.println(id+"\t"+name+"\t"+address+"\t"+salary);

            Customer customer = new Customer(id, name, address, salary);
            Connection connection = DBConnection.getInstance().getConnection();
            String SQL = "Insert into Customer Values(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(SQL);

            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getName());
            pstm.setObject(3, customer.getAddress());
            pstm.setObject(4, customer.getSalary());
            int i = pstm.executeUpdate();
            loadTable();
            if (i > 0) {
                System.out.println("Added Success");
                JOptionPane.showMessageDialog(null, "Added Success");
            } else {
                System.out.println("Failed");

            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Added Failed");
        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        double salary=Double.parseDouble(txtSalary.getText());

        //System.out.println(id+"\t"+name+"\t"+address+"\t"+salary);

        Customer customer=new Customer(id,name,address,salary);
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="Update Customer Set name=? , address=? , salary=? where id=?";
        PreparedStatement pstm=connection.prepareStatement(SQL);


        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getAddress());
        pstm.setObject(3,customer.getSalary());
        pstm.setObject(4,customer.getId());
        int i =pstm.executeUpdate();
        loadTable();
        if(i>0) {
            System.out.println("Update Success");
            JOptionPane.showMessageDialog(null, "Update Success");
        }else {
            System.out.println("Failed");

        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean SQL=DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete from Customer where id='"+txtId.getText()+"'")>0;
        if(SQL){

            JOptionPane.showMessageDialog(null,"Delete Success");
        }
        loadTable();
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtSalary.setText(null);
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
}
