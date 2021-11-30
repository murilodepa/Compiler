/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/*
 * Responsável para a criação da lista ligada que conterá os campos lexema e símbolo em cada posição.
 */

package analiseLexical;

public class Token {
    private String lexema;
    private String simbolo;

    public Token(String lexema, String simbolo) {
        this.lexema = lexema;
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}