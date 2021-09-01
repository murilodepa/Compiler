package analiseLexical;

import java.util.HashMap;
import java.util.Map;

public enum IDs {
    sprograma("programa"),
    sse("se"),
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

    final private String lexema;

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

    public static String pegaSimboloPorLexama(String lexama) {
        return simboloPorLexema.getOrDefault(lexama, Sidentificador).toString();
    }
}