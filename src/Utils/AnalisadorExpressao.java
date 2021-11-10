package Utils;

import analiseLexical.IDs;
import analiseLexical.OperadoresRelacional;
import analiseLexical.Token;
import analiseSintatica.Simbolo;
import analiseSintatica.TabelaDeSimbolos;

import java.util.LinkedList;
import java.util.List;

public class AnalisadorExpressao {
    LinkedList<Objeto> stack;

    public String analisarExpressao(List<Token> posFixa, TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        LinkedList<Token> stack = new LinkedList<>();
        for (int i = 0; i < posFixa.size(); i++) {
            if (posFixa.get(i).getSimbolo().equals(IDs.Sidentificador.toString()) || posFixa.get(i).getSimbolo().equals(Operadores.NUMERO) ||
                    posFixa.get(i).getSimbolo().equals(IDs.Sverdadeiro.toString()) || posFixa.get(i).getSimbolo().equals(IDs.Sfalso.toString())) {
                stack.push(posFixa.get(i));
            }
            if (posFixa.get(i).getSimbolo().equals(Operadores.MAIS) ||
                    posFixa.get(i).getSimbolo().equals(Operadores.MENOS) ||
                    posFixa.get(i).getSimbolo().equals(Operadores.MULTIPLICACAO) ||
                    posFixa.get(i).getSimbolo().equals(IDs.Sdiv.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarInteiro(token,tabelaDeSimbolos);
                }
                stack.push(new Token("I",Operadores.NUMERO));
            }

            if(posFixa.get(i).getSimbolo().equals(Operadores.POSITIVO) ||
                    posFixa.get(i).getSimbolo().equals(Operadores.NEGATIVO)) {
                Token token = stack.pop();
                validarInteiro(token,tabelaDeSimbolos);
                stack.push(new Token("I",Operadores.NUMERO));
            }

            if(posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Smaior.toString()) ||
                    posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Smaiorig.toString()) ||
                    posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Sig.toString()) ||
                    posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Smenor.toString()) ||
                    posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Smenorig.toString()) ||
                    posFixa.get(i).getSimbolo().equals(OperadoresRelacional.Sdif.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarInteiro(token,tabelaDeSimbolos);
                }
                stack.push(new Token("B",IDs.Sverdadeiro.toString()));
            }

            if(posFixa.get(i).getSimbolo().equals(IDs.Sou.toString()) ||
                    posFixa.get(i).getSimbolo().equals(IDs.Se.toString())) {
                for (int j = 0; j < 2; j++) {
                    Token token = stack.pop();
                    validarBooleano(token,tabelaDeSimbolos);
                }
                stack.push(new Token("B",IDs.Sverdadeiro.toString()));
            }

            if(posFixa.get(i).getSimbolo().equals(IDs.Snao.toString())) {
                Token token = stack.pop();
                validarBooleano(token,tabelaDeSimbolos);
                stack.push(new Token("B",IDs.Sverdadeiro.toString()));
            }
        }
        Token retorno = stack.pop();
        return tipoRetorno(retorno,tabelaDeSimbolos);
    }


    private void validarInteiro(Token token,TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        if(token.getSimbolo().equals(IDs.Sidentificador.toString()) ||  token.getSimbolo().equals(Operadores.NUMERO)) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!tabelaDeSimbolos.pesquisaGlobalVariavelFuncInt(token.getLexema())) {
                    throw new Exception("Erro! variável não encontrada ou tipo errado");
                }
            }
        } else {
            throw new Exception("Erro! expressão inválida");
        }
    }

    private void validarBooleano(Token token,TabelaDeSimbolos tabelaDeSimbolos) throws Exception {
        if (token.getSimbolo().equals(IDs.Sidentificador.toString()) || token.getSimbolo().equals(IDs.Sverdadeiro.toString()) ||
                token.getSimbolo().equals(IDs.Sfalso.toString())) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!tabelaDeSimbolos.pesquisaGlobalVariavelFuncBool(token.getLexema())) {
                    throw new Exception("Erro! variável não encontrada ou tipo errado");
                }
            }
        } else {
            throw new Exception("Erro! expressão inválida");
        }
    }

    private String tipoRetorno(Token token,TabelaDeSimbolos tabelaDeSimbolos) {
        if(token.getSimbolo().equals(IDs.Sverdadeiro.toString()) ||
                token.getSimbolo().equals(IDs.Sfalso.toString())) {
            return "B";
        } else if(token.getSimbolo().equals(Operadores.NUMERO)){
            return "I";
        } else {
            if(tabelaDeSimbolos.pesquisaGlobalVariavelFuncInt(token.getLexema())){
                return "I";
            } else {
                return "B";
            }
        }
    }

}
