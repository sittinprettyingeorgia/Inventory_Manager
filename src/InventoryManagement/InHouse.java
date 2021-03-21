package InventoryManagement;

/**InHouse class is for inhouse parts*/
public class InHouse extends Part {
    private int machineId;
    /**create new inhouse part*/
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId=machineId;
    }
    /**set machine id for part*/
    public void setMachineId(int Id){
        machineId = Id;
    }
    /**get machine id for part*/
    public int getMachineId(){
        return machineId;
    }


}
