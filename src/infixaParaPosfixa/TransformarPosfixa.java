/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package infixaParaPosfixa;

import java.util.Stack;

public class TransformarPosfixa {

    // String infixa = "a*b-c*d^e/f+g*h";
    // System.out.println(transformarInParaPos(infixa));
    // Resultado esperado: ab*cde^*f/-gh*+

    public static int analisaPrioridade(char elemento)
    {
        int prioridade;
        switch(elemento)
        {
            case '+':
            case '-':
                prioridade = 1;
                break;

            case '*':
            case '/':
                prioridade = 2;
                break;

            case '^':
                prioridade = 3;
                break;

            default:
                prioridade = 0;
                break;
        }

        return prioridade;
    }

    public static String transformarInParaPos(String expressaoInfixa) {
        Stack<Integer> pilha = new Stack<Integer>();
        int cont = 0;
        char elemento;
        String expressaoPosfixa = "";

        while (cont < expressaoInfixa.length()) {
            elemento = expressaoInfixa.charAt(cont);

            if(elemento == '*' || elemento == '/' || elemento == '+' || elemento == '-' || elemento == '^') {
                while((!pilha.empty()) && analisaPrioridade(elemento) <= analisaPrioridade((char)((int) pilha.peek())))
                    expressaoPosfixa += (char)((int)pilha.pop());

                pilha.add((int)elemento);
            } else if(elemento == '(') {
                pilha.add((int)elemento);
            } else if(elemento == ')') {
                while((char)((int)pilha.peek()) != '(')
                    expressaoPosfixa += (char)((int)pilha.pop());

                if((char)((int)pilha.peek()) == '(')
                    pilha.pop();
            } else {
                expressaoPosfixa += expressaoInfixa.charAt(cont);
            }
            cont++;
        }
        while(pilha.size() > 0)
            if((char)((int)pilha.peek()) != '(')
                expressaoPosfixa += (char)((int)pilha.pop());
            else
                pilha.pop();

        return expressaoPosfixa;
    }
}
