/*
 * Copyright (c) 2021 created by Computer Engineering students (Cesar Marrote Manzano,
 * Christopher de Oliveira Souza and Murilo de Paula Araujo) at PUC-Campinas.
 *
 * All rights reserved.
 */

package analiseSintatica;

import Utils.AnalisadorExpressao;
import Utils.Conversor;
import Utils.Objeto;
import Utils.Operadores;
import analiseLexical.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Sintatico {
    LinkedList<Token> tokens;
    public int rotulo;
    public int var;
    private int i = 0;
    private TabelaDeSimbolos tabelaDeSimbolos;
    Lexical lexical;
    private List<Objeto> objetoList;
    Conversor conversor;
    AnalisadorExpressao analisadorExpressao;
    FileWriter arq;
    PrintWriter gravarArq;
    int varLocal=0;

    public Sintatico() throws Exception {
        lexical = new Lexical();
        tabelaDeSimbolos = new TabelaDeSimbolos();
        conversor= new Conversor();
        analisadorExpressao= new AnalisadorExpressao();
        arq = new FileWriter("./gerador.txt");
        gravarArq = new PrintWriter(arq);
        rotulo=1;
        var=0;
        varLocal=0;
    }

    public void limpar() {
        rotulo=1;
        var=0;
        tokens=null;
        i=0;
        lexical.setTokens(new LinkedList<>());
        lexical.setI(0);
        lexical.setLinha(1);
        lexical.setColuna(-1);
        tabelaDeSimbolos=new TabelaDeSimbolos();
    }

    public void run () throws Exception {
            lexical.analisadorLexical();
            tokens = lexical.getTokens();
            for (Token token : tokens) {
                System.out.print(token.getLexema() + " -> ");
                System.out.println(token.getSimbolo());
            }

        analisadorSintatico();
    }

    public void analisadorSintatico() throws Exception {
       // rotulo = 1;
        if (tokens.get(i).getSimbolo().equals(IDs.sprograma.toString())) {
            gera(completar8(""),completar8("START"),completar8(""),completar8(""));
            gera(completar8(""),completar8("ALLOC"),completar8(String.valueOf(var)),completar8(String.valueOf(++var)));
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), "", "nomedeprograma", "");
                i++;
                if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                     analisaBloco();
                    if (i != tokens.size() && tokens.get(i).getSimbolo().equals(Pontuacoes.Sponto.toString())) {
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
        int aux=tabelaDeSimbolos.desempilhaTudo();
        gera(completar8(""), completar8("DALLOC"),completar8(String.valueOf(var-aux)), completar8(String.valueOf(aux)));
        gera(completar8(""),completar8("DALLOC"),completar8(String.valueOf(0)),completar8(String.valueOf(1)));
        gera(completar8(""),completar8("HLT"),completar8(""),completar8(""));
        arq.close();

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
        if(varLocal>0) {
            gera(completar8(""), completar8("ALLOC"), completar8(String.valueOf(var)), completar8(String.valueOf(varLocal)));
            var += varLocal;
            varLocal = 0;
        }
    }

    public void analisaVariaveis() throws Exception {
        do {
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (!tabelaDeSimbolos.pesquisarDuplicidade(tokens.get(i).getLexema())) {
                    tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), "", "variavel", "");
                    i++;
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString()) || tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.Svirgula.toString())) {
                            i++;
                            if (tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                                throw new Exception("ERRO! - Para o elemento: '" + tokens.get(i-1).getLexema() + "' não é esperado ser seguido por dois pontos ':'!");
                            }
                        }
                    } else {
                        throw new Exception("ERRO! - Esperado uma vírgula ',' ou dois pontos ':'!");
                    }
                } else {
                    throw new Exception("analisaVariaveis - ERRO na inserção da tabela - ");
                }
            } else {
                throw new Exception("ERRO! - Esperado um identificador!");
            }

        } while (!tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS));
        i++;
        analisaTipo();
    }

