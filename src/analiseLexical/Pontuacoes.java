/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

/*
 * Responsável por conter enum das "Pontuações" utilizado no Lexical, e também responsável por retornar
 * qual a Pontuação ou símbolo em relação ao lexema de entrada na chamada do método "pegaSimboloDaPontuacao()".
 */

package analiseLexical;

import java.util.HashMap;
import java.util.Map;

public enum Pontuacoes {
    sponto_virgula(";"),
    Svirgula(","),
    sabre_parenteses("("),
    sfecha_parenteses(")"),
    Sponto(".");

    private final String lexema;
    private static final Map<String, Pontuacoes> pontuacaoPorLexema = new HashMap<>();

    static {
        for (Pontuacoes pontuacao : Pontuacoes.values()) {
            pontuacaoPorLexema.put(pontuacao.getLexema(), pontuacao);
        }
    }

    Pontuacoes(String lexema) {
        this.lexema = lexema;
    }

    private String getLexema() {
        return lexema;
    }

    public static String pegaSimboloDaPontuacao(String lexema) {
        return pontuacaoPorLexema.get(lexema).toString();
    }
}
