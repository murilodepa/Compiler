package analiseLexical;

import Utils.Caracteres;
import Utils.Simbolos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class AnalisadorLexical {

    LinkedList<Token> tokens;
    final private byte[] data;
    private int i;

    public AnalisadorLexical(String arquivo) throws IOException {
        tokens = new LinkedList<>();
        data = Files.readAllBytes(Paths.get(arquivo));
        i = 0;
    }

    public void analisarArquivo() throws Exception {
        while (i < data.length) {
            while (i < data.length && ((char) data[i] == '{' || Character.isWhitespace((char) data[i]))) {

                // Ignora os comentários
                if ((char) data[i] == '{') {
                    while (i < data.length - 1 && (char) data[i] != '}') {
                        i++;
                    }
                    i++;
                }

                // Ignora os espaços
                while (i < data.length && Character.isWhitespace((char) data[i])) {
                    i++;
                }
            }

            if (i < data.length) {
                pegaToken();
                i++;
            }
        }

        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).getLexema());
            System.out.println(tokens.get(i).getSimbolo());
        }
    }

    private void pegaToken() throws Exception {
        char caracter = (char) data[i];
        if (Character.isDigit((caracter))) {
            trataDigito(caracter);
        } else if (Character.isAlphabetic(caracter)) {
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
            throw new Exception(caracter + " é um caracter inválido");
        }
    }

    private void trataDigito(char caracter) {
        StringBuilder numero = new StringBuilder();
        numero.append(caracter);

        while (i < data.length - 1 && Character.isDigit((char) data[i + 1])) {
            i++;
            numero.append((char) data[i]);
        }
        tokens.add(new Token(numero.toString(), Simbolos.NUMERO));
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
                } else {
                    throw new Exception(caracter + " inválido");
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
    }

    private void trataIdentificadorEPalavraReservada(char caracter) {
        StringBuilder id = new StringBuilder();
        id.append(caracter);

        while (i < data.length - 1 && (Character.isAlphabetic((char) data[i + 1]) || Character.isDigit((char) data[i + 1]) || (char) data[i + 1] == '_')) {
            i++;
            id.append((char) data[i]);
        }

        switch (id.toString()) {
            case "programa":
                tokens.add(new Token(id.toString(), Simbolos.PROGRAMA));
                break;
            case "se":
                tokens.add(new Token(id.toString(), Simbolos.SE));
                break;
            case "entao":
                tokens.add(new Token(id.toString(), Simbolos.ENTAO));
                break;
            case "senao":
                tokens.add(new Token(id.toString(), Simbolos.SENAO));
                break;
            case "enquanto":
                tokens.add(new Token(id.toString(), Simbolos.ENQUANTO));
                break;
            case "faca":
                tokens.add(new Token(id.toString(), Simbolos.FACA));
                break;
            case "inicio":
                tokens.add(new Token(id.toString(), Simbolos.INICIO));
                break;
            case "fim":
                tokens.add(new Token(id.toString(), Simbolos.FIM));
                break;
            case "escreva":
                tokens.add(new Token(id.toString(), Simbolos.ESCREVA));
                break;
            case "leia":
                tokens.add(new Token(id.toString(), Simbolos.LEIA));
                break;
            case "var":
                tokens.add(new Token(id.toString(), Simbolos.VAR));
                break;
            case "inteiro":
                tokens.add(new Token(id.toString(), Simbolos.INTEIRO));
                break;
            case "booleano":
                tokens.add(new Token(id.toString(), Simbolos.BOOLEANO));
                break;
            case "verdadeiro":
                tokens.add(new Token(id.toString(), Simbolos.VERDADEIRO));
                break;
            case "falso":
                tokens.add(new Token(id.toString(), Simbolos.FALSO));
                break;
            case "procedimento":
                tokens.add(new Token(id.toString(), Simbolos.PROCEDIMENTO));
                break;
            case "funcao":
                tokens.add(new Token(id.toString(), Simbolos.FUNCAO));
                break;
            case "div":
                tokens.add(new Token(id.toString(), Simbolos.DIVISAO));
                break;
            case "e":
                tokens.add(new Token(id.toString(), Simbolos.E));
                break;
            case "ou":
                tokens.add(new Token(id.toString(), Simbolos.OU));
                break;
            case "nao":
                tokens.add(new Token(id.toString(), Simbolos.NAO));
                break;
            default:
                tokens.add(new Token(id.toString(), Simbolos.IDENTIFICADOR));
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
    }
}
