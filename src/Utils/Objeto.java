package Utils;

public class Objeto {
    private final int posicao;
    private final String valor;
    private final String Simbolo;

    public int getPosicao() {
        return posicao;
    }

    public String getValor() {
        return valor;
    }

    public String getSimbolo() {
        return Simbolo;
    }

    public Objeto(int posicao, String valor, String simbolo) {
        this.posicao = posicao;
        this.valor = valor;
        Simbolo = simbolo;
    }
}
