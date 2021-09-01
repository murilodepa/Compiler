/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package sample;

import analiseLexical.IDs;
import analiseLexical.Lexical;
import analiseLexical.Token;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Main /* extends Application */ {
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
*/

    public static void main(String[] args) throws IOException {
        LinkedList<Token> tokens;
        Lexical lexical = new Lexical("./exemplo.txt");

        try {
            lexical.analisadorLexical();
            tokens = lexical.getTokens();

            for (Token token : tokens) {
                System.out.print(token.getLexema() + " -> ");
                System.out.println(token.getSimbolo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // launch(args);
    }
}