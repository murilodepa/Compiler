/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/*
 * Responsável por conter enum dos "IDs" utilizado no Lexical, e também responsável por retornar qual o id ou
 * símbolo em relação ao lexema de entrada na chamada do método "pegaSimboloDoId".
 */

package analiseLexical;

import java.util.HashMap;
import java.util.Map;

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

    static {
        for (IDs id : IDs.values()) {
            simboloPorLexema.put(id.getLexema(), id);
        }
    }

    IDs(String lexema) {
        this.lexema = lexema;
    }

    private String getLexema() {
        return lexema;
    }

    public static String pegaSimboloDoId(String lexema) {
        return simboloPorLexema.getOrDefault(lexema, Sidentificador).toString();
    }
}