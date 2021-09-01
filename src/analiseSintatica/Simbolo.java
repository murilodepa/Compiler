/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseSintatica;

public class Simbolo {

    private String lexema;
    private int escopo;
    private String tipo;
    private String memoria;

    public Simbolo(String lexema, int escopo, String tipo, String memoria) {
        this.lexema = lexema;
        this.escopo = escopo;
        this.tipo = tipo;
        this.memoria = memoria;
    }
}
