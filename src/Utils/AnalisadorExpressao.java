/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/*
 * Responsável por analisar as expressões, validação de variável inteiro, booleano e o tipo do retorno.
 */

package Utils;

import analiseLexical.IDs;
import analiseLexical.OperadoresRelacional;
import analiseLexical.Token;
import analiseSintatica.TabelaDeSimbolos;

import java.util.LinkedList;
import java.util.List;

public class AnalisadorExpressao {
    public String analisarExpressao(List<Token> posFixa, TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        LinkedList<Token> stack = new LinkedList<>();
        for (Token value : posFixa) {
            if (value.getSimbolo().equals(IDs.Sidentificador.toString()) || value.getSimbolo().equals(Operadores.NUMERO) ||
                    value.getSimbolo().equals(IDs.Sverdadeiro.toString()) || value.getSimbolo().equals(IDs.Sfalso.toString())) {
                stack.push(value);
            }
            if (value.getSimbolo().equals(Operadores.MAIS) ||
                    value.getSimbolo().equals(Operadores.MENOS) ||
                    value.getSimbolo().equals(Operadores.MULTIPLICACAO) ||
                    value.getSimbolo().equals(IDs.Sdiv.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarInteiro(token, tabelaDeSimbolos);
                }
                stack.push(new Token("I", Operadores.NUMERO));
            }

            if (value.getSimbolo().equals(Operadores.POSITIVO) ||
                    value.getSimbolo().equals(Operadores.NEGATIVO)) {
                Token token = stack.pop();
                validarInteiro(token, tabelaDeSimbolos);
                stack.push(new Token("I", Operadores.NUMERO));
            }

            if (value.getSimbolo().equals(OperadoresRelacional.Smaior.toString()) ||
                    value.getSimbolo().equals(OperadoresRelacional.Smaiorig.toString()) ||
                    value.getSimbolo().equals(OperadoresRelacional.Sig.toString()) ||
                    value.getSimbolo().equals(OperadoresRelacional.Smenor.toString()) ||
                    value.getSimbolo().equals(OperadoresRelacional.Smenorig.toString()) ||
                    value.getSimbolo().equals(OperadoresRelacional.Sdif.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarInteiro(token, tabelaDeSimbolos);
                }
                stack.push(new Token("B", IDs.Sverdadeiro.toString()));
            }

            if (value.getSimbolo().equals(IDs.Sou.toString()) ||
                    value.getSimbolo().equals(IDs.Se.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarBooleano(token, tabelaDeSimbolos);
                }
                stack.push(new Token("B", IDs.Sverdadeiro.toString()));
            }

            if (value.getSimbolo().equals(IDs.Snao.toString())) {
                Token token = stack.pop();
                validarBooleano(token, tabelaDeSimbolos);
                stack.push(new Token("B", IDs.Sverdadeiro.toString()));
            }
        }
        Token retorno = stack.pop();
        return tipoRetorno(retorno, tabelaDeSimbolos);
    }

    private void validarInteiro(Token token, TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        if (token.getSimbolo().equals(IDs.Sidentificador.toString()) || token.getSimbolo().equals(Operadores.NUMERO)) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!tabelaDeSimbolos.pesquisaGlobalVariavelFuncInt(token.getLexema())) {
                    throw new Exception("Erro! variável não encontrada ou tipo errado");
                }
            }
        } else {
            throw new Exception("Erro! expressão inválida");
        }
    }

    private void validarBooleano(Token token, TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        if (token.getSimbolo().equals(IDs.Sidentificador.toString()) || token.getSimbolo().equals(IDs.Sverdadeiro.toString()) ||
                token.getSimbolo().equals(IDs.Sfalso.toString())) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!tabelaDeSimbolos.pesquisaGlobalVariavelFuncBool(token.getLexema())) {
                    throw new Exception("Erro! variável não encontrada ou tipo errado");
                }
            }
        } else {
            throw new Exception("Erro! expressão inválida");
        }
    }

    private String tipoRetorno(Token token, TabelaDeSimbolos tabelaDeSimbolos) {
        if (token.getSimbolo().equals(IDs.Sverdadeiro.toString()) ||
                token.getSimbolo().equals(IDs.Sfalso.toString())) {
            return "B";
        } else if (token.getSimbolo().equals(Operadores.NUMERO)) {
            return "I";
        } else {
            if (tabelaDeSimbolos.pesquisaGlobalVariavelFuncInt(token.getLexema())) {
                return "I";
            } else {
                return "B";
            }
        }
    }
}
