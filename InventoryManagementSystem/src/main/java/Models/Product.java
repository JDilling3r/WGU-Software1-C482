package Models;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product new instance constructor
     *
     * @param id ID for Product.
     * @param name Name for Product.
     * @param price Cost of the Product.
     * @param stock Amount of Inventory of Product.
     * @param min Minimum amount of Inventory allowed for Product.
     * @param max Maximum amount of Inventory allowed for Product.
     *
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getters for Product variables **/
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setId(int id) { this.id = id; }
    public int getStock() { return stock; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public ObservableList<Part> getAssociatedParts(){ return associatedParts; }

    /** Setters for Product variables **/
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }

    /** adding Part association to Product **/
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /** Remove association of Part to Product **/
    public void removeAssociatedPart(Part associatedPart){
        associatedParts.remove(associatedPart);
    }
}
