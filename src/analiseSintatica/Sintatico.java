/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseSintatica;

import Utils.Operadores;
import analiseLexical.*;

import java.io.IOException;
import java.util.LinkedList;

public class Sintatico {
    LinkedList<Token> tokens;
    public int rotulo;
    private int i = 0;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public Sintatico(String caminhoDoArquivo) throws IOException {
        Lexical lexical = new Lexical(caminhoDoArquivo);
        tabelaDeSimbolos = new TabelaDeSimbolos();
        try {
            lexical.analisadorLexical();
            tokens = lexical.getTokens();
            for (Token token : tokens) {
                System.out.print(token.getLexema() + " -> ");
                System.out.println(token.getSimbolo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        analisadorSintatico();
    }

    public void analisadorSintatico() {
        rotulo = 1;

        if (tokens.get(i).getSimbolo().equals(IDs.sprograma.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), -1, "", "");
                i++;
                if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                    i++;
                    // analisaBloco();
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.Sponto.toString())) {
                        i++;
                        if (i == tokens.size()) {
                            System.out.println("SUCESSO!");
                        } else {
                            System.out.println("ERROU!");
                        }
                    } else {
                        System.out.println("ERROU!");
                    }
                } else {
                    System.out.println("ERROU!");
                }
            } else {
                System.out.println("ERROU!");
            }
        } else {
            System.out.println("ERROU!");
        }
    }

    public void analisaBloco() {
        analisaEtVariaveis();
        analisaSubrotinas();
        analisaComandos();
    }

    public void analisaEtVariaveis() {
        if (tokens.get(i).getSimbolo().equals(IDs.svar.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                while (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                    analisaVariaveis();
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                        i++;
                    } else {
                        System.out.println("ERROU!");
                    }
                }
            } else {
                System.out.println("ERROU!");
            }
        }
    }

    public void analisaVariaveis() {
        do {
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!pesquisaDuplicadoVarTabela()) {
                    //insereTabela(tokens.get(i).getLexema());
                    i++;
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString()) || tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString())) {
                            i++;
                            if (tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                                System.out.println("ERROU!");
                            }
                        }
                    } else {
                        System.out.println("ERROU!");
                    }
                } else {
                    System.out.println("ERROU!");
                }
            } else {
                System.out.println("ERROU!");
            }

        } while (!tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS));
        i++;
        analisaTipo();
    }

    public boolean pesquisaDuplicadoVarTabela() {
        return true;
    }

    public void analisaTipo() {
        String simboloAtual = tokens.get(i).getSimbolo();
        if (!simboloAtual.equals(IDs.sinteiro.toString()) && !simboloAtual.equals(IDs.Sbooleano.toString())) {
            System.out.println("ERROU");
        } else {
            // colocaTipoTabela(tokens.get(i).getLexema());
        }
        i++;
    }

    public void analisaSubrotinas() {
        //int auxRot, flag;
        //flag = 0;
        if(tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString()) || tokens.get(i).getSimbolo().equals(IDs.sfuncao.toString())) {
            /*
             * auxRot = rotulo
             * gera()
             * rotulo ++
             * flag = 1
             * */
        }

        while (tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString() || tokens.get(i).getSimbolo().equals(IDs.sfuncao.toString()) {
            if(tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString())){
                analisaDeclaracaoProcedimento();
            }else{
                analisaDeclaracaoFuncao();
            }

            if(tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                i++;
            }
        }

        /*if(flag == 1){
            gera()
        }*/
    }

    private void analisaDeclaracaoProcedimento() {

    }

    private void analisaDeclaracaoFuncao() {

    }

    public void analisaComandos() {
        if (tokens.get(i).getSimbolo().equals(IDs.sinicio.toString())) {
            i++;
            analisaComandoSimples();
            while (!tokens.get(i).getSimbolo().equals(IDs.sfim.toString())) {
                if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                    i++;
                    if (!tokens.get(i).getSimbolo().equals(IDs.sfim.toString())) {
                        analisaComandoSimples();
                    }
                } else {
                    System.out.println("ERROU");
                }
            }
            i++;
        } else {
            System.out.println("ERROU");
        }
    }

    public void analisaComandoSimples() {

        String simboloAtual = tokens.get(i).getSimbolo();
        if (simboloAtual.equals(IDs.Sidentificador.toString())) {
            analisaAtribuicaoChamadaProcedimento();
        } else if (simboloAtual.equals(IDs.sse.toString())) {
            analisaSe();
        } else if (simboloAtual.equals(IDs.senquanto.toString())) {
            analisaEnquanto();
        } else if (simboloAtual.equals(IDs.sleia.toString())) {
            analisaLeia();
        } else if (simboloAtual.equals(IDs.sescreva.toString())) {
            analisaEscreva();
        } else {
            analisaComandos();
        }
    }

    private void analisaAtribuicaoChamadaProcedimento() {
        i++;
        if (tokens.get(i).getSimbolo().equals(Operadores.ATRIBUICAO)) {
            //analisaAtribuicao();
        } else {
            //chamadaProcedimento();
        }
    }

    private void analisaSe() {
        i++;
        analisaExpressao();
        if (tokens.get(i).getSimbolo().equals(IDs.sentao.toString())) {
            i++;
            analisaComandoSimples();
        }
    }

    private void analisaEnquanto() {
        int auxRot1, auxRot2;

        auxRot1 = rotulo;
        /*
GERA
*/

        rotulo = rotulo + 1;


        i++;
        analisaExpressao();

        if (tokens.get(i).getSimbolo().equals(IDs.sfaca.toString())) {
            auxRot2 = rotulo;
            //GERA
            rotulo = rotulo + 1;
            i++;
            analisaComandoSimples();
            //GERA
            //GERA
        } else {
            System.out.println("ERROU!");
        }
    }

    private void analisaLeia() {
        i++;
        String simboloAtual = tokens.get(i).getSimbolo();
        if (simboloAtual.equals(Pontuacoes.sabre_parenteses.toString())) {
            if (pesquisaDeclaracaoVariavelTabela(tokens.get(i).getLexema())) { //OBS: pesquisa em toda a tabela
                i++;
                simboloAtual = tokens.get(i).getSimbolo();
                if (simboloAtual.equals(String.valueOf(Pontuacoes.sfecha_parenteses.toString()))) {
                    i++;
                } else {
                    System.out.println("ERROU");
                }
            }
        } else {
            System.out.println("ERROU");
        }
    }

    private void analisaEscreva() {
        i++;
        String simboloAtual = tokens.get(i).getSimbolo();

        if (simboloAtual.equals(String.valueOf(Pontuacoes.sabre_parenteses))) {
            i++;
            simboloAtual = tokens.get(i).getSimbolo();
            if (simboloAtual.equals(IDs.Sidentificador.toString())) {
                if (pesquisaDeclaracaoVariavelTabela(tokens.get(i).getLexema())) {
                    i++;
                    simboloAtual = tokens.get(i).getSimbolo();
                    if (simboloAtual.equals(String.valueOf(Pontuacoes.sfecha_parenteses))) {
                        i++;
                    } else {
                        System.out.println("ERROU!");
                    }
                } else {
                    System.out.println("ERROU!");
                }
            } else {
                System.out.println("ERROU!");
            }
        } else {
            System.out.println("ERROU!");
        }
    }

    private void analisaExpressao() {
        String simboloAtual = tokens.get(i).getSimbolo();
        if (simboloAtual.equals(OperadoresRelacional.Smaior.toString()) ||
                simboloAtual.equals(OperadoresRelacional.Smaiorig.toString()) ||
                simboloAtual.equals(OperadoresRelacional.Sig.toString()) ||
                simboloAtual.equals(OperadoresRelacional.Smenor.toString()) ||
                simboloAtual.equals(OperadoresRelacional.Smenorig.toString()) ||
                simboloAtual.equals(OperadoresRelacional.Sdif.toString())) {
            i++;
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() {
        String simboloAtual = tokens.get(i).getSimbolo();
        if(simboloAtual.equals(Operadores.MAIS) || simboloAtual.equals(Operadores.MENOS)){
            i++;
            simboloAtual = tokens.get(i).getSimbolo();
            analisaTermo();
            while(simboloAtual.equals(Operadores.MAIS) || simboloAtual.equals(Operadores.MENOS) || simboloAtual.equals(IDs.Sou.toString())){
                i++;
                simboloAtual = tokens.get(i).getSimbolo();
                analisaTermo();
            }
        }
    }

    private boolean pesquisaDeclaracaoVariavelTabela(String lexema) {
        return true;
    }

    private void analisaFator() {
        if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
           /* if(pesquisaTabela()){
                if(){

                   analisaChamadaFuncao();
                } else {
                i++;
            } else{
            System.out.println("ERROU!");
        }
       */
        } else {
            if (tokens.get(i).getSimbolo().equals(Operadores.NUMERO)) {
                i++;
            } else {
                if (tokens.get(i).getSimbolo().equals(IDs.Snao.toString())) {
                    i++;
                    analisaFator();
                } else {
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
                        // Expressão está entre parentes
                        System.out.println("Expressão está entre parentes");
                        i++;
                        analisaExpressao();
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.sfecha_parenteses.toString())) {
                            i++;
                        } else {
                            System.out.println("ERRO!");
                        }
                    }
                }
            }
        }
    }

    private void analisaTermo() {
        analisaFator();
        String simboloAtual = tokens.get(i).getSimbolo();
        while ((simboloAtual.equals(Operadores.MULTIPLICACAO)) || simboloAtual.equals(IDs.Sdiv.toString()) || simboloAtual.equals(IDs.sse.toString())) {
            i++;
            analisaFator();
        }
    }

    private void analisaChamadaProcedimento(){

    }

    private void analisaChamadaFuncao(){

    }

}