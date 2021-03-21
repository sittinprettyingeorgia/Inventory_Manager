package InventoryManagement;

import java.util.ArrayList;

/**Data class holds data for transfer between controllers
 * */
public final class Data {

    private static Part part;
    private static Product product;
    private final static Data DATA = new Data();
    private static int partIds = 0;
    private static int productIds = 0;
    private Data() {}
    /**gets data element*/
    public static Data getData(){
        return DATA;
    }
    /** sets current data part element*/
    public static void setDataPart(Part myPart) {
        part=myPart;
    }
    /** sets current data product element*/
    public static void setDataProduct(Product myProduct){
        product=myProduct;
    }
    /**return current data product element*/
    public static Product getDataProduct(){
        return product;
    }
    /**return current data part element*/
    public static Part getDataPart() {
         return part;
    }
    /** returns id counter to generate unique part id number; add one to this value for unique part id*/
    public static int getPartIds(){
        return partIds;
    }
    /**sets the current value for unique part ids*/
    public static void setPartIds(int val){
        partIds = val;
    }
    /**returns id counter to generate unique product id number; add one to this value for unique product id*/
    public static int getProductIds(){
        return productIds;
    }
    /**sets the current value for unique product id*/
    public static void setProductIds(int val){
        productIds=val;
    }
}
