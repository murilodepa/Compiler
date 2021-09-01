/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseLexical;

import java.util.HashMap;
import java.util.Map;

public enum Pontuacoes {

    sponto_virgula(";"),
    Svirgula(","),
    sabre_parenteses("("),
    sfecha_parentes(")"),
    Sponto(".");

    final private String lexema;

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

    public static String pegaSimboloPorLexama(String lexema) {
        return pontuacaoPorLexema.get(lexema).toString();
    }
}
