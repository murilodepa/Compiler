/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável por executar o programa (normal e passo a passo). E também acessar o github que contém o projeto.
 */
package menuBar;

import analiseSintatica.Sintatico;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URI;

public class CompilarPrograma {

    private final String GITHUB_LINK = "https://github.com/murilodepa/Compiler";

    public CompilarPrograma(){}

    /**
     * Método responsável por realizar a compilação do programa.
     *
     * @param sintatico onde acontecerá a análise sintática e semântica, entretanto, chamando a classe Lexical antes para
     *                  realizar a análise lexical.
     * @param alerta para identificar se o usuário irá compilar o programa sem ter escolhido um arquivo antes.
     * @param labelMensagem para mostrar na tela uma mensagem de sucesso ou de algum erro encontrado durante a compilação.
     */
    public void compilar(Sintatico sintatico, Alert alerta, Label labelMensagem) {
        try {
            try {
                if (sintatico.getLexical().getArquivo() == null) {
                    alerta.show();
                } else {
                    sintatico.limpar();
                    sintatico.executar();
                    labelMensagem.setTextFill(javafx.scene.paint.Color.GREEN);
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

    /** Se o usuário selecionar a tecla 'F9' */
    public void compilarF9(VBox vbox, Sintatico sintatico, Alert alerta, Label labelMensagem) {
        vbox.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.F9)) {
                compilar(sintatico, alerta, labelMensagem);
            }
        });
    }

    /**
     * Abrir o GitHub que contém o repositório do projeto do Compilador.
     */
    public void openGitHub() {
        try {
            URI link = new URI(GITHUB_LINK);
            Desktop.getDesktop().browse(link);
        } catch (Exception error) {
            System.out.println("Error to open the GitHub link: " + error);
        }
    }
}
