package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Full Inventory for application. **/
public class Inventory {

    /** List of all Parts and Products saved in Inventory. **/
    private static final ObservableList<Part> parts = FXCollections.observableArrayList();
    private static final ObservableList<Product> products = FXCollections.observableArrayList();

    /** Adding Parts and Products to Inventory. **/
    /**
     * @param newPart
     * RUNITIME ERROR: Part ID is not increasing like product ID on save
     */
    public static void addPart(Part newPart) {
        parts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    /** Returns Parts matching ID or Name in Inventory. **/
    public static ObservableList<Part> LookUpPart(String partName)
    {
        ObservableList<Part> partResults = FXCollections.observableArrayList();

        for (Part p : parts){
            if (((p.getName()).toLowerCase()).contains(partName.toLowerCase())) {
                partResults.add(p);
            }
            return partResults;
        }
        return partResults;
    }
    public static Part lookUpPart(int partID) {
        Part partResult = null;

        for(Part p : parts){
            if(p.getId() == partID){
                partResult = p;
            }
        }
        return partResult;
    }

    /** Returns Products matching ID or containing pattern in Name in Inventory. **/
    public Product searchProduct(int productID) {
        Product productResult = null;
        for(Product i : products){
            if(i.getId() == productID){
                productResult= i;
            }
        }
        return productResult;
    }
    public ObservableList<Product> searchProducts(String productName) {
        ObservableList<Product> productResults = FXCollections.observableArrayList();
        for(Product i : products) {
            if(((i.getName()).toLowerCase()).contains(productName.toLowerCase())){
                productResults.add(i);
            }
        }
        return productResults;
    }

    /** Modify (replaces) Part / Product. **/
    public static void modifyPart(int index, Part part) {
        parts.set(index, part);
    }
    public static void modifyProduct(int index, Product product) {
        products.set(index, product);
    }

    /** Delete Part or Product from Inventory. **/
    public static void deletePart(Part part) {
        parts.remove(part);
    }
    public static void deleteProduct(Product product) {
        products.remove(product);
    }

    /** Return list of all Parts or Products in Inventory. **/
    public static ObservableList<Part> getParts()
    {
        return parts;
    }
    public static ObservableList<Product> getProducts()
    {
        return products;
    }

}
