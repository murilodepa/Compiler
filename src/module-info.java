
module Compiler {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens sample;
    opens analiseLexical;
    opens Utils;
    opens analiseSintatica;
}