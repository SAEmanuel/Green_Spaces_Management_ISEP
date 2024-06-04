module pt.ipp.isep.dei.esoft.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires AuthLib;
    requires jlayer;
    requires org.apache.commons.lang3;
    requires java.logging;

    opens pt.ipp.isep.dei.esoft.project.javaFX to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.javaFX;

}