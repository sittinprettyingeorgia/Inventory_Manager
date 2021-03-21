package InventoryManagement;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/** Inventory class manages all parts and products available*/
public class Inventory {
    private static ObservableList<Part> allParts= FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**add part to inventory*/
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**add product to inventory*/
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**lookup part in inventory by id number*/
    public static Part lookupPart(int partId){
        Part res = null;
        for(Part part : allParts){
            if(part.getId()==partId)return part;
        }
        return res;

    }
    /**look up product in inventory by id number*/
    public static Product lookupProduct(int productId){
        Product res = null;
        for(Product product:allProducts) {
            if (product.getId() == productId) res = product;
        }
        return res;
    }
    /**look up part by name*/
    public static ObservableList<Part> lookupPart(String name){
        ObservableList<Part> res = FXCollections.observableArrayList();
        for(Part part:allParts) {
            if (part.getName().contains(name)) res.add(part);
        }
        return res;
    }
    /**look up product by name*/
    public static ObservableList<Product> lookupProduct(String name){
        ObservableList<Product> res = FXCollections.observableArrayList();
        for(Product product: allProducts) {
            if (product.getName().contains(name)) res.add(product);
        }
        return res;
    }
    /**update inventory part*/
    public static void updatePart(int index, Part part){
        allParts.set(index,part);
    }
    /**update product*/
    public static void updateProduct(int index,Product product){
        allProducts.set(index,product);
    }
    /**delete part from inventory*/
    public static boolean deletePart(Part part){
        int index =allParts.indexOf(part);
        Part deleted=allParts.remove(index);

        return deleted!=null;
    }
    /**delete product from inventory*/
    public static boolean deleteProduct(Product product){
        boolean res = false;
        if(allProducts.remove(product))res = true;
        return res;
    }
    /**get all parts in inventory*/
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**get all products in inventory*/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}
