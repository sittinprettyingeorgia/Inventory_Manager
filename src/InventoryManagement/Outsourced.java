package InventoryManagement;

/** Outsourced is the class for outsourced parts*/
public class Outsourced extends Part {
    private String companyName;

    /**construct new outsourced part*/
    public Outsourced(int id, java.lang.String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName=companyName;
    }
    /**set company name of part*/
    public void setCompanyName(String name){
        companyName=name;
    }
    /**get company name of part*/
    public String getCompanyName(){
        return companyName;
    }

}
