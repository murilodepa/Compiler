package analiseLexical;

public class Token {

    final private String lexema;
    final private String simbolo;

    public Token(String lexema, String simbolo) {
        this.lexema = lexema;
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
