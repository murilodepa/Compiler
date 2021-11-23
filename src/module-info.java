module Compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens sample;
    opens analiseLexical;
    opens Utils;
    opens analiseSintatica;
}