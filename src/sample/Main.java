/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package sample;

import analiseSintatica.Sintatico;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main extends Application {

    private final static String GITHUB_LINK = "https://github.com/murilodepa/Compiler";

    @Override
    public void start(Stage primaryStage) throws Exception {

        Sintatico sintatico = new Sintatico();
        primaryStage.setTitle("Compilador");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icones/CompilerIcon.png"))));

        TextArea areaTextoCodigo = new TextArea();
        areaTextoCodigo.setEditable(true);
        areaTextoCodigo.setFont(new Font("Roboto", 13));
        areaTextoCodigo.setPrefHeight(650);
        areaTextoCodigo.setPrefWidth(520);
        areaTextoCodigo.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));

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

        Label labelCodigo = new Label("Código: ");
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

        final Menu menuArquivo = new Menu("Arquivo");
        final MenuItem abrir = new MenuItem("Abrir");
        menuArquivo.getItems().add(abrir);
        menuArquivo.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");

        final Menu menuFuncaos = new Menu("Funções");
        final MenuItem compilar = new MenuItem("Compilar");
        menuFuncaos.getItems().add(compilar);
        menuFuncaos.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");

        final Menu menuSobre = new Menu("Sobre");
        final MenuItem github = new MenuItem("GitHub");
        menuSobre.getItems().add(github);
        menuSobre.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-family: roboto");

        /* Definindo Menu Bar com menu arquivos e funções */
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuArquivo, menuFuncaos, menuSobre);

        /* Definindo o posicionamento dos itens criados na tela principal */
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(labelArquivo, 4, 2);
        GridPane.setConstraints(areaTextoCaminhoArquivo, 5, 2);
        GridPane.setConstraints(labelCodigo, 5, 5);
        GridPane.setConstraints(areaTextoCodigo, 5, 6);
        GridPane.setConstraints(labelMensagem, 5, 7);
        inputGridPane.setHgap(5);
        inputGridPane.setVgap(10);
        inputGridPane.setGridLinesVisible(false);
        inputGridPane.getChildren().addAll(labelArquivo, areaTextoCaminhoArquivo, areaTextoCodigo, labelMensagem, labelCodigo);
        VBox vbox = new VBox(menuBar, inputGridPane);
        String colorString = "#B7D5E5";
        vbox.setStyle("-fx-background-color:" + colorString + "; -fx-fill:" + colorString + ";");
        primaryStage.setScene(new Scene(vbox, 710, 860));
        primaryStage.setResizable(false);
        primaryStage.show();

        /* Se o usuário selecionar a opção abrir*/
        abrir.setOnAction(actionEvent -> abrirArquivo(fileChooser, primaryStage, areaTextoCaminhoArquivo, sintatico, areaTextoCodigo));

        /* Se o usuário selecionar a opção compilar */
        compilar.setOnAction(actionEvent -> compilarPrograma(sintatico, alerta, labelMensagem));

        /* Se o usuário selecionar a tecla 'F9' */
        vbox.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.F9)) {
                compilarPrograma(sintatico, alerta, labelMensagem);
            }
        });

        /* Se o usuário selecionar a opção GitHub */
        github.setOnAction(actionEvent -> openGitHub());
    }

    /**
     * Método responsável por realizar a validação e compilação do código de entrada
     */
    public void compilarPrograma(Sintatico sintatico, Alert alerta, Label labelMensagem) {
        try {
            try {
                if (sintatico.getLexical().getArquivo() == null) {
                    alerta.show();
                } else {
                    sintatico.limpar();
                    sintatico.run();
                    labelMensagem.setTextFill(Color.GREEN);
                    labelMensagem.setText("Sucesso!");
                }
            } catch (Exception e) {
                labelMensagem.setTextFill(Color.rgb(255, 0, 0));
                labelMensagem.setText(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método responsável por abrir o arquivo
     */
    public void abrirArquivo(FileChooser fileChooser, Stage primaryStage, TextField areaTextoCaminhoArquivo, Sintatico sintatico, TextArea areaTextoCodigo) {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        try {
            if (selectedFile != null) {
                Path caminhoDoArquivo;
                caminhoDoArquivo = Paths.get(selectedFile.getPath());
                char[] arquivo = Files.readString(caminhoDoArquivo).toCharArray();
                areaTextoCaminhoArquivo.setText(caminhoDoArquivo.toString());
                sintatico.getLexical().setArquivo(arquivo);
                areaTextoCodigo.setText(String.copyValueOf(sintatico.getLexical().getArquivo()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGitHub(){
        try{
            URI link = new URI(GITHUB_LINK);
            Desktop.getDesktop().browse(link);
        }catch(Exception error){
            System.out.println("Error to open the GitHub link: " + error);
        }
    }


    public static void main(String[] args) {
        launch(args);
        //Queue<Integer>
    }
}