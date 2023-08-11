package Thogakade.controller;

import Thogakade.model.AddCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class OrderFormController implements Initializable {
    public ObservableList<AddCart> itemList = FXCollections.observableArrayList();
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
    public TableView tblTotalOrder;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colQty;

    private void loadAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> list= CustomerFormController.getAllCustomerIds();
        combCustomerId.getItems().addAll(list);
    }

    private void loadDate() {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }



    public void txtOrderDateOnAction(ActionEvent actionEvent) {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void combCustomerIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = combCustomerId.getSelectionModel().getSelectedItem().toString();
        txtCustomerName.setText(CustomerFormController.OrderSearchCustomer(customerId).getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createOrderId();
            loadAllCustomerIds();
            loadDate();
            loadAllItemIds();

            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
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

    public void btnNewOnAction(ActionEvent actionEvent) {

    }

    public void combItemCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ItemCode = combItemCode.getSelectionModel().getSelectedItem().toString();
        txtDescription.setText(ItemFormController.searchItem(ItemCode).getDescription());
        txtUnitPrice.setText(String.valueOf(ItemFormController.searchItem(ItemCode).getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(ItemFormController.searchItem(ItemCode).getQtyOnHand()));
    }

    private void loadAllItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> list= ItemFormController.getAllItemIds();
        combItemCode.getItems().addAll(list);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        String itemCode=combItemCode.getSelectionModel().getSelectedItem().toString();
        String description = txtDescription.getText();
        int qty= Integer.parseInt(txtQty.getText());
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        double total= unitPrice*qty;
        int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());

        if(qty<=qtyOnHand){
            AddCart code= new AddCart(itemCode,description,qty,unitPrice,total);

            if(!isAlreadyExists(code)) {
                itemList.add(code);
            }
            tblTotalOrder.setItems(itemList);
            tblTotalOrder.refresh();
            setTotal();
        }else{
            new Alert(Alert.AlertType.ERROR,"Quantity is Unavailable").show();

        }

    }

    private boolean isAlreadyExists(AddCart code) {
        for (AddCart i: itemList){

            if(code.getItemCode().equals(i.getItemCode())){
                i.setQty(i.getQty()+code.getQty());
                i.setTotal(i.getTotal()+code.getTotal());
                return true;
            }
        }
        return false;
    }

    public void setTotal(){
        int total=0;
        for (AddCart i:itemList) {
            total+=i.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        int selected = tblTotalOrder.getSelectionModel().getSelectedIndex();
        if(selected>-1){
            itemList.remove(selected);
            setTotal();
        }else{
            new Alert(Alert.AlertType.ERROR,"Error Found").show();
        }
    }
}
