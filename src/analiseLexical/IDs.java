/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/**
 * Responsável por conter enum dos "IDs" utilizado no Lexical, e também responsável por retornar qual o id ou
 * símbolo em relação ao lexema de entrada na chamada do método "pegaSimboloDoId".
 */

package analiseLexical;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum com todos os IDs e símbolos desses IDs
 */
public enum IDs {
    sprograma("programa"),
    sse("se"),
    sentao("entao"),
    ssenao("senao"),
    senquanto("enquanto"),
    sfaca("faca"),
    sinicio("inicio"),
    sfim("fim"),
    sescreva("escreva"),
    sleia("leia"),
    svar("var"),
    sinteiro("inteiro"),
    Sbooleano("booleano"),
    Sverdadeiro("verdadeiro"),
    Sfalso("falso"),
    sprocedimento("procedimento"),
    sfuncao("funcao"),
    Sdiv("div"),
    Se("e"),
    Sou("ou"),
    Snao("nao"),
    Sidentificador("identificador");

    private final String lexema;
    private static final Map<String, IDs> simboloPorLexema = new HashMap<>();

    /**
     * Realiza um mapeamento para identificar qual é o símbolo referente ao lexema de entrada.
     */
    static {
        for (IDs id : IDs.values()) {
            simboloPorLexema.put(id.getLexema(), id);
        }
    }

    /**
     * Define o lexema que é uma "string".
     *
     * @param lexema que será analisado ou comparado no "enum".
     */
    IDs(String lexema) {
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
    public static String pegaSimboloDoId(String lexema) {
        return simboloPorLexema.getOrDefault(lexema, Sidentificador).toString();
    }
}