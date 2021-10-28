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

import java.util.*;

public class TabelaDeSimbolos {

    LinkedList<Simbolo> tabela;

    public TabelaDeSimbolos() {
        tabela = new LinkedList<>();
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

    public void insereTabela(String lexema, String escopo, String tipo, String memoria) {
        tabela.push(new Simbolo(lexema, escopo, tipo, memoria));
    }

    public boolean pesquisarDuplicidade(String lexama){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty() && aux.peek().getEscopo()==null){
            Simbolo simbolo= aux.pop();
            if(simbolo.getLexema().equals(lexama))
                return true;
        }
        return false;
    }

    public void colocaTipo(String tipo){
        LinkedList<Simbolo> aux= new LinkedList<Simbolo>();
        while(!tabela.isEmpty() && tabela.peek().getTipo().contains("variavel")){
            aux.push(tabela.pop());
        }
        while(!aux.isEmpty()){
            Simbolo simbolo = aux.remove();
            simbolo.setTipo(tipo);
            tabela.push(simbolo);
        }
    }

    public boolean pesquisaGlobal(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.pop().getLexema().equals(lexema))
                return true;
        }
        return false;
    }

    public Simbolo pesquisaLocal(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            Simbolo simbolo = aux.pop();
            if(simbolo.getLexema().equals(lexema))
                return simbolo;
        }
        return null;
    }

    public void desempilhaMarca(){
        while(!tabela.isEmpty() && tabela.peek().getEscopo()!="L"){
            tabela.pop();
        }
        //tabela.pop();
    }

    public void alteraTipoTopo(String tipo){
        if(!tabela.isEmpty()){
            Simbolo simbolo = tabela.pop();
            simbolo.setTipo(tipo);
            tabela.push(simbolo);
        }
    }

    //public Simbolo consultaNaTabela(String lexema) {
        //eturn tabela.get(tabela.indexOf(lexema));
    //}

    public void colocaTipoNasVariaveis() {

    }
}