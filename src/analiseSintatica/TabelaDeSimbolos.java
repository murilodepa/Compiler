/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseSintatica;

import analiseLexical.IDs;
import analiseLexical.Pontuacoes;
import analiseLexical.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TabelaDeSimbolos {

    ArrayList<Simbolo> tabela;

    public TabelaDeSimbolos() {
        tabela = new ArrayList<Simbolo>();
    }

    /* Analisa escopos das variáveis */
    public void analisarListaDeTokens(LinkedList<Token> tokens) {
        int i = 0;

        for (Token token : tokens) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString())) {



            }
/* Verificar escopo */
//            programa - sprograma
//            início - sinicio
//            procedimento - sprocedimento
//            funcao - sfuncao
//            se - sse
//            entao - sentao
//            senao - ssenao
//            enquanto - senquanto

/* Verificar identificadores */
/*
            var - svar
            inteiro - sinteiro
            booleano - Sbooleano
            identificador - Sidentificador
            número - Snumero
*/

            if (token.getSimbolo().equals(IDs.Sidentificador.toString()) || token.getSimbolo().equals(IDs.Sidentificador.toString())) {

            }
        }
    }

    public void insereTabela(String lexema, int escopo, String tipo, String memoria) {
        tabela.add(new Simbolo(lexema, escopo, tipo, memoria));
    }

    public Simbolo consultaNaTabela(String lexema) {
        return tabela.get(tabela.indexOf(lexema));
    }

    public void colocaTipoNasVariaveis() {

    }
}