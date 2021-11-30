/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Classe que contém o analisador Lexical do compilador, sendo o primeiro ciclo de teste que o programa tenta encontrar
 * algum erro lexical.
 */
package analiseLexical;

import Utils.Caracteres;
import Utils.Operadores;

import java.util.LinkedList;

public class Lexical {
    private LinkedList<Token> tokens;
    private char[] arquivo;
    private int i;
    private int linha;
    private int coluna;

    /**
     * Construtor da classe que instância uma lista de tokens e inicia a variável i, linha e coluna.
     */
    public Lexical() {
        tokens = new LinkedList<>();
        i = 0;
        linha = 1;
        coluna = -1;
    }

    /**
     * Método responsável por pegar o arquivo.
     *
     * @return o arquivo de entrada.
     */
    public char[] getArquivo() {
        return arquivo;
    }

    /**
     * Define o arquivo de entrada que o usuário selecionar.
     *
     * @param arquivo que o programa compila-rá.
     */
    public void setArquivo(char[] arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * Define a lista de tokens.
     *
     * @param tokens uma lista com os tokens que será utilizado na análise lexical.
     */
    public void setTokens(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Define o valor do índice atual.
     *
     * @param i o índice que é utilizado para percorrer o arquivo de entrada.
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * Define o valor da linha atual.
     *
     * @param linha que está sendo analisada no arquivo da análise lexical do compilador.
     */
    public void setLinha(int linha) {
        this.linha = linha;
    }

    /**
     * Define o valor da coluna atual.
     *
     * @param coluna que está sendo analisada no arquivo da análise lexical do compilador.
     */
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    /**
     * Método responsável para verificar a linha e coluna atual que está sendo percorrida no arquivo.
     */
    public void verificarLinha() {
        if (arquivo[i] == '\n') {
            linha++;
            coluna = i;
        }
    }

    /**
     * Método principal que realiza a análise lexical do compilador, percorrendo até o final do arquivo, ignorando os
     * espaços em brancos e comentários.
     *
     * @throws Exception
     */
    public void analisadorLexical() throws Exception {
        int linhaChave, colunaChave;
        while (i < arquivo.length) {
            while (i < arquivo.length && (arquivo[i] == Caracteres.ABRE_CHAVES || Character.isWhitespace(arquivo[i]))) {
                // Ignora os comentários
                if (arquivo[i] == Caracteres.ABRE_CHAVES) {
                    linhaChave = linha;
                    colunaChave = i - coluna;

                    while (i < arquivo.length - 1 && arquivo[i] != Caracteres.FECHA_CHAVES) {
                        verificarLinha();
                        i++;
                    }
                    if (arquivo[i] != Caracteres.FECHA_CHAVES) {
                        throw new Exception("Faltou fechar a chave ou encerrar o comentário, da linha: " + linhaChave + " e coluna: " + colunaChave);
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

    /**
     * Método responsável por pegar qual o token do carácter de entrada e percorrendo o arquivo a partir dele se necessário.
     *
     * @param caracter de entrada que será analisado individualmente ou com o seu sucessor, identificando qual o tokens desse(s)
     *                 caracter(es).
     * @throws Exception
     */
    private void pegaToken(char caracter) throws Exception {
        if (Character.isDigit((caracter))) {
            trataDigito(caracter);
        } else if (Character.isAlphabetic(caracter)) {
            trataIdentificadorEPalavraReservada(caracter);
        } else if (caracter == Caracteres.DOIS_PONTOS) {
            trataAtribuicao(caracter);
        } else if (caracter == Caracteres.MAIS || caracter == Caracteres.MENOS || caracter == Caracteres.ASTERISCO) {
            trataOperadorAritmetico(caracter);
        } else if (caracter == Caracteres.EXCLAMACAO || caracter == Caracteres.MENOR || caracter == Caracteres.MAIOR ||
                caracter == Caracteres.IGUAL) {
            trataOperadorRelacional(caracter);
        } else if (caracter == Caracteres.PONTO_VIRGULA || caracter == Caracteres.VIRGULA ||
                caracter == Caracteres.ABRE_PARENTESES || caracter == Caracteres.FECHA_PARENTESES ||
                caracter == Caracteres.PONTO) {
            trataPontuacao(caracter);
        } else {
            throw new Exception("ERRO! Linha: " + linha + " e Coluna: " + (i - coluna) + ". O '" + caracter
                    + "' é um caracter inválido!");
        }
    }

    /**
     * Responsável por trata o dígito através de um caracter que é um digito ou com os seus sucessores se continuarem
     * esse dígito, e adicionando o seu respectivo símbolo na lista de tokens.
     *
     * @param caracter que é um digito em decimal de 0 a 9, que será analisado se o próximo carácter continua o número
     *                 de entrada.
     */
    private void trataDigito(char caracter) {
        StringBuilder numero = new StringBuilder();
        numero.append(caracter);
        while (i < arquivo.length - 1 && Character.isDigit(arquivo[i + 1])) {
            i++;
            numero.append(arquivo[i]);
        }
        tokens.add(new Token(numero.toString(), Operadores.NUMERO));
    }

    /**
     * Método responsável por tratar identificadores e palavras reservadas do compilador, adicionando o seu respectivo
     * símbolo na lista tokens qual é o identificador em relação ao carácter de entrada.
     *
     * @param caracter de entrada que será analisado os seus sucessores se não tiver acabado o arquivo, se for alfabeto,
     *                 número ou underline.
     */
    private void trataIdentificadorEPalavraReservada(char caracter) {
        StringBuilder id = new StringBuilder();
        id.append(caracter);
        while (i < arquivo.length - 1 && (Character.isAlphabetic(arquivo[i + 1]) || Character.isDigit(arquivo[i + 1]) ||
                arquivo[i + 1] == Caracteres.UNDERLINE)) {
            i++;
            id.append(arquivo[i]);
        }
        tokens.add(new Token(id.toString(), IDs.pegaSimboloDoId(id.toString())));
    }

    /**
     * Método responsável por tratar atribuição, analisando se é "=", "=:" ou ":" e adionando o seu respectivo símbolo
     * na lista de tokens.
     *
     * @param caracter de entrada que será analisado de qual operador se trata, podendo analisar caracteres sucessores
     *                 se necessário.
     */
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

    /**
     * Método responsável por analisar de qual operador aritmético se trata, podendo ser de "multiplicação (*)", "mais (+)"
     * ou "menos (-)" e depois adicionando o seu respectivo símbolo na lista de tokens.
     *
     * @param caracter de entrada que será identificado qual operador se trata.
     */
    private void trataOperadorAritmetico(char caracter) {
        switch (caracter) {
            case Caracteres.ASTERISCO -> tokens.add(new Token(String.valueOf(caracter), Operadores.MULTIPLICACAO));
            case Caracteres.MAIS -> tokens.add(new Token(String.valueOf(caracter), Operadores.MAIS));
            default -> tokens.add(new Token(String.valueOf(caracter), Operadores.MENOS));
        }
    }

    /**
     * Método responsável por tratar operador relacional, podendo ser "=", "!=", "<", ">", ">=" ou "<=" e adiciona o seu
     * respectivo símbolo na lista tokens.
     *
     * @param caracter de entrada que será identificado qual operador relacional se trata, podendo identificar os seus
     *                 caracteres sucessores do arquivo se necessários.
     * @throws Exception
     */
    private void trataOperadorRelacional(char caracter) throws Exception {
        String operador = "";
        operador += caracter;

        if (i < arquivo.length - 1 && arquivo[i + 1] == Caracteres.IGUAL) {
            i++;
            operador += arquivo[i];
            tokens.add(new Token(operador, OperadoresRelacional.pegaSimboloDoOperador(operador)));
        } else {
            if (caracter != Caracteres.EXCLAMACAO) {
                tokens.add(new Token(operador, OperadoresRelacional.pegaSimboloDoOperador(operador)));
            } else {
                throw new Exception("ERRO! Linha: " + linha + " e Coluna: " + (i - coluna) + ". O '" + operador
                        + "' é um operador inválido!");
            }
        }
    }

    /**
     * Responável por tratar as pontuações na análise lexical, podendo ser ";", ",", "(", ")" ou "." e adicionando o seu
     * respectivo símbolo na lista tokens.
     *
     * @param caracter de entrada que será identificado qual pontuação se trata.
     */
    private void trataPontuacao(char caracter) {
        String pontuacao = "";
        pontuacao += caracter;
        tokens.add(new Token(pontuacao, Pontuacoes.pegaSimboloDaPontuacao(pontuacao)));
    }

    /**
     * Responsável por pegar a lista de tokens.
     *
     * @return a lista de tokens com símbolos e lexemas atuais em relação à análise lexical do compilador.
     */
    public LinkedList<Token> getTokens() {
        return tokens;
    }
}