//    public boolean pesquisaDuplicadoVarTabela() {
//        return true;
//    }

    public void analisaTipo() throws Exception {
        if (!tokens.get(i).getSimbolo().equals(IDs.sinteiro.toString()) && !tokens.get(i).getSimbolo().equals(IDs.Sbooleano.toString())) {
            throw new Exception("ERRO! - Esperado um tipo inteiro ou booleano!");
        }  else {
           int contador= tabelaDeSimbolos.colocaTipo(tokens.get(i).getLexema(),var);
           varLocal+=contador;
        }
        i++;
    }

    public void analisaSubrotinas() throws Exception {
        Integer auxRot=null, flag=0;

        if(tokens.get(i).getSimbolo().equals(IDs.sprocedimento.toString()) || tokens.get(i).getSimbolo().equals(IDs.sfuncao.toString())) {
             auxRot = rotulo;
             gera(completar8(""),completar8("JMP"),completar8(String.valueOf(rotulo)),completar8(""));
             rotulo ++;
             flag = 1;
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

        if(flag == 1){
            gera(completar8(String.valueOf(auxRot)),completar8("NULL"),completar8(""),completar8(""));
        }
    }

    private void analisaDeclaracaoProcedimento() throws Exception {
        i++;
        String galho="L";
        if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
            if(!tabelaDeSimbolos.pesquisaGlobalProcedimento(tokens.get(i).getLexema())) {
                tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), galho, "procedimento", String.valueOf(rotulo));
                gera(completar8(String.valueOf(rotulo)),completar8("NULL"),completar8(""),completar8(""));
                rotulo++;
                i++;
                if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                    analisaBloco();
                } else {
                    throw new Exception("ERRO! - Esperado um ponto e vírgula ';'!");
                }
            } else {
                throw new Exception("ERRO! - procedimento já declarado!");
            }
        } else {
            throw new Exception("ERRO! - Esperado um identificador!");
        }
        int contador=tabelaDeSimbolos.desempilhaMarca();
        if(contador>0) {
            gera(completar8(""), completar8("DALLOC"),completar8(String.valueOf(var-contador)),completar8(String.valueOf(contador)));
            var -= contador;
        }
        gera(completar8(""),completar8("RETURN"),completar8(""),completar8(""));
        // Demsempilha ou Volta Nível
    }

    private void analisaDeclaracaoFuncao() throws Exception {
        i++;
        String galho="L";
        if(tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())){
            if(!tabelaDeSimbolos.pesquisaGlobalFuncao(tokens.get(i).getLexema())) {
                tabelaDeSimbolos.insereTabela(tokens.get(i).getLexema(), galho, "", String.valueOf(rotulo));
                gera(completar8(String.valueOf(rotulo)),completar8("NULL"),completar8(""),completar8(""));
                rotulo++;
                i++;
                if (tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS)) {
                    i++;
                    if (tokens.get(i).getSimbolo().equals(IDs.sinteiro.toString()) || tokens.get(i).getSimbolo().equals(IDs.Sbooleano.toString())) {
                        if(tokens.get(i).getSimbolo().equals(IDs.sinteiro.toString()))
                            tabelaDeSimbolos.alteraTipoTopo("função inteiro");
                        else
                            tabelaDeSimbolos.alteraTipoTopo("função boolean");
                        i++;
                        if (tokens.get(i).getSimbolo().equals(Pontuacoes.sponto_virgula.toString())) {
                            analisaBloco();
                        }
                    } else {
                        throw new Exception("ERRO! - Esperado um inteiro ou booleano!");
                    }
                } else {
                    throw new Exception("ERRO! - Esperado um dois pontos ';'!");
                }
            } else {
                throw new Exception("ERRO! - Função já declarada !");
            }
        } else {
            throw new Exception("ERRO! - Esperado um identificador!");
        }
        int contador=tabelaDeSimbolos.desempilhaMarca();
        if(contador>0) {
            gera(completar8(""), completar8("DALLOC"),completar8( String.valueOf(var-contador)),completar8( String.valueOf(contador)));
            var -= contador;
        }
        gera(completar8(""),completar8("RETURN"),completar8(""),completar8(""));
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
                    if(i+1 != tokens.size()-1 && tokens.get(i).getSimbolo().equals(Operadores.DOIS_PONTOS) && !tokens.get(i).getSimbolo().equals(OperadoresRelacional.Sig.toString())) {
                        throw new Exception("ERRO! - Por favor, utilizar ':=' para atribuição!");
                    } else{
                        throw new Exception("ERRO! - Esperado terminar com um ';' ou para o elemento: '" + tokens.get(i-1).getLexema() + "'\n não é esperado ser seguido por: '" + tokens.get(i).getLexema() + "'!");
                    }

                }
            }
            i++;
        } else {
            throw new Exception("ERRO! - Esperado um inicio ou para o elemento: '" + tokens.get(i-1).getLexema() + "'\n não é esperado ser seguido por: '" + tokens.get(i).getLexema()+ "'!");
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
            int inicio=i;
            objetoList=new ArrayList<>();
            List<Token> expressao= new ArrayList<>();
            analisaExpressao();
            String tipoRetorno= null;
            expressao=tokens.subList(inicio,i);
            for(Objeto objeto:objetoList) {
                expressao.get(objeto.getPosicao()-inicio).setLexema(objeto.getValor());
                expressao.get(objeto.getPosicao()-inicio).setSimbolo(objeto.getSimbolo());
            }

            expressao=conversor.converterPosFixa(expressao);
            tipoRetorno=analisadorExpressao.analisarExpressao(expressao,tabelaDeSimbolos);
            if(!tipoRetorno.equals(tabelaDeSimbolos.getTipo(tokens.get(inicio-2).getLexema()))){
                throw new Exception("Erro ! atribuição de tipos inválidos");
            }

            geraCodigoExpressao(expressao);
            String aux=tabelaDeSimbolos.pesquisaGlobalVariavelEndereco(tokens.get(inicio-2).getLexema());
            if(aux.isEmpty())
                aux=String.valueOf(0);

            gera(completar8(""),completar8("STR"),completar8(aux),completar8(""));
            //seguir
            //analisaAtribuicao();
        } else {
            //System.out.println("\n"+tokens.get(i-1).getLexema());
            if(!tabelaDeSimbolos.pesquisaGlobal(tokens.get(i-1).getLexema())){
                throw new Exception("ERRO! - Procedimento não declarado");
            }
            gera(completar8(""),completar8("CALL"),completar8(tabelaDeSimbolos.pesquisaGlobalProcedimentoEndereco(tokens.get(i-1).getLexema())),completar8(""));
            //i++;
            //chamadaProcedimento();
        }
    }

    private void analisaSe() throws Exception {
        int auxRot1 = rotulo, auxRot2;
        i++;

        int inicio=i;
        objetoList=new ArrayList<>();
        List<Token> expressao= new ArrayList<>();
        analisaExpressao();
        String tipoRetorno= null;
        expressao=tokens.subList(inicio,i);
        for(Objeto objeto:objetoList) {
            expressao.get(objeto.getPosicao()-inicio).setLexema(objeto.getValor());
            expressao.get(objeto.getPosicao()-inicio).setSimbolo(objeto.getSimbolo());
        }

        expressao=conversor.converterPosFixa(expressao);
        tipoRetorno=analisadorExpressao.analisarExpressao(expressao,tabelaDeSimbolos);
        if(!tipoRetorno.equals("B")){
            throw new Exception("Erro ! tipo errado para comando SE");
        }
        geraCodigoExpressao(expressao);

        if (tokens.get(i).getSimbolo().equals(IDs.sentao.toString())) {
            gera(completar8(""),completar8("JPMF"),completar8(String.valueOf(rotulo)),completar8(""));
            rotulo++;
            i++;
            analisaComandoSimples();
            if (tokens.get(i).getSimbolo().equals(IDs.ssenao.toString())) {
                auxRot2=rotulo;
                gera(completar8(""),completar8("JMP"),completar8(String.valueOf(rotulo)),completar8(""));
                rotulo++;
                gera(completar8(String.valueOf(auxRot1)),completar8("NULL"),completar8(""),completar8(""));
                i++;
                analisaComandoSimples();
                gera(completar8(String.valueOf(auxRot2)),completar8("NULL"),completar8(""),completar8(""));

            }
        } else {
            throw new Exception("ERRO! - Esperado um então!");
        }
    }

    private void analisaEnquanto() throws Exception {
         int auxRot1 = rotulo, auxRot2;
         gera(completar8(String.valueOf(rotulo)),completar8("NULL"),completar8(""),completar8(""));
         rotulo++;

        i++;
        int inicio=i;
        objetoList=new ArrayList<>();
        List<Token> expressao= new ArrayList<>();
        analisaExpressao();
        String tipoRetorno= null;
        expressao=tokens.subList(inicio,i);
        for(Objeto objeto:objetoList) {
            expressao.get(objeto.getPosicao()-inicio).setLexema(objeto.getValor());
            expressao.get(objeto.getPosicao()-inicio).setSimbolo(objeto.getSimbolo());
        }

        expressao=conversor.converterPosFixa(expressao);
        tipoRetorno=analisadorExpressao.analisarExpressao(expressao,tabelaDeSimbolos);
        if(!tipoRetorno.equals("B")){
            throw new Exception("Erro ! tipo errado para comando ENQUANTO");
        }
        geraCodigoExpressao(expressao);

        if (tokens.get(i).getSimbolo().equals(IDs.sfaca.toString())) {
            auxRot2 = rotulo;
            gera(completar8(""),completar8("JPMF"),completar8(String.valueOf(rotulo)),completar8(""));
           rotulo = rotulo + 1;
            i++;
            analisaComandoSimples();
            gera(completar8(""),completar8("JMP"),completar8(String.valueOf(auxRot1)),completar8(""));
            gera(completar8(String.valueOf(auxRot2)),completar8("NULL"),completar8(""),completar8(""));
        } else {
            throw new Exception("ERRO! - Esperado um faca ou algum problema na condição do enquanto!");
        }
    }

    private void analisaLeia() throws Exception {
        i++;
        if (tokens.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
            i++;
            if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
                if (tabelaDeSimbolos.pesquisaGlobalVariavel(tokens.get(i).getLexema())) { //OBS: pesquisa em toda a tabela
                    gera(completar8(""),completar8("RD"),completar8(""),completar8(""));
                    gera(completar8(""),completar8("STR LEIA"),completar8(tabelaDeSimbolos.pesquisaGlobalVariavelEndereco(tokens.get(i).getLexema())),String.valueOf(""));
                    i++;
                if (tokens.get(i).getSimbolo().equals(String.valueOf(Pontuacoes.sfecha_parenteses.toString()))) {
                    i++;
                } else {
                    throw new Exception("ERRO! - Esperado um fecha parenteses ')'!");
                    }
                } else {
                    throw new Exception("ERRO! - variável não encontrada!");
                }
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
                if (tabelaDeSimbolos.pesquisaGlobalVariavel(tokens.get(i).getLexema())) {
                    gera(completar8(""),completar8("LDV"),completar8(tabelaDeSimbolos.pesquisaGlobalVariavelEndereco(tokens.get(i).getLexema())),completar8(""));
                    gera(completar8(""),completar8("PRN"),completar8(""),completar8(""));
                    i++;
                    if (tokens.get(i).getSimbolo().equals(String.valueOf(Pontuacoes.sfecha_parenteses))) {
                        i++;
                    } else {
                        throw new Exception("ERRO! - Esperado um fecha parenteses ')'!");
                    }
                } else {
                    throw new Exception("ERRO! - variável não encontrada tchum");
                }
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
            if(tokens.get(i).getSimbolo().equals(Operadores.MAIS)){
                objetoList.add(new Objeto(i,"+u",Operadores.POSITIVO));
            } else {
                objetoList.add(new Objeto(i,"-u",Operadores.NEGATIVO));
            }
            i++;
        }
        analisaTermo();
        while (tokens.get(i).getSimbolo().equals(Operadores.MAIS) || tokens.get(i).getSimbolo().equals(Operadores.MENOS) || tokens.get(i).getSimbolo().equals(IDs.Sou.toString())) {
            i++;
            analisaTermo();
        }
    }

