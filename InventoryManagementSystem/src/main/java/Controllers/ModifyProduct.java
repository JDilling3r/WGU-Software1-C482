package Controllers;

import Models.Inventory;
import Models.Part;
import Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

/** Controller for modify Product Form. **/
public class ModifyProduct implements Initializable {

    Parent scene;
    Stage stage;

    private Product selectedProduct;
    int index;

    private ObservableList<Part> associatedParts = observableArrayList();
    private final ObservableList<Part> AssocPart = FXCollections.observableArrayList();
    private final ObservableList<Product> products = observableArrayList();


    @FXML
    private AnchorPane modifyProductForm;
    @FXML
    private TextField productID;
    @FXML
    private TextField productName;
    @FXML
    private Button modifyPartButton;
    @FXML
    private TextField productInventory;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMax;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productSearch;
    @FXML
    private TableView<Part> productParts;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventory;
    @FXML
    private TableColumn<Part, Double> partPrice;
    @FXML
    private TableView<Part> AssociatedParts;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartID;
    @FXML
    private TableColumn<Part, String> AssociatedPartName;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartInventory;
    @FXML
    private TableColumn<Part, Double> AssociatedPartPrice;

    /** Associated selected Part to Product. **/
    @FXML
    public void productAdd(ActionEvent event) {
        Part part = productParts.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        AssociatedParts.setItems(associatedParts);
    }

    /** Exit and return to Main Form. **/
    @FXML
    public void Cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setContentText("All unsaved progress will be lost.");
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/mainform.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Disassociation of part and removal from association table. **/
    @FXML
    public void productRemove(ActionEvent event) throws IOException {

        Part selectedAssociatedPart = AssociatedParts.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You do not have an associated part selected.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to remove the associated part?");
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            associatedParts.remove(selectedAssociatedPart);
            AssociatedParts.setItems(associatedParts);
        }
    }

    /** Saves product and updates Inventory.java List. **/
    @FXML
    public void saveProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(productID.getText());
            String name = productName.getText();
            int stock = Integer.parseInt(productInventory.getText());
            double price = Double.parseDouble(productPrice.getText());
            int min = Integer.parseInt(productMin.getText());
            int max = Integer.parseInt(productMax.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum value must be less than maximum value.");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory.java value must be between minimum and maximum.");
                alert.showAndWait();
                return;
            }

            Product modProduct = new Product(id,name,price,stock,min,max);
            for (Part part : associatedParts) {
                modProduct.addAssociatedPart(part);

            }

            Inventory.addProduct(modProduct);
            Inventory.deleteProduct(selectedProduct);

        } catch (NumberFormatException i) {

            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct data types.");
            alert.showAndWait();
            i.printStackTrace();
            return;
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/mainform.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void productSearch(ActionEvent event) {
    }

    /** data share between Main controller. **/
    public void getSelectedProduct(Product product){
        selectedProduct = product;
        productID.setText(Integer.toString(product.getId()));
        productName.setText(product.getName());
        productPrice.setText(Double.toString(product.getPrice()));
        productInventory.setText(Integer.toString(product.getStock()));
        productMax.setText(Integer.toString(product.getMax()));
        productMin.setText(Integer.toString(product.getMin()));
        associatedParts = product.getAssociatedParts();
        AssociatedParts.setItems(associatedParts);
    }

    /** Initializes and populates table. **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = new Product(0, null, 0.0, 0,0,0);
        associatedParts = selectedProduct.getAssociatedParts();
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productParts.setItems(Inventory.getParts());
        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedParts.setItems(selectedProduct.getAssociatedParts());
    }

    /** Part search button. **/
    public void partSearchButton(ActionEvent actionEvent) {
        String s = productSearch.getText();
        ObservableList<Part> parts = searchParts(s);
        productParts.setItems(parts);
        productSearch.setText("");
    }

    /** Case-insensitive / partial match search given Name for part. **/
    private ObservableList<Part> searchParts(String partialPart) {
        ObservableList<Part> nameParts = observableArrayList();
        ObservableList<Part> allParts = Inventory.getParts();
        String i = productSearch.getText().toLowerCase();
        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(i) || p.getName().toLowerCase().contains(partialPart)) {
                nameParts.add(p);
            }
        }
        if (nameParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
        return nameParts;
    }

}


