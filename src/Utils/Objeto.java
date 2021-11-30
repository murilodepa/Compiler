/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável por realizar um "objeto" com a posição, valor e símbolo.
 */
package Utils;

public class Objeto {
    private final int posicao;
    private final String valor;
    private final String Simbolo;

    public int getPosicao() {
        return posicao;
    }

    public String getValor() {
        return valor;
    }

    public String getSimbolo() {
        return Simbolo;
    }

    public Objeto(int posicao, String valor, String simbolo) {
        this.posicao = posicao;
        this.valor = valor;
        Simbolo = simbolo;
    }
}
