package InventoryManagement;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/** Product class is the class for products. It manages a list or parts which make up a product*/
public class Product {
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;


    /**construct new product*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /**set product id*/
    public void setId(int id){
        this.id=id;
    }
    /**set product name*/
    public void setName(String name){
        this.name=name;
    }
    /**set product price*/
    public void setPrice(Double price){
        this.price=price;
    }
    /**set product stock*/
    public void setStock(int stock){
        this.stock=stock;
    }
    /**set min products*/
    public void setMin(int min){
        this.min=min;
    }
    /**set max products*/
    public void setMax(int max){
        this.max=max;
    }
    /**get product id*/
    public int getId(){
        return id;
    }
    /**get product name*/
    public String getName(){
        return name;
    }
    /**get product price*/
    public Double getPrice(){
        return price;
    }
    /**get stock of product*/
    public int getStock(){
        return stock;
    }
    /** get min number of product*/
    public int getMin(){
        return min;
    }
    /** get max number of product*/
    public int getMax(){
        return max;
    }
    /**get associated parts of product*/
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**delete associated part of product*/
    public boolean deleteAssociatedParts(Part part){
        boolean res = false;
        if(associatedParts.remove(part))res=true;
        return res;
    }
    /**get all associated parts for product*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
