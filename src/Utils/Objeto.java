package Utils;

public class Objeto {
    private int posicao;
    private String valor;
    private String Simbolo;

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Objeto(int posicao, String valor) {
        this.posicao = posicao;
        this.valor = valor;
    }

    public String getSimbolo() {
        return Simbolo;
    }

    public void setSimbolo(String simbolo) {
        Simbolo = simbolo;
    }

    public Objeto(int posicao, String valor, String simbolo) {
        this.posicao = posicao;
        this.valor = valor;
        Simbolo = simbolo;
    }
}
