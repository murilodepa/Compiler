package com.example.maquinavirtual;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Máquina Virtual");
        //primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icones/CompilerIcon.png"))));

        TextArea areaTextoCodigo = new TextArea();
        areaTextoCodigo.setEditable(true);
        areaTextoCodigo.setFont(new Font("Roboto", 13));
        areaTextoCodigo.setPrefHeight(500);
        areaTextoCodigo.setPrefWidth(600);
        areaTextoCodigo.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));

        TextArea areaTextoSaida = new TextArea();
        areaTextoSaida.setEditable(true);
        areaTextoSaida.setFont(new Font("Roboto", 13));
        areaTextoSaida.setPrefHeight(100);
        areaTextoSaida.setMaxWidth(300);
        areaTextoSaida.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));

        TextField areaTextoCaminhoArquivo = new TextField();
        areaTextoCaminhoArquivo.setEditable(false);
        areaTextoCaminhoArquivo.setFont(new Font("Roboto", 13));
        areaTextoCaminhoArquivo.setPrefHeight(5);
        areaTextoCaminhoArquivo.setMaxWidth(300);
        areaTextoCaminhoArquivo.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));

        Label labelArquivo = new Label("");
        labelArquivo.setText("Arquivo: ");
        labelArquivo.setStyle("-fx-font-weight: bold");
        labelArquivo.setFont(new Font("Roboto", 16));
        labelArquivo.setPrefWidth(70);
        labelArquivo.setTextFill(Color.rgb(0, 33, 115));

        Label labelSaida = new Label("Saída de Dados: ");
        labelSaida.setStyle("-fx-font-weight: bold");
        labelSaida.setFont(new Font("Roboto", 16));
        labelSaida.setTextFill(Color.rgb(0, 33, 115));

        Label labelCodigo = new Label("Código de Máquina: ");
        labelCodigo.setStyle("-fx-font-weight: bold");
        labelCodigo.setFont(new Font("Roboto", 16));
        labelCodigo.setTextFill(Color.rgb(0, 33, 115));

        Label labelMensagem = new Label("");
        labelMensagem.setStyle("-fx-font-weight: bold");
        labelMensagem.setFont(new Font("Roboto", 13));

        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Arquivo em branco");
        alerta.setContentText("Escolher um arquivo antes");

        Alert alertaTerminou = new Alert(Alert.AlertType.INFORMATION);
        alertaTerminou.setTitle("Máquina Virtual");
        alertaTerminou.setContentText("O Programa terminou de executar!");
        //alertaTerminou.show();



        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Entrada");
        textInputDialog.setHeaderText("Entre com o dado: ");
        //textInputDialog.show();
        // show the text input dialog
        /*textInputDialog.showAndWait();
        try{
            Integer.parseInt(textInputDialog.getEditor().getText());
        }catch(NumberFormatException e){
            System.out.println("A entrada de dados não contém apenas números!");
        } */


        final Menu menuArquivo = new Menu("Arquivo");
        final MenuItem abrir = new MenuItem("Abrir");
        menuArquivo.getItems().add(abrir);
        menuArquivo.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");

        final Menu menuFuncaos = new Menu("Funções");
        final MenuItem executar = new MenuItem("Executar");
        menuFuncaos.getItems().add(executar);
        menuFuncaos.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");

        /* Definindo Menu Bar com menu arquivos e funções */
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuArquivo, menuFuncaos);

        /* Definindo o posicionamento dos itens criados na tela principal */
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(labelArquivo, 4, 2);
        GridPane.setConstraints(areaTextoCaminhoArquivo, 5, 2);
        GridPane.setConstraints(labelCodigo, 5, 5);
        GridPane.setConstraints(labelSaida, 5, 8);
        GridPane.setConstraints(areaTextoCodigo, 5, 6);
        GridPane.setConstraints(areaTextoSaida, 5, 9);
        GridPane.setConstraints(labelMensagem, 5, 7);
        inputGridPane.setHgap(5);
        inputGridPane.setVgap(10);
        inputGridPane.setGridLinesVisible(true);
        inputGridPane.getChildren().addAll(labelArquivo, areaTextoCaminhoArquivo, areaTextoCodigo, areaTextoSaida, labelMensagem, labelCodigo, labelSaida);
        VBox vbox = new VBox(menuBar, inputGridPane);
        String colorString = "#B7D5E5";
        vbox.setStyle("-fx-background-color:" + colorString + "; -fx-fill:" + colorString + ";");
        primaryStage.setScene(new Scene(vbox, 790, 850));
        primaryStage.setResizable(false);
        primaryStage.show();

        /* Se o usuário selecionar a opção abrir*/
        abrir.setOnAction(actionEvent -> abrirArquivo(fileChooser, primaryStage, areaTextoCaminhoArquivo, areaTextoCodigo));
        areaTextoCodigo.setOnInputMethodTextChanged(InputMethodEvent -> System.out.println("\n aabbbb"));

    }

    /**
     * Método responsável por abrir o arquivo
     */
    public void abrirArquivo(FileChooser fileChooser, Stage primaryStage, TextField areaTextoCaminhoArquivo, TextArea areaTextoCodigo) {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        try {
            if (selectedFile != null) {
                Path caminhoDoArquivo;
                caminhoDoArquivo = Paths.get(selectedFile.getPath());
                String arquivo = Files.readString(caminhoDoArquivo);
                areaTextoCaminhoArquivo.setText(caminhoDoArquivo.toString());
                areaTextoCodigo.setText(arquivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}