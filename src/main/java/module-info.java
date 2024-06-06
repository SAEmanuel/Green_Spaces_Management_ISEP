module pt.ipp.isep.dei.esoft.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires AuthLib;
    requires jlayer;
    requires org.apache.commons.lang3;
    requires java.logging;
    requires java.prefs;

    opens pt.ipp.isep.dei.esoft.project.domain to javafx.base;
    exports pt.ipp.isep.dei.esoft.project.domain;
    opens pt.ipp.isep.dei.esoft.project.javaFX to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.javaFX;
    exports pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu;
    opens pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;
    opens pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu to javafx.fxml;
    exports pt.ipp.isep.dei.esoft.project.javaFX.alerts;
    opens pt.ipp.isep.dei.esoft.project.javaFX.alerts to javafx.fxml;

}