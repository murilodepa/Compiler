/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

module Compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens sample;
    opens analiseLexical;
    opens Utils;
    opens analiseSintatica;
    opens menuBar;
}