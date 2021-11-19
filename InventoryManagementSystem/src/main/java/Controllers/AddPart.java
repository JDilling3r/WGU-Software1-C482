package Controllers;

import Models.*;
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

/** Controller for Add Part FXML form. **/
public class AddPart implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private AnchorPane addPartForm;
    /** Label displays either MachineID or CompanyName **/
    @FXML
    private Label KeyLabel;
    @FXML
    private ToggleGroup addPartRadio;
    @FXML
    private RadioButton addInHousePart;
    @FXML
    private RadioButton addOutSourcedPartRadio;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartStock;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartKeyLabel;

    /** Cancel and return. **/
    @FXML
    public void addPartCancel(ActionEvent event) throws IOException {
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

    /** Setting field name to Machine ID if InHouse. **/
    @FXML
    public void addInhousePartForm(ActionEvent event) {
        KeyLabel.setText("Machine ID");
    }

    /** Setting field name to Company Name for OutSourced. **/
    @FXML
    public void addOutSourcedPartForm(ActionEvent event) {
        KeyLabel.setText("Company Name");
    }

    /** Adding saved part to Inventory.java list.
     * RUNTIME ERROR: Part ID not incrementing correctly.
     * Fixed with reworking for loop.
     * **/
    public void addPartSave(ActionEvent event) throws IOException {
        int id = 0;
        for(Part part : Inventory.getParts()){
            if (part.getId() > id) {
                id = part.getId();
            }
        }
        ++id;

        try {
            addPartID.setText(String.valueOf(++id));
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartStock.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum must have a value less than maximum");
                alert.showAndWait();
                return;
            }

            if(stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inv value must be between min and max");
                alert.showAndWait();
                return;
            }


            Part part;
            if(addInHousePart.isSelected()) {
                part = new InHouse(id, "", 0.0, 0, 0, 0, 0);
                part.setName(addPartName.getText());
                part.setPrice(Double.parseDouble(addPartPrice.getText()));
                part.setStock(Integer.parseInt(addPartStock.getText()));
                part.setMin(Integer.parseInt(addPartMin.getText()));
                part.setMax(Integer.parseInt(addPartMax.getText()));
                ((InHouse) part).setMachineID(Integer.parseInt(addPartKeyLabel.getText()));

                Inventory.addPart(part);
                System.out.println("InHouse");


            }
            else {

                part = new Outsourced(id, "", 0.0, 0, 0, 0, "");
                part.setName(addPartName.getText());
                part.setPrice(Double.parseDouble(addPartPrice.getText()));
                part.setStock(Integer.parseInt(addPartStock.getText()));
                part.setMin(Integer.parseInt(addPartMin.getText()));
                part.setMax(Integer.parseInt(addPartMax.getText()));
                ((Outsourced) part).setCompanyName(addPartKeyLabel.getText());

                Inventory.addPart(part);
                System.out.println("OutSourced");

            }
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/johnathanbenge/inventorymanagementsystem/mainform.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException i) {
            i.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Error");
            alert.setContentText("Please enter correct value types.");
            alert.showAndWait();

        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

