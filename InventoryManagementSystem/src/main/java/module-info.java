/**
 * RUNTIME ERROR: JavaFX runtime error for missing requirements . Fixed by adding requires lines here.
 */
module johnathanbenge.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports johnathanbenge.inventorymanagementsystem;
    exports Controllers;
    opens Controllers;
    exports Models;
    opens Models;
    opens johnathanbenge.inventorymanagementsystem;
}