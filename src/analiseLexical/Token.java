package analiseLexical;

public class Token {

    private String lexema;
    private String simbolo;

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

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
