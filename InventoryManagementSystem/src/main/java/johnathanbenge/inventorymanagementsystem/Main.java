package johnathanbenge.inventorymanagementsystem;

/**
 *
 * @author Benge Johanthan
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Models.InHouse;
import Models.Outsourced;
import Models.Product;
import static Models.Inventory.addPart;
import static Models.Inventory.addProduct;

/**
 *
 * This application is used for management of a company's inventory parts and products.
 *
 * FUTURE ENHANCEMENTS:
 * A method to check if part or product already exists to dissallow multiple items in table that are the same.
 * Adding multiple parts and products at once.
 * One search bar that can search either products OR parts.
 * A page specifically used to show break out of all products and tree structure to show parts associated.
 * Use a database to load up and save inventory after closing application for persistence.
 * Add further data management implementation for data analytics - products sold , dates sold etc.
 *
 **/

/** Entry point of application FXML stage created here and scene loaded **/
public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
/**
 * RUNTIME ERROR: Had multiple errors with the class.getResource method
 * Used java.net URL method to see if it was actually getting the FXML file
 * moved FXML files from its own package to resources. where they are available under where main function lives.
 */
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainform.fxml"));
        Parent root;
        root = fxmlLoader.load();
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /** @param args: added test data **/
    public static void main(String[] args) {

        InHouse strings = new InHouse(1,"Strings", 12.95, 6, 1, 10,1);
        Outsourced tuningKnobs = new Outsourced(3,"Tuning Knobs", 34.24, 12, 1,20,"Fender");
        Product telecaster = new Product( 2, "Telecaster", 1200.69, 3, 1, 9);
        Product lesPaul = new Product(4,"Les Paul", 2300.41, 2,1,9);

        addProduct(lesPaul);
        addPart(strings);
        addPart(tuningKnobs);
        addProduct(telecaster);
        telecaster.addAssociatedPart(tuningKnobs);

        launch(args);
    }

}

