/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseLexical;

import java.util.HashMap;
import java.util.Map;

public enum OperadoresRelacional {
    Smaior(">"),
    Smaiorig(">="),
    Sig("="),
    Smenor("<"),
    Smenorig("<="),
    Sdif("!=");

    final private String lexema;

    private static final Map<String, OperadoresRelacional> operadorPorLexema = new HashMap<>();

    static {
        for (OperadoresRelacional operador : OperadoresRelacional.values()) {
            operadorPorLexema.put(operador.getLexema(), operador);
        }
    }

    OperadoresRelacional(String lexema) {
        this.lexema = lexema;
    }

    private String getLexema() {
        return lexema;
    }

    public static String pegaSimboloDoOperador(String lexema) {
        return operadorPorLexema.get(lexema).toString();
    }
}