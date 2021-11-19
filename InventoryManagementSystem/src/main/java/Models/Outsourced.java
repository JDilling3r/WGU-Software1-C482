package Models;

/** Inherits from Parts class, getters and setters for Outsourced. **/
public class Outsourced extends Part {

    /** companyName variable declared as string. **/
    private String companyName;

    /**
     * Outsourced object new instance constructor.
     *
     * @param id ID for Outsourced Part.
     * @param name Name for Outsourced Part.
     * @param price Cost for the Outsourced Part.
     * @param stock Amount of Inventory for Outsourced Part.
     * @param min Minimum amount of Inventory allowed for OutSourced Part.
     * @param max Maximum amount of Inventory allowed for OutSourced Part.
     * @param companyName Name of company Outsourced Part came from.
     *
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Setter and Getter for companyName. **/
    public void setCompanyName (String companyName) { this.companyName = companyName; }
    public String getCompanyName() { return companyName; }

}
