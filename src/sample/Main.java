package sample;

import analiseLexical.Lexical;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        Lexical lexical = new Lexical("./eg.txt");
        try {
            lexical.analisadorLexical();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // launch(args);
    }
}