package Utils;

import analiseLexical.IDs;
import analiseLexical.OperadoresRelacional;
import analiseLexical.Pontuacoes;
import analiseLexical.Token;
import analiseSintatica.TabelaDeSimbolos;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Conversor {

    List<Token> posFixa;
    LinkedList<Objeto> stack;

    public List<Token> converterPosFixa(List<Token> expressao)
    {
        int prioridade=-1;
        posFixa= new ArrayList<>();
        LinkedList<Token> stack = new LinkedList<>();
        for(int i=0;i<expressao.size();i++){
            if(expressao.get(i).getSimbolo().equals(IDs.Sidentificador.toString()) || expressao.get(i).getSimbolo().equals(Operadores.NUMERO) ||
                    expressao.get(i).getSimbolo().equals(IDs.Sverdadeiro.toString()) || expressao.get(i).getSimbolo().equals(IDs.Sfalso.toString())){
                posFixa.add(expressao.get(i));
            }
            if(expressao.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())){
                stack.push(expressao.get(i));
            }

            if(expressao.get(i).getSimbolo().equals(Pontuacoes.sfecha_parenteses.toString())){
                while(!stack.peek().getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())){
                    posFixa.add(stack.pop());
                }
                stack.pop();
            }

            if(expressao.get(i).getSimbolo().equals(Operadores.MAIS) ||
            expressao.get(i).getSimbolo().equals(Operadores.MENOS) ||
                    expressao.get(i).getSimbolo().equals(Operadores.MULTIPLICACAO) ||
                    expressao.get(i).getSimbolo().equals(IDs.Sdiv.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Smaior.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Smaiorig.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Sig.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Smenor.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Smenorig.toString()) ||
                    expressao.get(i).getSimbolo().equals(OperadoresRelacional.Sdif.toString()) ||
                    expressao.get(i).getSimbolo().equals(IDs.Sou.toString()) ||
                    expressao.get(i).getSimbolo().equals(IDs.Se.toString())||
                    expressao.get(i).getSimbolo().equals(Operadores.POSITIVO) ||
                    expressao.get(i).getSimbolo().equals(Operadores.NEGATIVO) ||
                    expressao.get(i).getSimbolo().equals(IDs.Snao.toString())) {
                prioridade=prioridade(expressao.get(i));
                while(!stack.isEmpty() && prioridade(stack.peek())>=prioridade) {
                    posFixa.add(stack.pop());
                }
                stack.push(expressao.get(i));
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
