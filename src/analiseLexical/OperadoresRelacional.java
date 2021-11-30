/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável por conter enum dos "Operadores Relacional" utilizado no Lexical, e também responsável por retornar
 * qual o Operadores Relacional ou símbolo em relação ao lexema de entrada na chamada do método "pegaSimboloDoOperador()".
 */
package analiseLexical;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum com todos os Operadores Relacional e símbolos desses Operadores
 */
public enum OperadoresRelacional {
    Smaior(">"),
    Smaiorig(">="),
    Sig("="),
    Smenor("<"),
    Smenorig("<="),
    Sdif("!=");

    private final String lexema;
    private static final Map<String, OperadoresRelacional> operadorPorLexema = new HashMap<>();

    /**
     * Realiza um mapeamento para identificar qual é o símbolo referente ao lexema de entrada.
     */
    static {
        for (OperadoresRelacional operador : OperadoresRelacional.values()) {
            operadorPorLexema.put(operador.getLexema(), operador);
        }
    }

    /**
     * Define o lexema que é uma "string".
     *
     * @param lexema que será analisado ou comparado no "enum".
     */
    OperadoresRelacional(String lexema) {
        this.lexema = lexema;
    }

    /**
     * Retorna qual o lexema que foi definido.
     *
     * @return o lexema para ser analisado ou comparado no enum.
     */
    private String getLexema() {
        return lexema;
    }

    /**
     * Método responsável por entrar com uma lexema "string" e retornar um símbolo, que seria o respectivo símbolo do
     * lexema de entrada.
     *
     * @param lexema é uma string que será analisada ou comparada no enum.
     * @return um símbolo, que seria o respectivo símbolo do lexema de entrada.
     */
    public static String pegaSimboloDoOperador(String lexema) {
        return operadorPorLexema.get(lexema).toString();
    }
}