/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável pela tabela de símbolo do programa, que analisa os procedimentos, funções e variáveis locais e
 * globais, analisando duplicidade, escopo e identificadores, inserindo tipo e altera "uma marca" para identificar o escopo.
 */

/** Verificar escopo                          ** Verificar identificadores
  * - programa - sprograma                     *  - var - svar
  * - início - sinicio                         * - inteiro - sinteiro
  * - procedimento - sprocedimento             * - booleano - Sbooleano
  * - funcao - sfuncao                         * - identificador - Sidentificador
  * - se - sse                                 * - número - Snumero
  * - entao - sentao
  * - senao - ssenao
  * - enquanto - senquanto
 */

package analiseSintatica;

import java.util.*;

public class TabelaDeSimbolos {

    private final LinkedList<Simbolo> tabela;

    public TabelaDeSimbolos() {
        tabela = new LinkedList<>();
    }

    public void insereTabela(String lexema, String escopo, String tipo, String memoria) {
        tabela.push(new Simbolo(lexema, escopo, tipo, memoria));
    }

    public boolean pesquisarDuplicidade(String lexama) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty() && aux.peek().getEscopo().equals("")) {
            Simbolo simbolo = aux.pop();
            if (simbolo.getLexema().equals(lexama))
                return true;
        }
        return false;
    }

    public int colocaTipo(String tipo, int endereco, int varLocal) {
        int contador = 0;
        LinkedList<Simbolo> aux = new LinkedList<>();
        while (!tabela.isEmpty() && tabela.peek().getTipo().contains("variavel")) aux.push(tabela.pop());
        while (!aux.isEmpty()) {
            Simbolo simbolo = aux.pop();
            simbolo.setTipo(tipo);
            simbolo.setMemoria(String.valueOf(endereco + contador + varLocal));
            tabela.push(simbolo);
            contador++;
        }
        return contador;
    }

    public boolean pesquisaGlobal(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.pop().getLexema().equals(lexema))
                return false;
        }
        return true;
    }

    public boolean pesquisaGlobalProcedimento(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && Objects.requireNonNull(aux.peek()).getTipo().equals("procedimento"))
                return false;
            else
                aux.pop();
        }
        return true;
    }

    public String pesquisaGlobalProcedimentoEndereco(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && Objects.requireNonNull(aux.peek()).getTipo().equals("procedimento"))
                return Objects.requireNonNull(aux.peek()).getMemoria();
            else
                aux.pop();
        }
        return "";
    }

    public boolean pesquisaGlobalFuncao(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && (Objects.requireNonNull(aux.peek()).getTipo().equals("função inteiro") || (Objects.requireNonNull(aux.peek()).getTipo().equals("função boolean"))))
                return true;
            else
                aux.pop();
        }
        return false;
    }

    public String pesquisaGlobalFuncaoEndereco(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && (Objects.requireNonNull(aux.peek()).getTipo().equals("função inteiro") || (Objects.requireNonNull(aux.peek()).getTipo().equals("função boolean"))))
                return Objects.requireNonNull(aux.peek()).getMemoria();
            else
                aux.pop();
        }
        return "";
    }

    public boolean pesquisaGlobalFuncaoBool(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema))
                return Objects.requireNonNull(aux.peek()).getTipo().equals("função boolean");
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalFuncaoInt(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema))
                return Objects.requireNonNull(aux.peek()).getTipo().equals("função inteiro");
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavel(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && tipoVariavel(Objects.requireNonNull(aux.peek())))
                return true;
            else
                aux.pop();
        }
        return false;
    }

    public String pesquisaGlobalVariavelEndereco(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && tipoVariavel(Objects.requireNonNull(aux.peek())))
                return Objects.requireNonNull(aux.peek()).getMemoria();
            else
                aux.pop();
        }
        return "";
    }

    public Boolean tipoVariavel(Simbolo token) {
        return token.getTipo().equals("inteiro") || token.getTipo().equals("booleano") || token.getTipo().equals("variavel");
    }

    public boolean pesquisaGlobalVariavelInt(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && tipoVariavel(Objects.requireNonNull(aux.peek())))
                return Objects.requireNonNull(aux.peek()).getTipo().equals("inteiro");
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavelBool(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.peek().getLexema().equals(lexema) && tipoVariavel(Objects.requireNonNull(aux.peek())))
                return Objects.requireNonNull(aux.peek()).getTipo().equals("booleano");
            else
                aux.pop();
        }
        return false;
    }

    public boolean pesquisaGlobalVariavelFuncInt(String lexema) {
        return pesquisaGlobalVariavelInt(lexema) || pesquisaGlobalFuncaoInt(lexema);
    }

    public boolean pesquisaGlobalVariavelFuncBool(String lexema) {
        return pesquisaGlobalVariavelBool(lexema) || pesquisaGlobalFuncaoBool(lexema);
    }

    public String getTipo(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            Simbolo simbolo = aux.pop();
            if (simbolo.getLexema().equals(lexema)) {
                if (simbolo.getTipo().contains("booleano"))
                    return "B";

                if (simbolo.getTipo().contains("inteiro"))
                    return "I";
            }
        }
        return null;
    }

    public Simbolo pesquisaLocal(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            Simbolo simbolo = aux.pop();
            if (simbolo.getLexema().equals(lexema))
                return simbolo;
        }
        return null;
    }

    public int desempilhaMarca() {
        int contador = 0;
        while (!tabela.isEmpty() && !Objects.equals(tabela.peek().getEscopo(), "L")) {
            if (pesquisaGlobalVariavel(Objects.requireNonNull(tabela.peek()).getLexema()))
                contador++;
            tabela.pop();
        }
        Objects.requireNonNull(tabela.peek()).setEscopo("");
        //tabela.pop();
        return contador;
    }

    public int desempilhaTudo() {
        int contador = 0;
        while (!tabela.isEmpty()) {
            if (pesquisaGlobalVariavel(tabela.peek().getLexema())) {
                contador++;
            }
            tabela.pop();
        }
        return contador;
    }

    public void alteraTipoTopo(String tipo) {
        if (!tabela.isEmpty()) {
            Simbolo simbolo = tabela.pop();
            simbolo.setTipo(tipo);
            tabela.push(simbolo);
        }
    }
}