/*
    private boolean pesquisaDeclaracaoVariavelTabela(String lexema) {
        return true;
    }
*/

    private void analisaFator() throws Exception {
        if (tokens.get(i).getSimbolo().equals(IDs.Sidentificador.toString())) {
            Simbolo simbolo=tabelaDeSimbolos.pesquisaLocal(tokens.get(i).getLexema());
            if(simbolo!=null) {
            if((simbolo.getTipo().equals("função inteiro") ||  simbolo.getTipo().equals("função booleano"))) {
                i++;
            } else {
                i++;
            }
           /* if(pesquisaTabela()){
                if(){

                   analisaChamadaFuncao();
                } else {
                i++;
            } else{
            System.out.println("ERROU!");
        }
       */} else {
                throw new Exception("ERRO! - simbolo não declarado!");
            }
        } else {
            if (tokens.get(i).getSimbolo().equals(Operadores.NUMERO)) {
                i++;
            } else {
                if (tokens.get(i).getSimbolo().equals(IDs.Snao.toString())) {
                    i++;
                    analisaFator();
                } else if (tokens.get(i).getSimbolo().equals(Pontuacoes.sabre_parenteses.toString())) {
                    // Expressão está entre parentes
                    i++;
                    analisaExpressao();
                    if (tokens.get(i).getSimbolo().equals(Pontuacoes.sfecha_parenteses.toString())) {
                        i++;
                    } else {
                        if(tokens.get(i-1).getSimbolo().equals(Operadores.NUMERO) && tokens.get(i).getSimbolo().equals(Pontuacoes.Sponto.toString())) {
                            throw new Exception("ERRO! - Não é esperado números decimais ou com ponto '.'!");
                        } else{
                            throw new Exception("ERRO! - Esperado um fecha parenteses ')'\n ou para o elemento: '" + tokens.get(i-1).getLexema() + "' não é esperado ser seguido por: '" + tokens.get(i).getLexema() + "'!");
                        }
                    }
                } else if (tokens.get(i).getSimbolo().equals(IDs.Sverdadeiro.toString()) || tokens.get(i).getSimbolo().equals(IDs.Sfalso.toString())) {
                    i++;
                } else {
                    throw new Exception("ERRO! - Operador relacional ou lógico inválido, para o elemento: '" + tokens.get(i-1).getLexema() + "' \n não é esperado ser seguido por: '" + tokens.get(i).getLexema()+ "'!");
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

    public Lexical getLexical() {
        return lexical;
    }

    public void gera(String texto, String texto2,String texto3,String texto4){
        gravarArq.println(texto+texto2+texto3+texto4);
    }

    private String completar8(String string){
        while (string.length()!=8){
            string+=" ";
        }
        return string;
    }

    private void geraCodigoExpressao(List<Token> expressao)
    {
        expressao.forEach(token -> {
            if(token.getSimbolo().equals(IDs.Sidentificador.toString())){
                if(tabelaDeSimbolos.pesquisaGlobalVariavel(token.getLexema()))
                    gera(completar8(""),completar8("LDV"),tabelaDeSimbolos.pesquisaGlobalVariavelEndereco(token.getLexema()),completar8(""));
                else if(tabelaDeSimbolos.pesquisaGlobalFuncao(token.getLexema())) {
                    gera(completar8(""),completar8("CALL"),tabelaDeSimbolos.pesquisaGlobalFuncaoEndereco(token.getLexema()),completar8(""));
                    gera(completar8(""),completar8("LDV"),completar8("0"),completar8(""));

                }
            } else if(token.getSimbolo().equals(Operadores.NUMERO)){
                gera(completar8(""),completar8("LDC"),completar8(token.getLexema()),completar8(""));
            } else if(token.getSimbolo().equals(Operadores.MAIS)){
                gera(completar8(""),completar8("ADD"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(Operadores.MENOS)){
                gera(completar8(""),completar8("SUB"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(Operadores.MULTIPLICACAO)){
                gera(completar8(""),completar8("MULT"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(Operadores.NEGATIVO)){
                gera(completar8(""),completar8("INV"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(IDs.Se.toString())){
                gera(completar8(""),completar8("AND"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(IDs.Sou.toString())){
                gera(completar8(""),completar8("OR"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(IDs.Snao.toString())){
                gera(completar8(""),completar8("NEG"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Smenor.toString())){
                gera(completar8(""),completar8("CME"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Smaior.toString())){
                gera(completar8(""),completar8("CMA"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Sig.toString())){
                gera(completar8(""),completar8("CEQ"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Sdif.toString())){
                gera(completar8(""),completar8("CDIF"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Smenorig.toString())){
                gera(completar8(""),completar8("CMEQ"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(OperadoresRelacional.Smaiorig.toString())){
                gera(completar8(""),completar8("CMAQ"),completar8(""),completar8(""));
            } else if(token.getSimbolo().equals(IDs.Sverdadeiro.toString())){
                gera(completar8(""),completar8("LDC"),completar8("1"),completar8(""));
            } else if(token.getSimbolo().equals(IDs.Sfalso.toString())) {
                gera(completar8(""),completar8("LDC"),completar8("0"),completar8(""));
            }
        });
    }


/*
    private void analisaChamadaProcedimento() {

    }

    private void analisaChamadaFuncao() {

    }
*/
}