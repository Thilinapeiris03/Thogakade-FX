package Thogakade.model;

import java.util.ArrayList;

public class Order {
    private String id;
    private String date;
    private String customerId;
    private ArrayList list;

    public Order() {
    }

    public Order(String id, String date, String customerId, ArrayList list) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }
}
