package Models;

/** Inherits from Parts class. **/
public class InHouse extends Part {

    /** MachineID variable declared as integer. **/
    private int machineID;

    /**
     * InHouse new instance constructor.
     *
     * @param id ID for InHouse part.
     * @param name Name for InHouse part.
     * @param price Cost for the InHouse part.
     * @param stock Amount of Inventory for InHouse part.
     * @param min Minimum amount of Inventory allowed for InHouse part.
     * @param max Maximum amount of Inventory allowed for InHouse part.
     *
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** Getter and Setter for machineID of part **/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    public int getMachineID() {
        return machineID;
    }

}
