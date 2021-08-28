package analiseLexical;

public class Token {

    private String lexema;
    private String simbolo;

    public Token(String lexema) {
        this.lexema = lexema;
    }

    public Token(String lexema, String simbolo) {
        this.lexema = lexema;
        this.simbolo = simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getLexema() {
        return lexema;
    }

    public String getSimbolo() {
        return simbolo;
    }
}