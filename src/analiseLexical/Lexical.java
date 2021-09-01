/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseLexical;

import Utils.Caracteres;
import Utils.Operadores;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Lexical {
    LinkedList<Token> tokens;
    final private char[] arquivo;
    private int i;
    private int linha;
    private int coluna;

    public Lexical(String caminhoDoArquivo) throws IOException {
        tokens = new LinkedList<>();
        byte[] arquivoLido = Files.readAllBytes(Paths.get(caminhoDoArquivo));
        arquivo = new String(arquivoLido, StandardCharsets.UTF_8).toCharArray();
        i = 0;
        linha=1;
        coluna=0;
    }

    public void verificarLinha()
    {
        if(arquivo[i]=='\n')
        {
            linha++;
            coluna=i;
        }
    }

    public void analisadorLexical() throws Exception {
        while (i < arquivo.length) {
            while (i < arquivo.length && (arquivo[i] == Caracteres.ABRE_CHAVES || Character.isWhitespace(arquivo[i]))) {
                // Ignora os comentários

                if (arquivo[i] == Caracteres.ABRE_CHAVES) {
                    while (i < arquivo.length - 1 && arquivo[i] != Caracteres.FECHA_CHAVES) {
                       verificarLinha();
                        i++;
                    }
                    if(arquivo[i]!= Caracteres.FECHA_CHAVES)
                    {
                        throw new Exception("faltando }");
                    }
                    i++;
                }
                // Ignora os espaços
                while (i < arquivo.length && Character.isWhitespace(arquivo[i])) {
                    verificarLinha();
                    i++;
                }
            }
            if (i < arquivo.length) {
                pegaToken(arquivo[i]);
                i++;
            }
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
            throw new Exception("ERRO! Linha:"+linha+" Coluna:"+(i-coluna)+" "+ caracter + " é um caracter inválido!");
        }
    }

    private void trataDigito(char caracter) {
        StringBuilder numero = new StringBuilder();
        numero.append(caracter);
        while (i < arquivo.length - 1 && Character.isDigit(arquivo[i + 1])) {
            i++;
            numero.append(arquivo[i]);
        }
        tokens.add(new Token(numero.toString(), Operadores.NUMERO));
    }

    private void trataIdentificadorEPalavraReservada(char caracter) {
        StringBuilder id = new StringBuilder();
        id.append(caracter);
        while (i < arquivo.length - 1 && (Character.isAlphabetic(arquivo[i + 1]) || Character.isDigit(arquivo[i + 1]) || arquivo[i + 1] == Caracteres.UNDERLINE)) {
            i++;
            id.append(arquivo[i]);
        }
        tokens.add(new Token(id.toString(), IDs.pegaSimboloPorLexama(id.toString())));
    }

    private void trataAtribuicao(char caracter) {
        String atribuicao = "";
        atribuicao += caracter;
        if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
            i++;
            atribuicao += arquivo[i];
            tokens.add(new Token(atribuicao, Operadores.ATRIBUICAO));
        } else {
            tokens.add(new Token(atribuicao, Operadores.DOIS_PONTOS));
        }
    }

    private void trataOperadorAritmetico(char caracter) {
        switch (caracter) {
            case Caracteres.ASTERISCO:
                tokens.add(new Token(String.valueOf(caracter), Operadores.MULTIPLICACAO));
                break;
            case Caracteres.MAIS:
                tokens.add(new Token(String.valueOf(caracter), Operadores.MAIS));
                break;
            default:
                tokens.add(new Token(String.valueOf(caracter), Operadores.MENOS));
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
                    tokens.add(new Token(operador, Operadores.DIFERENTE));
                    break;
                } else {
                    operador += arquivo[i + 1];
                    throw new Exception("ERRO! Linha:"+linha+" Coluna:"+(i-coluna)+" " + operador + "' é um operador inválido!");
                }
            case Caracteres.IGUAL:
                tokens.add(new Token(operador, Operadores.IGUAL));
                break;
            case Caracteres.MENOR:
                if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += arquivo[i];
                    tokens.add(new Token(operador, Operadores.MENOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Operadores.MENOR));
                }
                break;
            default:
                if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
                    i++;
                    operador += arquivo[i];
                    tokens.add(new Token(operador, Operadores.MAIOR_IGUAL));
                } else {
                    tokens.add(new Token(operador, Operadores.MAIOR));
                }
                break;
        }
    }

    private void trataPontuacao(char caracter) {
        String pontuacao = "";
        pontuacao += caracter;
        tokens.add(new Token(pontuacao, Pontuacoes.pegaSimboloPorLexama(pontuacao)));
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }
}