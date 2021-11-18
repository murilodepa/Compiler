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
        while(!aux.isEmpty() && aux.peek().getEscopo().equals("")){
            Simbolo simbolo= aux.pop();
            if(simbolo.getLexema().equals(lexama))
                return true;
        }
        return false;
    }

    public int colocaTipo(String tipo,int endereco){
        int contador=0;
        LinkedList<Simbolo> aux= new LinkedList<Simbolo>();
        while(!tabela.isEmpty() && tabela.peek().getTipo().contains("variavel")){
            aux.push(tabela.pop());
        }
        while(!aux.isEmpty()){
            Simbolo simbolo = aux.pop();
            simbolo.setTipo(tipo);
            simbolo.setMemoria(String.valueOf(endereco+contador));
            tabela.push(simbolo);
            contador++;
        }
        return contador;
    }

    public boolean pesquisaGlobal(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.pop().getLexema().equals(lexema))
                return true;
        }
        return false;
    }

    public boolean pesquisaGlobalProcedimento(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && aux.peek().getTipo().equals("procedimento"))
                return true;
            else
                aux.pop();
        }
        return false;
    }

    public String pesquisaGlobalProcedimentoEndereco(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && aux.peek().getTipo().equals("procedimento"))
                return aux.peek().getMemoria();
            else
                aux.pop();
        }
        return "";
    }

    public boolean pesquisaGlobalFuncao(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && (aux.peek().getTipo().equals("função inteiro") || (aux.peek().getTipo().equals("função boolean") )))
                return true;
            else
                aux.pop();
        }
        return false;
    }

    public String pesquisaGlobalFuncaoEndereco(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && (aux.peek().getTipo().equals("função inteiro") || (aux.peek().getTipo().equals("função boolean") )))
                return aux.peek().getMemoria();
            else
                aux.pop();
        }
        return "";
    }

    public boolean pesquisaGlobalFuncaoBool(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema))
                if((aux.peek().getTipo().equals("função boolean")))
                return true;
                else return false;
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalFuncaoInt(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema))
                if((aux.peek().getTipo().equals("função inteiro")))
                return true;
                else return false;
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavel(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && tipoVariavel(aux.peek()))
                return true;
            else
                aux.pop();
        }
        return false;
    }

    public String  pesquisaGlobalVariavelEndereco(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && tipoVariavel(aux.peek()))
                return aux.peek().getMemoria();
            else
                aux.pop();
        }
        return "";
    }


    public Boolean tipoVariavel(Simbolo token){
        if(token.getTipo().equals("inteiro") || token.getTipo().equals("booleano") || token.getTipo().equals("variavel"))
            return true;
        return false;
    }


    public boolean pesquisaGlobalVariavelInt(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && tipoVariavel(aux.peek()))
                if(aux.peek().getTipo().equals("inteiro"))
                return true;
                else return false;
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavelBool(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            if(aux.peek().getLexema().equals(lexema) && tipoVariavel(aux.peek()))
                if(aux.peek().getTipo().equals("booleano"))
                return true;
                else return false;
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavelFunc(String lexema){
        if(pesquisaGlobalVariavel(lexema) || pesquisaGlobalFuncao(lexema))
            return true;
        return false;
    }

    public boolean pesquisaGlobalVariavelFuncInt(String lexema){
        if(pesquisaGlobalVariavelInt(lexema) || pesquisaGlobalFuncaoInt(lexema))
            return true;
        return false;
    }

    public boolean pesquisaGlobalVariavelFuncBool(String lexema){
        if(pesquisaGlobalVariavelBool(lexema) || pesquisaGlobalFuncaoBool(lexema))
            return true;
        return false;
    }

    public String getTipo(String lexema){
        LinkedList<Simbolo> aux= new LinkedList<>(tabela);
        while(!aux.isEmpty()){
            Simbolo simbolo = aux.pop();
            if(simbolo.getLexema().equals(lexema)) {
                if (simbolo.getTipo().contains("booleano"))
                    return "B";

                if (simbolo.getTipo().contains("inteiro"))
                    return "I";
            }
        }
        return null;
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

    public int desempilhaMarca(){
        int contador=0;
        while(!tabela.isEmpty() && tabela.peek().getEscopo()!="L"){
           if(pesquisaGlobalVariavel(tabela.peek().getLexema()))
            contador++;
            tabela.pop();
        }
        tabela.peek().setEscopo("");
        //tabela.pop();
        return contador;
    }

    public int desempilhaTudo(){
        int contador=0;
        while(!tabela.isEmpty()){
            if(pesquisaGlobalVariavel(tabela.peek().getLexema())) {
                contador++;
            }
            tabela.pop();
        }
        //tabela.pop();
        return contador;
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