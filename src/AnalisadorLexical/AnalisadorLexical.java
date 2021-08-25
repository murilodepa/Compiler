package AnalisadorLexical;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class AnalisadorLexical {

    LinkedList<Token> tokens;

    public AnalisadorLexical() {
        tokens = new LinkedList<Token>();
    }

    public void analisarArquivo(String arquivo) throws IOException {
        Path filePath = Paths.get(arquivo);
        byte[] data = Files.readAllBytes(filePath);
        char character;
        int i = 0;

        while (i < data.length) {

            character = (char) data[i];
            i++;

            String palavra = "";
            while ((character == '{' || Character.isSpaceChar(character)) && i < data.length) {

                if (character == '{') {
                    while (character != '}' && i < data.length) {
                        character = (char) data[i];
                        i++;
                    }
                }

                while (Character.isSpaceChar(character) && i < data.length) {
                    character = (char) data[i];
                    i++;
                }
            }

                if (Character.isAlphabetic(character) && i < data.length){
                   // pegaToken(caracter);
                    //System.out.println(character);
                }
            }

        trataDigito("1234".toCharArray());
    }

/*
    private void pegaToken(char caracter)
    {
        if()
    }
*/

    private void trataDigito(char[] palavra) {
        int i = 0;
        String numero = new String();

        while (i < palavra.length && Character.isDigit(palavra[i])) {
            numero += palavra[i];
            i++;
        }

        tokens.add(new Token(numero, "snumero"));
        System.out.println(tokens.get(0).getLexema());
        System.out.println(tokens.get(0).getSimbolo());
    }
}
