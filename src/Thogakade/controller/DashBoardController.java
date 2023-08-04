package Thogakade.controller;

import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashBoardController {
    public AnchorPane root;
    @FXML


    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/Thogakade/view/CustomerForm.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        boolean add;
        if (this.root.getChildren().add(load)) add = true;
        else add = false;
    }



    public void orderOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void itemOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/Thogakade/view/ItemForm.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.root.getChildren().clear();
        boolean add;
        if (this.root.getChildren().add(load)) add = true;
        else add = false;
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
