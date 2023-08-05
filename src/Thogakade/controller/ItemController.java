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

public class ItemController implements Initializable {
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TextField txtQtyOnHand;
    public TextField txtItemcode;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TableView tblItem;

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtItemcode.getText();
        String description=txtDescription.getText();
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand= (int) Double.parseDouble(txtQtyOnHand.getText());

        //System.out.println(id+"\t"+name+"\t"+address+"\t"+salary);

        Item item=new Item(code,description,unitPrice,qtyOnHand);
        Connection connection= DBConnection.getInstance().getConnection();
        String SQL="Insert into Item Values(?,?,?,?)";
        PreparedStatement pstm=connection.prepareStatement(SQL);

        pstm.setObject(1,item.getItemCode());
        pstm.setObject(2,item.getDescription());
        pstm.setObject(3,item.getUnitPrice());
        pstm.setObject(4,item.getQtyOnHand());
        int i =pstm.executeUpdate();
        loadTable();
        tblItem.refresh();

        if(i>0) {
            System.out.println("Added Success");
            JOptionPane.showMessageDialog(null, "Added Success");
        }else {
            System.out.println("Failed");

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code=txtItemcode.getText();
        String description=txtDescription.getText();
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand= (int) Double.parseDouble(txtQtyOnHand.getText());

        //System.out.println(id+"\t"+name+"\t"+address+"\t"+salary);

        Item item=new Item(code,description,unitPrice,qtyOnHand);
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="Update Item Set description=? , unitPrice=? , qtyOnHand=? where code=?";
        PreparedStatement pstm=connection.prepareStatement(SQL);

        pstm.setObject(1,item.getDescription());
        pstm.setObject(2,item.getUnitPrice());
        pstm.setObject(3,item.getQtyOnHand());
        pstm.setObject(4,item.getItemCode());

        int i =pstm.executeUpdate();
        loadTable();
        tblItem.refresh();
        if(i>0) {
            System.out.println("Update Success");
            JOptionPane.showMessageDialog(null, "Update Success");
        }else {
            System.out.println("Failed");

        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean SQL=DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete from Item where code='"+txtItemcode.getText()+"'")>0;
        if(SQL){

            JOptionPane.showMessageDialog(null,"Delete Success");
        }
        loadTable();
        txtItemcode.setText(null);
        txtDescription.setText(null);
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
    }

    public void btnRefresh(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable,
                                                                            oldValue,
                                                                            newValue) -> {
            if(newValue!=null) {
                setTableValuesToTxt((Item) newValue);
            }
        });
    }

    private void setTableValuesToTxt(Item newValue) {
        txtItemcode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
    }

    public void loadTable(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        String SQL="Select * from Item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        Connection connection= null;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()){
                Item item = new Item(resultSet.getString(1), resultSet.getString(2),resultSet.getDouble(3), resultSet.getInt(4));
                list.add(item);
            }
            tblItem.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
/*
    private void setTableValuesToTxt(Item newValue) {
        txtItemcode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
    }

    public void loadTable(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        String SQL="Select * from Item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        Connection connection= null;
        try {
            connection = DBConnection.getInstance().getConnection();

            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()){
                Item item = new Item(resultSet.getString(1), resultSet.getString(2),resultSet.getDouble(3), resultSet.getInt(4));
                list.add(item);
            }
            tblItem.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

}
