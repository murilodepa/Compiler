package analiseLexical;

import Utils.Caracteres;
import Utils.Simbolos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Lexical {

    LinkedList<Token> tokens;
    final private char[] arquivo;
    private int i;

    public Lexical(String caminhoDoArquivo) throws IOException {
        tokens = new LinkedList<>();
        byte[] arquivoLido = Files.readAllBytes(Paths.get(caminhoDoArquivo));
        arquivo = new String(arquivoLido, StandardCharsets.UTF_8).toCharArray();
        i = 0;
    }

    public void analisadorLexical() throws Exception {
        while (i < arquivo.length) {
            while (i < arquivo.length && (arquivo[i] == Caracteres.ABRE_CHAVES || Character.isWhitespace(arquivo[i]))) {

                // Ignora os comentários
                if (arquivo[i] == Caracteres.ABRE_CHAVES) {
                    while (i < arquivo.length - 1 && arquivo[i] != Caracteres.FECHA_CHAVES) {
                        i++;
                    }
                    i++;
                }

                // Ignora os espaços
                while (i < arquivo.length && Character.isWhitespace(arquivo[i])) {
                    i++;
                }
            }

            if (i < arquivo.length) {
                pegaToken(arquivo[i]);
                i++;
            }
        }

        for (Token token : tokens) {
            System.out.println(token.getLexema());
            System.out.println(token.getSimbolo());
        }
    }

    private void pegaToken(char caracter) throws Exception {
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
            throw new Exception("ERRO! O '" + caracter + "' é um caracter inválido!");
        }
    }

    private void trataDigito(char caracter) {
        StringBuilder numero = new StringBuilder();
        numero.append(caracter);

        while (i < arquivo.length - 1 && Character.isDigit(arquivo[i + 1])) {
            i++;
            numero.append(arquivo[i]);
        }
        tokens.add(new Token(numero.toString(), Simbolos.NUMERO));
    }

    private void trataIdentificadorEPalavraReservada(char caracter) {
        StringBuilder id = new StringBuilder();
        id.append(caracter);

        while (i < arquivo.length - 1 && (Character.isAlphabetic(arquivo[i + 1]) || Character.isDigit(arquivo[i + 1]) || arquivo[i + 1] == Caracteres.UNDERLINE)) {
            i++;
            id.append(arquivo[i]);
        }

        Token token = new Token(id.toString());

        switch (id.toString()) {
            case "programa":
                token.setSimbolo(Simbolos.PROGRAMA);
                break;
            case "se":
                token.setSimbolo(Simbolos.SE);
                break;
            case "entao":
                token.setSimbolo(Simbolos.ENTAO);
                break;
            case "senao":
                token.setSimbolo(Simbolos.SENAO);
                break;
            case "enquanto":
                token.setSimbolo(Simbolos.ENQUANTO);
                break;
            case "faca":
                token.setSimbolo(Simbolos.FACA);
                break;
            case "inicio":
                token.setSimbolo(Simbolos.INICIO);
                break;
            case "fim":
                token.setSimbolo(Simbolos.FIM);
                break;
            case "escreva":
                token.setSimbolo(Simbolos.ESCREVA);
                break;
            case "leia":
                token.setSimbolo(Simbolos.LEIA);
                break;
            case "var":
                token.setSimbolo(Simbolos.VAR);
                break;
            case "inteiro":
                token.setSimbolo(Simbolos.INTEIRO);
                break;
            case "booleano":
                token.setSimbolo(Simbolos.BOOLEANO);
                break;
            case "verdadeiro":
                token.setSimbolo(Simbolos.VERDADEIRO);
                break;
            case "falso":
                token.setSimbolo(Simbolos.FALSO);
                break;
            case "procedimento":
                token.setSimbolo(Simbolos.PROCEDIMENTO);
                break;
            case "funcao":
                token.setSimbolo(Simbolos.FUNCAO);
                break;
            case "div":
                token.setSimbolo(Simbolos.DIVISAO);
                break;
            case "e":
                token.setSimbolo(Simbolos.E);
                break;
            case "ou":
                token.setSimbolo(Simbolos.OU);
                break;
            case "nao":
                token.setSimbolo(Simbolos.NAO);
                break;
            default:
                token.setSimbolo(Simbolos.IDENTIFICADOR);
        }
        tokens.add(token);
    }

    private void trataAtribuicao(char caracter) {
        String atribuicao = "";
        atribuicao += caracter;

        if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
            i++;
            atribuicao += arquivo[i];
            tokens.add(new Token(atribuicao, Simbolos.ATRIBUICAO));
        } else {
            tokens.add(new Token(atribuicao, Simbolos.DOIS_PONTOS));
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
            default:
                tokens.add(new Token(String.valueOf(caracter), Simbolos.MENOS));
                break;
        }
    }

    private void trataOperadorRelacional(char caracter) throws Exception {
        String operador = "";
        operador += caracter;
        switch (caracter) {
            case Caracteres.EXCLAMACAO:
                if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += arquivo[i];
                    tokens.add(new Token(operador, Simbolos.DIFERENTE));
                } else {
                    throw new Exception(caracter + " inválido");
                }
                break;
            case Caracteres.IGUAL:
                tokens.add(new Token(operador, Simbolos.IGUAL));
                break;
            case Caracteres.MENOR:
                if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += arquivo[i];
                    tokens.add(new Token(operador, Simbolos.MENOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Simbolos.MENOR));
                }
                break;
            default:
                if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += arquivo[i];
                    tokens.add(new Token(operador, Simbolos.MAIOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Simbolos.MAIOR));
                }
                break;
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
            default:
                tokens.add(new Token(pontuacao, Simbolos.PONTO));
                break;
        }
    }
}