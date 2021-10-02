/*
 * Copyright (c) 2021 created by Computer Engineering students (Cezar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseSintatica;

import Utils.Operadores;
import analiseLexical.*;

import java.util.LinkedList;

public class Sintatico {
    LinkedList<Token> tokens;
   // public int rotulo;
    private int i = 0;
    //private TabelaDeSimbolos tabelaDeSimbolos;

    public Sintatico(String caminhoDoArquivo) throws Exception {
        Lexical lexical = new Lexical(caminhoDoArquivo);
        //tabelaDeSimbolos = new TabelaDeSimbolos();
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

    public void analisadorSintatico() throws Exception {
       // rotulo = 1;
        if (tokens.get(i).getSimbolo().equals(IDs.sprograma.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                //tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), -1, "", "");
                i++;
                if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                     analisaBloco();
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.Sponto.toString())) {
                        if (i == tokens.size()-1) {
                            System.out.println("SUCESSO!");
                        } else {
                            throw new Exception("ERRO! - Ultrapassou o tamanho contido na lista!");
                        }
                    } else {
                        throw new Exception("ERRO! - Esperado um ponto '.'!");
                    }
                } else {
                    throw new Exception("ERRO! - Esperado um ponto e vírgula ';'!");
                }
            } else {
                throw new Exception("ERRO! - Esperado um identificador!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um programa!");
        }
    }

    public void analisaBloco() throws Exception {
        i++;
        analisaEtVariaveis();
        analisaSubrotinas();
        analisaComandos();
    }

    public void analisaEtVariaveis() throws Exception {
        if (tokens.get(i).getSimbolo().equals(IDs.svar.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                while (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                    analisaVariaveis();
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                        i++;
                    } else {
                        throw new Exception("ERRO! - Esperado um ponto e virgula ';'!");
                    }
                }
            } else {
                throw new Exception("ERRO! - Esperado um identificador!");
            }
        }
    }

    public void analisaVariaveis() throws Exception {
        do {
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
             //   if (!pesquisaDuplicadoVarTabela()) {
                    //insereTabela(tokens.get(i).getLexema());
                    i++;
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString()) || tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString())) {
                            i++;
                            if (tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                                //throw new Exception("ERRO! - Não é esperado dois pontos ':'!");
                                throw new Exception("ERRO! - Para o elemento: '" + tokens.get(i-1).getLexema() + "' não é esperado ser seguido por dois pontos ':'!");
                            }
                        }
                    } else {
                        throw new Exception("ERRO! - Esperado uma vírgula ',' ou dois pontos ':'!");
                    }
             //   } else {
             //       System.out.println("analisaVariaveis - ERRO na inserção da tabela - ");
            //    }
            } else {
                throw new Exception("ERRO! - Esperado um identificador!");
            }

        } while (!tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS));
        i++;
        analisaTipo();
    }

/*    public boolean pesquisaDuplicadoVarTabela() {
        return true;
    }
*/
    public void analisaTipo() throws Exception {
        if (!tokens.get(i).getSimbolo().equals(IDs.sinteiro.toString()) && !tokens.get(i).getSimbolo().equals(IDs.Sbooleano.toString())) {
            throw new Exception("ERRO! - Esperado um inteiro ou booleano!");
        } else {
            //colocaTipoTabela(tokens.get(i).getLexema());
        }
        i++;
    }

    public void analisaSubrotinas() throws Exception {
        //int auxRot;
        int flag;
        flag = 0;
        if(tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString()) || tokens.get(i).getSimbolo().equals(IDs.sfuncao.toString())) {
            /*
             * auxRot = rotulo
             * gera()
             * rotulo ++
             * flag = 1
             * */
        }

        while (tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString()) || tokens.get(i).getSimbolo().equals(IDs.sfuncao.toString())) {
            if(tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString())){
                analisaDeclaracaoProcedimento();
            }else{
                analisaDeclaracaoFuncao();
            }

            if(tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                i++;
            } else {
                throw new Exception("ERRO! - Esperado um ponto e vírgula ';'!");
            }
        }

        /*if(flag == 1){
            gera()
        }*/
    }

    private void analisaDeclaracaoProcedimento() throws Exception {
        i++;
        if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
            // pesquisaDeclaracaoProcedimentoTabela(tokens.lexema)
            // insereTabela()
            // Se não encontrou
            i++;
            if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                analisaBloco();
            } else {
                throw new Exception("ERRO! - Esperado um ponto e vírgula ';'!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um identificador!");
        }
        // Demsempilha ou Volta Nível
    }

    private void analisaDeclaracaoFuncao() throws Exception {
        i++;
        if(tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())){
            i++;
            if(tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS.toString())){
                i++;
                if(tokens.get(i).getSimbolo().equals(IDs.sinteiro.toString()) || tokens.get(i).getSimbolo().equals(IDs.Sbooleano.toString())){
                    i++;
                    if(tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())){
                        analisaBloco();
                    }
                } else {
                    throw new Exception("ERRO! - Esperado um inteiro ou booleano!");
                }
            } else {
                throw new Exception("ERRO! - Esperado um dois pontos ';'!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um identificador!");
        }
    }

    public void analisaComandos() throws Exception {
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
                    throw new Exception("ERRO! - Esperado um ponto e vírgula!");
                }
            }
            i++;
        } else {
            throw new Exception("ERRO! - Esperado um inicio no no lugar do simbolo: " + tokens.get(i).getSimbolo());
        }
    }

    public void analisaComandoSimples() throws Exception {
        if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
            analisaAtribuicaoChamadaProcedimento();
        } else if (tokens.get(i).getSimbolo().equals(IDs.sse.toString())) {
            analisaSe();
        } else if (tokens.get(i).getSimbolo().equals(IDs.senquanto.toString())) {
            analisaEnquanto();
        } else if (tokens.get(i).getSimbolo().equals(IDs.sleia.toString())) {
            analisaLeia();
        } else if (tokens.get(i).getSimbolo().equals(IDs.sescreva.toString())) {
            analisaEscreva();
        } else {
            analisaComandos();
        }
    }

    private void analisaAtribuicaoChamadaProcedimento() throws Exception {
        i++;
        if (tokens.get(i).getSimbolo().equals(Operadores.ATRIBUICAO)) {
            i++;
            analisaExpressao();
            //analisaAtribuicao();
        } else {
            //i++;
            //chamadaProcedimento();
        }
    }

    private void analisaSe() throws Exception {
        i++;
        analisaExpressao();
        if (tokens.get(i).getSimbolo().equals(IDs.sentao.toString())) {
            i++;
            analisaComandoSimples();
            if (tokens.get(i).getSimbolo().equals(IDs.ssenao.toString())) {
                i++;
                analisaComandoSimples();
            }
        } else {
            throw new Exception("ERRO! - Esperado um então!");
        }
    }

    private void analisaEnquanto() throws Exception {
        int auxRot1, auxRot2;

       // auxRot1 = rotulo;
        /*
GERA
*/

      //  rotulo = rotulo + 1;


        i++;
        analisaExpressao();

        if (tokens.get(i).getSimbolo().equals(IDs.sfaca.toString())) {
           // auxRot2 = rotulo;
            //GERA
           // rotulo = rotulo + 1;
            i++;
            analisaComandoSimples();
            //GERA
            //GERA
        } else {
            throw new Exception("ERRO! - Esperado um faca!");
        }
    }

    private void analisaLeia() throws Exception {
        i++;
        if (tokens.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                //if (pesquisaDeclaracaoVariavelTabela(tokens.get(i).getLexema())) { //OBS: pesquisa em toda a tabela
                i++;
                if (tokens.get(i).getSimbolo().equals(String.valueOf(Pontuacoes.sfecha_parenteses.toString()))) {
                    i++;
                } else {
                    throw new Exception("ERRO! - Esperado um fecha parenteses ')'!");
                }
                // }
            } else {
                throw new Exception("ERRO! - Esperado um identificador!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um abre parenteses '('!");
        }
    }

    private void analisaEscreva() throws Exception {
        i++;

        if (tokens.get(i).getSimbolo().equals(String.valueOf(Pontuacoes.sabre_parenteses))) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                //if (pesquisaDeclaracaoVariavelTabela(tokens.get(i).getLexema())) {
                    i++;
                    if (tokens.get(i).getSimbolo().equals(String.valueOf(Pontuacoes.sfecha_parenteses))) {
                        i++;
                    } else {
                        throw new Exception("ERRO! - Esperado um fecha parenteses ')'!");
                    }
               // } else {
                //    System.out.println("analisaEscreva - ERRO - Esperado um!");
               // }
            } else {
                throw new Exception("ERRO! - Esperado um Identificador!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um abre parenteses '('!");
        }
    }

    private void analisaExpressao() throws Exception {
        analisaExpressaoSimples();

        if (tokens.get(i).getSimbolo().equals(OperadoresRelacional.Smaior.toString()) ||
                tokens.get(i).getSimbolo().equals(OperadoresRelacional.Smaiorig.toString()) ||
                tokens.get(i).getSimbolo().equals(OperadoresRelacional.Sig.toString()) ||
                tokens.get(i).getSimbolo().equals(OperadoresRelacional.Smenor.toString()) ||
                tokens.get(i).getSimbolo().equals(OperadoresRelacional.Smenorig.toString()) ||
                tokens.get(i).getSimbolo().equals(OperadoresRelacional.Sdif.toString())) {
            i++;
            analisaExpressaoSimples();
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if (tokens.get(i).getSimbolo().equals(Operadores.MAIS) || tokens.get(i).getSimbolo().equals(Operadores.MENOS)) {
            i++;
        }
        analisaTermo();
        while (tokens.get(i).getSimbolo().equals(Operadores.MAIS) || tokens.get(i).getSimbolo().equals(Operadores.MENOS) || tokens.get(i).getSimbolo().equals(IDs.Sou.toString())) {
            i++;

            analisaTermo();
        }
    }

    private boolean pesquisaDeclaracaoVariavelTabela(String lexema) {
        return true;
    }

    private void analisaFator() throws Exception {
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
            i++;
        } else {
            if (tokens.get(i).getSimbolo().equals(Operadores.NUMERO)) {
                i++;
            } else {
                if (tokens.get(i).getSimbolo().equals(IDs.Snao.toString())) {
                    i++;
                    analisaFator();
                } else if (tokens.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
                        // Expressão está entre parentes
                        //System.out.println("Expressão está entre parentes");
                        i++;
                        analisaExpressao();
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.sfecha_parenteses.toString())) {
                            i++;
                        } else {
                            throw new Exception("ERRO! - Esperado um fecha parenteses ')'!");
                        }
                    } else if (tokens.get(i).getSimbolo().equals(IDs.Sverdadeiro.toString()) || tokens.get(i).getSimbolo().equals(IDs.Sfalso.toString())) {
                        i++;
                    } else {
                    throw new Exception("ERRO! - Esperado um verdadeiro ou falso!");
                }
            }
        }
    }

    private void analisaTermo() throws Exception {
        analisaFator();
        while ((tokens.get(i).getSimbolo().equals(Operadores.MULTIPLICACAO)) || tokens.get(i).getSimbolo().equals(IDs.Sdiv.toString()) || tokens.get(i).getSimbolo().equals(IDs.Se.toString())) {
            i++;
            analisaFator();
        }
    }

    private void analisaChamadaProcedimento() {

    }

    private void analisaChamadaFuncao() {

    }

}