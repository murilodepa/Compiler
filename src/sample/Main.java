/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package sample;

import analiseLexical.Token;
import analiseSintatica.Sintatico;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Main  extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));



        Sintatico sintatico = new Sintatico();
        primaryStage.setTitle("Compilador");
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefHeight(650);
        textArea.setPrefWidth(400);
        Label label = new Label("");
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Arquivo em branco");
        a.setContentText("Escolher um arquivo antes");

        final Menu menu = new Menu("Arquivo");
        final MenuItem abrir = new MenuItem("Abrir");

        menu.getItems().add(abrir);
        final Menu funcao = new Menu("Funções");
        final MenuItem compilar = new MenuItem("Compilar");
        funcao.getItems().add(compilar);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu, funcao);

        compilar.setOnAction(actionEvent ->  {
            try {
                try {
                    if(sintatico.getLexical().getArquivo()==null){
                        a.show();
                    } else {
                        sintatico.limpar();
                        sintatico.run();
                        label.setText("Sucesso!");
                    }
                } catch(Exception e) {
                    label.setText(e.getMessage());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        abrir.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                if(selectedFile!=null) {
                    char[] arquivo = new String(Files.readAllBytes(Paths.get(selectedFile.getPath())), StandardCharsets.UTF_8).toCharArray();
                    sintatico.getLexical().setArquivo(arquivo);
                    textArea.setText(String.copyValueOf(sintatico.getLexical().getArquivo()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(textArea, 5, 3);
        GridPane.setConstraints(label,5,4);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(12);
        inputGridPane.getChildren().addAll(textArea,label);

        VBox vbox = new VBox(menuBar,inputGridPane);
        primaryStage.setScene(new Scene(vbox, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        LinkedList<Token> tokens;
         launch(args);
    }


}