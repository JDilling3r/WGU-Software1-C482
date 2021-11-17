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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for modify Part FXML form. **/
public class ModifyPart implements Initializable {

    private final ObservableList<Part> parts = FXCollections.observableArrayList();

    Parent scene;
    Stage stage;
    private int index;
    private Part selectedPart;

    @FXML
    private AnchorPane addPartForm;
    @FXML
    private ToggleGroup partToggle;
    /** This label will change between Machine ID and Company Name. */
    @FXML
    private Label labelKey;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outSourceRadio;
    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInventory;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMachineID;
    @FXML
    private TextField partMin;

    /** Set label to Company name. **/
    @FXML
    public void outSourcedForm(ActionEvent event) {
        labelKey.setText("Company Name");
    }
    /** Set label to Machine ID **/
    @FXML
    public void inHouseForm(ActionEvent event) {
        labelKey.setText("Machine ID");
    }

    /** Cancel and return to Main Form. **/
    @FXML
    public void cancelButton(ActionEvent event) throws IOException {
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

    /** Delete the part specified for modify, and adds "modified" part as new part. **/
    @FXML
    public void saveModifiedPart(ActionEvent event) throws IOException {
        String name = partName.getText();
        int stock = Integer.parseInt(partInventory.getText());
        double price = Double.parseDouble(partPrice.getText());
        int min = Integer.parseInt(partMin.getText());
        int max = Integer.parseInt(partMax.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Minimum value must be less than Maximum.");
            alert.showAndWait();
            return;
        }

        if (stock > max || stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inv value must be between min and max values.");
            alert.showAndWait();
            return;
        }

        if (partToggle.getSelectedToggle().equals(inHouseRadio)) {
            Part modParts = new InHouse(Integer.parseInt(partID.getText()),
                    partName.getText(),
                    Double.parseDouble(partPrice.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    Integer.parseInt(partMachineID.getText()));

            Inventory.addPart(modParts);
            Inventory.deletePart(selectedPart);
        }

        if (partToggle.getSelectedToggle().equals(outSourceRadio)) {
            Part modParts = new Outsourced(Integer.parseInt(partID.getText()),
                    partName.getText(),
                    Double.parseDouble(partPrice.getText()),
                    Integer.parseInt(partInventory.getText()),
                    Integer.parseInt(partMin.getText()),
                    Integer.parseInt(partMax.getText()),
                    partMachineID.getText());

            Inventory.addPart(modParts);
            Inventory.deletePart(selectedPart);

        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/mainform.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** Check for part being InHouse or OutSourced. **/
    public void getSelectedPart(Part part) {
        selectedPart = part;
        partID.setText(String.valueOf(selectedPart.getId()));
        partName.setText(selectedPart.getName());
        partInventory.setText(String.valueOf(selectedPart.getStock()));
        partPrice.setText(String.valueOf(selectedPart.getPrice()));
        partMax.setText(String.valueOf(selectedPart.getMax()));
        partMin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            labelKey.setText("Machine ID");
            partMachineID.setText(String.valueOf(((InHouse)selectedPart).getMachineID()));

        }

        if (selectedPart instanceof Outsourced) {
            outSourceRadio.setSelected(true);
            labelKey.setText("Company Name");
            partMachineID.setText(((Outsourced) selectedPart).getCompanyName());

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
