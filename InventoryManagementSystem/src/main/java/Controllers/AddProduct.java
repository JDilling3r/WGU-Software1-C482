package Controllers;

import Models.*;
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

/** Controller for Add Product FXML form. **/
public class AddProduct implements Initializable {

    @FXML
    private AnchorPane addProductForm;
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductStock;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField productSearch;
    @FXML
    private TableView<Part> addProductPart;
    @FXML
    private TableColumn<Part, Integer> addPartID;
    @FXML
    private TableColumn<Part, String> addPartName;
    @FXML
    private TableColumn<Part, Integer> addPartStock;
    @FXML
    private TableColumn<Part, Double> addPartPrice;
    @FXML
    private TableView<Part> AssociatedParts;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartID;
    @FXML
    private TableColumn<Part, String> AssociatedPartName;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartStock;
    @FXML
    private TableColumn<Part, Double> AssociatedPartPrice;

    Parent scene;
    Stage stage;

    public Part associatePart;
    private final ObservableList<Part>associatedParts = FXCollections.observableArrayList();

    /** Add selected part from table to Associated table. **/
    public void addProduct(ActionEvent event) {

        Part part = addProductPart.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        AssociatedParts.setItems(associatedParts);
    }

    /** Cancel and return. **/
    public void addProductCancel(ActionEvent event) throws IOException {

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


    /** Deletes product from association table and removes association. **/
    @FXML
    public void removePartAssociation(ActionEvent event) throws IOException {
        Part selectedAssociatedPart = AssociatedParts.getSelectionModel().getSelectedItem();

        if(selectedAssociatedPart == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an Associated Part!");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove");
        alert.setContentText("Are you sure you want to remove the associated part?");
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            associatedParts.remove(selectedAssociatedPart);
        }
    }

    /** save Product and add to Inventory.java list
     *
     * RUNTIME ERROR: Product ID not incrementing correctly.
     * Fixed with reworking for loop.
     * **/
    @FXML
    public void addProductSave(ActionEvent event) throws IOException {
        int id = 0;
        for (Product product : Inventory.getProducts()) {
            if (product.getId() > id)
                id = product.getId();
        }

        try {
            addPartID.setText(String.valueOf(++id));
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductStock.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("MIN value cannot be greater than MAX");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory.java value must be between min-max range.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, " ", 0.0, 0, 0, 0);
            newProduct.setName(addProductName.getText());
            newProduct.setPrice(Double.parseDouble(addProductPrice.getText()));
            newProduct.setStock(Integer.parseInt(addProductStock.getText()));
            newProduct.setMin(Integer.parseInt(addProductMin.getText()));
            newProduct.setMax(Integer.parseInt(addProductMax.getText()));

            Inventory.addProduct(newProduct);

            for(Part part : associatedParts)
            {
                newProduct.addAssociatedPart(part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/mainform.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (NumberFormatException i) {
            i.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Please enter correct value types.");
            alert.showAndWait();
        }
    }

    /** Populating tables **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPart.setItems(Inventory.getParts());
        AssociatedParts.setItems(associatedParts);


        addPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** Search for parts on product screen. **/
    public void productSearch(ActionEvent actionEvent) {

        String s = productSearch.getText();

        ObservableList<Part> parts = searchParts(s);

        addProductPart.setItems(parts);
        productSearch.setText("");
    }

    /** Case-insensitive partial match search for Part Names and IDs. **/
    private ObservableList<Part> searchParts(String part) {
        ObservableList<Part> nameParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getParts();

        String s = productSearch.getText().toLowerCase();

        for (Part i : allParts) {
            if (String.valueOf(i.getId()).contains(s) || i.getName().toLowerCase().contains(part)) {
                nameParts.add(i);
            }

        }
        if (nameParts.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found!");
            alert.showAndWait();

        }
        return nameParts;
    }

}
