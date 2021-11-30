/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável para a criação da lista ligada que conterá os campos lexema e símbolo em cada posição.
 */
package analiseLexical;

public class Token {
    private String lexema;
    private String simbolo;

    /**
     * Construtor da classe Token que instanciará uma lista ligada com os seguintes campos:
     *
     * @param lexema  primeiro campo que conterá em todas as posições da lista ligada instanciada.
     * @param simbolo segundo campo que conterá em todas as posições da lista ligada instanciada.
     */
    public Token(String lexema, String simbolo) {
        this.lexema = lexema;
        this.simbolo = simbolo;
    }

    /**
     * Define o lexema na posição desejada da lista ligada.
     *
     * @param lexema que será inserido na lista.
     */
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    /**
     * Define o símbolo na posição desejada da lista ligada.
     *
     * @param simbolo que será inserido na lista.
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return o lexema da posição que deseja analisar da lista ligada.
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @return o símbolo da posição que deseja analisar da lista ligada.
     */
    public String getSimbolo() {
        return simbolo;
    }
}