module kong.frameselect {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.swing;
    requires  static lombok;
    exports kong.frameselect;
    exports kong.frameselect.gui;
    opens kong.frameselect.gui to javafx.fxml;
    exports kong.frameselect.framesplitter;
    opens kong.frameselect.framesplitter to javafx.fxml;
}