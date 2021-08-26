package analiseLexical;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class AnalisadorLexical {

    LinkedList<Token> tokens;
    private byte[] data

    public AnalisadorLexical() {
        tokens = new LinkedList<Token>();
    }

    public void analisarArquivo(String arquivo) throws IOException {
        Path filePath = Paths.get(arquivo);
        data = Files.readAllBytes(filePath);
        char character;
        int i = 0;

        while (i < data.length) {

            character = (char) data[i];
            i++;

            String palavra = "";
            while ((character == '{' || Character.isSpaceChar(character)) && i < data.length) {

                //ignora comentário
                if (character == '{') {
                    while (character != '}' && i < data.length) {
                        character = (char) data[i];
                        i++;
                    }
                }

                //ignora espaço em branco
                while (Character.isSpaceChar(character) && i < data.length) {
                    character = (char) data[i];
                    i++;
                }
            }

            if (i < data.length) {
                pegaToken((char) data[i]);
                // System.out.println(character);
            }
        }
        trataDigito('a');
    }

    private void pegaToken(char character) {
        if (Character.isDigit((character))) {
            //trataDigito(character);
        } else if (Character.isLetter(character)) {
            //trata identificador e palavra reservada
        } else if (character == ':') {
            //trata atribuicao
        } else if (character == '+' || character == '-' || character == '*') {
            //trata operador aritmetico
        } else if (character == '!' || character == '<' || character == '>' || character == '=') {
            // trata operador relacional
        } else if (character == ';' || character == ',' || character == '(' || character == ')' || character == '.') {
            //trata pontuação
        } else {
            //exception
        }
    }

    private void trataDigito(char caracter) {
        int i = 0;
        String numero = new String();

        while (Character.isDigit(caracter)) {
            numero += caracter;
            i++;
            caracter = (char) data[i];
        }

        tokens.add(new Token(numero, Simbolos.NUMERO));
        System.out.println(tokens.get(0).getLexema());
        System.out.println(tokens.get(0).getSimbolo());
    }

    private void trataIdentificadorEPalavraReservada(char caracter) {
        int i = 0;
        String id = new String();

        while (Character.isLetter(caracter) || Character.isDigit(caracter) || caracter == '_') {
            id += caracter;
            i++;
            caracter = (char) data[i];
        }

        switch (id) {
            case Simbolos.PROGRAMA:
                tokens.add(new Token(id, Simbolos.PROGRAMA));
                break;
            case Simbolos.SE:
                tokens.add(new Token(id, Simbolos.SE));
                break;
            case Simbolos.ENTAO:
                tokens.add(new Token(id, Simbolos.ENTAO));
                break;
            case Simbolos.SENAO:
                tokens.add(new Token(id, Simbolos.SENAO));
                break;
            case Simbolos.ENQUANTO:
                tokens.add(new Token(id, Simbolos.ENQUANTO));
                break;
            case Simbolos.FACA:
                tokens.add(new Token(id, Simbolos.FACA));
                break;
            case Simbolos.INICIO:
                tokens.add(new Token(id, Simbolos.INICIO));
                break;
            case Simbolos.FIM:
                tokens.add(new Token(id, Simbolos.FIM));
                break;
            case Simbolos.ESCREVA:
                tokens.add(new Token(id, Simbolos.ESCREVA));
                break;
            case Simbolos.LEIA:
                tokens.add(new Token(id, Simbolos.LEIA));
                break;
            case Simbolos.VAR:
                tokens.add(new Token(id, Simbolos.VAR));
                break;
            case Simbolos.INTEIRO:
                tokens.add(new Token(id, Simbolos.INTEIRO));
                break;
            case Simbolos.BOOLEANO:
                tokens.add(new Token(id, Simbolos.BOOLEANO));
                break;
            case Simbolos.VERDADEIRO:
                tokens.add(new Token(id, Simbolos.VERDADEIRO));
                break;
            case Simbolos.FALSO:
                tokens.add(new Token(id, Simbolos.FALSO));
                break;
            case Simbolos.FUNCAO:
                tokens.add(new Token(id, Simbolos.FUNCAO));
                break;
            case Simbolos.DIVISAO:
                tokens.add(new Token(id, Simbolos.DIVISAO));
                break;
            case Simbolos.E:
                tokens.add(new Token(id, Simbolos.E));
                break;
            case Simbolos.OU:
                tokens.add(new Token(id, Simbolos.OU));
                break;
            case Simbolos.NAO:
                tokens.add(new Token(id, Simbolos.NAO));
                break;
            default:
                tokens.add(new Token(id, "identificador"));
        }
    }

    private void trataAtribuicao() {

    }

    private void trataOperadorAritmetico() {

    }

    private void trataOperadorRelacional() {

    }

    private void trataPontuacao() {

    }

}
