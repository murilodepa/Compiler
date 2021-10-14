/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package sample;

import analiseSintatica.Sintatico;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main  extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));



        Sintatico sintatico = new Sintatico();
        primaryStage.setTitle("Compilador");


        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icones/CompilerIcon.png"))));
        TextArea textArea = new TextArea();
        TextField textAreaPath = new TextField();
        textArea.setEditable(false);
        textAreaPath.setEditable(false);
        textArea.setPrefHeight(650);
        textArea.setPrefWidth(520);
        textAreaPath.setPrefHeight(5);
        textAreaPath.setMaxWidth(300);
        Label labelMensagem = new Label("");
        Label labelArquivo = new Label("");
        Label labelCodigo = new Label("Código: ");
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);


        textAreaPath.setPrefColumnCount(1);
        labelArquivo.setText("Arquivo: ");
        labelArquivo.setFont(new Font("Roboto", 16));
        labelArquivo.setPrefWidth(70);
        labelArquivo.setStyle("-fx-font-weight: bold");
        labelCodigo.setStyle("-fx-font-weight: bold");
        labelMensagem.setStyle("-fx-font-weight: bold");

        labelMensagem.setFont(new Font("Roboto", 13));
        labelCodigo.setFont(new Font("Roboto", 16));
        textAreaPath.setFont(new Font("Roboto", 13));
        textArea.setFont(new Font("Roboto", 13));


        labelCodigo.setTextFill(Color.rgb(0, 33, 115));
        labelArquivo.setTextFill(Color.rgb(0, 33, 115));
        textAreaPath.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
        textArea.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));


        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Arquivo em branco");
        a.setContentText("Escolher um arquivo antes");

        final Menu menuArquivo = new Menu("Arquivo");
        final MenuItem abrir = new MenuItem("Abrir");

        menuArquivo.getItems().add(abrir);
        final Menu menuFuncaos = new Menu("Funções");



        final MenuItem compilar = new MenuItem("Compilar");
        menuFuncaos.getItems().add(compilar);



        MenuBar menuBar = new MenuBar();
        menuArquivo.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");
        menuFuncaos.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");
        menuBar.getMenus().addAll(menuArquivo, menuFuncaos);

        compilar.setOnAction(actionEvent ->  {
            try {
                try {
                    if(sintatico.getLexical().getArquivo()==null){
                        a.show();
                    } else {
                        sintatico.limpar();
                        sintatico.run();
                        labelMensagem.setTextFill(Color.GREEN);
                        labelMensagem.setText("Sucesso!");
                    }
                } catch(Exception e) {
                    labelMensagem.setTextFill(Color.rgb(255,0,0));
                    labelMensagem.setText(e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        abrir.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                if(selectedFile!=null) {
                    Path path;
                    path = Paths.get(selectedFile.getPath());
                    char[] arquivo = Files.readString(path).toCharArray();
                    textAreaPath.setText(path.toString());
                    sintatico.getLexical().setArquivo(arquivo);
                    textArea.setText(String.copyValueOf(sintatico.getLexical().getArquivo()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(labelArquivo,4,2);
        GridPane.setConstraints(textAreaPath, 5, 2);
        GridPane.setConstraints(labelCodigo, 5, 5);
        GridPane.setConstraints(textArea, 5, 6);
        GridPane.setConstraints(labelMensagem,5,7);
        inputGridPane.setHgap(5);
        inputGridPane.setVgap(10);
        inputGridPane.setGridLinesVisible(false);
        inputGridPane.getChildren().addAll(labelArquivo, textAreaPath, textArea,labelMensagem,labelCodigo);
        VBox vbox = new VBox(menuBar,inputGridPane);
        String colorString = "#B7D5E5";
        vbox.setStyle("-fx-background-color:" + colorString + "; -fx-fill:" + colorString+";");
        primaryStage.setScene(new Scene(vbox, 710, 860));
        primaryStage.show();
    }

    public static void main(String[] args) {
         launch(args);
    }
}