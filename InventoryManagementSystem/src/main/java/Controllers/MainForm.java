package Controllers;

import Controllers.ModifyPart;
import Controllers.ModifyProduct;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for Main FXML form. **/
public class MainForm implements Initializable {

    Parent scene;
    Stage stage;


    @FXML
    private Button partSearch;
    @FXML
    private Button productSearch;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;
    @FXML
    private TableColumn<Part, Double> partPrice;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevel;
    @FXML
    private TableColumn<Product, Double> productPrice;
    @FXML
    private Label Message;
    @FXML
    private TextField searchPart;
    @FXML
    private TextField searchProduct;

    @FXML
    void partSearchBox(ActionEvent event) {
        System.out.println("part search box clicked");
    }

    @FXML
    void productSearchBox(ActionEvent event) {
        System.out.println("product search box clicked");
    }

    /** Buttons to take you to add Part or Product Forms. **/
    @FXML
    public void addPartButton(ActionEvent event) throws IOException {
        System.out.println("On part Add Clicked");
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/addpartform.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void addProductButton(ActionEvent event) throws IOException {
        System.out.println("On part Add Clicked");
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/addproductform.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Button to remove part from inventory. **/
    @FXML
    public void removePartButton(ActionEvent event) {
        System.out.println("part delete Clicked");
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Part!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE");
        alert.setContentText("Are you sure you want to delete the selected part?");
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }

    }

    /** Button to delete product, contingent on if product has associated part and if user confirms deletion. **/
    @FXML
    public void removeProductButton(ActionEvent event) {
        System.out.println("product delete Clicked");
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Product!");
            alert.showAndWait();
            return;
        }

        ObservableList<Part> associatedParts = selectedProduct.getAssociatedParts();
        if (associatedParts.size() >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Selected Product has part association, modify to delete.");
            alert.showAndWait();

        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }

        }

    }

    /** Button to close application. **/
    @FXML
    public void EXIT(ActionEvent event) {
        System.out.println("system exit button clicked");
        System.exit(0);

    }

    /** Button to modify Part **/
    @FXML
    public void modifyPartButton(ActionEvent event) throws IOException {
        System.out.println("main part modify Clicked");

        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        //int index = partstable.getSelectionModel().getSelectedIndex();

        if(selectedPart == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Part first!");
            alert.showAndWait();

            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/johnathanbenge/inventorymanagementsystem/modifypartform.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ModifyPart controller = loader.getController();
        controller.getSelectedPart(partTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    /** Button to modify Product. **/
    @FXML
    public void modifyProductButton(ActionEvent event) throws IOException {
        System.out.println("main product modify Clicked");
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a Product!");
            alert.showAndWait();
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/johnathanbenge/inventorymanagementsystem/modifyproductform.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ModifyProduct controller = loader.getController();
        controller.getSelectedProduct(productTable.getSelectionModel().getSelectedItem());
        stage.show();

    }

    /** Initialize and populate Product table. **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getProducts());
        productID.setCellValueFactory((new PropertyValueFactory<>("id")));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Search for parts Button. **/
    public void searchPartButton(ActionEvent actionEvent) {
        String i = searchPart.getText();
        ObservableList<Part> parts = searchParts(i);
        partTable.setItems(parts);
        searchPart.setText("");
    }

    /** Case-insensitive partial match search for parts. */
    private ObservableList<Part> searchParts(String partialPart) {
        ObservableList<Part> nameParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getParts();
        String i = searchPart.getText().toLowerCase();
        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(i) || p.getName().toLowerCase().contains(partialPart)) {
                nameParts.add(p);
            }
        }
        if (nameParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
        return nameParts;
    }

    /** Product search Button. **/
    public void productSearchButton(ActionEvent actionEvent) {
        String s = searchProduct.getText();

        ObservableList<Product> products = searchProduct(s);

        productTable.setItems(products);
        searchProduct.setText("");
    }
    /** Case-insensitive partial match search for products. **/
    private ObservableList<Product> searchProduct(String partialProduct) {
        ObservableList<Product> nameProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getProducts();
        String i = searchProduct.getText().toLowerCase();
        for (Product p : allProducts) {
            if (String.valueOf(p.getId()).contains(i) || p.getName().toLowerCase().contains(partialProduct)) {
                nameProducts.add(p);
            }
        }
        if (nameProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        }
        return nameProducts;
    }

}



