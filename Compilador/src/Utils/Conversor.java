package Utils;

import analiseLexical.IDs;
import analiseLexical.OperadoresRelacional;
import analiseLexical.Pontuacoes;
import analiseLexical.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Conversor {

    List<Token> posFixa;
    LinkedList<Objeto> stack;

    public List<Token> converterPosFixa(List<Token> expressao)
    {
        int prioridade=-1;
        posFixa= new ArrayList<>();
        LinkedList<Token> stack = new LinkedList<>();
        for (Token token : expressao) {
            if (token.getSimbolo().equals(IDs.Sidentificador.toString()) || token.getSimbolo().equals(Operadores.NUMERO) ||
                    token.getSimbolo().equals(IDs.Sverdadeiro.toString()) || token.getSimbolo().equals(IDs.Sfalso.toString())) {
                posFixa.add(token);
            }
            if (token.getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
                stack.push(token);
            }

            if (token.getSimbolo().equals(Pontuacoes.sfecha_parenteses.toString())) {
                while (!Objects.requireNonNull(stack.peek()).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
                    posFixa.add(stack.pop());
                }
                stack.pop();
            }

            String simbolo = token.getSimbolo();

            if (simbolo.equals(Operadores.MAIS) ||
                    simbolo.equals(Operadores.MENOS) ||
                    simbolo.equals(Operadores.MULTIPLICACAO) ||
                    simbolo.equals(IDs.Sdiv.toString()) ||
                    simbolo.equals(OperadoresRelacional.Smaior.toString()) ||
                    simbolo.equals(OperadoresRelacional.Smaiorig.toString()) ||
                    simbolo.equals(OperadoresRelacional.Sig.toString()) ||
                    simbolo.equals(OperadoresRelacional.Smenor.toString()) ||
                    simbolo.equals(OperadoresRelacional.Smenorig.toString()) ||
                    simbolo.equals(OperadoresRelacional.Sdif.toString()) ||
                    simbolo.equals(IDs.Sou.toString()) ||
                    simbolo.equals(IDs.Se.toString()) ||
                    simbolo.equals(Operadores.POSITIVO) ||
                    simbolo.equals(Operadores.NEGATIVO) ||
                    simbolo.equals(IDs.Snao.toString())) {
                prioridade = prioridade(token);
                while (!stack.isEmpty() && prioridade(stack.peek()) >= prioridade) {
                    posFixa.add(stack.pop());
                }
                stack.push(token);
            }

        }
        while(!stack.isEmpty()){
            posFixa.add(stack.pop());
        }
        return posFixa;
    }

    private int prioridade(Token token){

        if (token.getSimbolo().equals(Operadores.MAIS) ||
                token.getSimbolo().equals(Operadores.MENOS)) {
            return 5;
        } else if(token.getSimbolo().equals(Operadores.MULTIPLICACAO) ||
                token.getSimbolo().equals(IDs.Sdiv.toString())) {
            return 6;
        }else if(token.getSimbolo().equals(Operadores.POSITIVO) ||
                token.getSimbolo().equals(Operadores.NEGATIVO)) {
            return 7;
        }else if(token.getSimbolo().equals(OperadoresRelacional.Smaior.toString()) ||
                token.getSimbolo().equals(OperadoresRelacional.Smaiorig.toString()) ||
                token.getSimbolo().equals(OperadoresRelacional.Sig.toString()) ||
                token.getSimbolo().equals(OperadoresRelacional.Smenor.toString()) ||
                token.getSimbolo().equals(OperadoresRelacional.Smenorig.toString()) ||
                token.getSimbolo().equals(OperadoresRelacional.Sdif.toString())) {
           return 4;
       }else if( token.getSimbolo().equals(IDs.Snao.toString())) {
           return 3;
       } else if(token.getSimbolo().equals(IDs.Se.toString())){
            return 2;
        } else if(token.getSimbolo().equals(IDs.Sou.toString())) {
            return 1;
        } else {
            return -99;
        }
    }

}
