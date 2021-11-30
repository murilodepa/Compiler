/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável por ler o arquivo ".txt" para realizar a compilação do código contido neste arquivo.
 */
package menuBar;

import analiseSintatica.Sintatico;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LerArquivo {

    private final FileChooser fileChooser = new FileChooser();

    /**
     * Construtor da classe que define que o arquivo que o usuário escolher para o compilador deverá ser do tipo ".txt".
     */
    public LerArquivo() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    /**
     * Responsável por abrir o arquivo para o usuário e inserir o código contido no arquivo na interface gráfica, onde o
     * usuário pode validar se de fato é esse arquivo que desejava abrir.
     *
     * @param primaryStage seria a interface gráfica, para saber onde será mostrado o campo que conterá o arquivo.
     * @param areaTextoCaminhoArquivo campo para mostrar o caminho do arquivo que o usuário escolheu.
     * @param sintatico servirá para definir na classe o arquivo escolhido e nessa classe trabalhar com as análises necessárias
     *                  neste arquivo no processo de compilação.
     * @param areaTextoCodigo campo que irá mostrar o código contido no arquivo escolhido pelo usuário.
     */
    public void abrirArquivo(Stage primaryStage, TextField areaTextoCaminhoArquivo, Sintatico sintatico, TextArea areaTextoCodigo) {
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
}
