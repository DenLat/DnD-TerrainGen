module com.example.dndterraingen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.dndterraingen to javafx.fxml;
    exports com.example.dndterraingen;
}