/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável pela tabela de símbolo do programa, que analisa os procedimentos, funções e variáveis locais e
 * globais, analisando duplicidade, escopo e identificadores, inserindo tipo e altera "uma marca" para identificar o escopo.
 * Verificar escopo                          ** Verificar identificadores
 * - programa - sprograma                     *  - var - svar
 * - início - sinicio                         * - inteiro - sinteiro
 * - procedimento - sprocedimento             * - booleano - Sbooleano
 * - funcao - sfuncao                         * - identificador - Sidentificador
 * - se - sse                                 * - número - Snumero
 * - entao - sentao
 * - senao - ssenao
 * - enquanto - senquanto
 * Verificar escopo                          ** Verificar identificadores
 * - programa - sprograma                     *  - var - svar
 * - início - sinicio                         * - inteiro - sinteiro
 * - procedimento - sprocedimento             * - booleano - Sbooleano
 * - funcao - sfuncao                         * - identificador - Sidentificador
 * - se - sse                                 * - número - Snumero
 * - entao - sentao
 * - senao - ssenao
 * - enquanto - senquanto
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

    /**
     * Responsável por inserir um novo simbolo na tabela
     *
     * @param lexema lexema do novo símbolo
     * @param escopo escopo de declaração do novo símbolo
     * @param tipo tipo do novo símbolo
     * @param memoria valor de memória do novo símbolo
     */
    public void insereTabela(String lexema, String escopo, String tipo, String memoria) {
        tabela.push(new Simbolo(lexema, escopo, tipo, memoria));
    }

    /**
     * Responsável por perquisar a duplicidade na declaração de um lexema (variável) na tabela de símbolo
     *
     * @param lexema lexema a ser analisado
     *
     * @return true ou false, dependendo se o lexema foi ou não encontrado
     */
    public boolean pesquisarDuplicidade(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty() && aux.peek().getEscopo().equals("")) {
            Simbolo simbolo = aux.pop();
            if (simbolo.getLexema().equals(lexema))
                return true;
        }
        return false;
    }

    /**
     * Responsável por inserir o tipo de um simbolo na tabela
     *
     * @param tipo o tipo a ser inserido na tabela
     * @param endereco endereco da variável
     * @param varLocal endereço local da variável, ajuda a compor a memória do simbolo
     *
     * @return o contador
     */
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

    /**
     * Faz uma pesquisa em toda a tabela de símbolo, verificando se aquele lexema está contido na tabela
     *
     * @param lexema lexema a ser pesquisado
     *
     * @return true ou false, dependendo se o lexema foi ou não encontrado
     */
    public boolean pesquisaGlobal(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            if (aux.pop().getLexema().equals(lexema))
                return false;
        }
        return true;
    }

    /**
     * Metodo responsável por retornar se um procedimento existe na tabela
     *
     * @param lexema lexema (procedimento) que será buscado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por retornar o endereço de um procedimento
     *
     * @param lexema lexema (procedimento) que será utilizado para buscar o endereco
     * @return o endereco do lexema
     */
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

    /**
     * Metodo responsável por retornar se uma função existe na tabela e se é do tipo booleano ou inteiro
     *
     * @param lexema lexema (função) que será buscado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por retornar o endereço de uma função
     *
     * @param lexema lexema (função) que será utilizado para buscar o endereco
     * @return o endereco do lexema
     */
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

    /**
     * Metodo responsável por retornar se uma função existe na tabela e se é do tipo booleano
     *
     * @param lexema lexema (função) que será buscado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por retornar se uma função existe na tabela e se é do tipo inteiro
     *
     * @param lexema lexema (função) que será buscado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por retornar se um lexema já existe na tabela
     *
     * @param lexema lexema que será buscado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por retornar o endereço de uma variável
     *
     * @param lexema lexema (variável) que será utilizado para buscar o endereco
     * @return o endereco do lexema
     */
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

    /**
     * Verifica se um token é igual a inteiro, booleano ou variavel
     *
     * @return verdadeiro ou falso, dependendo da verificação de tipo
     */
    public Boolean tipoVariavel(Simbolo token) {
        return token.getTipo().equals("inteiro") || token.getTipo().equals("booleano") || token.getTipo().equals("variavel");
    }

    /**
     * Metodo responsável por pesquisar na tabela se já existe uma variável inteira de mesmo nome
     *
     * @param lexema lexema a ser pesquisado/analisado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por pesquisar na tabela se já existe uma variável booleana de mesmo nome
     *
     * @param lexema lexema a ser pesquisado/analisado
     * @return verdadeiro ou falso, dependendo da análise
     */
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

    /**
     * Metodo responsável por pesquisar na tabela se já existe uma variável ou função inteira de mesmo nome
     *
     * @param lexema lexema a ser pesquisado
     * @return verdadeiro ou falso, dependendo da análise
     */
    public boolean pesquisaGlobalVariavelFuncInt(String lexema) {
        return pesquisaGlobalVariavelInt(lexema) || pesquisaGlobalFuncaoInt(lexema);
    }

    /**
     * Metodo responsável por pesquisar na tabela se já existe uma variável ou função booleana de mesmo nome
     *
     * @param lexema lexema a ser pesquisado
     * @return verdadeiro ou falso, dependendo da análise
     */
    public boolean pesquisaGlobalVariavelFuncBool(String lexema) {
        return pesquisaGlobalVariavelBool(lexema) || pesquisaGlobalFuncaoBool(lexema);
    }

    /**
     * Metodo responsável por verificar o tipo de uma variável
     *
     * @param lexema variável a ser analisada
     * @return o tipo da variável
     */
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

    /**
     * Metodo responsável por pesquisar na tabela, a partir do lexema, um símbolo
     *
     * @param lexema lexema a ser pesquisado
     * @return o simbolo encontrado
     */
    public Simbolo pesquisaLocal(String lexema) {
        LinkedList<Simbolo> aux = new LinkedList<>(tabela);
        while (!aux.isEmpty()) {
            Simbolo simbolo = aux.pop();
            if (simbolo.getLexema().equals(lexema))
                return simbolo;
        }
        return null;
    }

    /**
     * Metodo responsável por desempilhar a pilha até achar a marcas "L" (de procedimento e função)
     *
     * @return o contador, de quantas funções ou procedimentos existem
     */
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

    /**
     * Metodo responsável por desempilhar a tabela e verificar quantos lexemas foram declarados
     *
     * @return o contador (lexemas) declarados
     */
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

    /**
     * Metodo responsável por alterar o tipo do simbolo do topo da tabela
     *
     * @param tipo novo tipo que será inserido
     */
    public void alteraTipoTopo(String tipo) {
        if (!tabela.isEmpty()) {
            Simbolo simbolo = tabela.pop();
            simbolo.setTipo(tipo);
            tabela.push(simbolo);
        }
    }
}