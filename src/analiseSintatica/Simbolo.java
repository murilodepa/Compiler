/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
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

    public String getLexema() {
        return lexema;
    }

    public int getEscopo() {
        return escopo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setEscopo(int escopo) {
        this.escopo = escopo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }
}
