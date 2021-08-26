package sample;

import analiseLexical.AnalisadorLexical;

import java.io.IOException;

public class Main /* extends Application*/ {
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/


    public static void main(String[] args) throws IOException {
        AnalisadorLexical teste = new AnalisadorLexical("./eg.txt");
        teste.analisarArquivo();
        //launch(args);
    }
}
