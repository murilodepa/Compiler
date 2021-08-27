package analiseLexical;

import Utils.Caracteres;
import Utils.Simbolos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class AnalisadorLexical {

    LinkedList<Token> tokens;
    private byte[] data;
    private int i;

    public AnalisadorLexical(String arquivo) throws IOException {
        tokens = new LinkedList<Token>();
        data = Files.readAllBytes(Paths.get(arquivo));
        i = 0;
    }

    public void analisarArquivo() throws Exception {
        while (i < data.length) {
            while (i < data.length && ((char) data[i] == '{' || Character.isSpace((char) data[i]))) {

                //ignora comentário
                if ((char) data[i] == '{') {
                    while (i < data.length -1 && (char) data[i] != '}') {
                        i++;
                    }
                    i++;
                }

                //ignora espaço em branco
                while ( i < data.length && Character.isSpace((char) data[i])) {
                    i++;
                }
            }

            if (i < data.length) {
                pegaToken();
            }
        }

        for(int i=0;i<tokens.size();i++)
        {
            System.out.println(tokens.get(i).getLexema());
            System.out.println(tokens.get(i).getSimbolo());
        }
    }

    private void pegaToken() throws Exception {

        char caracter = (char) data[i];
        if (Character.isDigit((caracter))) {
            trataDigito(caracter);
        } else if (Character.isAlphabetic(caracter) ) {
            trataIdentificadorEPalavraReservada(caracter);
        } else if (caracter == Caracteres.DOIS_PONTOS) {
            trataAtribuicao(caracter);
        } else if (caracter == Caracteres.MAIS || caracter == Caracteres.MENOS || caracter == Caracteres.ASTERISCO) {
            trataOperadorAritmetico(caracter);
        } else if (caracter == Caracteres.EXCLAMACAO || caracter == Caracteres.MENOR || caracter == Caracteres.MAIOR || caracter == Caracteres.IGUAL) {
            trataOperadorRelacional(caracter);
        } else if (caracter == Caracteres.PONTO_VIRGULA ||
                   caracter == Caracteres.VIRGULA ||
                   caracter == Caracteres.ABRE_PARENTESES ||
                   caracter == Caracteres.FECHA_PARENTESES ||
                   caracter == Caracteres.PONTO) {
            trataPontuacao(caracter);
        } else {
            throw new Exception(caracter+" é um caracter inválido");
            //System.out.println(caracter+" é um caracter inválido");
            //i++;
        }
    }

    private void trataDigito(char caracter) {
        String numero = "";
        numero += caracter;
        i++;

        while (i < data.length && Character.isDigit((char) data[i])) {
            numero += (char) data[i];
            i++;
        }

        tokens.add(new Token(numero, Simbolos.NUMERO));
    }

    private void trataOperadorRelacional(char caracter) throws Exception {
        String operador = "";
        operador += caracter;
        switch (caracter) {
            case Caracteres.EXCLAMACAO:
                if (i < data.length - 1 && (char) data[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += (char) data[i];
                    tokens.add(new Token(operador, Simbolos.DIFERENTE));
                } else{
                    throw new Exception(caracter+" inválido");
                    //System.out.println((char) data[i]+" é um caracter inválido");
                }
                break;
            case Caracteres.IGUAL:
                tokens.add(new Token(operador, Simbolos.IGUAL));
                break;
            case Caracteres.MENOR:
                if (i < data.length - 1 && (char) data[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += (char) data[i];
                    tokens.add(new Token(operador, Simbolos.MENOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Simbolos.MENOR));
                }
                break;
            case Caracteres.MAIOR:
                if (i < data.length - 1 && (char) data[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += (char) data[i];
                    tokens.add(new Token(operador, Simbolos.MAIOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Simbolos.MAIOR));
                }
                break;
        }
        i++;
    }

    private void trataOperadorAritmetico(char caracter) {
        switch (caracter) {
            case Caracteres.ASTERISCO:
                tokens.add(new Token(String.valueOf(caracter), Simbolos.MULTIPLICACAO));
                break;
            case Caracteres.MAIS:
                tokens.add(new Token(String.valueOf(caracter), Simbolos.MAIS));
                break;
            case Caracteres.MENOS:
                tokens.add(new Token(String.valueOf(caracter), Simbolos.MENOS));
                break;
        }
        i++;
    }

    private void trataIdentificadorEPalavraReservada(char caracter) {
        String id = "";
        id += caracter;
        i++;

        while (i < data.length && (Character.isAlphabetic((char) data[i]) || Character.isDigit((char) data[i]) || (char) data[i] == '_')) {
            id += (char) data[i];
            i++;
        }

        switch (id) {
            case "programa":
                tokens.add(new Token(id, Simbolos.PROGRAMA));
                break;
            case "se":
                tokens.add(new Token(id, Simbolos.SE));
                break;
            case "entao":
                tokens.add(new Token(id, Simbolos.ENTAO));
                break;
            case "senao":
                tokens.add(new Token(id, Simbolos.SENAO));
                break;
            case "enquanto":
                tokens.add(new Token(id, Simbolos.ENQUANTO));
                break;
            case "faca":
                tokens.add(new Token(id, Simbolos.FACA));
                break;
            case "inicio":
                tokens.add(new Token(id, Simbolos.INICIO));
                break;
            case "fim":
                tokens.add(new Token(id, Simbolos.FIM));
                break;
            case "escreva":
                tokens.add(new Token(id, Simbolos.ESCREVA));
                break;
            case "leia":
                tokens.add(new Token(id, Simbolos.LEIA));
                break;
            case "var":
                tokens.add(new Token(id, Simbolos.VAR));
                break;
            case "inteiro":
                tokens.add(new Token(id, Simbolos.INTEIRO));
                break;
            case "booleano":
                tokens.add(new Token(id, Simbolos.BOOLEANO));
                break;
            case "verdadeiro":
                tokens.add(new Token(id, Simbolos.VERDADEIRO));
                break;
            case "falso":
                tokens.add(new Token(id, Simbolos.FALSO));
                break;
            case "funcao":
                tokens.add(new Token(id, Simbolos.FUNCAO));
                break;
            case "div":
                tokens.add(new Token(id, Simbolos.DIVISAO));
                break;
            case "e":
                tokens.add(new Token(id, Simbolos.E));
                break;
            case "ou":
                tokens.add(new Token(id, Simbolos.OU));
                break;
            case "nao":
                tokens.add(new Token(id, Simbolos.NAO));
                break;
            default:
                tokens.add(new Token(id, Simbolos.IDENTIFICADOR));
        }
    }

    private void trataAtribuicao(char caracter) {
        String atribuicao = "";
        atribuicao += caracter;

        if (i < data.length - 1 && (char) data[i + 1] == Caracteres.IGUAL) {
            i++;
            atribuicao += (char) data[i];
            tokens.add(new Token(atribuicao, Simbolos.ATRIBUICAO));
        } else {
            tokens.add(new Token(atribuicao, Simbolos.DOIS_PONTOS));
        }
        i++;
    }

    private void trataPontuacao(char caracter) {
        String pontuacao = "";
        pontuacao += caracter;

        switch (caracter) {
            case Caracteres.PONTO_VIRGULA:
                tokens.add(new Token(pontuacao, Simbolos.PONTO_VIRGULA));
                break;
            case Caracteres.VIRGULA:
                tokens.add(new Token(pontuacao, Simbolos.VIRGULA));
                break;
            case Caracteres.ABRE_PARENTESES:
                tokens.add(new Token(pontuacao, Simbolos.ABRE_PARENTESES));
                break;
            case Caracteres.FECHA_PARENTESES:
                tokens.add(new Token(pontuacao, Simbolos.FECHA_PARENTESES));
                break;
            case Caracteres.PONTO:
                tokens.add(new Token(pontuacao, Simbolos.PONTO));
                break;
        }
        i++;
    }
}
