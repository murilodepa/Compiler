package analiseSintatica;

import analiseLexical.Token;

import java.util.LinkedList;
import java.util.Stack;

public class TabelaDeSimbolos {

    Stack<Simbolo> pilha;


    public TabelaDeSimbolos() {
        pilha = new Stack<Simbolo>();
    }

    public void analisarListaDeTokens(LinkedList<Token> tokens) {
        int i = 0;

        for (Token token : tokens) {
            if (token.getLexema() == "procedimento") {

            }
            if (token.getSimbolo() == "sprocedimento") {

            }
        }
    }

    private void insereNaTabela(char identificador) {

    }

    private boolean consultaNaTabela(char identificador, LinkedList<Token> tokens) {
        if (tokens.contains(identificador)) {
            return true;
        }

        return false;
    }

    private void colocaTipoNasVariaveis() {

    }
}